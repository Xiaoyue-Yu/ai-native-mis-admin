import axios, { type AxiosResponse, type InternalAxiosRequestConfig } from "axios";
import { ElMessage } from "element-plus";
import qs from "qs";
import type { ApiResult } from "@/types/api";
import { getToken } from "@/utils/auth";

const service = axios.create({
  baseURL: "/proxy",
  timeout: 50000,
});

service.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    config.paramsSerializer = (params) =>
      qs.stringify(params, { arrayFormat: "repeat", encode: true });

    const skipToken = (config.headers || {}).isToken === false;
    const token = getToken();
    if (token && !skipToken) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    config.headers.Accept = "application/json";
    if (!config.headers["Content-Type"]) {
      config.headers["Content-Type"] = "application/json";
    }
    return config;
  },
  (error) => Promise.reject(error)
);

service.interceptors.response.use(
  (res: AxiosResponse) => {
    const contentType = String(res.headers["content-type"] || "");
    if (
      contentType.includes(
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      )
    ) {
      return res.data;
    }
    return res.data;
  },
  (error) => {
    const response = error.response;
    const data = response?.data as ApiResult | undefined;
    const payload = {
      status: response?.status,
      code: data?.code,
      message: (data?.message as string) || error.message || "请求失败",
    };
    if (response?.status === 401) {
      window.location.href = `/login?redirect=${encodeURIComponent(
        window.location.pathname + window.location.search
      )}`;
      return Promise.reject(payload);
    }
    ElMessage.error(payload.message);
    return Promise.reject(payload);
  }
);

export default service;

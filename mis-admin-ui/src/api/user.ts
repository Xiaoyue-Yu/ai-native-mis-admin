import request from "./request";
import type { ApiResult } from "@/types/api";
import type {
  UserCreatePayload,
  UserDetail,
  UserUpdatePayload,
} from "@/types/user";

export const createUser = (
  payload: UserCreatePayload
): Promise<ApiResult<number>> => request.post("/user/add", payload);

export const updateUser = (
  payload: UserUpdatePayload
): Promise<ApiResult<null>> => request.put("/user/update", payload);

export const deleteUser = (id: number): Promise<ApiResult<null>> =>
  request.delete(`/user/delete/${id}`);

export const fetchUserDetail = (id: number): Promise<ApiResult<UserDetail>> =>
  request.get(`/user/detail/${id}`);

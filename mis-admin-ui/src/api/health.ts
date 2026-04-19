import request from "./request";
import type { ApiResult } from "@/types/api";

export const fetchPing = (): Promise<ApiResult<string>> => request.get("/ping");

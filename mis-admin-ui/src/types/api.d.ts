/** Backend {@code Result<T>} (api-design.md). */
export interface ApiResult<T = unknown> {
  code: number;
  message: string;
  data: T;
}

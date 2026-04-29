export interface UserDetail {
  id: number;
  username: string;
  nickname: string;
  email: string;
  phone: string;
  avatar: string;
  sex: number;
  status: number;
  deptId: number | null;
  remark: string;
  roleIds: number[];
  createTime?: string;
  updateTime?: string;
}

export interface UserCreatePayload {
  username: string;
  password: string;
  nickname: string;
  email: string;
  phone: string;
  avatar: string;
  sex: number;
  status: number;
  deptId: number | null;
  remark: string;
  roleIds: number[];
}

export interface UserUpdatePayload
  extends Omit<UserCreatePayload, "username" | "password"> {
  id: number;
}

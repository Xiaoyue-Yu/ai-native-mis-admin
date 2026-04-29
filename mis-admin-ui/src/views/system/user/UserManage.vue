<script setup lang="ts">
import { reactive, ref } from "vue";
import {
  ElMessage,
  ElMessageBox,
  type FormInstance,
  type FormRules,
} from "element-plus";
import {
  createUser,
  deleteUser,
  fetchUserDetail,
  updateUser,
} from "@/api/user";
import type {
  UserCreatePayload,
  UserDetail,
  UserUpdatePayload,
} from "@/types/user";

type UserRow = Pick<
  UserDetail,
  "id" | "username" | "nickname" | "email" | "phone" | "status" | "roleIds"
>;

const roleOptions = [
  { label: "超级管理员", value: 1 },
  { label: "普通管理员", value: 2 },
  { label: "普通用户", value: 3 },
];

const tableData = ref<UserRow[]>([]);
const formRef = ref<FormInstance>();
const dialogVisible = ref(false);
const dialogMode = ref<"create" | "edit">("create");
const dialogLoading = ref(false);
const detailLoading = ref(false);
const deleteLoadingId = ref<number | null>(null);
const editIdInput = ref<number | null>(null);

const form = reactive<UserCreatePayload & { id?: number }>({
  id: undefined,
  username: "",
  password: "",
  nickname: "",
  email: "",
  phone: "",
  avatar: "",
  sex: 0,
  status: 1,
  deptId: null,
  remark: "",
  roleIds: [],
});

const rules: FormRules<typeof form> = {
  username: [
    { required: true, message: "请输入用户名", trigger: "blur" },
    { min: 2, max: 50, message: "用户名长度为2-50个字符", trigger: "blur" },
  ],
  password: [
    { required: true, message: "请输入密码", trigger: "blur" },
    { min: 8, max: 64, message: "密码长度为8-64个字符", trigger: "blur" },
  ],
  nickname: [{ required: true, message: "请输入昵称", trigger: "blur" }],
  email: [{ type: "email", message: "邮箱格式不正确", trigger: "blur" }],
  phone: [
    { pattern: /^$|^1\d{10}$/, message: "手机号格式不正确", trigger: "blur" },
  ],
};

const resetForm = () => {
  form.id = undefined;
  form.username = "";
  form.password = "";
  form.nickname = "";
  form.email = "";
  form.phone = "";
  form.avatar = "";
  form.sex = 0;
  form.status = 1;
  form.deptId = null;
  form.remark = "";
  form.roleIds = [];
};

const openCreateDialog = () => {
  dialogMode.value = "create";
  resetForm();
  dialogVisible.value = true;
};

const openEditDialog = async (id?: number) => {
  const targetId = id ?? editIdInput.value;
  if (!targetId) {
    ElMessage.warning("请输入要编辑的用户ID");
    return;
  }

  dialogMode.value = "edit";
  dialogVisible.value = true;
  detailLoading.value = true;
  try {
    const res = await fetchUserDetail(targetId);
    const data = res.data;
    form.id = data.id;
    form.username = data.username;
    form.password = "";
    form.nickname = data.nickname;
    form.email = data.email;
    form.phone = data.phone;
    form.avatar = data.avatar;
    form.sex = data.sex;
    form.status = data.status;
    form.deptId = data.deptId;
    form.remark = data.remark;
    form.roleIds = [...(data.roleIds || [])];
  } finally {
    detailLoading.value = false;
  }
};

const submitForm = async () => {
  const valid = await formRef.value?.validate().catch(() => false);
  if (!valid) {
    return;
  }

  dialogLoading.value = true;
  try {
    if (dialogMode.value === "create") {
      const payload: UserCreatePayload = {
        username: form.username,
        password: form.password,
        nickname: form.nickname,
        email: form.email,
        phone: form.phone,
        avatar: form.avatar,
        sex: form.sex,
        status: form.status,
        deptId: form.deptId,
        remark: form.remark,
        roleIds: [...form.roleIds],
      };
      const res = await createUser(payload);
      tableData.value.unshift({
        id: res.data,
        username: payload.username,
        nickname: payload.nickname,
        email: payload.email,
        phone: payload.phone,
        status: payload.status,
        roleIds: [...payload.roleIds],
      });
      ElMessage.success("用户新增成功");
    } else if (form.id) {
      const payload: UserUpdatePayload = {
        id: form.id,
        nickname: form.nickname,
        email: form.email,
        phone: form.phone,
        avatar: form.avatar,
        sex: form.sex,
        status: form.status,
        deptId: form.deptId,
        remark: form.remark,
        roleIds: [...form.roleIds],
      };
      await updateUser(payload);
      const index = tableData.value.findIndex((item) => item.id === payload.id);
      if (index >= 0) {
        tableData.value[index] = {
          ...tableData.value[index],
          nickname: payload.nickname,
          email: payload.email,
          phone: payload.phone,
          status: payload.status,
          roleIds: [...payload.roleIds],
        };
      }
      ElMessage.success("用户编辑成功");
    }
    dialogVisible.value = false;
  } finally {
    dialogLoading.value = false;
  }
};

const handleDelete = async (id: number) => {
  await ElMessageBox.confirm("删除后将进行逻辑删除，是否继续？", "提示", {
    type: "warning",
  });
  deleteLoadingId.value = id;
  try {
    await deleteUser(id);
    tableData.value = tableData.value.filter((item) => item.id !== id);
    ElMessage.success("用户删除成功");
  } finally {
    deleteLoadingId.value = null;
  }
};
</script>

<template>
  <div class="page">
    <div class="toolbar">
      <div class="action-group">
        <el-button type="primary" @click="openCreateDialog">新增用户</el-button>
      </div>
      <div class="action-group">
        <el-input-number v-model="editIdInput" :min="1" :controls="false" />
        <el-button @click="openEditDialog()">加载用户详情</el-button>
      </div>
    </div>

    <el-alert
      title="当前页面优先承载新增、编辑、删除能力。完整分页列表将在 F001 中补齐。"
      type="info"
      :closable="false"
    />

    <el-table :data="tableData" border empty-text="暂无本页操作产生的数据">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="username" label="用户名" min-width="140" />
      <el-table-column prop="nickname" label="昵称" min-width="120" />
      <el-table-column prop="email" label="邮箱" min-width="200" />
      <el-table-column prop="phone" label="手机号" min-width="140" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'info'">
            {{ row.status === 1 ? "启用" : "禁用" }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="角色" min-width="180">
        <template #default="{ row }">
          <el-space wrap>
            <el-tag v-for="roleId in row.roleIds" :key="roleId" effect="plain">
              {{ roleOptions.find((item) => item.value === roleId)?.label || `角色${roleId}` }}
            </el-tag>
          </el-space>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="160" fixed="right">
        <template #default="{ row }">
          <el-button link type="primary" @click="openEditDialog(row.id)">编辑</el-button>
          <el-popconfirm title="确认删除该用户？" @confirm="handleDelete(row.id)">
            <template #reference>
              <el-button link type="danger" :loading="deleteLoadingId === row.id">
                删除
              </el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      v-model="dialogVisible"
      :title="dialogMode === 'create' ? '新增用户' : '编辑用户'"
      width="720px"
      destroy-on-close
    >
      <el-skeleton v-if="detailLoading" :rows="8" animated />
      <el-form
        v-else
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="90px"
        class="form"
      >
        <el-row :gutter="16">
          <el-col :xs="24" :sm="12">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="form.username" :disabled="dialogMode === 'edit'" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item v-if="dialogMode === 'create'" label="密码" prop="password">
              <el-input v-model="form.password" type="password" show-password />
            </el-form-item>
            <el-form-item v-else label="用户ID">
              <el-input :model-value="String(form.id || '')" disabled />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="昵称" prop="nickname">
              <el-input v-model="form.nickname" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="form.phone" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="头像">
              <el-input v-model="form.avatar" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="性别">
              <el-select v-model="form.sex" class="full-width">
                <el-option label="未知" :value="0" />
                <el-option label="男" :value="1" />
                <el-option label="女" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio :value="1">启用</el-radio>
                <el-radio :value="0">禁用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="部门ID">
              <el-input-number v-model="form.deptId" :min="1" class="full-width" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="角色分配">
              <el-select v-model="form.roleIds" multiple class="full-width">
                <el-option
                  v-for="item in roleOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input v-model="form.remark" type="textarea" :rows="3" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="dialogLoading" @click="submitForm">
          {{ dialogMode === "create" ? "提交新增" : "保存修改" }}
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss">
.page {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.toolbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  flex-wrap: wrap;
}

.action-group {
  display: flex;
  align-items: center;
  gap: 12px;
}

.form {
  padding-top: 8px;
}

.full-width {
  width: 100%;
}

@media (max-width: 768px) {
  .toolbar,
  .action-group {
    width: 100%;
  }

  .toolbar,
  .action-group {
    flex-direction: column;
    align-items: stretch;
  }
}
</style>

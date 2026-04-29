<script setup lang="ts">
import { ref } from "vue";
import { fetchPing } from "@/api/health";
import type { ApiResult } from "@/types/api";

const loading = ref(false);
const ping = ref<string>("");
const error = ref<string>("");

const runPing = async () => {
  loading.value = true;
  error.value = "";
  try {
    const res = (await fetchPing()) as ApiResult<string>;
    if (res.code === 200) {
      ping.value = res.data ?? "";
    } else {
      error.value = res.message || "接口返回异常";
    }
  } catch (e: unknown) {
    error.value = e instanceof Error ? e.message : "请求失败";
  } finally {
    loading.value = false;
  }
};
</script>

<template>
  <div class="page">
    <h2>首页</h2>
    <p class="hint">
      点击下方按钮调用后端 <code>GET /api/admin/ping</code>。用户管理能力已从顶部导航接入。
    </p>
    <el-space>
      <el-button type="primary" :loading="loading" @click="runPing">Ping 后端</el-button>
    </el-space>
    <p v-if="ping" class="ok">响应: {{ ping }}</p>
    <p v-if="error" class="err">{{ error }}</p>
  </div>
</template>

<style scoped lang="scss">
.page {
  padding: 8px 0;
}

.hint {
  color: var(--el-text-color-secondary);
  margin-bottom: 16px;
}

.ok {
  margin-top: 16px;
  color: var(--el-color-success);
}

.err {
  margin-top: 16px;
  color: var(--el-color-danger);
}
</style>

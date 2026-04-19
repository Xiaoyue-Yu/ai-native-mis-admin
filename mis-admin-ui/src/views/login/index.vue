<script setup lang="ts">
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { setToken } from "@/utils/auth";

const router = useRouter();
const route = useRoute();

const loading = ref(false);

const onLogin = async () => {
  loading.value = true;
  try {
    setToken("dev-placeholder-token");
    const redirect = (route.query.redirect as string) || "/";
    await router.replace(redirect);
  } finally {
    loading.value = false;
  }
};
</script>

<template>
  <div class="login">
    <el-card class="card" shadow="hover">
      <template #header>
        <span>登录（脚手架占位）</span>
      </template>
      <p class="tip">后续接入真实登录接口；当前仅写入本地 Token 以便调试路由守卫。</p>
      <el-button type="primary" :loading="loading" style="width: 100%" @click="onLogin">
        进入系统
      </el-button>
    </el-card>
  </div>
</template>

<style scoped lang="scss">
.login {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e8ec 100%);
}

.card {
  width: 360px;
}

.tip {
  font-size: 13px;
  color: var(--el-text-color-secondary);
  margin: 0 0 16px;
}
</style>

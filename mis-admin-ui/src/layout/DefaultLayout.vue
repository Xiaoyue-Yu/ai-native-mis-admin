<script setup lang="ts">
import { RouterView, useRouter } from "vue-router";
import useAppStore from "@/stores/modules/app";
import { useUserStore } from "@/stores/modules/user";

const app = useAppStore();
const user = useUserStore();
const router = useRouter();
</script>

<template>
  <el-container class="layout">
    <el-header class="header">
      <span class="brand">MIS Admin</span>
      <div class="actions">
        <el-button text @click="router.push('/')">首页</el-button>
        <el-button text @click="router.push('/system/user')">用户管理</el-button>
        <el-button text type="primary" @click="app.toggleSidebar()">
          {{ app.sidebarCollapsed ? "展开侧栏" : "收起侧栏" }}
        </el-button>
        <span class="user">{{ user.profile?.nickname || user.profile?.username }}</span>
      </div>
    </el-header>
    <el-main>
      <RouterView />
    </el-main>
  </el-container>
</template>

<style scoped lang="scss">
.layout {
  min-height: 100vh;
  flex-direction: column;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid var(--el-border-color);
  gap: 16px;
}

.brand {
  font-weight: 600;
}

.actions {
  display: flex;
  align-items: center;
  gap: 12px;
  flex-wrap: wrap;
}

.user {
  color: var(--el-text-color-secondary);
  font-size: 13px;
}
</style>

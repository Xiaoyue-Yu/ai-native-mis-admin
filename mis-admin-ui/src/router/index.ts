import { createRouter, createWebHistory, type RouteRecordRaw } from "vue-router";
import DefaultLayout from "@/layout/DefaultLayout.vue";
import { getToken } from "@/utils/auth";
import { useUserStore } from "@/stores/modules/user";

const routes: RouteRecordRaw[] = [
  {
    path: "/login",
    name: "login",
    component: () => import("@/views/login/index.vue"),
    meta: { title: "登录", public: true },
  },
  {
    path: "/",
    component: DefaultLayout,
    meta: { title: "工作台" },
    children: [
      {
        path: "",
        name: "home",
        component: () => import("@/views/home/index.vue"),
        meta: { title: "首页" },
      },
      {
        path: "system/user",
        name: "system-user",
        component: () => import("@/views/system/user/UserManage.vue"),
        meta: { title: "用户管理" },
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

router.beforeEach((to, _from, next) => {
  document.title = (to.meta.title as string) || "MIS Admin";

  if (to.meta.public) {
    next();
    return;
  }

  if (!getToken()) {
    next({ path: "/login", query: { redirect: to.fullPath } });
    return;
  }

  const user = useUserStore();
  if (!user.profile) {
    user.setProfile({
      id: "0",
      username: "admin",
      nickname: "管理员",
    });
  }
  next();
});

export default router;

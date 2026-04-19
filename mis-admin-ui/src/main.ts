import "@/styles/global.scss";

import { createApp } from "vue";
import ElementPlus from "element-plus";
import zhCn from "element-plus/es/locale/lang/zh-cn";
import "element-plus/dist/index.css";

import App from "./App.vue";
import pinia from "./stores";
import router from "./router";

const app = createApp(App);

app.use(pinia);
app.use(router);
app.use(ElementPlus, { locale: zhCn, size: "small" });

app.mount("#app");

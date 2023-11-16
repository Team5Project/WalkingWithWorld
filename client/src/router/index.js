import { createRouter, createWebHistory } from "vue-router";

import Main from "@/pages/Main.vue";
import WalkingPath from "@/pages/WalkingPath.vue";
import WalkingPathDetail from "@/pages/WalkingPathDetail.vue";
import Visitor from "@/pages/Visitor.vue";
import Login from "@/pages/Login.vue";
import SignUp from "@/pages/SignUp.vue";

const routes = [
  { path: "/", component: Main },
  { path: "/walking-path", component: WalkingPath },
  { path: "/walking-path/:id", component: WalkingPathDetail, props: true },
  { path: "/visitor", component: Visitor },
  { path: "/login", component: Login },
  { path: "/signUp", component: SignUp },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;

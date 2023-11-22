import { createRouter, createWebHistory } from "vue-router";

import Main from "@/pages/Main.vue";
import WalkingPath from "@/pages/WalkingPath.vue";
import WalkingPathDetail from "@/pages/WalkingPathDetail.vue";
import Visitor from "@/pages/Visitor.vue";
import Login from "@/pages/Login.vue";
import SignUp from "@/pages/SignUp.vue";
import ReviewModify from "@/pages/ReviewModify.vue";
import VisitorUpdate from "@/components/visitor/VisitorUpdate.vue";

const routes = [
  { path: "/", component: Main },
  { path: "/walking-path", component: WalkingPath },
  {
    path: "/walking-path/search",
    component: WalkingPath,
    name: "Search",
    props: true,
  },
  { path: "/walking-path/:id", component: WalkingPathDetail, props: true },
  { path: "/walking-path/filter",
    component: WalkingPath,
    name: "Filter",
    props: true,
  },
  { path: "/visitor", component: Visitor },
  { path: "/visitor/:id", component: Visitor, props: true },
  { path: "/login", component: Login },
  { path: "/signup", component: SignUp },
  {
    name: ReviewModify,
    path: "/:id/reviews",
    component: ReviewModify,
    props: true,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;

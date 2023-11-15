import { createRouter,createWebHistory } from 'vue-router'

import Main from '@/pages/Main.vue'
import WalkingPath from '@/pages/WalkingPath.vue'
import WalkingPathDetail from '@/pages/WalkingPathDetail.vue'
import Visitor from '@/pages/Visitor.vue'
import login from '@/pages/login.vue'
import signup from '@/pages/signup.vue'


const router = createRouter({
  history : createWebHistory(),
  routes : [
    { path: '/', component: Main },
    { path: '/walking-path', component: WalkingPath },
    { path: '/walking-path-detail', component: WalkingPathDetail },
    { path: '/visitor', component: Visitor },
    { path: '/login', component: login },
    { path: '/signup', component: signup },
  ],
})

export default router;
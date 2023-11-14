import { createRouter,createWebHistory } from 'vue-router'

import Main from '@/pages/Main.vue'
import WalkingPath from '@/pages/WalkingPath.vue'
import Visitor from '@/pages/Visitor.vue'
import Login from '@/pages/Login.vue'
import SignUp from '@/pages/SignUp.vue'


const router = createRouter({
  history : createWebHistory(),
  routes : [
    { path: '/', component: Main },
    { path: '/WalkingPath', component: WalkingPath },

    { path: '/Visitor', component: Visitor },
    { path: '/Login', component: Login },
    { path: '/SignUp', component: SignUp },
  ],
})

export default router;
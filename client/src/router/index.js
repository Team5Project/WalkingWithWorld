import { createRouter,createWebHistory } from 'vue-router'

import Main from '@/pages/Main.vue'
import WalkingPath from '@/pages/WalkingPath.vue'

const router = createRouter({
  history : createWebHistory(),
  routes : [
    { path: '/', component: Main },
    { path: '/WalkingPath', component: WalkingPath },
  ],
})

export default router;
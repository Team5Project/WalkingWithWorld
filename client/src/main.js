import './assets/common.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia'
import axios from 'axios'

const app =createApp(App);
app.use(router).use(createPinia());

const httpClient = axios.create();

app.config.globalProperties.$http = httpClient;
app.mount('#app');

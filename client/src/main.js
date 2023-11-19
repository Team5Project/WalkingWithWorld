import './assets/common.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia'
import axios from 'axios'
import dayjs from 'dayjs';

const app =createApp(App);
app.use(router).use(createPinia());

const httpClient = axios.create();

app.config.globalProperties.$http = httpClient;
app.config.globalProperties.$dayjs = dayjs;
app.mount('#app');

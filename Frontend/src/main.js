import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './assets/main.css'

import axios from 'axios'  // Eklenen satır

axios.interceptors.request.use(function (config) {  // Eklenen satır
    const token = localStorage.getItem('token');  // Eklenen satır
    if (token) {  // Eklenen satır
        config.headers.Authorization = 'Bearer ' + token;  // Eklenen satır
    }  // Eklenen satır
    return config;  // Eklenen satır
}, function (error) {  // Eklenen satır
    return Promise.reject(error);  // Eklenen satır
});  // Eklenen satır

createApp(App).use(router).mount('#app')

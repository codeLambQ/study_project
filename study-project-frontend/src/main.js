import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'

import App from './App.vue'
import 'element-plus/dist/index.css'
import router from './router'
import axios from "axios";

axios.defaults.baseURL='http://localhost:8081'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(ElementPlus)

app.mount('#app')

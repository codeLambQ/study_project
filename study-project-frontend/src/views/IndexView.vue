<template>
  欢迎来到我的网页
  <el-button @click="logout()" type="danger" plain>退出登陆</el-button>
</template>

<script setup>
import {get} from "@/net";
import {ElMessage} from "element-plus";
import router from "@/router";

const logout = () => {
    get('/api/auth/logout',(message) => {
        ElMessage.success(message)
        router.push('/')
    })
}

</script>

<style scoped>

</style>
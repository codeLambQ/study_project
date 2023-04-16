<template>
    <div style="text-align: center; margin: 0 20px">
        <div style="margin-top: 150px">
            <div style="font-size: 25px">登陆</div>
            <div style="font-size: 14px;color: grey">在进入系统之前青先输入用户名和密码</div>
        </div>
        <div style="margin-top: 30px">
            <el-input type="text" v-model="form.username" placeholder="用户名/邮箱">
                <template #prefix>
                    <el-icon>
                        <UserFilled/>
                    </el-icon>
                </template>
            </el-input>
            <el-input style="margin-top: 10px" v-model="form.password" type="password" placeholder="密码">
                <template #prefix>
                    <el-icon>
                        <Lock/>
                    </el-icon>
                </template>
            </el-input>
        </div>
        <el-row style="margin-top: 5px">
            <el-col :span="12" style="text-align: left">
                <el-checkbox v-model="form.remember" label="记住我"/>
            </el-col>
            <el-col :span="12" style="text-align: right">
                <el-link>忘记密码？</el-link>
            </el-col>
        </el-row>

        <div>
            <el-button @click="login()" style="width: 250px" type="success" plain>登陆</el-button>
        </div>
        <el-divider>
            <span>没有账号</span>
        </el-divider>
        <div>
            <el-button @click="router.push('register')"  style="width: 250px" type="warning" plain>注册账号</el-button>
        </div>
    </div>
</template>

<script setup>
import {Lock, UserFilled} from "@element-plus/icons-vue";
import {reactive} from "vue";
import {post} from "@/net";
import {ElMessage} from "element-plus";
import Router from "@/router";
import router from "@/router";


const form = reactive({
    username: '',
    password: '',
    remember: false
})

const login = () => {
    if (!form.username || !form.password) {
        ElMessage.warning('请填写用户名和密码')
    } else {
        post('/api/auth/login', {
            username: form.username,
            password: form.password,
            remember: form.remember
        },(message) => {
            ElMessage.success('登陆成功')
            Router.push('/index')
        })
    }
}
</script>

<style scoped>

</style>
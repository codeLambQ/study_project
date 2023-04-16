<template>
    <div style="text-align: center; margin: 0 20px">
        <div style="margin-top: 100px">
            <div style="font-size: 25px">注册用户</div>
            <div style="font-size: 14px;color: grey">欢迎来到注册页面，欢迎注册我的学习页面</div>
        </div>
        <div style="margin-top: 20px; margin-left: 20px">
            <el-form :rules="rules" :model="form" @validate="isValidate" ref="ruleFormRef">
                <el-form-item prop="username">
                    <el-input v-model="form.username" type="text" placeholder="用户名">
                        <template #prefix>
                            <el-icon><User /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="form.password" type="password" placeholder="密码">
                        <template #prefix>
                            <el-icon><Lock /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password_confirm">
                    <el-input v-model="form.password_confirm" type="password" placeholder="确认密码">
                        <template #prefix>
                            <el-icon><Lock /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="email">
                    <el-input v-model="form.email" type="email" placeholder="邮箱   ">
                        <template #prefix>
                            <el-icon><Message /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="code">
                    <el-row>
                        <el-col :span="17">
                            <el-input v-model="form.code" type="text" placeholder="请输入验证码">
                                <template #prefix>
                                    <el-icon><EditPen /></el-icon>
                                </template>
                            </el-input>
                        </el-col>
                        <el-col :span="6"  style="margin-left: 12px">
                            <el-button @click="sendEmail()" :disabled="!isDisable" type="success">获取验证码</el-button>
                        </el-col>
                    </el-row>
                </el-form-item>
            </el-form>


        </div>
        <div style="margin-top: 50px; margin-left: 40px">
            <el-button @click="onSubmit()" style="width: 250px" type="warning" plain>立即注册</el-button>
        </div>
        <div style="margin-top: 50px; margin-left: 40px">
            <span style="color: grey">已有账号?</span>
            <el-link type="primary" @click="router.push('/')" style="translate: 0 -2px">立即登陆</el-link>
        </div>
    </div>

</template>

<script setup>
import {reactive, ref} from "vue";
import {EditPen, Lock, Message, User} from "@element-plus/icons-vue";
import router from "@/router";
import {ElMessage} from "element-plus";
import {post} from "@/net";

const ruleFormRef = ref()

const form = reactive({
    username: '',
    password: '',
    password_confirm: '',
    email: '',
    code: ''
})

// 自定义规则
const validateUsername = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请输入密码!'))
    } else if (!/^[a-zA-Z0-9_\u4E00-\u9FA5]+$/.test(value)) {
        callback(new Error('用户名只能包含中英文，不能有特殊字符!'))
    }
    else {
        callback()
    }
}

const validatePassword = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请输入密码!'))
    } else if (value !== form.password) {
        callback(new Error('两次密码不一致!'))
    }
    else {
        callback()
    }
}

const rules = {
    username: [
        { validator: validateUsername,trigger: ['blur', 'change'] },
        { min: 3, max: 16, message: '用户名必须在 3 - 16 位之间', trigger: 'blur' },
    ],
    password: [
        { required: true, message: '密码不能为空!', trigger: ['blur', 'change'] },
        { min: 3, max: 16, message: '密码必须在 3 - 16 位之间', trigger: 'blur' },
    ],
    password_confirm: [
        { validator: validatePassword,trigger: ['blur', 'change'] }
    ],
    email: [
        { required: true, message: '密码不能为空!', trigger: ['blur', 'change'] },
        {
            type: 'email',
            message: '请输入正确的邮箱!',
            trigger: ['blur', 'change'],
        }
    ],
    code: [
        { required: true, message: '验证码不能为空!', trigger: ['blur', 'change'] },
    ]
}

const isDisable = ref(false);

const isValidate = (prop, isValid) => {
    if (prop === 'email')
        isDisable.value = isValid;
}
const onSubmit = () => {
    ruleFormRef.value.validate((isValid) => {
        if(isValid) {

        } else {
            ElMessage.warning('请完整填写表单')
        }
    })
}

const sendEmail = () => {
    post('/api/auth/valid-email', {
        email: form.email
    }, (message) => {
        ElMessage.warning(message)
    })
}
</script>

<style scoped>

</style>
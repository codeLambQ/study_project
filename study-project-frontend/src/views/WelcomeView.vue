<template>
    <div style="width: 100vw; height: 100vh; overflow: hidden; display: flex">
        <div style="flex: 2">
            <el-image style="width: 100%; height: 100%" fit="cover"
                      src="https://img1.baidu.com/it/u=413643897,2296924942&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500"/>

        </div>
        <div class="welcome">
            <div style="font-size: 30px">欢迎来到我的学习平台</div>
            <div style="font-size: 15px">在这里你甚至可以认识 Java 之父</div>
            <div style="font-size: 10px">还可以进行同性交友，因为全是男的!</div>
        </div>

        <div style="width: 400px; background-color: white; z-index: 1">

            <router-view v-slot="{ Component }">
                <el-collapse-transition>
                    <component :is="Component"/>
                </el-collapse-transition>
            </router-view>

        </div>

    </div>

</template>

<script setup>
</script>

<style scoped>
.welcome {
    position: absolute;
    bottom: 30px;
    left: 30px;
    color: white;
    text-shadow: 0 0 10px black;
}
</style>
<template>
    <div class="container">
        <Canvas />
        <LoginForm />
    </div>
</template>

<script>
    import Canvas from "../components/Canvas";
    import LoginForm from "../components/LoginForm";

    export default {
        name: "Login",
        // 挂载子组件
        components: {
            Canvas: Canvas,
            LoginForm: LoginForm,
        },
    }
</script>

<style scoped>
    html,body {
        width:100%;
        height:100%;
        margin:0;
        padding:0;
        overflow:hidden hidden;
    }
    .container {
        width:100%;
        height:100%;
        margin:0 auto;
    }
</style>
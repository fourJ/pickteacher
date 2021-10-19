<template id="header">
    <div class="w3header">
        <a class="logo" @click="main">Pick Teacher</a>
        <div class="w3header-right">
            <!-- <a v-if="slogin" @click="logout">로그아웃</a> -->
            <a @click="login">로그인</a>
            <a @click="signup">회원가입</a>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'main-header',
        data() {
            return {
                slogin: sessionStorage.getItem("studentIdx"),
                // tlogin: sessionStorage.getItem("teacherIdx"),
            }
        },
        created() {
            this.slogin = this.$slogin;
            axios.post("/student/login")
            .then(resData => {
                if(resData.data != null) {
                    return this.slogin;
                }else {
                    return false;
                }
            })
        },
        methods: {
            main() {
                window.location.href="http://localhost/index.html"
            },
            login() {
                window.location.href="로그인페이지"
            },
            // logout() {
            //     axios({
            //         method: "get",
            //         url: "/logout"
            //     }).then(res => {
            //         console.log(res.data);
            //         window.location.href = res.data;
            //         sessionStorage.removeItem("user");
            //     }).catch(err => {
            //         exceptionHandler(err);
            //     })
            // },
        },
    }
</script>
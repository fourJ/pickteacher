/*!
* Start Bootstrap - Stylish Portfolio v6.0.3 (https://startbootstrap.com/theme/stylish-portfolio)
* Copyright 2013-2021 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-stylish-portfolio/blob/master/LICENSE)
*/
window.addEventListener('DOMContentLoaded', event => {

    const sidebarWrapper = document.getElementById('sidebar-wrapper');
    let scrollToTopVisible = false;
    // Closes the sidebar menu
    const menuToggle = document.body.querySelector('.menu-toggle');
    menuToggle.addEventListener('click', event => {
        event.preventDefault();
        sidebarWrapper.classList.toggle('active');
        _toggleMenuIcon();
        menuToggle.classList.toggle('active');
    })

    // Closes responsive menu when a scroll trigger link is clicked
    var scrollTriggerList = [].slice.call(document.querySelectorAll('#sidebar-wrapper .js-scroll-trigger'));
    scrollTriggerList.map(scrollTrigger => {
        scrollTrigger.addEventListener('click', () => {
            sidebarWrapper.classList.remove('active');
            menuToggle.classList.remove('active');
            _toggleMenuIcon();
        })
    });

    function _toggleMenuIcon() {
        const menuToggleBars = document.body.querySelector('.menu-toggle > .fa-bars');
        const menuToggleTimes = document.body.querySelector('.menu-toggle > .fa-times');
        if (menuToggleBars) {
            menuToggleBars.classList.remove('fa-bars');
            menuToggleBars.classList.add('fa-times');
        }
        if (menuToggleTimes) {
            menuToggleTimes.classList.remove('fa-times');
            menuToggleTimes.classList.add('fa-bars');
        }
    }

    // Scroll to top button appear
    document.addEventListener('scroll', () => {
        const scrollToTop = document.body.querySelector('.scroll-to-top');
        if (document.documentElement.scrollTop > 100) {
            if (!scrollToTopVisible) {
                fadeIn(scrollToTop);
                scrollToTopVisible = true;
            }
        } else {
            if (scrollToTopVisible) {
                fadeOut(scrollToTop);
                scrollToTopVisible = false;
            }
        }
    })
})

function fadeOut(el) {
    el.style.opacity = 1;
    (function fade() {
        if ((el.style.opacity -= .1) < 0) {
            el.style.display = "none";
        } else {
            requestAnimationFrame(fade);
        }
    })();
};

function fadeIn(el, display) {
    el.style.opacity = 0;
    el.style.display = display || "block";
    (function fade() {
        var val = parseFloat(el.style.opacity);
        if (!((val += .1) > 1)) {
            el.style.opacity = val;
            requestAnimationFrame(fade);
        }
    })();
};


// 상단바 추가 =======================================================================
function mainheader() {
    mainheaderScript =
        `
    <div>
        <div class="w3header">
            <a href="/index.html" class="logo">Pick Teacher</a>
        </div>
        <div class="w3header" id="header" v-if="success" style="background-color: #1D809F; margin-bottom: 30px">
            <a href="mypage_student.html" class="logo">My Page</a>
            <div class="w3header-right">
                <a @click="studentinfo">내 정보</a>
                <a href="student_catalog.html">수강 내역</a>
                <a href="student_cart.html">장바구니</a>
                <a href="student_likes.html">찜한 강의</a>
            </div>
        </div>
    </div>
    `
    get_session = sessionStorage.getItem("studentIdx");
    login = false;
    logout = true;
    if (get_session != null) {
        console.log("세션 비어있음");
        // logRecord();
    }
}


// 로그인 확인 =======================================================================
function reviewcommunity(url) {
    if (sessionStorage.getItem("studentIdx") != null) {
        window.location.href = "http://localhost/reviewcommunity/reivewall.html"
    } else if (sessionStorage.getItem("teacherIdx") != null) {
        window.location.href = "http://localhost/reviewcommunity/reivewall.html";
    } else {
        alert("로그인은 하셨나요? 🤔");
    }
}


//  로그인 모달 =======================================================================
const studentLog = document.querySelector("#studentLog");
const teacherLog = document.querySelector("#teacherLog");

studentLog.addEventListener("click", function () {
    window.location.href = "http://localhost/login/studentlogin.html";
})
teacherLog.addEventListener("click", function () {
    window.location.href = "http://localhost/login/teacherlogin.html";
})

function login() {
    const modal = document.getElementById("m1")
    const btnModal = document.getElementById("login")
    btnModal.addEventListener("click", e => {
        modal.style.display = "flex"
    })
    const closeBtn = modal.querySelector(".close-area")
    closeBtn.addEventListener("click", e => {
        modal.style.display = "none"
    })
    modal.addEventListener("click", e => {
        const evTarget = e.target
        if (evTarget.classList.contains("modal-overlay")) {
            modal.style.display = "none"
        }
    })
}


//  회원가입 모달 =======================================================================
const studentsignup = document.querySelector("#studentsignup");
const teachersignup = document.querySelector("#teachersignup");

studentsignup.addEventListener("click", function () {
    window.location.href = "http://localhost/login/studentsignup.html";
})
teachersignup.addEventListener("click", function () {
    window.location.href = "http://localhost/login/teachersignup.html";
})

function signup() {
    const modal = document.getElementById("m2")
    const btnModal = document.getElementById("signup")
    btnModal.addEventListener("click", e => {
        modal.style.display = "flex"
    })
    const closeBtn = modal.querySelector(".close-area")
    closeBtn.addEventListener("click", e => {
        modal.style.display = "none"
    })
    modal.addEventListener("click", e => {
        const evTarget = e.target
        if (evTarget.classList.contains("modal-overlay")) {
            modal.style.display = "none"
        }
    })
}


//  로그인 유형별 마이페이지 구분 =======================================================================
function mypage(url) {
    if (sessionStorage.getItem("studentIdx") != null) {
        window.location.href = "http://localhost/mypage/mypage_student.html";
    } else if (sessionStorage.getItem("teacherIdx") != null) {
        window.location.href = "http://localhost/mypage/mypage_teacher.html";
    } else {
        alert("로그인은 하셨나요? 🤔");
    }
}


//  채널톡 API =======================================================================
(function () {
    var w = window;
    if (w.ChannelIO) {
        return (window.console.error || window.console.log || function () { })('ChannelIO script included twice.');
    }
    var ch = function () {
        ch.c(arguments);
    };
    ch.q = [];
    ch.c = function (args) {
        ch.q.push(args);
    };
    w.ChannelIO = ch;
    function l() {
        if (w.ChannelIOInitialized) {
            return;
        }
        w.ChannelIOInitialized = true;
        var s = document.createElement('script');
        s.type = 'text/javascript';
        s.async = true;
        s.src = 'https://cdn.channel.io/plugin/ch-plugin-web.js';
        s.charset = 'UTF-8';
        var x = document.getElementsByTagName('script')[0];
        x.parentNode.insertBefore(s, x);
    }
    if (document.readyState === 'complete') {
        l();
    } else if (window.attachEvent) {
        window.attachEvent('onload', l);
    } else {
        window.addEventListener('DOMContentLoaded', l, false);
        window.addEventListener('load', l, false);
    }
})();
ChannelIO('boot', {
    "pluginKey": "8db98516-1f72-46e5-8f5c-6c36416a271a"
});
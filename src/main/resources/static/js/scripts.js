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

const studentLog = document.querySelector("#studentLog");
const teacherLog = document.querySelector("#teacherLog");
const studentsignup = document.querySelector("#studentsignup");
const teachersignup = document.querySelector("#teachersignup");

studentLog.addEventListener("click", function() {
window.location.href = "login/studentlogin.html";
})

teacherLog.addEventListener("click", function() {
window.location.href = "login/teacherlogin.html";
})

studentsignup.addEventListener("click", function() {
window.location.href = "login/studentsignup.html";
})

teachersignup.addEventListener("click", function() {
window.location.href = "login/teachersignup.html";
})

function mypage(url) {
  if(sessionStorage.getItem("studentIdx") != null) {
    window.location.href = "mypage/mypage_student.html";
  }else if(sessionStorage.getItem("teacherIdx") != null) {
    window.location.href = "mypage/mypage_teacher.html";
  }else {
    alert("로그인은 하셨나요? 🤔");
  }
}

function login() {
  const modal = document.getElementById("m1")
  const btnModal = document.getElementById("login")
  btnModal.addEventListener("click", e => {
  modal.style.display = "flex"})

  const closeBtn = modal.querySelector(".close-area")
  closeBtn.addEventListener("click", e => {
    modal.style.display = "none"
  })

  modal.addEventListener("click", e => {
    const evTarget = e.target
    if(evTarget.classList.contains("modal-overlay")) {
        modal.style.display = "none"
    }
  })
}

function signup() {
  const modal = document.getElementById("m2")
  const btnModal = document.getElementById("signup")
  btnModal.addEventListener("click", e => {
  modal.style.display = "flex"})

  const closeBtn = modal.querySelector(".close-area")
  closeBtn.addEventListener("click", e => {
    modal.style.display = "none"
  })

  modal.addEventListener("click", e => {
    const evTarget = e.target
    if(evTarget.classList.contains("modal-overlay")) {
        modal.style.display = "none"
    }
  })
}

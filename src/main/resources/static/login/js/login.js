console.clear();

var signupBtn = document.querySelector('.app.front .btn.signup');
var loginBtn = document.querySelector('.app.back .btn.login');
var loginForm = document.querySelector('.app.front');
var signupForm = document.querySelector('.app.back');
var container = document.querySelector('.container');

signupBtn.addEventListener('click', () => {
	container.classList.toggle('active');
});
loginBtn.addEventListener('click', () => {
	container.classList.toggle('active');
});



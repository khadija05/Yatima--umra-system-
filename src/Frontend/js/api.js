const API = 'https://yatima-umra-backend.onrender.com/api';
let token = localStorage.getItem('token') || '';
function headers(){return {'Content-Type':'application/json','Authorization':'Bearer '+token}}
function saveToken(t){token=t;localStorage.setItem('token',t)}
function logout(){token='';localStorage.removeItem('token');localStorage.removeItem('user');window.location.href='login.html'}
function checkLogin(){if(!token){window.location.href='login.html';return false}return true}
function getUserInfo(){try{return JSON.parse(localStorage.getItem('user')||'{}')}catch(e){return {}}}
function showMsg(msg,isError=false){
  let el=document.getElementById('msgBox'); if(!el){alert(msg);return}
  el.style.display='block'; el.style.background=isError?'#fee2e2':'#dcfce7'; el.style.color=isError?'#991b1b':'#166534'; el.style.border=isError?'1px solid #fca5a5':'1px solid #86efac'; el.textContent=msg;
}
function toggleMenu(){document.getElementById('app-sidebar').classList.toggle('open');document.getElementById('overlay').classList.toggle('show');}
function closeMenu(){document.getElementById('app-sidebar').classList.remove('open');document.getElementById('overlay').classList.remove('show');}

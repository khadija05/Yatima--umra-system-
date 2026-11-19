const API = 'https://yatima-umra-system.onrender.com/api';
let token = localStorage.getItem('token') || '';
function headers(){return {'Content-Type':'application/json','Authorization':'Bearer '+token}}
function saveToken(t){token=t;localStorage.setItem('token',t)}
function logout(){token='';localStorage.removeItem('token');localStorage.removeItem('user');go('login');toast('Umetoka')}
function checkLogin(){if(!token){go('login');return false}return true}
function getUserInfo(){try{return JSON.parse(localStorage.getItem('user')||'{}')}catch(e){return {}}}
function showMsg(msg,isError=false,elId){
  let el=document.getElementById(elId||'msgBox'); if(!el){alert(msg);return}
  el.style.display='block'; el.style.background=isError?'#fee2e2':'#dcfce7'; el.style.color=isError?'#991b1b':'#166534'; el.textContent=msg;
}
function toggleMenu(){document.getElementById('app-sidebar').classList.toggle('open');document.getElementById('overlay').classList.toggle('show');}
function closeMenu(){document.getElementById('app-sidebar').classList.remove('open');document.getElementById('overlay').classList.remove('show');}
function toast(m){let t=document.getElementById('toast');t.textContent=m;t.style.display='block';setTimeout(()=>t.style.display='none',2500)}
async function api(path,method,body){try{let r=await fetch(API+path,{method:method||'GET',headers:headers(),body:body?JSON.stringify(body):undefined});return await r.json()}catch(e){return {}}}

// --- ROUTER: Page zote ndani ya index.html ---
function go(v){
  ['home','login','register','dashboard','bookings','payments','admin'].forEach(x=>{
    let e=document.getElementById('v-'+x); if(e) e.classList.toggle('hidden',x!==v);
    let m=document.getElementById('m-'+(x==='home'?'home':x==='dashboard'?'dash':x==='bookings'?'book':x==='payments'?'pay':x==='admin'?'admin':x==='login'?'login':'')); if(m) m.classList.toggle('active',x===v);
  });
  if((v==='dashboard'||v==='bookings'||v==='payments'||v==='admin') && !token){go('login');return}
  if(v==='dashboard') loadMyBookings();
  if(v==='bookings') loadMyBookings2();
  if(v==='payments') loadMyPayments();
  if(v==='admin'){loadUsers();loadPayments();}
  closeMenu(); window.scrollTo(0,0);
}
function tab(v,el){document.querySelectorAll('.tab').forEach(t=>t.classList.remove('on'));el.classList.add('on');['book','my','pay'].forEach(x=>document.getElementById('t-'+x).classList.toggle('hidden',x!==v)); if(v==='my') loadMyBookings();}

// --- PACKAGES ---
const PACKAGES=[
 {name:"Umra Standard",price:3500000,img:"https://images.unsplash.com/photo-1564769625905-50e93615e769?w=800",desc:"Siku 14 • Hoteli 3* • Visa + Tiketi"},
 {name:"Umra VIP",price:5500000,img:"https://images.unsplash.com/photo-1519791883288-dc8bd696e667?w=800",desc:"Siku 14 • Hoteli 5* • Karibu na Haram"},
 {name:"Umra Ramadhan",price:6500000,img:"https://images.unsplash.com/photo-1542816417-0983c9c9ad53?w=800",desc:"Ramadhan • Iftar • Hoteli 5*"},
];
function renderPkgs(){
  let el=document.getElementById('pkgGrid'); if(!el) return;
  el.innerHTML=PACKAGES.map(p=>`<div class="pkg"><img src="${p.img}"><div class="pkg-body"><h3>${p.name}</h3><p class="small">${p.desc}</p><div class="price">${p.price.toLocaleString()} TZS</div><button class="btn" onclick="choosePkg('${p.name}')">Book Hiki</button></div></div>`).join('');
  let s=document.getElementById('pkgSelect'); if(s) s.innerHTML=PACKAGES.map(p=>`<option value="${p.name}">${p.name} - ${p.price.toLocaleString()} TZS</option>`).join('');
}
function choosePkg(n){if(!token){go('login');toast('Ingia kwanza');return} document.getElementById('pkgSelect').value=n; go('dashboard')}
function setUserName(){let u=getUserInfo(); document.querySelectorAll('.user-name').forEach(e=>e.textContent=u.username||u.fullName||'Karibu'); document.querySelectorAll('.user-role').forEach(e=>e.textContent=u.role||'');}

// --- AUTH ---
async function doLogin(){
  let u=document.getElementById('username').value,p=document.getElementById('password').value;
  if(!u||!p) return showMsg('Jaza username na password',true);
  let d=await api('/auth/login','POST',{username:u,password:p,tenantId:document.getElementById('tenantId').value||'ZANZIBAR'});
  if(d.token||d.jwt){saveToken(d.token||d.jwt);localStorage.setItem('user',JSON.stringify(d.user||{username:u,role:d.role||'USER'})); setUserName(); toast('Karibu!'); go('dashboard');}
  else showMsg(d.message||'Login imeshindikana',true);
}
async function doRegister(){
  let u=document.getElementById('regUser').value,p=document.getElementById('regPass').value;
  let d=await api('/auth/register','POST',{username:u,password:p,tenantId:document.getElementById('regTenant').value||'ZANZIBAR',fullName:document.getElementById('regName').value,role:'USER'});
  if(d.token||d.jwt||d.message){showMsg('Umejisajili! Sasa ingia.',false,'msgBox2'); setTimeout(()=>go('login'),1200);}
  else showMsg('Kujisajili kumeshindikana',true,'msgBox2');
}

// --- BOOKING / PAYMENT ---
async function bookNow(){
  if(!checkLogin()) return;
  let pkg=document.getElementById('pkgSelect').value;
  let d=await api('/bookings','POST',{jinaPackage:pkg,jinaKifurushi:pkg,packageName:pkg,orphanName:document.getElementById('orphanName').value,status:'pending'});
  toast(d.message||'Imebookiwa!'); loadMyBookings();
}
async function loadMyBookings(){
  let el=document.getElementById('myList'); if(!el) return;
  let d=await api('/bookings/my');
  el.innerHTML=(Array.isArray(d)?d:[]).map(b=>`<div class="item"><span>${b.jinaPackage||b.packageName||''} - ${b.orphanName||''}</span><b>${b.status||''}</b></div>`).join('')||'Hakuna bookings';
}
async function loadMyBookings2(){
  let el=document.getElementById('myList2'); if(!el) return;
  let d=await api('/bookings/my');
  el.innerHTML=(Array.isArray(d)?d:[]).map(b=>`<div class="item"><span>${b.jinaPackage||b.packageName||''} - ${b.orphanName||''}</span><b>${b.status||''}</b></div>`).join('')||'Hakuna bookings';
}
async function payNow(){
  if(!checkLogin()) return;
  let d=await api('/payments','POST',{amount:Number(document.getElementById('payAmount').value),paymentMethod:document.getElementById('payMethod').value,reference:document.getElementById('payRef').value,status:'pending'});
  toast(d.message||'Malipo yametumwa!'); loadMyPayments();
}
async function loadMyPayments(){
  let el=document.getElementById('myPayList'); if(!el) return;
  let d=await api('/payments/my');
  el.innerHTML=(Array.isArray(d)?d:[]).map(p=>`<div class="item"><span>${p.amount} TZS - ${p.paymentMethod||''}</span><b>${p.status||''}</b></div>`).join('')||'Hakuna malipo';
}

// --- ADMIN ---
async function loadUsers(){
  let el=document.getElementById('userList'); if(!el) return;
  let d=await api('/admin/users');
  el.innerHTML=(Array.isArray(d)?d:[]).map(u=>`<div class="item"><span>${u.username} - ${u.status||''}</span><span><button class="btn small" onclick="approveUser('${u.username}')">Approve</button> <button class="btn small gold" onclick="lockUser('${u.username}')">Lock</button></span></div>`).join('');
}
async function approveUser(u){await api('/admin/approve/'+u,'POST',{});toast('Amethibitishwa');loadUsers();}
async function lockUser(u){await api('/admin/lock/'+u,'POST',{});toast('Amefungwa');loadUsers();}
async function loadPayments(){
  let el=document.getElementById('payList'); if(!el) return;
  let d=await api('/admin/payments');
  el.innerHTML=(Array.isArray(d)?d:[]).map(p=>`<div class="item"><span>${p.username} - ${p.amount} TZS</span><button class="btn small" onclick="approvePay(${p.id})">Approve</button></div>`).join('');
}
async function approvePay(id){await api('/admin/payments/'+id+'/approve','POST',{});toast('Malipo yamethibitishwa');loadPayments();}
async function loadStats(){
  let d=await api('/admin/stats'); if(!d||!d.total) return;
  let s=(id,v)=>{let e=document.getElementById(id);if(e)e.textContent=v};
  s('sTotal',d.total||0);s('sActive',d.active||0);s('sMoney',(d.money||0).toLocaleString());
}

// --- START ---
renderPkgs(); setUserName(); loadStats();
if(token) go('home'); else go('home');

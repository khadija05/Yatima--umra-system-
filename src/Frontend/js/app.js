const API = 'https://yatima-umra-system.onrender.com/api';
let token = localStorage.getItem('token') || '';
function headers(){ return {'Content-Type':'application/json'} }
function logout(){ token=''; localStorage.removeItem('token'); localStorage.removeItem('user'); go('login'); toast('Umetoka'); }
function getUserInfo(){ try{ return JSON.parse(localStorage.getItem('user')||'{}') }catch(e){ return {} } }
function showMsg(msg,isError=false,elId){ let el=document.getElementById(elId||'msgBox'); if(!el){ alert(msg); return } el.style.display='block'; el.style.background=isError?'#fee2e2':'#dcfce7'; el.style.color=isError?'#991b1b':'#166534'; el.style.border=isError?'1px solid #fca5a5':'1px solid #86efac'; el.textContent=msg; }
function toggleMenu(){ document.getElementById('app-sidebar').classList.toggle('open'); document.getElementById('overlay').classList.toggle('show'); }
function closeMenu(){ document.getElementById('app-sidebar').classList.remove('open'); document.getElementById('overlay').classList.remove('show'); }
function toast(m){ let t=document.getElementById('toast'); t.textContent=m; t.style.display='block'; setTimeout(()=>t.style.display='none',2800) }
async function api(path,method,body){ try{ let r=await fetch(API+path,{method:method||'GET',headers:headers(),body:body?JSON.stringify(body):undefined}); let txt=await r.text(); try{ return JSON.parse(txt) }catch(e){ return {raw:txt,status:r.status} } }catch(e){ return {} } }

function go(v){
  ['home','login','register','dashboard','bookings','payments','admin'].forEach(x=>{ let e=document.getElementById('v-'+x); if(e) e.classList.toggle('hidden',x!==v); });
  let map={home:'m-home',dashboard:'m-dash',bookings:'m-book',payments:'m-pay',admin:'m-admin',login:'m-login'};
  Object.keys(map).forEach(k=>{ let m=document.getElementById(map[k]); if(m) m.classList.toggle('active',k===v) });
  if((v==='dashboard'||v==='bookings'||v==='payments'||v==='admin') && !localStorage.getItem('user')){ go('login'); return }
  if(v==='dashboard') loadMyBookings();
  if(v==='bookings') loadMyBookings2();
  if(v==='payments') loadMyPayments();
  if(v==='admin') loadUsers();
  closeMenu(); window.scrollTo(0,0);
}
function tab(v,el){ document.querySelectorAll('#v-dashboard .tab').forEach(t=>t.classList.remove('on')); el.classList.add('on'); ['book','my','pay'].forEach(x=>document.getElementById('t-'+x).classList.toggle('hidden',x!==v)); if(v==='my') loadMyBookings(); }
function adminTab(v,el){ document.querySelectorAll('#v-admin .tab').forEach(t=>t.classList.remove('on')); el.classList.add('on'); document.getElementById('a-users').classList.toggle('hidden',v!=='users'); document.getElementById('a-pays').classList.toggle('hidden',v!=='pays'); if(v==='pays') loadPayments(); }
function setUserName(){
  let u=getUserInfo();
  document.querySelectorAll('.user-name').forEach(e=>e.textContent=u.name||u.username||'Karibu');
  document.querySelectorAll('.user-role').forEach(e=>e.textContent=(u.role||'')+' • '+(u.plan||'')+' • '+(u.status||''));
  let ph=document.getElementById('user-phone'); if(ph) ph.textContent=u.phone||'';
  let ex=document.getElementById('user-exp'); if(ex) ex.textContent=u.expiresAt?('⏰ Inaisha: '+new Date(u.expiresAt).toLocaleDateString()):'';
  let de=document.getElementById('dash-exp'); if(de) de.textContent=u.expiresAt?('⏰ Mfumo wako unaisha: '+new Date(u.expiresAt).toLocaleDateString()+' • Plan: '+(u.plan||'')):'';
}

const PACKAGES=[
 {name:"Umra Standard",price:3500000,desc:"Siku 14 • Hoteli 3* • Visa + Tiketi"},
 {name:"Umra VIP",price:5500000,desc:"Siku 14 • Hoteli 5* • Karibu na Haram"},
 {name:"Umra Ramadhan",price:6500000,desc:"Ramadhan • Iftar • Hoteli 5*"},
];
function renderPkgs(){ let s=document.getElementById('pkgSelect'); if(s) s.innerHTML=PACKAGES.map(p=>`<option value="${p.name}">${p.name} - ${p.price.toLocaleString()} TZS</option>`).join(''); }
function choosePkg(n){ if(!localStorage.getItem('user')){ go('login'); toast('Ingia kwanza'); return } let s=document.getElementById('pkgSelect'); if(s) s.value=n; go('dashboard'); }

// --- AUTH ---
async function doLogin(){
  let u=document.getElementById('username').value.trim(); let p=document.getElementById('password').value;
  if(!u||!p) return showMsg('Jaza username na password',true);
  let isPhone=/^[0-9+]+$/.test(u);
  let body=isPhone?{phone:u,password:p}:{username:u,password:p,phone:u};
  let d=await api('/auth/login','POST',body);
  console.log('LOGIN:',d);
  if(d.success){
    localStorage.setItem('user',JSON.stringify(d)); localStorage.setItem('token','logged-in'); token='logged-in';
    setUserName(); toast('Karibu '+(d.name||d.username)+'!'); go('dashboard');
  } else {
    showMsg(d.message||'Login imeshindikana',true);
    if(d.error==='EXPIRED'||d.error==='LOCKED'){ setTimeout(()=>{ go('home'); let b=document.getElementById('payBox'); if(b) b.classList.remove('hidden'); },1000); }
  }
}
async function doRegister(){
  let f=document.getElementById('regName').value.trim(); let u=document.getElementById('regUser').value.trim(); let ph=document.getElementById('regPhone').value.trim(); let p=document.getElementById('regPass').value; let plan=document.getElementById('regPlan').value;
  if(!u||!p) return showMsg('Jaza username na password',true,'msgBox2');
  if(!ph) return showMsg('Jaza namba ya simu',true,'msgBox2');
  let body={name:f||u,username:u,phone:ph,password:p,role:'Mthamini',plan:plan};
  let d=await api('/auth/register','POST',body);
  if(d.success){ showMsg('Umejisajili! Subiri Khadija akuthibitishe.',false,'msgBox2'); setTimeout(()=>go('login'),1500); }
  else showMsg(d.message||'Kujisajili kumeshindikana',true,'msgBox2');
}

// --- KODI MFUMO / LIPA ---
let selectedPlan='FREE-DEMO';
function choosePlan(p){ selectedPlan=p; let b=document.getElementById('payBox'); if(b) b.classList.remove('hidden'); let s=document.getElementById('payPlan'); if(s) s.value=p; let u=getUserInfo(); let ph=document.getElementById('payPhone'); if(ph && u.phone) ph.value=u.phone; if(p==='FREE-DEMO'){ go('register'); let rp=document.getElementById('regPlan'); if(rp) rp.value='FREE-DEMO'; } else { b.scrollIntoView({behavior:'smooth'}); } }
async function payForSystem(){
  let ph=document.getElementById('payPhone').value.trim(); let plan=document.getElementById('payPlan').value;
  if(!ph) return toast('Jaza namba ya simu');
  let d=await api('/auth/pay','POST',{phone:ph,plan:plan,reference:document.getElementById('payRef2').value});
  if(d.success) toast('✅ Malipo yamepokelewa! Subiri Khadija athibitishe.'); else toast(d.message||'Imeshindikana');
}

// --- BOOKING / PAYMENT ---
function getBookings(){ try{ return JSON.parse(localStorage.getItem('myBookings')||'[]') }catch(e){ return [] } }
async function bookNow(){
  let pkg=document.getElementById('pkgSelect').value; let orphan=document.getElementById('orphanName').value.trim();
  if(!orphan) return toast('Jaza jina la yatima');
  let list=getBookings(); list.unshift({packageName:pkg,orphanName:orphan,status:'pending',date:new Date().toLocaleDateString()});
  localStorage.setItem('myBookings',JSON.stringify(list)); toast('✅ Imebookiwa!'); document.getElementById('orphanName').value=''; loadMyBookings();
  await api('/bookings','POST',{packageName:pkg,orphanName:orphan,status:'pending'});
}
async function loadMyBookings(){ let el=document.getElementById('myList'); if(!el) return; let list=getBookings(); el.innerHTML=list.map(b=>`<div class="item"><span>🕋 ${b.packageName} - ${b.orphanName}</span><b>${b.status}</b></div>`).join('')||'Hakuna bookings'; }
async function loadMyBookings2(){ let el=document.getElementById('myList2'); if(!el) return; let list=getBookings(); el.innerHTML=list.map(b=>`<div class="item"><span>🕋 ${b.packageName} - ${b.orphanName}</span><b>${b.status}</b></div>`).join('')||'Hakuna bookings'; }
function getPays(){ try{ return JSON.parse(localStorage.getItem('myPays')||'[]') }catch(e){ return [] } }
async function payNow(){ let amt=document.getElementById('payAmount').value; if(!amt) return toast('Jaza kiasi'); let list=getPays(); list.unshift({amount:amt,paymentMethod:document.getElementById('payMethod').value,status:'pending'}); localStorage.setItem('myPays',JSON.stringify(list)); toast('✅ Malipo yametumwa!'); loadMyPayments(); await api('/payments','POST',{amount:Number(amt),paymentMethod:document.getElementById('payMethod').value,status:'pending'}); }
async function loadMyPayments(){ let el=document.getElementById('myPayList'); if(!el) return; let list=getPays(); el.innerHTML=list.map(p=>`<div class="item"><span>💰 ${p.amount} TZS - ${p.paymentMethod}</span><b>${p.status}</b></div>`).join('')||'Hakuna malipo'; }

// --- ADMIN ---
async function loadUsers(){
  let el=document.getElementById('userList'); if(!el) return;
  let d=await api('/auth/all'); let list=Array.isArray(d)?d:[];
  el.innerHTML=list.map(u=>{
    let pill = u.status==='active' ? '<span class="pill active">ACTIVE</span>' : u.status==='waiting-payment' ? '<span class="pill waiting">⏳ ANALIPA</span>' : u.status==='expired' ? '<span class="pill expired">⏰ MUDA UMEISHA</span>' : u.status==='locked' ? '<span class="pill locked">🔒 LOCKED</span>' : '<span class="pill pending">PENDING</span>';
    return `<div class="item"><div><b>${u.name||u.username}</b> <span class="small">(${u.phone||''})</span><br><span class="small">${u.plan||''} • Inaisha: ${u.expiresAt?new Date(u.expiresAt).toLocaleDateString():'-'}</span> ${pill}</div><span><button class="btn small" onclick="approveUser(${u.id})">✅ Approve</button> <button class="btn small gold" onclick="lockUser(${u.id})">🔒 Lock</button></span></div>`
  }).join('')||'Hakuna watumiaji';
  let sT=document.getElementById('sTotal'); if(sT) sT.textContent=list.length;
  let sA=document.getElementById('sActive'); if(sA) sA.textContent=list.filter(x=>x.status==='active').length;
}
async function approveUser(id){ await api('/auth/approve/'+id,'PUT',{}); toast('✅ Amethibitishwa - siku 30 zimeongezwa'); loadUsers(); }
async function lockUser(id){ await api('/auth/lock/'+id,'PUT',{}); toast('🔒 Amefungwa'); loadUsers(); }
async function loadPayments(){ let el=document.getElementById('payList'); if(!el) return; let d=await api('/payments'); let list=Array.isArray(d)?d:getPays(); el.innerHTML=list.map(p=>`<div class="item"><span>💰 ${p.username||'Mteja'} - ${p.amount} TZS</span><b>${p.status||'pending'}</b></div>`).join('')||'Hakuna malipo'; }

renderPkgs(); setUserName(); loadUsers(); go('home');

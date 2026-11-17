async function doSignup(){
  let btn=document.getElementById('btnSignup'); if(btn){btn.disabled=true;btn.textContent='⏳ Inasajili...'}
  showMsg('⏳ Inasajili... Subiri sekunde 30, server inamka...');
  let body={username:document.getElementById('aUser').value.trim(),email:document.getElementById('aEmail').value.trim(),password:document.getElementById('aPass').value,role:document.getElementById('aRole').value,orgName:document.getElementById('aOrg').value,plan:document.getElementById('aPlan').value};
  if(!body.username||!body.password||!body.email){showMsg('❌ Weka Username, Email na Password!',true);if(btn){btn.disabled=false;btn.textContent='Jisajili na Plan Hii'}return}
  try{ let r=await fetch(API+'/auth/signup',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(body)}); let txt=await r.text(); if(r.ok){showMsg('✅ '+txt+' Sasa bonyeza INGA.');} else{showMsg('❌ '+txt,true);} }catch(e){showMsg('❌ Imeshindwa kuunganisha na server. Jaribu tena baada ya sekunde 30.',true)}
  if(btn){btn.disabled=false;btn.textContent='Jisajili na Plan Hii'}
}
async function doLogin(){
  showMsg('⏳ Ina-ingia...');
  try{ let r=await fetch(API+'/auth/signin',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({username:document.getElementById('aUser').value.trim(),password:document.getElementById('aPass').value})}); let txt=await r.text(); if(r.status===403){showMsg('🔒 '+txt,true);setTimeout(()=>window.location.href='plans.html',1500);return} if(!r.ok){showMsg('❌ '+txt,true);return} let d=JSON.parse(txt); saveToken(d.accessToken); localStorage.setItem('user',JSON.stringify(d)); showMsg('✅ Umeingia! Plan: '+d.plan); setTimeout(()=>window.location.href='dashboard.html',1000); }catch(e){showMsg('❌ Server haipatikani. Subiri kidogo jaribu tena.',true)}
}

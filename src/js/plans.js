async function loadPlans() {
  let r = await fetch(API + '/plans'); let d = await r.json();
  document.getElementById('planList').innerHTML = d.map(p => `<div class="card plan-card"><b>${p.displayName}</b><br>Bei: ${p.price} TZS | Siku: ${p.days}<br>Max Mayatima: ${p.maxOrphans} | Max Umra: ${p.maxUmra}<br><button onclick="chaguaPlan('${p.code}',${p.price})">Chagua ${p.code}</button></div>`).join('');
}
function chaguaPlan(code, price) { document.getElementById('payPlan').value = code; document.getElementById('payKiasi').value = price; alert('Umechagua ' + code); }
async function lipaPlan() {
  if (!checkLogin()) return;
  let r = await fetch(API + '/payments/lipa', { method: 'POST', headers: headers(), body: JSON.stringify({ plan: document.getElementById('payPlan').value, kiasi: document.getElementById('payKiasi').value, muamala: document.getElementById('payMuamala').value }) });
  alert(await r.text()); checkLockBanner();
}

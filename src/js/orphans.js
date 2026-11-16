async function loadOrphans() {
  if (!checkLogin()) return;
  let r = await fetch(API + '/orphans', { headers: headers() }); let d = await r.json();
  document.getElementById('orphanList').innerHTML = d.length===0 ? '<p>Hakuna Mayatima bado.</p>' : d.map(o=>`<div class="card"><b>${o.jinaKamili}</b> - Umri ${o.umri} | ${o.jinsia}<br>${o.mkoa} | ${o.wilaya||''} | Shule: ${o.shule||''}<br>Mahitaji: ${o.mahitaji||''}</div>`).join('');
}
async function sajiliOrphan() {
  let r = await fetch(API + '/orphans', { method: 'POST', headers: headers(), body: JSON.stringify({ jinaKamili: document.getElementById('oJina').value, umri: parseInt(document.getElementById('oUmri').value), jinsia: document.getElementById('oJinsia').value, mkoa: document.getElementById('oMkoa').value, wilaya: document.getElementById('oWilaya').value, shule: document.getElementById('oShule').value, mahitaji: document.getElementById('oMahitaji').value }) });
  alert(await r.text()); loadOrphans();
}

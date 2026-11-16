async function loadTasisi() {
  let r = await fetch(API + '/tasisi'); let d = await r.json();
  document.getElementById('tasisiList').innerHTML = d.map(t=>`<div class="card"><b>${t.jinaTasisi}</b> <span class="badge">⭐ ${t.wastaniRating} | Watu ${t.idadiWatu}</span><br>${t.mkoa} | ${t.mahitaji}<br><button onclick="rate(${t.id})">Toa Nyota ⭐</button> <button onclick="donate(${t.id})">Toa Msaada</button></div>`).join('');
}
async function loadTasisiBora() {
  let r = await fetch(API + '/tasisi/ranking/bora-zaidi'); let d = await r.json();
  document.getElementById('tasisiList').innerHTML = d.map((t,i)=>`<div class="card"><b>#${i+1} ${t.jinaTasisi}</b> ⭐ ${t.wastaniRating}<br>${t.mkoa} | Watu ${t.idadiWatu}</div>`).join('');
}
async function sajiliTasisi() {
  if (!checkLogin()) return;
  let r = await fetch(API + '/tasisi', { method: 'POST', headers: headers(), body: JSON.stringify({ jinaTasisi: document.getElementById('tJina').value, mkoa: document.getElementById('tMkoa').value, wilaya: document.getElementById('tWilaya').value, simu: document.getElementById('tSimu').value, idadiWatu: parseInt(document.getElementById('tWatu').value), mahitaji: document.getElementById('tMahitaji').value, bajeti: parseFloat(document.getElementById('tBajeti').value), aina: 'MAYATIMA' }) });
  alert(await r.text()); loadTasisi();
}
async function rate(id) { let n = prompt('Nyota 1-5:', '5'); await fetch(API + '/tasisi/' + id + '/rate?nyota=' + n, { method: 'POST', headers: headers() }); alert('Asante!'); loadTasisi(); }
async function donate(id) { let k = prompt('Kiasi:', '50000'); let a = prompt('Aina: PESA / CHAKULA / NGUO', 'PESA'); await fetch(API + '/donations', { method: 'POST', headers: headers(), body: JSON.stringify({ jinaMthamini: 'Mthamini', ainaMsaada: a, kiasi: parseFloat(k), tasisiId: id }) }); alert('Msaada umetumwa!'); }

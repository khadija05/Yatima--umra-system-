async function loadUmra() {
  let r = await fetch(API + '/umra'); let d = await r.json();
  document.getElementById('umraList').innerHTML = d.map(u=>`<div class="card"><b>${u.jinaKifurushi}</b> - ${u.jinaTaasisi}<br>Bei: ${u.bei} TZS | Siku: ${u.siku} | Ndege: ${u.ndege}<br>Makkah: ${u.hoteliMakkah} | Madinah: ${u.hoteliMadinah}<br>Nafasi: ${u.nafasiZilizobaki} | ⭐ ${u.wastaniRating}</div>`).join('');
}
async function sajiliUmra() {
  if (!checkLogin()) return;
  let r = await fetch(API + '/umra', { method: 'POST', headers: headers(), body: JSON.stringify({ jinaTaasisi: document.getElementById('uTaasisi').value, jinaKifurushi: document.getElementById('uKifurushi').value, bei: parseFloat(document.getElementById('uBei').value), siku: parseInt(document.getElementById('uSiku').value), ndege: document.getElementById('uNdege').value, hoteliMakkah: document.getElementById('uMakkah').value, hoteliMadinah: document.getElementById('uMadinah').value, ainaChumba: document.getElementById('uChumba').value, idadiWatu: 0, uwezoKuchukuaWatu: parseInt(document.getElementById('uUwezo').value || 40) }) });
  alert(await r.text()); loadUmra();
}

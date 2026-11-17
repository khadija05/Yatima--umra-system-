async function loadPackages(aina) {
  let r = await fetch(API + '/packages' + (aina ? '?aina=' + aina : '')); let d = await r.json();
  document.getElementById('pkgList').innerHTML = d.map(p=>`<div class="card"><b>${p.jinaPackage}</b> - ${p.aina}<br>Bei: ${p.bei} TZS | ${p.maelezo||''}<br><button onclick="kodisha(${p.id})">KODISHA / BOOK</button></div>`).join('');
}
async function kodisha(id) {
  if (!checkLogin()) return;
  let miezi = prompt('Miezi mingapi? (1,3,12)', '1');
  let r = await fetch(API + '/bookings/kodisha/' + id + '?miezi=' + miezi, { method: 'POST', headers: headers() });
  alert(await r.text());
}
async function loadBookings() {
  if (!checkLogin()) return;
  let r = await fetch(API + '/bookings/yangu/status', { headers: headers() });
  if (r.status === 403) { document.getElementById('bookingList').innerHTML = `<div class="card alert-lock"><h2>🔒 IMEFUNGWA!</h2><p>${await r.text()}</p></div>`; return; }
  let d = await r.json();
  document.getElementById('bookingList').innerHTML = d.map(b=>`<div class="card"><b>${b.jinaPackage}</b> - ${b.status}<br>Bei: ${b.bei} | Mwisho: ${b.tareheMwisho}<br>Siku Zilizobaki: ${b.sikuZilizobaki} | Imelipa: ${b.imelipa}<br><button onclick="lipaBooking(${b.id})">LIPA</button></div>`).join('');
}
async function lipaBooking(id) {
  let njia = prompt('Njia: M-PESA / TIGO PESA', 'M-PESA'); let muamala = prompt('Namba ya Muamala:');
  await fetch(API + '/bookings/' + id + '/lipa?njia=' + njia + '&muamala=' + muamala, { method: 'POST', headers: headers() });
  alert('Umelipa! Imefunguka ✅'); loadBookings();
}

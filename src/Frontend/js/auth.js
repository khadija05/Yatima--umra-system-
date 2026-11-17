async function doSignup() {
  let body = { username: document.getElementById('aUser').value, email: document.getElementById('aEmail').value, password: document.getElementById('aPass').value, role: document.getElementById('aRole').value, orgName: document.getElementById('aOrg').value, plan: document.getElementById('aPlan').value };
  if (!body.username || !body.password) return alert('Weka Username na Password!');
  let r = await fetch(API + '/auth/signup', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(body) });
  alert(await r.text());
}
async function doLogin() {
  let r = await fetch(API + '/auth/signin', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ username: document.getElementById('aUser').value, password: document.getElementById('aPass').value }) });
  let d = await r.json();
  if (r.status === 403) { alert('🔒 ' + d); window.location.href = 'plans.html'; return; }
  saveToken(d.accessToken);
  localStorage.setItem('user', JSON.stringify(d));
  alert('Umeingia! Plan: ' + d.plan);
  window.location.href = 'dashboard.html';
}

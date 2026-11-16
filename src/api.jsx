// API ya Backend
const API = 'http://localhost:8080/api';
let token = localStorage.getItem('token') || '';

function headers() {
  return { 'Content-Type': 'application/json', 'Authorization': 'Bearer ' + token };
}
function headersAuthOnly() {
  return { 'Authorization': 'Bearer ' + token };
}
async function apiGet(path) {
  let r = await fetch(API + path, { headers: headers() });
  if (r.status === 401 || r.status === 403) { window.location.href = 'login.html'; throw new Error('Login kwanza'); }
  return await r.json();
}
async function apiPost(path, body) {
  let r = await fetch(API + path, { method: 'POST', headers: headers(), body: JSON.stringify(body) });
  let txt = await r.text();
  if (!r.ok) throw new Error(txt);
  try { return JSON.parse(txt); } catch(e){ return txt; }
}
function saveToken(t) { token = t; localStorage.setItem('token', t); }
function logout() { token = ''; localStorage.removeItem('token'); window.location.href = 'login.html'; }
function checkLogin() {
  if (!token) { window.location.href = 'login.html'; return false; }
  return true;
}
function getUserInfo() {
  try { return JSON.parse(localStorage.getItem('user') || '{}'); } catch(e){ return {}; }
}

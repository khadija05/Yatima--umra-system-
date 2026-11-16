import axios from 'axios';
const api = axios.create({ baseURL: 'http://localhost:8080/api' });
api.interceptors.request.use(c=>{ const t=localStorage.getItem('token'); if(t) c.headers.Authorization=`Bearer ${t}`; return c; });
api.interceptors.response.use(r=>r, err=>{
  if(err.config?.url?.includes('/renew-request')) return Promise.reject(err);
  if(err.response?.status===402) window.location.href='/pay';
  if(err.response?.status===401){ localStorage.clear(); window.location.href='/login'; }
  return Promise.reject(err);
});
export default api;

import { BrowserRouter, Routes, Route, useNavigate, Navigate, useParams } from 'react-router-dom';
import { useEffect, useState, useMemo } from 'react';
import api from './api';
import Pay from './Pay';
import LandingPage from './LandingPage';
import './App.css';

function getRole() { return (localStorage.getItem('role') || 'user').toLowerCase().replace('role_', ''); }
function getEmail() { return localStorage.getItem('email') || ''; }
function ProtectedRoute({ children }) {
  const token = localStorage.getItem('token');
  if (!token) return <Navigate to="/login" replace />;
  return children;
}
const validateEmail = (e) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(e);
const validatePassport = (p) => /^[A-Z0-9]{5,15}$/.test(p || '');
const validateName = (n) => /^[a-zA-Z\s'-]{3,100}$/.test(n || '');

// ================= LOGIN =================
function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showInfo, setShowInfo] = useState(false);
  const nav = useNavigate();
  const login = async (e) => {
    e.preventDefault();
    if (!validateEmail(email)) return alert('❌ Email si sahihi');
    if (password.length < 6) return alert('❌ Password angalau 6');
    try {
      const r = await api.post('/auth/login', { username: email, email, password });
      localStorage.setItem('token', r.data.token);
      localStorage.setItem('email', email);
      localStorage.setItem('role', (r.data.role || 'USER').replace('ROLE_', '').toLowerCase());
      localStorage.setItem('fullName', r.data.fullName || '');
      localStorage.setItem('userType', r.data.userType || 'USER');
      localStorage.setItem('plan', r.data.plan || 'KAWAIDA');
      localStorage.setItem('orgName', r.data.orgName || r.data.fullName || 'Yatima-Umra');
      nav('/dashboard');
    } catch (err) {
      if (err.response?.status === 402) { localStorage.setItem('expiredEmail', email); nav('/pay'); }
      else alert('Login failed - Hakikisha Admin / Owner amekusajili kwanza');
    }
  };
  return (
    <div className="login-container">
      <div className="login-card">
        <div style={{ textAlign: 'center' }}>
          <div style={{ fontSize: 52, background: '#f0fdfa', width: 80, height: 80, borderRadius: '50%', display: 'flex', alignItems: 'center', justifyContent: 'center', margin: '0 auto' }}>🕌</div>
          <h2 style={{ margin: '12px 0 4px', fontSize: 24 }}>Yatima & Umra</h2>
          <p style={{ color: '#0f766e', fontWeight: 800, fontSize: 13 }}>NUR SYSTEM • KHADIJA DENGE</p>
          <p style={{ fontSize: 12, color: '#64748b', marginTop: 6 }}>PBZ 0887387001 | Simu 0778709707</p>
          <div style={{ display: 'flex', gap: 6, justifyContent: 'center', marginTop: 10, flexWrap: 'wrap' }}>
            <span style={{ background: '#ecfdf5', padding: '5px 10px', borderRadius: 20, fontSize: 11, fontWeight: 700 }}>KIDOGO 50k</span>
            <span style={{ background: '#fffbeb', padding: '5px 10px', borderRadius: 20, fontSize: 11, fontWeight: 700 }}>KAWAIDA 100k</span>
            <span style={{ background: '#eff6ff', padding: '5px 10px', borderRadius: 20, fontSize: 11, fontWeight: 700 }}>KUBWA 200k</span>
          </div>
        </div>
        <form onSubmit={login} style={{ marginTop: 18 }}>
          <input className="input" value={email} onChange={(e) => setEmail(e.target.value)} placeholder="✉️ Email uliyosajiliwa na Admin" required />
          <input className="input" type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="🔒 Password uliyopewa na Admin" required />
          <button className="btn" type="submit">Ingia - Ona Taarifa Zangu →</button>
          <div style={{ textAlign: 'center', marginTop: 12 }}>
            <button type="button" onClick={() => setShowInfo(!showInfo)} style={{ background: 'none', border: 'none', color: '#0f766e', fontWeight: 700, cursor: 'pointer', fontSize: 13 }}>Huna akaunti? Bofya hapa</button>
          </div>
          {showInfo && <div style={{ background: '#fffbeb', padding: 12, borderRadius: 12, marginTop: 10, fontSize: 13 }}><b>⚠️ Kumbuka:</b><br />Yatima na Mhujaji hawawezi kujisajili wenyewe. <b>Admin</b> ndio anawasajili.</div>}
          <p style={{ fontSize: 11, textAlign: 'center', color: '#94a3b8', marginTop: 12 }}>Owner: owner@yatima.com / owner123<br />Admin: admin@yatima.com / admin123</p>
          <div style={{ textAlign: 'center', marginTop: 8 }}><button type="button" onClick={() => nav('/')} style={{ background: 'none', border: 'none', color: '#0f766e', cursor: 'pointer', fontWeight: 700 }}>← Rudi Nyumbani - Vifurushi</button></div>
        </form>
      </div>
    </div>
  );
}

// ================= PUBLIC YATIMA =================
function PublicYatima() {
  const { id } = useParams();
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  useEffect(() => {
    fetch(`http://localhost:8080/api/orphans/public/${id}`).then((r) => r.json()).then((d) => { setData(d); setLoading(false); }).catch(() => setLoading(false));
  }, [id]);
  if (loading) return <div style={{ textAlign: 'center', padding: 40 }}>⏳ Inapakia...</div>;
  if (!data || data.message) return <div style={{ textAlign: 'center', padding: 40 }}>❌ Yatima hajapatikana.</div>;
  return (
    <div style={{ maxWidth: 500, margin: '0 auto', padding: 16 }}>
      <div style={{ textAlign: 'center', background: 'linear-gradient(135deg,#0f172a,#0f766e)', color: '#fff', padding: 22, borderRadius: 20 }}>
        <div style={{ fontSize: 40 }}>🕌</div><h2>NUR SYSTEM - Khadija Denge</h2><p>PBZ 0887387001 | WhatsApp 0778709707</p>
      </div>
      <div style={{ background: '#fff', padding: 20, borderRadius: 20, marginTop: 12, textAlign: 'center', boxShadow: '0 10px 30px rgba(0,0,0,.08)' }}>
        {data.imageUrl && <img src={data.imageUrl} alt="" style={{ width: 140, height: 140, borderRadius: '50%', objectFit: 'cover', border: '4px solid #0f766e' }} />}
        <h2 style={{ marginTop: 10 }}>{data.jinaKamili}</h2>
        <p style={{ color: '#64748b' }}>👶 Yatima • {data.umri} Miaka • {data.jinsia}</p>
        <div style={{ textAlign: 'left', background: '#f8fafc', padding: 14, borderRadius: 12, marginTop: 12 }}>
          <p><b>🏫 Shule:</b> {data.shule} - {data.darasa}</p><p><b>👑 Mdhamini:</b> {data.mdhamini}</p><p><b>👩‍👧 Mlezi:</b> {data.jinaMlezi} - {data.simuMlezi}</p><p><b>📍 Anuani:</b> {data.anuani}</p><p><b>❤️ Afya:</b> {data.haliAfya}</p><p><b>🎒 Mahitaji:</b> {data.mahitaji}</p><p><b>📌 Status:</b> {data.status}</p>
        </div>
        <a href={`https://wa.me/255778709707?text=${encodeURIComponent(`Assalam Alaikum, mimi ni Mdhamini wa ${data.jinaKamili}.`)}`} target="_blank" rel="noreferrer" style={{ display: 'block', background: '#25D366', color: '#fff', padding: '14px', borderRadius: 12, fontWeight: 800, textDecoration: 'none', marginTop: 14 }}>💬 WhatsApp - Uliza Kuhusu Yatima Huyu</a>
      </div>
    </div>
  );
}

// ================= DASHBOARD =================
function Dashboard() {
  const nav = useNavigate();
  const userRole = getRole();
  const userEmail = getEmail();
  const userType = localStorage.getItem('userType') || 'USER';
  const isAdmin = userRole === 'admin';
  const isOwner = userRole === 'owner';

  const [orphans, setOrphans] = useState([]);
  const [umra, setUmra] = useState([]);
  const [banks, setBanks] = useState([]);
  const [payments, setPayments] = useState([]);
  const [feedbacks, setFeedbacks] = useState([]);
  const [renewRequests, setRenewRequests] = useState([]);
  const [candidates, setCandidates] = useState([]);
  const [myVotes, setMyVotes] = useState([]);
  const [myProfile, setMyProfile] = useState(null);
  const [ownerAdmins, setOwnerAdmins] = useState([]);
  const [ownerIncome, setOwnerIncome] = useState({ totalIncome: 0, approvedCount: 0, pendingCount: 0, byPlan: {}, demoCount: 0 });
  const [myPlan, setMyPlan] = useState(null);
  const [newAdmin, setNewAdmin] = useState({ plan: 'KAWAIDA' });

  const [tab, setTab] = useState(isOwner ? 'owner' : isAdmin ? 'bank' : 'myinfo');
  const [form, setForm] = useState({});
  const [uform, setUform] = useState({});
  const [bform, setBform] = useState({});
  const [fform, setFform] = useState({});
  const [cform, setCform] = useState({});
  const [preview, setPreview] = useState('');
  const [upreview, setUpreview] = useState('');
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const [search, setSearch] = useState('');
  const [yearFilter, setYearFilter] = useState('all');
  const [statusFilter, setStatusFilter] = useState('all');
  const [voteCat, setVoteCat] = useState('MDHAMINI_BORA');
  const [orgName, setOrgName] = useState(localStorage.getItem('orgName') || 'Yatima-Umra');
  const [editOrg, setEditOrg] = useState(false);
  const [newOrgName, setNewOrgName] = useState('');

  const getQR = (id) => { const link = `${window.location.origin}/yatima/${id}`; return `https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=${encodeURIComponent(link)}`; };
  const getQRLink = (id) => `${window.location.origin}/yatima/${id}`;
  const waLink = (phone, msg) => {
    if (!phone) return '#';
    let clean = phone.replace(/[^0-9]/g, '');
    if (clean.startsWith('0')) clean = '255' + clean.substring(1);
    if (!clean.startsWith('255')) clean = '255' + clean;
    return `https://wa.me/${clean}?text=${encodeURIComponent(msg)}`;
  };
  const changeTab = (t) => { setTab(t); setSearch(''); setYearFilter('all'); setStatusFilter('all'); setSidebarOpen(false); };

  const fetchData = async () => {
    try {
      const me = await api.get('/auth/me');
      setMyProfile(me.data);
      if (me.data.orgName) { setOrgName(me.data.orgName); localStorage.setItem('orgName', me.data.orgName); }
      const o = await api.get('/orphans'); setOrphans(o.data);
      const u = await api.get('/umra'); setUmra(u.data);
      try { const f = await api.get('/feedback'); setFeedbacks(f.data); } catch { setFeedbacks([]); }
      try { const cv = await api.get('/vote/candidates'); setCandidates(cv.data); } catch {}
      try { const mv = await api.get('/vote/my-votes'); setMyVotes(mv.data); } catch {}
      if (isAdmin || isOwner) {
        try { const mp = await api.get('/my-plan'); setMyPlan(mp.data); } catch {}
        const b = await api.get('/bank/accounts'); setBanks(b.data);
        const p = await api.get('/bank/payments'); setPayments(p.data);
      }
      if (isOwner) {
        const a = await api.get('/owner/admins'); setOwnerAdmins(a.data);
        const inc = await api.get('/owner/income'); setOwnerIncome(inc.data);
      }
      try { const rr = await api.get('/subscription/renew-request'); setRenewRequests(Array.isArray(rr.data) ? rr.data : []); } catch { setRenewRequests([]); }
    } catch (err) {
      if (err.response?.status === 401) { localStorage.clear(); nav('/login'); }
      if (err.response?.status === 402) { nav('/pay'); }
    }
  };
  useEffect(() => { fetchData(); }, []);

  const passportExpiring = useMemo(() => {
    const now = new Date();
    return umra.filter((u) => u.passportExpire && new Date(u.passportExpire) < new Date(Date.now() + 90 * 24 * 60 * 60 * 1000)).map((u) => {
      const daysLeft = Math.ceil((new Date(u.passportExpire) - now) / (1000 * 60 * 60 * 24));
      return { ...u, daysLeft, isExpired: daysLeft < 0 };
    });
  }, [umra]);

  const allYears = useMemo(() => {
    const s = new Set();
    [...orphans, ...umra].forEach((x) => { if (x.createdAt) s.add(new Date(x.createdAt).getFullYear()); });
    if (s.size === 0) s.add(new Date().getFullYear());
    return [...s].sort((a, b) => b - a);
  }, [orphans, umra]);

  const yearly = useMemo(() => {
    const map = {};
    orphans.forEach((o) => { const y = o.createdAt ? new Date(o.createdAt).getFullYear() : new Date().getFullYear(); map[y] = map[y] || { year: y, orphans: 0, umra: 0 }; map[y].orphans++; });
    umra.forEach((u) => { const y = u.createdAt ? new Date(u.createdAt).getFullYear() : new Date().getFullYear(); map[y] = map[y] || { year: y, orphans: 0, umra: 0 }; map[y].umra++; });
    return Object.values(map).sort((a, b) => b.year - a.year);
  }, [orphans, umra]);

  const filteredOrphans = useMemo(() => orphans.filter((o) => {
    const ms = (o.jinaKamili || '').toLowerCase().includes(search.toLowerCase()) || (o.shule || '').toLowerCase().includes(search.toLowerCase());
    const y = o.createdAt ? new Date(o.createdAt).getFullYear().toString() : '';
    return ms && (yearFilter === 'all' || y === yearFilter);
  }), [orphans, search, yearFilter]);

  const filteredUmra = useMemo(() => umra.filter((u) => {
    const ms = (u.jinaKamili || '').toLowerCase().includes(search.toLowerCase()) || (u.passportNo || '').toLowerCase().includes(search.toLowerCase());
    const y = u.createdAt ? new Date(u.createdAt).getFullYear().toString() : '';
    return ms && (yearFilter === 'all' || y === yearFilter) && (statusFilter === 'all' || u.statusMalipo === statusFilter);
  }), [umra, search, yearFilter, statusFilter]);

  const filteredPayments = useMemo(() => payments.filter((p) => {
    const ms = (p.payerName || '').toLowerCase().includes(search.toLowerCase());
    return ms && (statusFilter === 'all' || p.status === statusFilter);
  }), [payments, search, statusFilter]);

  const uploadImage = async (file, setFn, setPrev) => {
    if (!file) return;
    if (!file.type.startsWith('image/')) return alert('❌ Ruhusu picha tu');
    if (file.size > 2 * 1024 * 1024) return alert('❌ Picha kubwa - chini ya 2MB');
    setPrev(URL.createObjectURL(file));
    const fd = new FormData(); fd.append('file', file);
    try { const r = await api.post('/upload', fd, { headers: { 'Content-Type': 'multipart/form-data' } }); setFn((prev) => ({ ...prev, imageUrl: r.data.imageUrl })); }
    catch { alert('Upload imeshindwa'); }
  };

  const totalMapato = payments.filter((p) => p.status === 'VERIFIED').reduce((s, p) => s + (p.amount || 0), 0);

  const FilterBar = ({ showStatus = false }) => (
    <div style={{ display: 'flex', gap: 8, flexWrap: 'wrap', marginBottom: 12 }}>
      <input className="input" style={{ flex: 1, minWidth: 160, margin: 0 }} placeholder="🔍 Tafuta..." value={search} onChange={(e) => setSearch(e.target.value)} />
      <select className="input" style={{ width: 130, margin: 0 }} value={yearFilter} onChange={(e) => setYearFilter(e.target.value)}>
        <option value="all">📅 Miaka Yote</option>
        {allYears.map((y) => <option key={y} value={y}>{y}</option>)}
      </select>
      {showStatus && (
        <select className="input" style={{ width: 140, margin: 0 }} value={statusFilter} onChange={(e) => setStatusFilter(e.target.value)}>
          <option value="all">Status: Zote</option><option value="VERIFIED">✅ VERIFIED</option><option value="PENDING">🟠 PENDING</option><option value="REJECTED">🔴 REJECTED</option>
        </select>
      )}
    </div>
  );

  const myOrphan = myProfile?.myOrphan || orphans.find((o) => o.email === userEmail);
  const myUmraData = myProfile?.myUmra || umra.find((u) => u.email === userEmail);
  const myFeedbacks = feedbacks.filter((f) => f.email === userEmail || f.createdBy === userEmail);

  return (
    <div className="dashboard">
      {sidebarOpen && <div className="sidebar-overlay" onClick={() => setSidebarOpen(false)} />}
      <div className={`sidebar ${sidebarOpen ? 'open' : ''}`}>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <h2>🕌 {orgName}</h2>
          <button className="close-btn" onClick={() => setSidebarOpen(false)}>✕</button>
        </div>
        <small style={{ color: '#5eead4' }}>{orgName} - PBZ 0887387001 | Simu 0778709707</small>
        {isAdmin && (
          <div style={{ marginTop: 10 }}>
            {!editOrg ? (
              <button onClick={() => { setNewOrgName(orgName); setEditOrg(true); }} style={{ background: 'rgba(255,255,255,.12)', color: '#5eead4', border: '1px solid rgba(255,255,255,.2)', padding: '8px 10px', borderRadius: 10, cursor: 'pointer', fontSize: 12, width: '100%' }}>✏️ Badilisha Jina la Taasisi</button>
            ) : (
              <div style={{ display: 'flex', gap: 4 }}>
                <input className="input" style={{ margin: 0, flex: 1, fontSize: 12, padding: '8px' }} value={newOrgName} onChange={(e) => setNewOrgName(e.target.value)} />
                <button onClick={async () => { if (!newOrgName.trim()) return alert('Weka jina'); const r = await api.put('/my-org', { orgName: newOrgName }); setOrgName(newOrgName); localStorage.setItem('orgName', newOrgName); setEditOrg(false); alert(r.data.message); }} style={{ background: '#22c55e', color: '#fff', border: 'none', padding: '8px 10px', borderRadius: 8, cursor: 'pointer', fontSize: 12 }}>Sawa</button>
                <button onClick={() => setEditOrg(false)} style={{ background: '#ef4444', color: '#fff', border: 'none', padding: '8px 10px', borderRadius: 8, cursor: 'pointer', fontSize: 12 }}>✕</button>
              </div>
            )}
          </div>
        )}
        <div style={{ marginTop: 22 }}>
          {!isAdmin && !isOwner && <div onClick={() => changeTab('myinfo')} className={`sidebar-item ${tab === 'myinfo' ? 'active' : ''}`}>👤 Taarifa Zangu</div>}
          {isOwner && <div onClick={() => changeTab('owner')} className={`sidebar-item ${tab === 'owner' ? 'active' : ''}`}>👑 Mimi Mmiliki - Pesa Zangu</div>}
          {isOwner && <div onClick={() => changeTab('renew')} className={`sidebar-item ${tab === 'renew' ? 'active' : ''}`}>🔒 Thibitisha Malipo PBZ <span style={{ background: '#ef4444', color: '#fff', padding: '2px 8px', borderRadius: 20, fontSize: 11, fontWeight: 800 }}>{renewRequests.length}</span></div>}
          {(isAdmin || isOwner) && <div onClick={() => changeTab('bank')} className={`sidebar-item ${tab === 'bank' ? 'active' : ''}`}>💰 Malipo PBZ</div>}
          <div onClick={() => changeTab('orphans')} className={`sidebar-item ${tab === 'orphans' ? 'active' : ''}`}>👶 Mayatima <span>{orphans.length}</span></div>
          <div onClick={() => changeTab('umra')} className={`sidebar-item ${tab === 'umra' ? 'active' : ''}`}>🕋 Umra <span>{umra.length}</span></div>
          <div onClick={() => changeTab('vote')} className={`sidebar-item ${tab === 'vote' ? 'active' : ''}`}>🗳️ Vote / Kura <span>{candidates.length}</span></div>
          <div onClick={() => changeTab('feedback')} className={`sidebar-item ${tab === 'feedback' ? 'active' : ''}`}>💬 Feedback & Safari <span>{feedbacks.length}</span></div>
          <div onClick={() => changeTab('notices')} className={`sidebar-item ${tab === 'notices' ? 'active' : ''}`}>📢 Taarifa Muhimu</div>
          {isAdmin && <div onClick={() => changeTab('renew')} className={`sidebar-item ${tab === 'renew' ? 'active' : ''}`}>🔒 Mkodisha - Renew <span>{renewRequests.length}</span></div>}
          <div style={{ padding: '12px', fontSize: 12, color: '#cbd5e1', marginTop: 10, background: 'rgba(255,255,255,.08)', borderRadius: 12 }}>
            Umeingia kama: <b style={{ color: '#5eead4', textTransform: 'uppercase' }}>{userRole} - {userType}</b><br />
            <span style={{ fontSize: 11 }}>{userEmail}</span><br /><span style={{ fontSize: 11 }}>{myProfile?.fullName || ''}</span>
            {myPlan && (<><br /><span>Kifurushi: <b style={{ color: '#fbbf24' }}>{myPlan.plan}</b> - {myPlan.planName}</span><br /><span>Inaisha: {myPlan.expiry} - Siku {myPlan.daysLeft} zimebaki</span></>)}
          </div>
          <div onClick={() => { localStorage.clear(); window.location.href = '/'; }} style={{ padding: 12, marginTop: 15, cursor: 'pointer', color: '#fca5a5' }}>🚪 Toka</div>
        </div>
      </div>

      <div className="main">
        <div className="print-header"><h1>🕌 {orgName}</h1><p>KHADIJA DENGE - PBZ 0887387001 | Simu 0778709707 | Ripoti - {new Date().toLocaleDateString('sw-TZ')}</p><p>Yatima: {orphans.length} | Umra: {umra.length} | Malipo Verified: TZS {totalMapato.toLocaleString()}</p></div>
        <div className="topbar">
          <button className="hamburger" onClick={() => setSidebarOpen(!sidebarOpen)}>☰</button>
          <h2 style={{ margin: 0 }}>🕌 {orgName} - {tab === 'myinfo' ? 'Taarifa Zangu' : tab === 'owner' ? 'Mimi Mmiliki' : tab === 'bank' ? 'PBZ Bank' : tab === 'orphans' ? 'Mayatima' : tab === 'umra' ? 'Umra' : tab === 'vote' ? 'Vote' : tab === 'feedback' ? 'Feedback' : tab === 'renew' ? 'Mkodisha' : 'Taarifa'}</h2>
          <span className="admin-badge">{userRole} - {userType}</span>
        </div>

        {(isAdmin || isOwner) && passportExpiring.length > 0 && (
          <div style={{ background: '#fee2e2', border: '2px solid #ef4444', padding: 14, borderRadius: 14, marginBottom: 14 }}>
            <h3 style={{ margin: '0 0 8px', color: '#991b1b' }}>⚠️ PASSPORT ZINAZOKARIBIA KUISHIA ({passportExpiring.length})</h3>
            {passportExpiring.map((u) => (
              <div key={u.id} style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', background: '#fff', padding: 10, borderRadius: 10, marginTop: 6, flexWrap: 'wrap', gap: 8 }}>
                <div><b>{u.jinaKamili}</b> - {u.passportNo} - Inaisha: <b>{u.passportExpire}</b><br /><span style={{ fontSize: 12, color: u.isExpired ? '#ef4444' : '#b45309', fontWeight: 700 }}>{u.isExpired ? '🔴 IMESHAKWISHA!' : `🟠 Siku ${u.daysLeft} zimebaki`}</span></div>
                <a href={waLink(u.phone, `Assalam Alaikum ${u.jinaKamili}, Passport yako inaisha ${u.passportExpire}.`)} target="_blank" rel="noreferrer" style={{ background: '#25D366', color: '#fff', padding: '8px 12px', borderRadius: 8, textDecoration: 'none', fontWeight: 700, fontSize: 12 }}>💬 WhatsApp</a>
              </div>
            ))}
          </div>
        )}

        {(isAdmin || isOwner) && (
          <div style={{ display: 'flex', gap: 8, marginBottom: 12, flexWrap: 'wrap' }}>
            <button onClick={() => window.print()} className="btn" style={{ width: 'auto', background: '#0f172a', padding: '12px 18px' }}>🖨️ Print Ripoti</button>
            <button onClick={() => { const text = `🕌 ${orgName}\nYatima: ${orphans.length}\nUmra: ${umra.length}\nMalipo: TZS ${totalMapato.toLocaleString()}\nPBZ 0887387001`; window.open(`https://wa.me/?text=${encodeURIComponent(text)}`, '_blank'); }} style={{ background: '#25D366', color: '#fff', border: 'none', padding: '12px 18px', borderRadius: 12, fontWeight: 800, cursor: 'pointer' }}>💬 Tuma Ripoti WhatsApp</button>
          </div>
        )}

        {!isAdmin && !isOwner && tab === 'myinfo' && (
          <div>
            <div className="hero-card">
              {(myOrphan?.imageUrl || myUmraData?.imageUrl) ? <img src={myOrphan?.imageUrl || myUmraData?.imageUrl} alt="" className="hero-avatar" /> : <div className="hero-avatar" style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', fontSize: 36, background: '#fff' }}>👶</div>}
              <div style={{ flex: 1 }}>
                <p style={{ fontSize: 13, opacity: .9 }}>Karibu tena 👋 • {new Date().toLocaleDateString('sw-TZ', { weekday: 'long', day: 'numeric', month: 'long' })}</p>
                <h2>{myProfile?.fullName || userEmail}</h2>
                <p style={{ fontSize: 13, opacity: .9, marginTop: 4 }}>Aina: <b>{userType}</b> • Taasisi: <b>{orgName}</b></p>
                <div style={{ display: 'flex', gap: 6, marginTop: 12, flexWrap: 'wrap' }}>
                  <span style={{ background: '#fff', color: '#0f172a', padding: '6px 12px', borderRadius: 20, fontSize: 12, fontWeight: 800 }}>👶 {myOrphan ? 'Yatima ✓' : 'Hakuna'}</span>
                  <span style={{ background: '#fff', color: '#0f172a', padding: '6px 12px', borderRadius: 20, fontSize: 12, fontWeight: 800 }}>🕋 {myUmraData ? 'Umra ✓' : 'Hakuna'}</span>
                  <span style={{ background: 'rgba(255,255,255,.25)', padding: '6px 12px', borderRadius: 20, fontSize: 12, fontWeight: 700 }}>💬 Feedback: {myFeedbacks.length}</span>
                </div>
              </div>
            </div>

            {myOrphan && (
              <div className="table-wrap">
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: 8 }}><h3 style={{ margin: 0 }}>👶 Kadi Yangu ya Yatima</h3><span className="badge badge-green">{myOrphan.status}</span></div>
                <div style={{ display: 'grid', gap: 16, marginTop: 14 }} className="info-grid-wrap">
                  <div style={{ textAlign: 'center' }}>
                    {myOrphan.imageUrl && <img src={myOrphan.imageUrl} alt="" style={{ width: 130, height: 130, borderRadius: '50%', objectFit: 'cover', border: '4px solid #0f766e' }} />}
                    <div style={{ background: '#f8fafc', padding: 12, borderRadius: 14, marginTop: 10 }}>
                      <p style={{ fontSize: 12, fontWeight: 800 }}>📱 QR Yangu - Mdhamini Aniscan</p>
                      <img src={getQR(myOrphan.id)} alt="QR" style={{ width: 120, height: 120 }} />
                      <p style={{ fontSize: 11, color: '#64748b' }}>Mdhamini akiscan anaona taarifa zako</p>
                    </div>
                  </div>
                  <div className="info-grid">
                    <div className="info-item"><b>👶 JINA</b><span>{myOrphan.jinaKamili}</span></div>
                    <div className="info-item"><b>🎂 UMRI</b><span>{myOrphan.umri} Miaka • {myOrphan.jinsia}</span></div>
                    <div className="info-item"><b>🏫 SHULE</b><span>{myOrphan.shule}</span></div>
                    <div className="info-item"><b>📚 DARASA</b><span>{myOrphan.darasa}</span></div>
                    <div className="info-item"><b>👑 MDHAMINI</b><span>{myOrphan.mdhamini}</span></div>
                    <div className="info-item"><b>👩‍👧 MLEZI</b><span>{myOrphan.jinaMlezi}</span></div>
                    <div className="info-item"><b>📞 SIMU YA MLEZI</b><span>{myOrphan.simuMlezi}</span></div>
                    <div className="info-item"><b>📍 ANUANI</b><span>{myOrphan.anuani}</span></div>
                    <div className="info-item"><b>❤️ AFYA</b><span>{myOrphan.haliAfya}</span></div>
                    <div className="info-item"><b>🎒 MAHITAJI</b><span>{myOrphan.mahitaji}</span></div>
                  </div>
                </div>
              </div>
            )}

            {myUmraData && (() => {
              const jumla = myUmraData.jumlaYaGharama || 3500000;
              const amelipa = myUmraData.kiasiKilicholipwa || 0;
              const baki = jumla - amelipa;
              const percent = Math.min(100, Math.round((amelipa / jumla) * 100));
              const passportDays = myUmraData.passportExpire ? Math.ceil((new Date(myUmraData.passportExpire) - new Date()) / (1000 * 60 * 60 * 24)) : 999;
              const passportUrgent = passportDays < 90;
              const passportExpired = passportDays < 0;
              return (
                <div className="table-wrap" style={{ padding: 0, overflow: 'hidden', border: 'none' }}>
                  <div style={{ background: 'linear-gradient(135deg,#0f172a,#0f766e,#14b8a6)', color: '#fff', padding: 22, display: 'flex', gap: 16, alignItems: 'center', flexWrap: 'wrap' }}>
                    {myUmraData.imageUrl ? <img src={myUmraData.imageUrl} alt="" style={{ width: 92, height: 92, borderRadius: '50%', objectFit: 'cover', border: '4px solid #fff', boxShadow: '0 8px 20px rgba(0,0,0,.3)' }} /> : <div style={{ width: 92, height: 92, borderRadius: '50%', background: '#fff', display: 'flex', alignItems: 'center', justifyContent: 'center', fontSize: 40 }}>🕋</div>}
                    <div style={{ flex: 1, minWidth: 200 }}>
                      <p style={{ fontSize: 12, opacity: .9 }}>Assalam Alaikum, karibu kwenye Safari yako 🕋</p>
                      <h2 style={{ margin: '4px 0', fontSize: 26 }}>{myUmraData.jinaKamili}</h2>
                      <p style={{ fontSize: 13, opacity: .95 }}>Kifurushi: <b style={{ background: 'rgba(255,255,255,.2)', padding: '2px 8px', borderRadius: 10 }}>{myUmraData.kifurushi}</b> • Safari: <b>{myUmraData.tareheSafari || 'Haijapangwa'}</b></p>
                      <div style={{ display: 'flex', gap: 6, marginTop: 12, flexWrap: 'wrap' }}>
                        <span style={{ background: myUmraData.statusMalipo === 'VERIFIED' ? '#22c55e' : '#f59e0b', padding: '6px 14px', borderRadius: 20, fontSize: 12, fontWeight: 800, boxShadow: '0 4px 12px rgba(0,0,0,.2)' }}>
                          {myUmraData.statusMalipo === 'VERIFIED' ? '✅ UMEMALIZA KULIPA' : `🟠 UJAMALIZA - ${percent}%`}
                        </span>
                        <span style={{ background: 'rgba(255,255,255,.25)', padding: '6px 14px', borderRadius: 20, fontSize: 12 }}>🛂 Passport: {myUmraData.passportNo}</span>
                      </div>
                    </div>
                    <div style={{ background: '#fff', color: '#0f172a', padding: 12, borderRadius: 16, textAlign: 'center', minWidth: 110, boxShadow: '0 8px 20px rgba(0,0,0,.2)' }}>
                      <p style={{ fontSize: 11, fontWeight: 800, margin: 0 }}>📱 QR YANGU</p>
                      <img src={getQR(myUmraData.id)} alt="QR" style={{ width: 80, height: 80 }} />
                      <p style={{ fontSize: 10, margin: 0, color: '#64748b' }}>Scan uwanja wa ndege</p>
                    </div>
                  </div>
                  {passportUrgent && (
                    <div style={{ background: passportExpired ? '#fee2e2' : '#fef9c3', border: '2px solid ' + (passportExpired ? '#ef4444' : '#f59e0b'), padding: 12, margin: 14, borderRadius: 12 }}>
                      <b>{passportExpired ? '🔴 PASSPORT IMESHAKWISHA!' : '⚠️ PASSPORT INAKARIBIA KUISHIA!'}</b>
                      <p style={{ fontSize: 13, margin: '4px 0 0' }}>Passport yako inaisha <b>{myUmraData.passportExpire}</b> - {passportExpired ? `Imepita siku ${Math.abs(passportDays)}` : `Siku ${passportDays} tu zimebaki`}. Nenda Immigration u-renew HARAKA kabla ya safari.</p>
                    </div>
                  )}
                  <div style={{ padding: 16 }}>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap' }}>
                      <h3 style={{ margin: 0 }}>💰 Malipo Yangu</h3><b style={{ fontSize: 18, color: '#0f766e' }}>{percent}% Imelipwa</b>
                    </div>
                    <div style={{ height: 16, background: '#e2e8f0', borderRadius: 20, overflow: 'hidden', marginTop: 10 }}>
                      <div style={{ width: `${percent}%`, height: '100%', background: percent >= 100 ? '#22c55e' : 'linear-gradient(90deg,#f59e0b,#f97316)', borderRadius: 20, transition: 'width .5s' }}></div>
                    </div>
                    <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit,minmax(130px,1fr))', gap: 10, marginTop: 14 }}>
                      <div style={{ background: '#f8fafc', padding: 12, borderRadius: 12, textAlign: 'center' }}><p style={{ fontSize: 11, color: '#64748b', margin: 0 }}>JUMLA</p><b>TZS {jumla.toLocaleString()}</b></div>
                      <div style={{ background: '#ecfdf5', padding: 12, borderRadius: 12, textAlign: 'center' }}><p style={{ fontSize: 11, color: '#64748b', margin: 0 }}>✅ AMELIPA</p><b style={{ color: '#166534' }}>TZS {amelipa.toLocaleString()}</b></div>
                      <div style={{ background: baki > 0 ? '#fef2f2' : '#ecfdf5', padding: 12, borderRadius: 12, textAlign: 'center' }}><p style={{ fontSize: 11, color: '#64748b', margin: 0 }}>🔴 BAKI</p><b style={{ color: baki > 0 ? '#ef4444' : '#166534' }}>TZS {baki.toLocaleString()}</b></div>
                    </div>
                    {baki > 0 ? (
                      <div style={{ background: '#fffbeb', padding: 12, borderRadius: 12, marginTop: 12, textAlign: 'center' }}>
                        <p style={{ margin: 0, fontSize: 13 }}>Baki yako ni <b>TZS {baki.toLocaleString()}</b>. Lipa sasa ili safari yako isichelewe.</p>
                        <p style={{ margin: '6px 0 0', fontSize: 13 }}>PBZ: <b>KHADIJA DENGE - 0887387001</b> | Simu: <b>0778709707</b></p>
                        <a href={`https://wa.me/255778709707?text=${encodeURIComponent(`Assalam Alaikum, mimi ${myUmraData.jinaKamili}, nataka kulipa baki ya Umra TZS ${baki.toLocaleString()}.`)}`} target="_blank" rel="noreferrer" style={{ display: 'inline-block', background: '#25D366', color: '#fff', padding: '10px 18px', borderRadius: 20, fontWeight: 800, textDecoration: 'none', marginTop: 10 }}>💬 Lipa Baki Sasa - WhatsApp</a>
                      </div>
                    ) : (
                      <div style={{ background: '#ecfdf5', padding: 14, borderRadius: 12, marginTop: 12, textAlign: 'center' }}><h3 style={{ margin: 0, color: '#166534' }}>🎉 Hongera! Umemaliza Kulipa!</h3><p style={{ margin: '6px 0 0', fontSize: 13 }}>Safari yako iko tayari. Subiri taarifa za ndege kutoka kwa Admin.</p></div>
                    )}
                  </div>
                  <div style={{ padding: '0 16px 16px' }}>
                    <h3 style={{ margin: '0 0 10px' }}>📋 Taarifa Zangu Kamili</h3>
                    <div className="info-grid">
                      <div className="info-item"><b>🛂 PASSPORT NO</b><span>{myUmraData.passportNo}</span></div>
                      <div className="info-item"><b>📅 PASSPORT INAISHA</b><span>{myUmraData.passportExpire}</span></div>
                      <div className="info-item"><b>📞 SIMU YANGU</b><span>{myUmraData.phone}</span></div>
                      <div className="info-item"><b>📍 ANUANI</b><span>{myUmraData.anuani}</span></div>
                      <div className="info-item"><b>👨‍👩‍👧 MAHRAM</b><span>{myUmraData.jinaMahram || 'Hakuna'}</span></div>
                      <div className="info-item"><b>🚨 SIMU YA DHARURA</b><span>{myUmraData.simuDharura}</span></div>
                      <div className="info-item"><b>❤️ HALI YA AFYA</b><span>{myUmraData.haliAfya}</span></div>
                      <div className="info-item"><b>📅 TAREHE YA SAFARI</b><span>{myUmraData.tareheSafari}</span></div>
                      <div className="info-item"><b>✈️ KIFURUSHI</b><span>{myUmraData.kifurushi}</span></div>
                      <div className="info-item"><b>🏦 BANK</b><span>{myUmraData.bankName || 'PBZ'} - {myUmraData.transactionRef}</span></div>
                    </div>
                  </div>
                </div>
              );
            })()}
            {!myOrphan && !myUmraData && <div className="table-wrap"><p>Hakuna taarifa zako bado. Mwambie Admin akusajili na Email hii: <b>{userEmail}</b></p></div>}
            <div className="table-wrap">
              <h3>💬 Feedback Zangu</h3>
              {myFeedbacks.map((f) => (<div key={f.id} style={{ background: '#f8fafc', padding: 14, borderRadius: 12, marginTop: 8 }}><b>{f.type} - {f.jina}</b> <span className="badge badge-green">{f.status}</span><p style={{ marginTop: 6 }}>{f.message}</p></div>))}
              {myFeedbacks.length === 0 && <p>Hakuna feedback bado.</p>}
            </div>
          </div>
        )}

        {isOwner && tab === 'owner' && (
          <div>
            <div className="stats">
              <div className="stat-card"><p>💰 Pesa Nimepokea</p><h3>TZS {ownerIncome.totalIncome?.toLocaleString()}</h3></div>
              <div className="stat-card"><p>👑 Admin Wanaokodi</p><h3>{ownerAdmins.length}</h3></div>
              <div className="stat-card"><p>🎁 Demo</p><h3>{ownerIncome.demoCount}</h3></div>
              <div className="stat-card"><p>⏳ Wanasubiri</p><h3>{ownerIncome.pendingCount}</h3></div>
            </div>
            <div className="table-wrap"><h3>💰 Pesa kwa Kifurushi</h3><p>KIDOGO: TZS {(ownerIncome.byPlan?.KIDOGO || 0).toLocaleString()} | KAWAIDA: TZS {(ownerIncome.byPlan?.KAWAIDA || 0).toLocaleString()} | KUBWA: TZS {(ownerIncome.byPlan?.KUBWA || 0).toLocaleString()}</p></div>
            <div className="table-wrap">
              <h3>➕ Tengeneza Admin Mpya</h3>
              <form onSubmit={async (e) => { e.preventDefault(); await api.post('/owner/create-admin', newAdmin); setNewAdmin({ plan: 'KAWAIDA' }); fetchData(); alert('Admin ametengenezwa!'); }} style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 8 }}>
                <input className="input" placeholder="Jina la Admin" value={newAdmin.fullName || ''} onChange={(e) => setNewAdmin({ ...newAdmin, fullName: e.target.value })} required />
                <input className="input" placeholder="Jina la Taasisi" value={newAdmin.orgName || ''} onChange={(e) => setNewAdmin({ ...newAdmin, orgName: e.target.value })} required />
                <input className="input" placeholder="Email" value={newAdmin.email || ''} onChange={(e) => setNewAdmin({ ...newAdmin, email: e.target.value })} required />
                <input className="input" placeholder="Password" value={newAdmin.password || ''} onChange={(e) => setNewAdmin({ ...newAdmin, password: e.target.value })} required />
                <select className="input" value={newAdmin.plan || 'KAWAIDA'} onChange={(e) => setNewAdmin({ ...newAdmin, plan: e.target.value })}><option value="KIDOGO">KIDOGO - 50,000</option><option value="KAWAIDA">KAWAIDA - 100,000</option><option value="KUBWA">KUBWA - 200,000</option></select>
                <button className="btn" style={{ gridColumn: 'span 2' }}>Tengeneza Admin 👑</button>
              </form>
            </div>
            <div className="table-wrap"><h3>👑 Admin Wote</h3><table><thead><tr><th>Admin</th><th>Taasisi</th><th>Kifurushi</th><th>Bei</th><th>Mwisho</th><th>Status</th></tr></thead><tbody>{ownerAdmins.map((a) => (<tr key={a.id}><td>{a.fullName}<br /><span style={{ fontSize: 11 }}>{a.username}</span></td><td>{a.orgName}</td><td>{a.plan}</td><td>TZS {(a.planPrice || 0).toLocaleString()}</td><td>{a.subscriptionExpiry}</td><td><span className={`badge ${a.locked ? 'badge-red' : 'badge-green'}`}>{a.locked ? '🔒 UMEFUNGA' : '✅ INAFANYA'}</span></td></tr>))}</tbody></table></div>
          </div>
        )}

        {(isOwner || isAdmin) && tab === 'renew' && (
          <div className="table-wrap">
            <h3>🔒 Thibitisha Malipo - PBZ 0887387001 - KHADIJA DENGE</h3>
            <p style={{ fontSize: 12, background: '#fef2f2', padding: 10, borderRadius: 10 }}>⚠️ KABLA HUJATHIBITISHA: Angalia SMS yako ya PBZ. Kama pesa imeingia kweli, ndio ubonyeze Thibitisha.</p>
            <div style={{ display: 'flex', gap: 8, marginTop: 10 }}><button onClick={() => fetchData()} style={{ background: '#0f766e', color: '#fff', border: 'none', padding: '10px 16px', borderRadius: 10, fontWeight: 700, cursor: 'pointer' }}>🔄 Refresh - Angalia Maombi Mapya</button><span style={{ alignSelf: 'center', fontSize: 13 }}>Maombi yaliyopo: <b>{renewRequests.length}</b></span></div>
            {renewRequests.length === 0 && <div style={{ textAlign: 'center', padding: 20, background: '#f8fafc', borderRadius: 12, marginTop: 12 }}><p style={{ fontSize: 30 }}>📭</p><p><b>Hakuna ombi jipya.</b></p><p style={{ fontSize: 13, color: '#64748b' }}>Kama mteja ametuma na huoni, mwambie atume tena kwenye /pay na ahakikishe amejaza Email iliyosajiliwa.</p></div>}
            {renewRequests.map((req) => (
              <div key={req.id} style={{ display: 'flex', justifyContent: 'space-between', padding: 14, border: '1.5px solid #e2e8f0', borderRadius: 12, marginTop: 10, flexWrap: 'wrap', gap: 10, background: '#fff' }}>
                <div style={{ flex: 1, minWidth: 200 }}><b style={{ fontSize: 15 }}>{req.payerName} - {req.email}</b><br /><span style={{ fontSize: 13 }}>Kifurushi: <b>{req.plan}</b> • Ref: <b>{req.transactionRef}</b></span><br /><span style={{ fontSize: 13 }}>TZS <b>{req.amount?.toLocaleString()}</b> • {req.paymentMethod} • ID: {req.id}</span></div>
                <div style={{ display: 'flex', gap: 8, flexWrap: 'wrap', alignItems: 'center' }}>
                  {isOwner ? (<><button onClick={async () => { if (!window.confirm(`Umehakiki SMS ya PBZ? TZS ${req.amount} kutoka ${req.payerName} imeingia kweli?`)) return; try { const r = await api.put(`/owner/approve/${req.id}`); alert(r.data.message || '✅ Admin amefunguliwa!'); fetchData(); } catch (e) { alert('❌ Imeshindwa: ' + (e.response?.data?.message || 'Jaribu tena')); } }} style={{ background: '#22c55e', color: '#fff', border: 'none', padding: '12px 16px', borderRadius: 10, cursor: 'pointer', fontWeight: 800 }}>✅ Nimepokea - Fungua</button><button onClick={async () => { await api.put(`/owner/reject/${req.id}`); fetchData(); }} style={{ background: '#ef4444', color: '#fff', border: 'none', padding: '12px 16px', borderRadius: 10, cursor: 'pointer', fontWeight: 700 }}>❌ Kataa</button></>) : <span style={{ fontSize: 12, background: '#fef9c3', padding: '8px 12px', borderRadius: 8 }}>👀 Unaona tu - Owner ndio anathibitisha</span>}
                </div>
              </div>
            ))}
          </div>
        )}

        {(isAdmin || isOwner) && (
          <div className="stats">
            <div className="stat-card"><p>💰 PBZ Verified</p><h3>TZS {totalMapato.toLocaleString()}</h3></div>
            <div className="stat-card"><p>👶 Mayatima</p><h3>{orphans.length}</h3></div>
            <div className="stat-card"><p>🕋 Wanaohiji</p><h3>{umra.length}</h3></div>
            <div className="stat-card"><p>🗳️ Wagombea</p><h3>{candidates.length}</h3></div>
          </div>
        )}
        {(isAdmin || isOwner) && (
          <div className="year-box"><h3>📊 Ripoti kwa Mwaka - {orgName}</h3><div className="year-grid">{yearly.map((y) => (<div key={y.year} onClick={() => setYearFilter(String(y.year))} className="year-card" style={{ border: yearFilter === String(y.year) ? '2px solid #0f766e' : '1px solid #e2e8f0', background: yearFilter === String(y.year) ? '#ecfdf5' : '#f8fafc' }}><div style={{ fontWeight: 800 }}>{y.year}</div><div>👶 <b>{y.orphans}</b> Yatima</div><div>🕋 <b>{y.umra}</b> Umra</div></div>))}</div></div>
        )}

        {(isAdmin || isOwner) && tab === 'bank' && (
          <>
            <div className="table-wrap"><h3>Akaunti - PBZ - {orgName}</h3>{banks.map((b) => (<div key={b.id} className="bank-card"><h4>{b.bankName}</h4><p>{b.accountName} - {b.accountNumber}</p><p>Simu / WhatsApp: 0778709707</p></div>))}</div>
            <div className="table-wrap">
              <h3>Rekodi Malipo</h3>
              <form onSubmit={async (e) => { e.preventDefault(); await api.post('/bank/pay', { ...bform, amount: parseFloat(bform.amount), bankAccount: { id: banks[0]?.id } }); setBform({}); fetchData(); }} style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 8 }}>
                <input className="input" placeholder="Jina la Mlipa" value={bform.payerName || ''} onChange={(e) => setBform({ ...bform, payerName: e.target.value })} required />
                <input className="input" placeholder="Simu" value={bform.payerPhone || ''} onChange={(e) => setBform({ ...bform, payerPhone: e.target.value })} />
                <input className="input" type="number" placeholder="Kiasi" value={bform.amount || ''} onChange={(e) => setBform({ ...bform, amount: e.target.value })} required />
                <select className="input" value={bform.bankName || ''} onChange={(e) => setBform({ ...bform, bankName: e.target.value })}><option value="">Benki</option><option>PBZ Bank</option><option>NMB</option><option>CRDB Bank</option></select>
                <input className="input" placeholder="Transaction Ref" value={bform.transactionRef || ''} onChange={(e) => setBform({ ...bform, transactionRef: e.target.value })} required />
                <select className="input" value={bform.paymentFor || ''} onChange={(e) => setBform({ ...bform, paymentFor: e.target.value })}><option value="">Malipo ya?</option><option value="UMRA">Umra</option><option value="YATIMA_ADA">Yatima</option></select>
                <button className="btn" style={{ gridColumn: 'span 2' }}>Hifadhi</button>
              </form>
            </div>
            <div className="table-wrap"><h3>Malipo Yote</h3><FilterBar showStatus={true} /><table><thead><tr><th>Mlipa</th><th>TZS</th><th>Ref</th><th>Status</th><th>Action</th></tr></thead><tbody>{filteredPayments.map((p) => (<tr key={p.id}><td>{p.payerName}</td><td>{p.amount}</td><td>{p.transactionRef}</td><td><span className={`badge ${p.status === 'VERIFIED' ? 'badge-green' : p.status === 'REJECTED' ? 'badge-red' : 'badge-orange'}`}>{p.status}</span></td><td>{p.status === 'PENDING' && (<><button onClick={async () => { if (!window.confirm('Umehakiki SMS ya PBZ?')) return; await api.put(`/bank/payments/${p.id}/verify`); fetchData(); }} style={{ background: '#22c55e', color: '#fff', border: 'none', padding: '8px 12px', borderRadius: 7, fontWeight: 700 }}>✅ Thibitisha</button>{' '}<button onClick={async () => { await api.put(`/bank/payments/${p.id}/reject`); fetchData(); }} style={{ background: '#ef4444', color: '#fff', border: 'none', padding: '8px 12px', borderRadius: 7 }}>❌ Kataa</button></>)}{p.status !== 'PENDING' && <span style={{ fontSize: 12, color: '#64748b' }}>Imeshathibitishwa</span>}</td></tr>))}</tbody></table></div>
          </>
        )}

        {tab === 'orphans' && (
          <>
            {isAdmin && (
              <div className="table-wrap">
                <h3>➕ Sajili Yatima + Mpe Akaunti - {orgName}</h3>
                <p style={{ fontSize: 12, background: '#fffbeb', padding: 8, borderRadius: 8 }}>⚠️ Weka Email + Password ya Yatima. Hiyo ndio atatumia ku-login.</p>
                <form onSubmit={async (e) => { e.preventDefault(); if (!validateName(form.jinaKamili)) return alert('❌ Jina si sahihi'); if (!validateEmail(form.email)) return alert('❌ Email si sahihi'); if (!form.password || form.password.length < 6) return alert('❌ Password angalau 6'); try { await api.post('/orphans', { ...form, umri: parseInt(form.umri || 0) }); setForm({}); setPreview(''); fetchData(); alert('✅ Yatima amesajiliwa!'); } catch (err) { alert(err.response?.data?.message || 'Kuna kosa'); } }} style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 8, marginTop: 10 }}>
                  <label className="upload-box">{preview ? <img src={preview} alt="" className="upload-preview" /> : <div>📷 Picha ya Yatima</div>}<input type="file" accept="image/*" hidden onChange={(e) => uploadImage(e.target.files[0], setForm, setPreview)} /></label>
                  <input className="input" placeholder="Jina Kamili *" value={form.jinaKamili || ''} onChange={(e) => setForm({ ...form, jinaKamili: e.target.value })} required />
                  <input className="input" placeholder="Email ya Yatima ku-login *" value={form.email || ''} onChange={(e) => setForm({ ...form, email: e.target.value })} required />
                  <input className="input" placeholder="Password - mfano: 123456" value={form.password || ''} onChange={(e) => setForm({ ...form, password: e.target.value })} required />
                  <input className="input" type="date" value={form.tareheKuzaliwa || ''} onChange={(e) => { const v = e.target.value; const age = v ? new Date().getFullYear() - new Date(v).getFullYear() : ''; setForm({ ...form, tareheKuzaliwa: v, umri: age }); }} />
                  <input className="input" type="number" placeholder="Umri" value={form.umri || ''} onChange={(e) => setForm({ ...form, umri: e.target.value })} />
                  <select className="input" value={form.jinsia || ''} onChange={(e) => setForm({ ...form, jinsia: e.target.value })}><option value="">Jinsia</option><option>MVULANA</option><option>MSICHANA</option></select>
                  <input className="input" placeholder="Shule" value={form.shule || ''} onChange={(e) => setForm({ ...form, shule: e.target.value })} />
                  <input className="input" placeholder="Darasa" value={form.darasa || ''} onChange={(e) => setForm({ ...form, darasa: e.target.value })} />
                  <input className="input" placeholder="Jina la Mdhamini" value={form.mdhamini || ''} onChange={(e) => setForm({ ...form, mdhamini: e.target.value })} />
                  <input className="input" placeholder="Jina la Mlezi" value={form.jinaMlezi || ''} onChange={(e) => setForm({ ...form, jinaMlezi: e.target.value })} />
                  <input className="input" placeholder="Simu ya Mlezi" value={form.simuMlezi || ''} onChange={(e) => setForm({ ...form, simuMlezi: e.target.value })} />
                  <input className="input" placeholder="Anuani" value={form.anuani || ''} onChange={(e) => setForm({ ...form, anuani: e.target.value })} />
                  <select className="input" value={form.haliAfya || ''} onChange={(e) => setForm({ ...form, haliAfya: e.target.value })}><option value="">Hali ya Afya</option><option>AFYA NJEMA</option><option>ANA UGONJWA</option><option>ULEMAVU</option></select>
                  <select className="input" value={form.mahitaji || ''} onChange={(e) => setForm({ ...form, mahitaji: e.target.value })}><option value="">Mahitaji</option><option>ADA YA SHULE</option><option>MAVAZI</option><option>CHAKULA</option><option>DAWA</option><option>VYOTE</option></select>
                  <button className="btn" style={{ gridColumn: 'span 2' }}>Sajili Yatima ✅</button>
                </form>
              </div>
            )}
            <div className="table-wrap"><h3>Orodha ya Mayatima</h3><FilterBar /><table><thead><tr><th>Picha</th><th>Jina</th><th>Email</th><th>Umri</th><th>Shule</th><th>Mdhamini</th><th>QR</th><th>WhatsApp</th>{isAdmin && <th>Action</th>}</tr></thead><tbody>{filteredOrphans.map((o) => (<tr key={o.id}><td>{o.imageUrl ? <img src={o.imageUrl} alt="" className="avatar" /> : <div className="avatar">👶</div>}</td><td>{o.jinaKamili}</td><td>{o.email}</td><td>{o.umri}</td><td>{o.shule} - {o.darasa}</td><td>{o.mdhamini}</td><td style={{ textAlign: 'center' }}><a href={getQRLink(o.id)} target="_blank" rel="noreferrer"><img src={getQR(o.id)} alt="QR" style={{ width: 60, height: 60, border: '2px solid #0f766e', borderRadius: 10 }} /></a><br /><span style={{ fontSize: 10, color: '#0f766e', fontWeight: 700 }}>SCAN HAPA</span></td><td>{o.simuMlezi ? (<a href={waLink(o.simuMlezi, `Assalam Alaikum ${o.jinaMlezi}, Yatima wako ${o.jinaKamili} anaendelea vizuri.`)} target="_blank" rel="noreferrer" style={{ background: '#25D366', color: '#fff', padding: '6px 10px', borderRadius: 8, textDecoration: 'none', fontSize: 12, fontWeight: 700 }}>💬 Mlezi</a>) : <span style={{ fontSize: 11 }}>Hana simu</span>}</td>{isAdmin && <td><button onClick={async () => { await api.delete(`/orphans/${o.id}`); fetchData(); }} style={{ background: '#ef4444', color: '#fff', border: 'none', padding: '5px 10px', borderRadius: 7 }}>Futa</button></td>}</tr>))}</tbody></table></div>
          </>
        )}

        {tab === 'umra' && (
          <>
            {isAdmin && (
              <div className="table-wrap">
                <h3>➕ Sajili Mhujaji - {orgName}</h3>
                <form onSubmit={async (e) => { e.preventDefault(); if (!validateEmail(uform.email)) return alert('❌ Email si sahihi'); if (!validatePassport(uform.passportNo)) return alert('❌ Passport si sahihi'); try { await api.post('/umra', { ...uform, kiasiKilicholipwa: parseFloat(uform.kiasiKilicholipwa || 0) }); setUform({}); setUpreview(''); fetchData(); alert('✅ Mhujaji amesajiliwa!'); } catch (err) { alert(err.response?.data?.message || 'Kuna kosa'); } }} style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 8, marginTop: 10 }}>
                  <label className="upload-box">{upreview ? <img src={upreview} alt="" className="upload-preview" /> : <div>🕋📷 Picha ya Mhujaji</div>}<input type="file" accept="image/*" hidden onChange={(e) => uploadImage(e.target.files[0], setUform, setUpreview)} /></label>
                  <input className="input" placeholder="Jina Kamili *" value={uform.jinaKamili || ''} onChange={(e) => setUform({ ...uform, jinaKamili: e.target.value })} required />
                  <input className="input" placeholder="Email *" value={uform.email || ''} onChange={(e) => setUform({ ...uform, email: e.target.value })} required />
                  <input className="input" placeholder="Password" value={uform.password || ''} onChange={(e) => setUform({ ...uform, password: e.target.value })} required />
                  <input className="input" placeholder="Passport No *" value={uform.passportNo || ''} onChange={(e) => setUform({ ...uform, passportNo: e.target.value })} required />
                  <input className="input" type="date" value={uform.passportExpire || ''} onChange={(e) => setUform({ ...uform, passportExpire: e.target.value })} />
                  <input className="input" type="date" value={uform.tareheKuzaliwa || ''} onChange={(e) => setUform({ ...uform, tareheKuzaliwa: e.target.value })} />
                  <select className="input" value={uform.jinsia || ''} onChange={(e) => setUform({ ...uform, jinsia: e.target.value })}><option value="">Jinsia</option><option>MWANAUME</option><option>MWANAMKE</option></select>
                  <input className="input" placeholder="Simu" value={uform.phone || ''} onChange={(e) => setUform({ ...uform, phone: e.target.value })} />
                  <input className="input" placeholder="Anuani" value={uform.anuani || ''} onChange={(e) => setUform({ ...uform, anuani: e.target.value })} />
                  <input className="input" placeholder="Jina la Mahram" value={uform.jinaMahram || ''} onChange={(e) => setUform({ ...uform, jinaMahram: e.target.value })} />
                  <input className="input" placeholder="Simu ya Dharura" value={uform.simuDharura || ''} onChange={(e) => setUform({ ...uform, simuDharura: e.target.value })} />
                  <select className="input" value={uform.haliAfya || ''} onChange={(e) => setUform({ ...uform, haliAfya: e.target.value })}><option value="">Hali ya Afya</option><option>AFYA NJEMA</option><option>PRESHA</option><option>KISUKARI</option><option>ULEMAVU</option></select>
                  <input className="input" type="date" value={uform.tareheSafari || ''} onChange={(e) => setUform({ ...uform, tareheSafari: e.target.value })} />
                  <select className="input" value={uform.kifurushi || ''} onChange={(e) => setUform({ ...uform, kifurushi: e.target.value })}><option value="">Kifurushi</option><option>ECONOMY - 3,500,000</option><option>VIP - 4,500,000</option><option>5 STAR - 5,500,000</option></select>
                  <input className="input" type="number" placeholder="Jumla - 3500000" value={uform.jumlaYaGharama || ''} onChange={(e) => setUform({ ...uform, jumlaYaGharama: parseFloat(e.target.value) })} />
                  <input className="input" type="number" placeholder="Amelipa" value={uform.kiasiKilicholipwa || ''} onChange={(e) => setUform({ ...uform, kiasiKilicholipwa: e.target.value })} />
                  <input className="input" placeholder="Transaction Ref - PBZ" value={uform.transactionRef || ''} onChange={(e) => setUform({ ...uform, transactionRef: e.target.value })} />
                  <button className="btn" style={{ gridColumn: 'span 2' }}>Sajili Mhujaji 🕋</button>
                </form>
              </div>
            )}
            <div className="table-wrap">
              <h3>Orodha ya Wanaohiji</h3>
              <div style={{ display: 'flex', gap: 8, flexWrap: 'wrap', marginBottom: 10 }}>
                <button onClick={() => setStatusFilter('all')} style={{ padding: '8px 14px', borderRadius: 20, border: statusFilter === 'all' ? '2px solid #0f766e' : '1px solid #e2e8f0', background: statusFilter === 'all' ? '#ecfdf5' : '#fff', cursor: 'pointer', fontWeight: 700 }}>Wote ({umra.length})</button>
                <button onClick={() => setStatusFilter('PENDING')} style={{ padding: '8px 14px', borderRadius: 20, border: statusFilter === 'PENDING' ? '2px solid #f59e0b' : '1px solid #e2e8f0', background: statusFilter === 'PENDING' ? '#fef9c3' : '#fff', cursor: 'pointer', fontWeight: 700 }}>🟠 Hawajamaliza</button>
                <button onClick={() => setStatusFilter('VERIFIED')} style={{ padding: '8px 14px', borderRadius: 20, border: statusFilter === 'VERIFIED' ? '2px solid #22c55e' : '1px solid #e2e8f0', background: statusFilter === 'VERIFIED' ? '#dcfce7' : '#fff', cursor: 'pointer', fontWeight: 700 }}>✅ Wamemaliza</button>
              </div>
              <FilterBar showStatus={false} />
              <table><thead><tr><th>Picha</th><th>Jina</th><th>Email</th><th>Passport</th><th>Amelipa</th><th>Baki</th><th>Status</th><th>WhatsApp</th>{isAdmin && <th>Action</th>}</tr></thead><tbody>{filteredUmra.map((p) => { const baki = (p.jumlaYaGharama || 3500000) - (p.kiasiKilicholipwa || 0); return (<tr key={p.id}><td>{p.imageUrl ? <img src={p.imageUrl} alt="" className="avatar" /> : <div className="avatar">🕋</div>}</td><td>{p.jinaKamili}</td><td>{p.email}</td><td>{p.passportNo}<br /><span style={{ fontSize: 11 }}>{p.passportExpire}</span></td><td>{(p.kiasiKilicholipwa || 0).toLocaleString()}</td><td><b style={{ color: baki > 0 ? '#ef4444' : '#22c55e' }}>{baki.toLocaleString()}</b></td><td><span className={`badge ${p.statusMalipo === 'VERIFIED' ? 'badge-green' : 'badge-orange'}`}>{p.statusMalipo}</span></td><td><a href={waLink(p.phone, `Assalam Alaikum ${p.jinaKamili}, Baki yako ni TZS ${baki.toLocaleString()}. Lipa PBZ 0887387001.`)} target="_blank" rel="noreferrer" style={{ background: '#25D366', color: '#fff', padding: '6px 10px', borderRadius: 8, textDecoration: 'none', fontSize: 12, fontWeight: 700 }}>💬 Dai</a></td>{isAdmin && <td><button onClick={async () => { await api.delete(`/umra/${p.id}`); fetchData(); }} style={{ background: '#ef4444', color: '#fff', border: 'none', padding: '5px 10px', borderRadius: 7 }}>Futa</button></td>}</tr>); })}</tbody></table>
            </div>
          </>
        )}

        {tab === 'vote' && (
          <>
            {isAdmin && (<div className="table-wrap"><h3>🗳️ Ongeza Mgombea</h3><form onSubmit={async (e) => { e.preventDefault(); await api.post('/vote/candidates', { ...cform }); setCform({}); fetchData(); }} style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 8 }}><select className="input" value={cform.category || ''} onChange={(e) => setCform({ ...cform, category: e.target.value })} required><option value="">Kategoria</option><option value="MDHAMINI_BORA">🏆 Mdhamini Bora</option><option value="YATIMA_BORA">👶 Yatima Bora</option><option value="MHUJAJI_BORA">🕋 Mhujaji Bora</option></select><input className="input" placeholder="Jina" value={cform.jina || ''} onChange={(e) => setCform({ ...cform, jina: e.target.value })} required /><input className="input" placeholder="Maelezo" value={cform.description || ''} onChange={(e) => setCform({ ...cform, description: e.target.value })} /><button className="btn">Ongeza</button></form></div>)}
            <div className="table-wrap"><h3>🗳️ Pigia Kura - {orgName}</h3><select className="input" value={voteCat} onChange={(e) => setVoteCat(e.target.value)}><option value="MDHAMINI_BORA">🏆 Mdhamini Bora</option><option value="YATIMA_BORA">👶 Yatima Bora</option><option value="MHUJAJI_BORA">🕋 Mhujaji Bora</option></select><div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit,minmax(240px,1fr))', gap: 12, marginTop: 12 }}>{candidates.filter((c) => c.category === voteCat).map((c) => (<div key={c.id} style={{ border: '1px solid #e2e8f0', borderRadius: 12, padding: 14 }}><h3 style={{ textAlign: 'center' }}>{c.jina}</h3><p style={{ textAlign: 'center' }}>{c.description}</p><p style={{ textAlign: 'center', fontWeight: 800 }}>🗳️ Kura: {c.voteCount || 0}</p><button className="btn" onClick={async () => { const r = await api.post('/vote', { category: voteCat, candidateName: c.jina, candidateId: String(c.id) }); alert(r.data.message); fetchData(); }}>Pigia Kura ✅</button></div>))}</div><p>Kura ulizopiga: {myVotes.length}</p></div>
          </>
        )}

        {tab === 'feedback' && (
          <>
            {isAdmin && (<div className="table-wrap"><h3>✍️ Andika Feedback</h3><form onSubmit={async (e) => { e.preventDefault(); await api.post('/feedback', { ...fform, createdBy: fform.email }); setFform({}); fetchData(); }} style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 8 }}><input className="input" placeholder="Email ya User" value={fform.email || ''} onChange={(e) => setFform({ ...fform, email: e.target.value, createdBy: e.target.value })} required /><select className="input" value={fform.type || ''} onChange={(e) => setFform({ ...fform, type: e.target.value })}><option value="">Aina</option><option value="UMRA">Umra</option><option value="YATIMA">Yatima</option><option value="MALIPO">Malipo</option></select><input className="input" placeholder="Status" value={fform.status || ''} onChange={(e) => setFform({ ...fform, status: e.target.value })} /><input className="input" placeholder="Jina" value={fform.jina || ''} onChange={(e) => setFform({ ...fform, jina: e.target.value })} /><textarea className="input" style={{ gridColumn: 'span 2' }} placeholder="Ujumbe" value={fform.message || ''} onChange={(e) => setFform({ ...fform, message: e.target.value })} required /><button className="btn" style={{ gridColumn: 'span 2' }}>Tuma</button></form></div>)}
            <div className="table-wrap"><h3>Feedback - {orgName}</h3>{feedbacks.map((f) => (<div key={f.id} style={{ background: '#f8fafc', padding: 14, borderRadius: 12, marginTop: 8 }}><b>{f.jina} - {f.type}</b> <span>{f.status}</span><p>{f.message}</p></div>))}</div>
          </>
        )}

        {tab === 'notices' && (
          <div className="table-wrap"><h3>📢 Taarifa - {orgName}</h3><div style={{ background: '#fffbeb', padding: 14, borderRadius: 12, marginTop: 8 }}><b>🕌 Taarifa: Umra 2026</b><p>Safari ya Umra mwezi wa 12 imefunguliwa.</p></div><div style={{ background: '#fffbeb', padding: 14, borderRadius: 12, marginTop: 8 }}><b>👶 Mdhamini Mpya</b><p>Wadhamini wapya wamejitolea kulea yatima 20.</p></div></div>
        )}

      </div>
    </div>
  );
}

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/yatima/:id" element={<PublicYatima />} />
        <Route path="/login" element={<Login />} />
        <Route path="/pay" element={<Pay />} />
        <Route path="/dashboard" element={<ProtectedRoute><Dashboard /></ProtectedRoute>} />
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

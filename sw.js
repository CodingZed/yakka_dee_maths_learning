const ASSETS = [
  './',
  './index.html',
  './yakka_dee_maths_v1.html',
  './yakke_dee_figure1.jpg',
  './yakke_dee_figure2.jpg',
  './yakke_dee_figure3.jpg',
  './yeah yakka dee.m4a',
  './try again.m4a'
];
const CACHE_NAME = 'yakka-dee-v1';

self.addEventListener('install', e => {
  e.waitUntil(caches.open(CACHE_NAME).then(c => c.addAll(ASSETS)));
});

self.addEventListener('activate', e => {
  e.waitUntil(caches.keys().then(keys => Promise.all(keys.filter(k => k!==CACHE_NAME).map(k => caches.delete(k)))));
});

self.addEventListener('fetch', e => {
  const req = e.request;
  if (req.method !== 'GET') return;
  e.respondWith(
    caches.match(req).then(res => res || fetch(req).then(r => {
      const copy = r.clone();
      caches.open(CACHE_NAME).then(c => c.put(req, copy));
      return r;
    }).catch(() => res))
  );
});

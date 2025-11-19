const baseURL = 'http://localhost:4567';

async function request(path, options = {}) {
  const res = await fetch(`${baseURL}${path}`, {
    headers: { 'Content-Type': 'application/json' },
    ...options
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(`${res.status} - ${text}`);
  }
  const contentType = res.headers.get('content-type') || '';
  if (contentType.includes('application/json')) return res.json();
  return null;
}

export const CategoriaAPI = {
  list: () => request('/categorias'),
  get: (id) => request(`/categorias/${id}`),
  create: (data) => request('/categorias', { method: 'POST', body: JSON.stringify(data) }),
  update: (id, data) => request(`/categorias/${id}`, { method: 'PUT', body: JSON.stringify(data) }),
  remove: (id) => request(`/categorias/${id}`, { method: 'DELETE' })
};

export const ProdutoAPI = {
  list: () => request('/produtos'),
  get: (id) => request(`/produtos/${id}`),
  create: (data) => request('/produtos', { method: 'POST', body: JSON.stringify(data) }),
  update: (id, data) => request(`/produtos/${id}`, { method: 'PUT', body: JSON.stringify(data) }),
  remove: (id) => request(`/produtos/${id}`, { method: 'DELETE' })
};
 
const API ={ CategoriaAPI, ProdutoAPI };

export default API;
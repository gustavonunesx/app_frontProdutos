const API_BASE = "http://localhost:8080/produto";

export async function listarProdutos() {
    const resp = await fetch(API_BASE);
    return resp.json();
}

export async function buscarProduto(id) {
    const resp = await fetch(`${API_BASE}/${id}`);
    return resp.json();
}

export async function criarProduto(produto) {
    return fetch(API_BASE, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(produto)
    });
}

export async function editarProduto(id, produto) {
    return fetch(`${API_BASE}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(produto)
    });
}

export async function removerProduto(id) {
    return fetch(`${API_BASE}/${id}`, { method: "DELETE" });
}

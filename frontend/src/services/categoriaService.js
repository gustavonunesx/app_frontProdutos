const API_BASE = "http://localhost:8080/categoria";

export async function listarCategorias() {
    const resp = await fetch(API_BASE);
    return resp.json();
}

export async function buscarCategoria(id) {
    const resp = await fetch(`${API_BASE}/${id}`);
    return resp.json();
}

export async function criarCategoria(categoria) {
    return fetch(API_BASE, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(categoria)
    });
}

export async function editarCategoria(id, categoria) {
    return fetch(`${API_BASE}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(categoria)
    });
}

export async function removerCategoria(id) {
    return fetch(`${API_BASE}/${id}`, { method: "DELETE" });
}

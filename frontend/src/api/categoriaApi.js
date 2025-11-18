const API_URL = "http://localhost:8080/categoria";

export async function listarCategorias() {
  const resposta = await fetch(API_URL);
  return resposta.json();
}

export async function buscarCategoria(id) {
  const resposta = await fetch(`${API_URL}/${id}`);
  return resposta.json();
}

export async function criarCategoria(dados) {
  const resposta = await fetch(API_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(dados),
  });
  return resposta.json();
}

export async function atualizarCategoria(id, dados) {
  const resposta = await fetch(`${API_URL}/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(dados),
  });
  return resposta.json();
}

export async function excluirCategoria(id) {
  await fetch(`${API_URL}/${id}`, {
    method: "DELETE",
  });
}

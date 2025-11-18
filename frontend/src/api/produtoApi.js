const API_URL = "http://localhost:8080/produto";

export async function listarProdutos() {
  const resposta = await fetch(API_URL);
  return resposta.json();
}

export async function buscarProduto(id) {
  const resposta = await fetch(`${API_URL}/${id}`);
  return resposta.json();
}

export async function criarProduto(dados) {
  const resposta = await fetch(API_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(dados),
  });
  return resposta.json();
}

export async function atualizarProduto(id, dados) {
  const resposta = await fetch(`${API_URL}/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(dados),
  });
  return resposta.json();
}

export async function excluirProduto(id) {
  await fetch(`${API_URL}/${id}`, {
    method: "DELETE",
  });
}

import { useState } from "react";
import { criarProduto } from "../../api/produtoApi";

export default function NovoProduto() {
  const [nome, setNome] = useState("");
  const [preco, setPreco] = useState("");
  const [estoque, setEstoque] = useState("");

  function salvar(e) {
    e.preventDefault();

    criarProduto({
      nome,
      preco: parseFloat(preco),
      estoque: parseInt(estoque),
    }).then(() => alert("Produto criado!"));
  }

  return (
    <div>
      <h2>Novo Produto</h2>
      <form onSubmit={salvar}>
        <input
          type="text"
          placeholder="Nome"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
        />

        <input
          type="number"
          placeholder="Preço"
          value={preco}
          onChange={(e) => setPreco(e.target.value)}
        />

        <input
          type="number"
          placeholder="Estoque"
          value={estoque}
          onChange={(e) => setEstoque(e.target.value)}
        />

        <button type="submit">Salvar</button>
      </form>
    </div>
  );
}

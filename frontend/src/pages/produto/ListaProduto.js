import { useEffect, useState } from "react";
import { listarProdutos } from "../../api/produtoApi";

export default function ListaProduto() {
  const [produtos, setProdutos] = useState([]);

  useEffect(() => {
    listarProdutos().then(setProdutos);
  }, []);

  return (
    <div>
      <h2>Listar Produtos</h2>
      <ul>
        {produtos.map((p) => (
          <li key={p.id}>
            ID: {p.id} | Nome: {p.nome} | Preço: R${p.preco} | Estoque: {p.estoque}
          </li>
        ))}
      </ul>
    </div>
  );
}

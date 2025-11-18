import { useState } from "react";
import { criarCategoria } from "../../api/categoriaApi";

export default function NovaCategoria() {
  const [nome, setNome] = useState("");

  function salvar(e) {
    e.preventDefault();
    criarCategoria({ nome }).then(() => alert("Categoria criada!"));
  }

  return (
    <div>
      <h2>Nova Categoria</h2>
      <form onSubmit={salvar}>
        <input
          type="text"
          placeholder="Nome"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
        />
        <button type="submit">Salvar</button>
      </form>
    </div>
  );
}

import { useEffect, useState } from "react";
import { listarCategorias } from "../../api/categoriaApi";

export default function ListaCategoria() {
  const [categorias, setCategorias] = useState([]);

  useEffect(() => {
    listarCategorias().then(setCategorias);
  }, []);

  return (
    <div>
      <h2>Listar Categorias</h2>
      <ul>
        {categorias.map((c) => (
          <li key={c.id}>
            ID: {c.id} | Nome: {c.nome}
          </li>
        ))}
      </ul>
    </div>
  );
}

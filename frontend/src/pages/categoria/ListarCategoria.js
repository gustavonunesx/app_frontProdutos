import { useEffect, useState } from "react";
import { listarCategorias } from "../../services/categoriaService";
import { Link } from "react-router-dom";

export default function ListarCategoria() {

    const [lista, setLista] = useState([]);

    useEffect(() => {
        carregar();
    }, []);

    async function carregar() {
        const dados = await listarCategorias();
        setLista(dados);
    }

    return (
        <div>
            <h1>Lista de Categorias</h1>
            
            <Link to="/categorias/novo">Nova Categoria</Link>

            <table border="1" style={{ marginTop: "20px" }}>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {lista.map(c => (
                        <tr key={c.id}>
                            <td>{c.id}</td>
                            <td>{c.nome}</td>
                            <td>
                                <Link to={`/categorias/editar/${c.id}`}>Editar</Link> |{" "}
                                <Link to={`/categorias/excluir/${c.id}`}>Excluir</Link>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

import { useEffect, useState } from "react";
import { listarProdutos } from "../../services/produtoService";
import { Link } from "react-router-dom";

export default function ListarProduto() {

    const [lista, setLista] = useState([]);

    useEffect(() => {
        carregar();
    }, []);

    async function carregar() {
        const dados = await listarProdutos();
        setLista(dados);
    }

    return (
        <div>
            <h1>Lista de Produtos</h1>

            <Link to="/produtos/novo">Novo Produto</Link>

            <table border="1" style={{ marginTop: "20px" }}>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Preço</th>
                        <th>Estoque</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {lista.map(p => (
                        <tr key={p.id}>
                            <td>{p.id}</td>
                            <td>{p.nome}</td>
                            <td>R$ {p.preco.toFixed(2)}</td>
                            <td>{p.estoque}</td>
                            <td>
                                <Link to={`/produtos/editar/${p.id}`}>Editar</Link> |{" "}
                                <Link to={`/produtos/excluir/${p.id}`}>Excluir</Link>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

        </div>
    );
}

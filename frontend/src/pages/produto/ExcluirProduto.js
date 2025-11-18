import { useEffect, useState, useCallback } from "react";
import { buscarProduto, removerProduto } from "../../services/produtoService";
import { useNavigate, useParams } from "react-router-dom";

export default function ExcluirProduto() {

    const { id } = useParams();
    const navigate = useNavigate();
    const [produto, setProduto] = useState(null);

    const carregar = useCallback(async () => {
        const dados = await buscarProduto(id);
        setProduto(dados);
    }, [id]);

    useEffect(() => {
        carregar();
    }, [carregar]);

    async function excluir() {
        await removerProduto(id);
        navigate("/produtos");
    }

    if (!produto) return <h3>Carregando...</h3>;

    return (
        <div>
            <h1>Excluir Produto</h1>
            <p>Tem certeza que deseja excluir este produto?</p>

            <strong>ID:</strong> {produto.id}<br />
            <strong>Nome:</strong> {produto.nome}<br />
            <strong>Preço:</strong> {produto.preco}<br />
            <strong>Estoque:</strong> {produto.estoque}<br /><br />

            <button onClick={excluir}>Sim, excluir</button> &nbsp;
            <button onClick={() => navigate("/produtos")}>Cancelar</button>
        </div>
    );
}

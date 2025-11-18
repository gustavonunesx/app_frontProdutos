import { useState, useEffect, useCallback } from "react";
import { buscarProduto, editarProduto } from "../../services/produtoService";
import { useNavigate, useParams } from "react-router-dom";

export default function EditarProduto() {

    const { id } = useParams();
    const navigate = useNavigate();

    const [nome, setNome] = useState("");
    const [preco, setPreco] = useState("");
    const [estoque, setEstoque] = useState("");

    const carregar = useCallback(async () => {
        const dados = await buscarProduto(id);
        setNome(dados.nome);
        setPreco(dados.preco);
        setEstoque(dados.estoque);
    }, [id]);

    useEffect(() => {
        carregar();
    }, [carregar]);

    async function salvar(e) {
        e.preventDefault();

        const produto = {
            nome,
            preco: parseFloat(preco),
            estoque: parseInt(estoque)
        };

        await editarProduto(id, produto);

        navigate("/produtos");
    }

    return (
        <div>
            <h1>Editar Produto</h1>
            <form onSubmit={salvar}>
                <label>Nome:</label><br />
                <input 
                    type="text"
                    value={nome}
                    onChange={(e) => setNome(e.target.value)}
                    required
                /><br /><br />

                <label>Preço:</label><br />
                <input 
                    type="number"
                    step="0.01"
                    value={preco}
                    onChange={(e) => setPreco(e.target.value)}
                    required
                /><br /><br />

                <label>Estoque:</label><br />
                <input 
                    type="number"
                    value={estoque}
                    onChange={(e) => setEstoque(e.target.value)}
                    required
                /><br /><br />

                <button type="submit">Salvar Alterações</button>
            </form>
        </div>
    );
}

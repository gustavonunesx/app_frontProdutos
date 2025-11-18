import { useState } from "react";
import { criarProduto } from "../../services/produtoService";
import { useNavigate } from "react-router-dom";

export default function CriarProduto() {

    const [nome, setNome] = useState("");
    const [preco, setPreco] = useState("");
    const [estoque, setEstoque] = useState("");
    const navigate = useNavigate();

    async function salvar(e) {
        e.preventDefault();

        const produto = {
            nome,
            preco: parseFloat(preco),
            estoque: parseInt(estoque)
        };

        await criarProduto(produto);

        navigate("/produtos");
    }

    return (
        <div>
            <h1>Criar Produto</h1>

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

                <button type="submit">Salvar Produto</button>
            </form>
        </div>
    );
}

import { useState } from "react";
import { criarCategoria } from "../../services/categoriaService";
import { useNavigate } from "react-router-dom";

export default function CriarCategoria() {

    const [nome, setNome] = useState("");
    const navigate = useNavigate();

    async function salvar(e) {
        e.preventDefault();

        const categoria = { nome };

        await criarCategoria(categoria);

        navigate("/categorias");
    }

    return (
        <div>
            <h1>Criar Categoria</h1>

            <form onSubmit={salvar}>
                <label>Nome da Categoria:</label><br />
                <input 
                    type="text"
                    value={nome}
                    onChange={(e) => setNome(e.target.value)}
                    required
                /><br /><br />

                <button type="submit">Salvar</button>
            </form>
        </div>
    );
}

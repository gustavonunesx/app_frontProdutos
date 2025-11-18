import { useState, useEffect, useCallback } from "react";
import { buscarCategoria, editarCategoria } from "../../services/categoriaService";
import { useNavigate, useParams } from "react-router-dom";

export default function EditarCategoria() {

    const { id } = useParams();
    const navigate = useNavigate();
    const [nome, setNome] = useState("");

    const carregar = useCallback(async () => {
        const dados = await buscarCategoria(id);
        setNome(dados.nome);
    }, [id]);

    useEffect(() => {
        carregar();
    }, [carregar]);

    async function salvar(e) {
        e.preventDefault();

        const categoria = { nome };

        await editarCategoria(id, categoria);

        navigate("/categorias");
    }

    return (
        <div>
            <h1>Editar Categoria</h1>
            <form onSubmit={salvar}>
                <label>Nome da Categoria:</label><br />
                <input 
                    type="text"
                    value={nome}
                    onChange={(e) => setNome(e.target.value)}
                    required
                /><br /><br />
                <button type="submit">Salvar Alterações</button>
            </form>
        </div>
    );
}

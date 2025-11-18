import { useEffect, useState, useCallback } from "react";
import { buscarCategoria, removerCategoria } from "../../services/categoriaService";
import { useNavigate, useParams } from "react-router-dom";

export default function ExcluirCategoria() {

    const { id } = useParams();
    const navigate = useNavigate();
    const [categoria, setCategoria] = useState(null);

    const carregar = useCallback(async () => {
        const dados = await buscarCategoria(id);
        setCategoria(dados);
    }, [id]);

    useEffect(() => {
        carregar();
    }, [carregar]);

    async function excluir() {
        await removerCategoria(id);
        navigate("/categorias");
    }

    if (!categoria) return <h3>Carregando...</h3>;

    return (
        <div>
            <h1>Excluir Categoria</h1>
            <p>Tem certeza que deseja excluir esta categoria?</p>
            <strong>ID:</strong> {categoria.id}<br />
            <strong>Nome:</strong> {categoria.nome}<br /><br />
            <button onClick={excluir}>Sim, excluir</button> &nbsp;
            <button onClick={() => navigate("/categorias")}>Cancelar</button>
        </div>
    );
}

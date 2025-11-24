import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ProdutoAPI, CategoriaAPI } from '../../services/api';

export default function ProdutoList(){
  const [produtos, setProdutos] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(()=>{ fetchAll(); },[]);

  async function fetchAll(){
    setLoading(true);
    try{ const data = await ProdutoAPI.list(); setProdutos(data); }
    catch(e){ alert(e.message); }
    setLoading(false);
  }

  async function handleDelete(id){
    if(!window.confirm('Confirma exclusão?')) return;
    try{ await ProdutoAPI.remove(id); fetchAll(); }
    catch(e){ alert(e.message); }
  }

  return (
    <div className="card">
      <div style={{display:'flex',justifyContent:'space-between',alignItems:'center'}}>
        <h3>Produtos</h3>
        <div>
          <button className="btn btn-primary" onClick={()=>navigate('/produtos/novo')}>Novo</button>
          <button className="btn" onClick={fetchAll} style={{marginLeft:8}}>Atualizar</button>
        </div>
      </div>

      {loading ? <p>Carregando...</p> : (
        <table className="table">
          <thead><tr><th>ID</th><th>Nome</th><th>Preço</th><th>Categoria</th><th>Ações</th></tr></thead>
          <tbody>
            {produtos.map(p=> (
              <tr key={p.id}>
                <td>{p.id}</td>
                <td>{p.nome}</td>
                <td>{p.preco}</td>
                <td>{p.categoria?.nome ?? '-'}</td>
                <td>
                  <button className="btn" onClick={()=>navigate(`/produtos/editar/${p.id}`)}>Editar</button>
                  <button className="btn btn-danger" onClick={()=>handleDelete(p.id)} style={{marginLeft:8}}>Excluir</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

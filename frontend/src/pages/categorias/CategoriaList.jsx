import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { CategoriaAPI } from '../../services/api';

export default function CategoriaList(){
  const [categorias, setCategorias] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(()=>{ fetchAll(); },[]);

  async function fetchAll(){
    setLoading(true);
    try{
      const data = await CategoriaAPI.list();
      setCategorias(data);
    }catch(err){ alert(err.message); }
    setLoading(false);
  }

  async function handleDelete(id){
    if(!window.confirm('Confirma exclusão?')) return;
    try{ await CategoriaAPI.remove(id); fetchAll(); }
    catch(e){ alert(e.message); }
  }

  return (
    <div className="card">
      <div style={{display:'flex',justifyContent:'space-between',alignItems:'center'}}>
        <h3>Categorias</h3>
        <div>
          <button className="btn btn-primary" onClick={()=>navigate('/categorias/novo')}>Nova</button>
          <button className="btn" onClick={fetchAll} style={{marginLeft:8}}>Atualizar</button>
        </div>
      </div>

      {loading ? <p>Carregando...</p> : (
        <table className="table">
          <thead><tr><th>ID</th><th>Nome</th><th>Ações</th></tr></thead>
          <tbody>
            {categorias.map(c=> (
              <tr key={c.id}>
                <td>{c.id}</td>
                <td>{c.nome}</td>
                <td>
                  <button className="btn" onClick={()=>navigate(`/categorias/editar/${c.id}`)}>Editar</button>
                  <button className="btn btn-danger" onClick={()=>handleDelete(c.id)} style={{marginLeft:8}}>Excluir</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

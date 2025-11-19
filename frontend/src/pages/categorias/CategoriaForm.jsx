import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { CategoriaAPI } from '../../services/api';

export default function CategoriaForm(){
  const { id } = useParams();
  const navigate = useNavigate();
  const [nome, setNome] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(()=>{ if(id) load(); },[id]);

  async function load(){
    try{
      const data = await CategoriaAPI.get(id);
      setNome(data.nome || '');
    }catch(e){ alert(e.message); }
  }

  async function handleSubmit(e){
    e.preventDefault();
    setLoading(true);
    try{
      if(id) await CategoriaAPI.update(id, { nome });
      else await CategoriaAPI.create({ nome });
      navigate('/categorias');
    }catch(e){ alert(e.message); }
    setLoading(false);
  }

  return (
    <div className="card">
      <h3>{id ? 'Editar' : 'Nova'} Categoria</h3>
      <form onSubmit={handleSubmit}>
        <div className="form-row">
          <input value={nome} onChange={e=>setNome(e.target.value)} placeholder="Nome" required />
        </div>
        <div style={{display:'flex',gap:8}}>
          <button className="btn btn-primary" type="submit">Salvar</button>
          <button className="btn" type="button" onClick={()=>navigate('/categorias')}>Cancelar</button>
        </div>
      </form>
    </div>
  );
}

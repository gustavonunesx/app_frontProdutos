import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ProdutoAPI, CategoriaAPI } from '../../services/api';

export default function ProdutoForm(){
  const { id } = useParams();
  const navigate = useNavigate();
  const [nome, setNome] = useState('');
  const [preco, setPreco] = useState('');
  const [categoriaId, setCategoriaId] = useState('');
  const [categorias, setCategorias] = useState([]);
  const [estoque, setEstoque] = useState('');

  useEffect(()=>{ fetchCategorias(); if(id) load(); },[id]);

  async function fetchCategorias(){ try{ const c = await CategoriaAPI.list(); setCategorias(c); }catch(e){} }
  async function load(){
    try{
      const p = await ProdutoAPI.get(id);
      setNome(p.nome || '');
      setPreco(p.preco ?? '');
      setCategoriaId(p.categoria?.id ?? '');
    }catch(e){ alert(e.message); }
  }

  async function handleSubmit(e){
    e.preventDefault();
    try{
      const data = {
        nome,
        preco: Number(preco),
        estoque: Number(estoque),
        categoria: categoriaId ? { id: Number(categoriaId) } : null
      };

      if(id) await ProdutoAPI.update(id,data); else await ProdutoAPI.create(data);
      navigate('/produtos');
    }catch(e){ alert(e.message); }
  }

  return (
    <div className="card">
      <h3>{id ? 'Editar' : 'Novo'} Produto</h3>
      <form onSubmit={handleSubmit}>
        <div className="form-row">
          <input value={nome} onChange={e=>setNome(e.target.value)} placeholder="Nome" required />
        </div>
        <div className="form-row">
          <input
            value={estoque} onChange={e => setEstoque(e.target.value)} placeholder="Estoque" required
          />
        </div>
        <div className="form-row">
          <input value={preco} onChange={e=>setPreco(e.target.value)} placeholder="Preço" required />
          <select value={categoriaId} onChange={e=>setCategoriaId(e.target.value)} required>
            <option value="">Escolha uma categoria</option>
            {categorias.map(c=> <option key={c.id} value={c.id}>{c.nome}</option>)}
          </select>
        </div>
        <div style={{display:'flex',gap:8}}>
          <button className="btn btn-primary" type="submit">Salvar</button>
          <button className="btn" type="button" onClick={()=>navigate('/produtos')}>Cancelar</button>
        </div>
      </form>
    </div>
  );
}

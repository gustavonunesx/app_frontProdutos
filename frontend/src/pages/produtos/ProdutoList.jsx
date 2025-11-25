import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ProdutoAPI } from '../../services/api';

export default function ProdutoList(){
  const [produtos, setProdutos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [erro, setErro] = useState(null); // ← NOVO: guarda mensagem de erro
  const navigate = useNavigate();

  useEffect(()=>{ 
    console.log('🚀 Componente montado, buscando produtos...');
    fetchAll(); 
  },[]);

  async function fetchAll(){
    console.log('📡 Iniciando busca de produtos...');
    setLoading(true);
    setErro(null); // Limpa erro anterior
    
    try{
      const data = await ProdutoAPI.list();
      console.log('✅ Produtos recebidos:', data); // ← Mostra os dados no console
      console.log('📊 Quantidade de produtos:', data.length);
      
      setProdutos(data);
    }catch(e){
      console.error('❌ Erro ao buscar produtos:', e); // ← Mostra erro detalhado
      setErro(e.message);
      alert(e.message);
    }
    
    setLoading(false);
    console.log('🏁 Busca finalizada');
  }

  async function handleDelete(id){
    if(!window.confirm('Confirma exclusão?')) return;
    
    console.log('🗑️ Deletando produto ID:', id);
    
    try{
      await ProdutoAPI.remove(id);
      console.log('✅ Produto deletado com sucesso');
      fetchAll();
    }catch(e){
      console.error('❌ Erro ao deletar:', e);
      alert(e.message);
    }
  }

  // ← NOVO: Mostra diferentes mensagens dependendo do estado
  if(loading) {
    return (
      <div className="card">
        <p>⏳ Carregando produtos...</p>
      </div>
    );
  }

  if(erro) {
    return (
      <div className="card">
        <h3 style={{color:'red'}}>❌ Erro ao carregar produtos</h3>
        <p>{erro}</p>
        <button className="btn btn-primary" onClick={fetchAll}>
          Tentar Novamente
        </button>
      </div>
    );
  }

  if(produtos.length === 0) {
    return (
      <div className="card">
        <div style={{display:'flex',justifyContent:'space-between',alignItems:'center'}}>
          <h3>Produtos</h3>
          <div>
            <button className="btn btn-primary" onClick={()=>navigate('/produtos/novo')}>
              Novo
            </button>
            <button className="btn" onClick={fetchAll} style={{marginLeft:8}}>
              Atualizar
            </button>
          </div>
        </div>
        <p style={{textAlign:'center', padding:'2rem', color:'#6b7280'}}>
          📦 Nenhum produto cadastrado ainda. Clique em "Novo" para adicionar!
        </p>
      </div>
    );
  }

  return (
    <div className="card">
      <div style={{display:'flex',justifyContent:'space-between',alignItems:'center'}}>
        <h3>Produtos ({produtos.length})</h3>
        <div>
          <button className="btn btn-primary" onClick={()=>navigate('/produtos/novo')}>
            Novo
          </button>
          <button className="btn" onClick={fetchAll} style={{marginLeft:8}}>
            Atualizar
          </button>
        </div>
      </div>

      <table className="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Preço</th>
            <th>Estoque</th>
            <th>Categoria</th>
            <th>Ações</th>
          </tr>
        </thead>

        <tbody>
          {produtos.map(p=>(
            <tr key={p.id}>
              <td>{p.id}</td>
              <td>{p.nome}</td>
              <td>R$ {Number(p.preco).toFixed(2)}</td>
              <td>{p.estoque}</td>
              <td>{p.categoria?.nome ?? '-'}</td>

              <td>
                <button 
                  className="btn" 
                  onClick={() => navigate(`/produtos/editar/${p.id}`)}
                >
                  Editar
                </button>

                <button
                  className="btn btn-danger"
                  onClick={() => handleDelete(p.id)}
                  style={{marginLeft:8}}
                >
                  Excluir
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
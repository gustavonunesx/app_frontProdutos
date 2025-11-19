import React from 'react';
import { NavLink } from 'react-router-dom';

export default function Sidebar(){
  return (
    <aside className="sidebar">
      <div className="logo">app_frontProdutos</div>
      <nav className="nav">
        <NavLink to="/categorias">Categorias</NavLink>
        <NavLink to="/produtos">Produtos</NavLink>
      </nav>
      <div style={{position:'absolute',bottom:16,left:16,fontSize:12,color:'#9ca3af'}}>Feito para a disciplina</div>
    </aside>
  );
}

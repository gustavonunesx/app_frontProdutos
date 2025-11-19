import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import Sidebar from './components/Sidebar';
import Header from './components/Header';
import CategoriaList from './pages/categorias/CategoriaList';
import CategoriaForm from './pages/categorias/CategoriaForm';
import ProdutoList from './pages/produtos/ProdutoList';
import ProdutoForm from './pages/produtos/ProdutoForm';

export default function App(){
  return (
    <div className="app-root">
      <Sidebar />
      <div className="main">
        <Header />
        <div className="content">
          <Routes>
            <Route path="/" element={<Navigate to="/categorias" replace />} />

            <Route path="/categorias" element={<CategoriaList />} />
            <Route path="/categorias/novo" element={<CategoriaForm />} />
            <Route path="/categorias/editar/:id" element={<CategoriaForm />} />

            <Route path="/produtos" element={<ProdutoList />} />
            <Route path="/produtos/novo" element={<ProdutoForm />} />
            <Route path="/produtos/editar/:id" element={<ProdutoForm />} />
          </Routes>
        </div>
      </div>
    </div>
  );
}

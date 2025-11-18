import { BrowserRouter, Routes, Route } from "react-router-dom";
import ListarCategoria from "./pages/categoria/ListaCategoria";
import CriarCategoria from "./pages/categoria/NovaCategoria";
import EditarCategoria from "./pages/categoria/EditarCategoria";
import ExcluirCategoria from "./pages/categoria/ExcluirCategoria";

import ListarProduto from "./pages/produto/ListaProduto";
import CriarProduto from "./pages/produto/NovoProduto";
import EditarProduto from "./pages/produto/EditarProduto";
import ExcluirProduto from "./pages/produto/ExcluirProduto";

function App() {
  return (
    <BrowserRouter>
      <Routes>

        {/* Categorias */}
        <Route path="/categorias" element={<ListarCategoria />} />
        <Route path="/categorias/novo" element={<CriarCategoria />} />
        <Route path="/categorias/editar/:id" element={<EditarCategoria />} />
        <Route path="/categorias/excluir/:id" element={<ExcluirCategoria />} />

        {/* Produtos */}
        <Route path="/produtos" element={<ListarProduto />} />
        <Route path="/produtos/novo" element={<CriarProduto />} />
        <Route path="/produtos/editar/:id" element={<EditarProduto />} />
        <Route path="/produtos/excluir/:id" element={<ExcluirProduto />} />

      </Routes>
    </BrowserRouter>
  );
}

export default App;

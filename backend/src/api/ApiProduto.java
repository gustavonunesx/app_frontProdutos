package api;

import static spark.Spark.*;

import java.util.List;

import com.google.gson.Gson;

import dao.CategoriaDAO;
import dao.ProdutoDAO;
import model.Categoria;
import model.Produto;


public class ApiProduto {

    private static final ProdutoDAO dao = new ProdutoDAO();
    private static final CategoriaDAO categoriaDao = new CategoriaDAO();
    private static final Gson gson = new Gson();

    private static final String APPLICATION_JSON = "application/json";

    public static void main(String[] args) {

        port(4567);
        enableCORS();

        after((request, response) -> {
            response.type(APPLICATION_JSON);
        });

        // ROTAS PRODUTOS
        get("/produtos", (req, res) -> {
            res.type("application/json");
            
            // Busca produtos do banco de dados
            List<Produto> produtos = dao.buscarTodos();
            
            return new Gson().toJson(produtos);
        });

        get("/produtos/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));
                Produto p = dao.buscarPorId(id);

                if (p != null) return gson.toJson(p);

                res.status(404);
                return "{\"mensagem\": \"Produto não encontrado\"}";
            } catch (Exception e) {
                res.status(400);
                return "{\"mensagem\": \"ID inválido\"}";
            }
        });

        post("/produtos", (req, res) -> {
            try {
                Produto novo = gson.fromJson(req.body(), Produto.class);
                dao.inserir(novo);
                res.status(201);
                return gson.toJson(novo);
            } catch (Exception e) {
                res.status(500);
                return "{\"mensagem\": \"Erro ao cadastrar produto\"}";
            }
        });

        put("/produtos/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));
                Produto existente = dao.buscarPorId(id);

                if (existente == null) {
                    res.status(404);
                    return "{\"mensagem\": \"Produto não encontrado\"}";
                }

                Produto atualizado = gson.fromJson(req.body(), Produto.class);
                atualizado.setId(id);
                dao.atualizar(atualizado);

                return gson.toJson(atualizado);

            } catch (Exception e) {
                res.status(400);
                return "{\"mensagem\": \"Erro ao atualizar\"}";
            }
        });

        delete("/produtos/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));

                Produto existente = dao.buscarPorId(id);
                if (existente == null) {
                    res.status(404);
                    return "{\"mensagem\": \"Produto não encontrado\"}";
                }

                dao.deletar(id);
                res.status(204);
                return "";
            } catch (Exception e) {
                res.status(400);
                return "{\"mensagem\": \"Erro ao deletar\"}";
            }
        });

        // ROTAS CATEGORIAS
        get("/categorias", (req, res) -> gson.toJson(categoriaDao.buscarTodos()));

        get("/categorias/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));
                Categoria c = categoriaDao.buscarPorId(id);

                if (c != null) return gson.toJson(c);

                res.status(404);
                return "{\"mensagem\": \"Categoria não encontrada\"}";
            } catch (Exception e) {
                res.status(400);
                return "{\"mensagem\": \"ID inválido\"}";
            }
        });

        post("/categorias", (req, res) -> {
            try {
                Categoria nova = gson.fromJson(req.body(), Categoria.class);
                categoriaDao.inserir(nova);
                res.status(201);
                return gson.toJson(nova);
            } catch (Exception e) {
                res.status(500);
                return "{\"mensagem\": \"Erro ao cadastrar categoria\"}";
            }
        });

        put("/categorias/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));
                Categoria existente = categoriaDao.buscarPorId(id);

                if (existente == null) {
                    res.status(404);
                    return "{\"mensagem\": \"Categoria não encontrada\"}";
                }

                Categoria atualizada = gson.fromJson(req.body(), Categoria.class);
                atualizada.setId(id);
                categoriaDao.atualizar(atualizada);

                return gson.toJson(atualizada);

            } catch (Exception e) {
                res.status(400);
                return "{\"mensagem\": \"Erro ao atualizar categoria\"}";
            }
        });

        delete("/categorias/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));

                Categoria existente = categoriaDao.buscarPorId(id);
                if (existente == null) {
                    res.status(404);
                    return "{\"mensagem\": \"Categoria não encontrada\"}";
                }

                categoriaDao.deletar(id);
                res.status(204);
                return "";
            } catch (Exception e) {
                res.status(400);
                return "{\"mensagem\": \"Erro ao excluir categoria\"}";
            }
        });
    }

    // ============================================
    // =============== CORS ========================
    // ============================================
    private static void enableCORS() {

        options("/*", (request, response) -> {
            String reqHeaders = request.headers("Access-Control-Request-Headers");
            if (reqHeaders != null) response.header("Access-Control-Allow-Headers", reqHeaders);

            String reqMethod = request.headers("Access-Control-Request-Method");
            if (reqMethod != null) response.header("Access-Control-Allow-Methods", reqMethod);

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
        });
    }
}

package api;

import static spark.Spark.*;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dao.CategoriaDAO;
import dao.ProdutoDAO;
import model.Categoria;
import model.Produto;


public class ApiProduto {

    private static final ProdutoDAO dao = new ProdutoDAO();
    private static final CategoriaDAO categoriaDao = new CategoriaDAO();
    private static final Gson gson = new Gson();

    private static final String APPLICATION_JSON = "application/json";

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
    
    public static void main(String[] args) {

        port(4567);
        enableCORS();

        after((request, response) -> {
            response.type(APPLICATION_JSON);
        });

        // ROTAS PRODUTOS
        get("/produtos", (req, res) -> {
            res.type("application/json");
            
            System.out.println("📡 GET /produtos - Buscando todos os produtos...");
            
            List<Produto> produtos = dao.buscarTodos();
            
            System.out.println("✅ Retornando " + produtos.size() + " produtos");
            
            return gson.toJson(produtos);
        });

        get("/produtos/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));
                
                System.out.println("📡 GET /produtos/" + id);
                
                Produto p = dao.buscarPorId(id);

                if (p != null) {
                    System.out.println("✅ Produto encontrado: " + p.getNome());
                    return gson.toJson(p);
                }

                res.status(404);
                return "{\"mensagem\": \"Produto não encontrado\"}";
            } catch (Exception e) {
                res.status(400);
                return "{\"mensagem\": \"ID inválido\"}";
            }
        });

        post("/produtos", (req, res) -> {
            try {
                System.out.println("📡 POST /produtos");
                System.out.println("📦 Body recebido: " + req.body());
                
                // ✅ Parse manual para lidar com categoria_id
                JsonObject json = gson.fromJson(req.body(), JsonObject.class);
                
                Produto novo = new Produto();
                novo.setNome(json.get("nome").getAsString());
                novo.setPreco(json.get("preco").getAsDouble());
                novo.setEstoque(json.get("estoque").getAsInt());
                
                // ✅ Lidar com categoria_id (vindo do frontend)
                if (json.has("categoria_id") && !json.get("categoria_id").isJsonNull()) {
                    Long categoriaId = json.get("categoria_id").getAsLong();
                    Categoria categoria = new Categoria();
                    categoria.setId(categoriaId);
                    novo.setCategoria(categoria);
                }
                
                dao.inserir(novo);
                
                System.out.println("✅ Produto criado: " + novo.getNome() + " (ID: " + novo.getId() + ")");
                
                res.status(201);
                return gson.toJson(novo);
            } catch (Exception e) {
                System.out.println("❌ Erro ao cadastrar: " + e.getMessage());
                e.printStackTrace();
                res.status(500);
                return "{\"mensagem\": \"Erro ao cadastrar produto: " + e.getMessage() + "\"}";
            }
        });

        put("/produtos/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));
                
                System.out.println("📡 PUT /produtos/" + id);
                System.out.println("📦 Body recebido: " + req.body());
                
                Produto existente = dao.buscarPorId(id);

                if (existente == null) {
                    res.status(404);
                    return "{\"mensagem\": \"Produto não encontrado\"}";
                }

                // ✅ Parse manual para lidar com categoria_id
                JsonObject json = gson.fromJson(req.body(), JsonObject.class);
                
                existente.setNome(json.get("nome").getAsString());
                existente.setPreco(json.get("preco").getAsDouble());
                existente.setEstoque(json.get("estoque").getAsInt());
                
                // ✅ Lidar com categoria_id (vindo do frontend)
                if (json.has("categoria_id") && !json.get("categoria_id").isJsonNull()) {
                    Long categoriaId = json.get("categoria_id").getAsLong();
                    Categoria categoria = new Categoria();
                    categoria.setId(categoriaId);
                    existente.setCategoria(categoria);
                } else {
                    existente.setCategoria(null);
                }
                
                dao.atualizar(existente);
                
                System.out.println("✅ Produto atualizado: " + existente.getNome());

                return gson.toJson(existente);

            } catch (Exception e) {
                System.out.println("❌ Erro ao atualizar: " + e.getMessage());
                e.printStackTrace();
                res.status(400);
                return "{\"mensagem\": \"Erro ao atualizar: " + e.getMessage() + "\"}";
            }
        });

        delete("/produtos/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));

                System.out.println("📡 DELETE /produtos/" + id);

                Produto existente = dao.buscarPorId(id);
                if (existente == null) {
                    res.status(404);
                    return "{\"mensagem\": \"Produto não encontrado\"}";
                }

                dao.deletar(id);
                
                System.out.println("✅ Produto deletado");
                
                res.status(204);
                return "";
            } catch (Exception e) {
                System.out.println("❌ Erro ao deletar: " + e.getMessage());
                res.status(400);
                return "{\"mensagem\": \"Erro ao deletar\"}";
            }
        });

        // ROTAS CATEGORIAS
        get("/categorias", (req, res) -> {
            System.out.println("📡 GET /categorias");
            List<Categoria> categorias = categoriaDao.buscarTodos();
            System.out.println("✅ Retornando " + categorias.size() + " categorias");
            return gson.toJson(categorias);
        });

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
        
        System.out.println("🚀 Servidor rodando na porta 4567");
    }
}
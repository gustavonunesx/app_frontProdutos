package api;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;

import com.google.gson.Gson;

import dao.CategoriaDAO;
import dao.ProdutoDAO;
import model.Categoria;
import model.Produto;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Route;

public class ApiProduto {

    private static final ProdutoDAO dao = new ProdutoDAO();
    private static final CategoriaDAO categoriaDao = new CategoriaDAO();
    private static final Gson gson = new Gson();

    private static final String APPLICATION_JSON = "application/json";

    public static void main(String[] args) {
        port(4567);

        // Define que todas as respostas serão JSON
        after(new Filter() {
            @Override
            public void handle(Request request, Response response) {
                response.type(APPLICATION_JSON);
            }
        });

        // GET /produtos - Buscar todos
        get("/produtos", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                return gson.toJson(dao.buscarTodos());
            }
        });

        // GET /produtos/:id - Buscar por ID
        get("/produtos/:id", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Long id = Long.parseLong(request.params(":id"));
                    Produto produto = dao.buscarPorId(id);

                    if (produto != null) {
                        return gson.toJson(produto);
                    } else {
                        response.status(404);
                        return "{\"mensagem\": \"Produto com ID " + id + " não encontrado.\"}";
                    }
                } catch (NumberFormatException e) {
                    response.status(400);
                    return "{\"mensagem\": \"ID inválido.\"}";
                }
            }
        });

        // POST /produtos - Criar novo produto
        post("/produtos", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Produto novoProduto = gson.fromJson(request.body(), Produto.class);
                    dao.inserir(novoProduto);

                    response.status(201); // Created
                    return gson.toJson(novoProduto);
                } catch (Exception e) {
                    response.status(500);
                    System.err.println("Erro ao processar requisição POST: " + e.getMessage());
                    e.printStackTrace();
                    return "{\"mensagem\": \"Erro ao cadastrar o produto.\"}";
                }
            }
        });

        // PUT /produtos/:id - Atualizar produto existente
        put("/produtos/:id", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Long id = Long.parseLong(request.params(":id"));

                    Produto produtoExistente = dao.buscarPorId(id);
                    if (produtoExistente == null) {
                        response.status(404);
                        return "{\"mensagem\": \"Produto não encontrado para atualização.\"}";
                    }

                    Produto produtoAtualizado = gson.fromJson(request.body(), Produto.class);
                    produtoAtualizado.setId(id);

                    dao.atualizar(produtoAtualizado);

                    response.status(200);
                    return gson.toJson(produtoAtualizado);

                } catch (NumberFormatException e) {
                    response.status(400);
                    return "{\"mensagem\": \"ID inválido no formato.\"}";
                } catch (Exception e) {
                    response.status(500);
                    System.err.println("Erro ao processar requisição PUT: " + e.getMessage());
                    e.printStackTrace();
                    return "{\"mensagem\": \"Erro ao atualizar o produto.\"}";
                }
            }
        });

        // DELETE /produtos/:id - Deletar produto
        delete("/produtos/:id", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Long id = Long.parseLong(request.params(":id"));

                    Produto produtoExistente = dao.buscarPorId(id);
                    if (produtoExistente == null) {
                        response.status(404);
                        return "{\"mensagem\": \"Produto não encontrado para exclusão.\"}";
                    }

                    dao.deletar(id);
                    response.status(204); // No Content
                    return ""; // sem corpo na resposta

                } catch (NumberFormatException e) {
                    response.status(400);
                    return "{\"mensagem\": \"ID inválido no formato.\"}";
                } catch (Exception e) {
                    response.status(500);
                    System.err.println("Erro ao processar requisição DELETE: " + e.getMessage());
                    e.printStackTrace();
                    return "{\"mensagem\": \"Erro ao excluir o produto.\"}";
                }
            }
        });
    

     // GET /categorias - Buscar todos
        get("/categorias", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                return gson.toJson(categoriaDao.buscarTodos());
            }
        });

     // GET /categorias/:id - Buscar por ID
        get("/categorias/:id", (request,response) -> {
           
                try {
                    Long id = Long.parseLong(request.params(":id"));
                    Categoria categoria = categoriaDao.buscarPorId(id);

                    if (categoria != null) {
                        return gson.toJson(categoria);
                    } else {
                        response.status(404);
                        return "{\"mensagem\": \"Produto com ID " + id + " não encontrado.\"}";
                    }
                } catch (NumberFormatException e) {
                    response.status(400);
                    return "{\"mensagem\": \"ID inválido.\"}";
                }
            }
        );

         // POST /categorias - Criar novo produto
        post("/categorias", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Categoria novaCategoria = gson.fromJson(request.body(), Categoria.class);
                    categoriaDao.inserir(novaCategoria);

                    response.status(201); // Created
                    return gson.toJson(novaCategoria);
                } catch (Exception e) {
                    response.status(500);
                    System.err.println("Erro ao processar requisição POST: " + e.getMessage());
                    e.printStackTrace();
                    return "{\"mensagem\": \"Erro ao cadastrar a categoria.\"}";
                }
            }
        });

        // PUT /produtos/:id - Atualizar produto existente
        put("/categorias/:id", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Long id = Long.parseLong(request.params(":id"));

                    Categoria categoriaExistente = categoriaDao.buscarPorId(id);
                    if (categoriaExistente == null) {
                        response.status(404);
                        return "{\"mensagem\": \"Categoria não encontrada para atualização.\"}";
                    }

                    Categoria categoriaAtualizada = gson.fromJson(request.body(), Categoria.class);
                    categoriaAtualizada.setId(id);

                    categoriaDao.atualizar(categoriaAtualizada);

                    response.status(200);
                    return gson.toJson(categoriaAtualizada);

                } catch (NumberFormatException e) {
                    response.status(400);
                    return "{\"mensagem\": \"ID inválido no formato.\"}";
                } catch (Exception e) {
                    response.status(500);
                    System.err.println("Erro ao processar requisição PUT: " + e.getMessage());
                    e.printStackTrace();
                    return "{\"mensagem\": \"Erro ao atualizar a categoria.\"}";
                }
            }
        });

        // DELETE /produtos/:id - Deletar produto
        delete("/categorias/:id", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Long id = Long.parseLong(request.params(":id"));

                    Categoria categoriaExistente = categoriaDao.buscarPorId(id);
                    if (categoriaExistente == null) {
                        response.status(404);
                        return "{\"mensagem\": \"Categoria não encontrado para exclusão.\"}";
                    }

                    dao.deletar(id);
                    response.status(204); // No Content
                    return ""; // sem corpo na resposta

                } catch (NumberFormatException e) {
                    response.status(400);
                    return "{\"mensagem\": \"ID inválido no formato.\"}";
                } catch (Exception e) {
                    response.status(500);
                    System.err.println("Erro ao processar requisição DELETE: " + e.getMessage());
                    e.printStackTrace();
                    return "{\"mensagem\": \"Erro ao excluir o produto.\"}";
                }
            }
        });


    }

}

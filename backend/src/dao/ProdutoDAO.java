package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Categoria;
import model.Produto;
import util.ConnectionFactory;

public class ProdutoDAO {
    
    // =====================================================================
    // READ - Buscar todos
    // =====================================================================
    public List<Produto> buscarTodos(){

        List<Produto> produtos = new ArrayList<>();

        String sql = """
            SELECT p.id, p.nome, p.preco, p.estoque,
                   c.id AS categoria_id, c.nome AS categoria_nome
            FROM produtos p
            LEFT JOIN categorias c ON c.id = p.categoria_id
        """;

        try (
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                Categoria categoria = null;
                if (rs.getObject("categoria_id") != null) {
                    categoria = new Categoria(
                        rs.getLong("categoria_id"),
                        rs.getString("categoria_nome")
                    );
                }

                Produto produto = new Produto(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getInt("estoque"),
                    categoria
                );

                produtos.add(produto);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar produtos: " + e.getMessage());
        }

        return produtos;
    }

    // =====================================================================
    // READ - Buscar por ID
    // =====================================================================
    public Produto buscarPorId(Long id){

        Produto produto = null;

        String sql = """
            SELECT p.id, p.nome, p.preco, p.estoque,
                   c.id AS categoria_id, c.nome AS categoria_nome
            FROM produtos p
            LEFT JOIN categorias c ON c.id = p.categoria_id
            WHERE p.id = ?
        """;

        try (
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Categoria categoria = null;
                    if (rs.getObject("categoria_id") != null) {
                        categoria = new Categoria(
                            rs.getLong("categoria_id"),
                            rs.getString("categoria_nome")
                        );
                    }

                    produto = new Produto(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getInt("estoque"),
                        categoria
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar produto por ID: " + e.getMessage());
        }

        return produto;
    }

    // =====================================================================
    // CREATE - Inserir
    // =====================================================================
    public void inserir(Produto produto) {

        String sql = "INSERT INTO produtos (nome, preco, estoque, categoria_id) VALUES (?,?,?,?)";

        try (
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getEstoque());

            if (produto.getCategoria() != null) {
                stmt.setLong(4, produto.getCategoria().getId());
            } else {
                stmt.setNull(4, java.sql.Types.NULL);
            }

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    produto.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir produto: " + e.getMessage());
        }
    }

    // =====================================================================
    // UPDATE
    // =====================================================================
    public void atualizar(Produto produto){

        String sql = "UPDATE produtos SET nome = ?, preco = ?, estoque = ?, categoria_id = ? WHERE id = ?";

        try (
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getEstoque());

            if (produto.getCategoria() != null) {
                stmt.setLong(4, produto.getCategoria().getId());
            } else {
                stmt.setNull(4, java.sql.Types.NULL);
            }

            stmt.setLong(5, produto.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    // =====================================================================
    // DELETE
    // =====================================================================
    public void deletar(Long id){

        String sql = "DELETE FROM produtos WHERE id = ?";

        try (
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao excluir produto: " + e.getMessage());
        }
    }
}

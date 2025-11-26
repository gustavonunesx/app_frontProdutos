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

        // ✅ CORRIGIDO: categoria (SEM S no final)
        String sql = """
            SELECT p.id, p.nome, p.preco, p.estoque,
                   c.id AS categoria_id, c.nome AS categoria_nome
            FROM produtos p
            LEFT JOIN categoria c ON c.id = p.categoria_id
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
            
            System.out.println("✅ Busca realizada: " + produtos.size() + " produtos encontrados");

        } catch (SQLException e) {
            System.out.println("❌ Erro ao buscar produtos: " + e.getMessage());
            e.printStackTrace();
        }

        return produtos;
    }

    // =====================================================================
    // READ - Buscar por ID
    // =====================================================================
    public Produto buscarPorId(Long id){

        System.out.println("🔍 Buscando produto ID: " + id);
        
        Produto produto = null;

        // ✅ CORRIGIDO: categoria (SEM S no final)
        String sql = """
            SELECT p.id, p.nome, p.preco, p.estoque,
                   c.id AS categoria_id, c.nome AS categoria_nome
            FROM produtos p
            LEFT JOIN categoria c ON c.id = p.categoria_id
            WHERE p.id = ?
        """;

        try (
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    
                    System.out.println("✅ Produto encontrado no banco!");

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
                    
                    System.out.println("📦 Produto carregado: " + produto.getNome());
                    
                } else {
                    System.out.println("❌ Nenhum produto encontrado com ID: " + id);
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Erro SQL ao buscar produto por ID: " + e.getMessage());
            e.printStackTrace();
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

            if (produto.getCategoria() != null && produto.getCategoria().getId() != null) {
                stmt.setLong(4, produto.getCategoria().getId());
            } else {
                stmt.setNull(4, java.sql.Types.BIGINT);
            }

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    produto.setId(rs.getLong(1));
                }
            }

            System.out.println("✅ Produto inserido: " + produto.getNome() + " (ID: " + produto.getId() + ")");

        } catch (SQLException e) {
            System.out.println("❌ Erro ao inserir produto: " + e.getMessage());
            e.printStackTrace();
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

            if (produto.getCategoria() != null && produto.getCategoria().getId() != null) {
                stmt.setLong(4, produto.getCategoria().getId());
            } else {
                stmt.setNull(4, java.sql.Types.BIGINT);
            }

            stmt.setLong(5, produto.getId());

            int linhas = stmt.executeUpdate();
            
            System.out.println("✅ Produto atualizado: " + produto.getNome() + " (linhas afetadas: " + linhas + ")");

        } catch (SQLException e) {
            System.out.println("❌ Erro ao atualizar produto: " + e.getMessage());
            e.printStackTrace();
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
            int linhas = stmt.executeUpdate();
            
            if (linhas > 0) {
                System.out.println("✅ Produto ID " + id + " deletado. Linhas afetadas: " + linhas);
            } else {
                System.out.println("⚠️ Nenhum produto deletado. ID " + id + " não existe?");
            }

        } catch (SQLException e) {
            System.out.println("❌ Erro ao excluir produto: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
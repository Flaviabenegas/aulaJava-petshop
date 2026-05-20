package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public final class ProdutoDAOBanco implements ProdutoDAO {
    private final String URL = "jdbc:postgresql://aws-1-us-east2.pooler.supabase.com:6543/postgres";
    private final String USER = "postgres.azkxtyptzlonxdbvminb";
    private final String PASSWORD = "@Fatec2026a";
    private Connection conectar() throws Exception {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        // SE CHEGAR NESSA LINHA, CONECTOU COMPLETAMENTE!
        System.out.println("[SUCESSO] Conexão estabelecida com o Supabase!");
        return conn;
    }
    @Override
    public void salvar(ProdutoDTO produto) {
        String sql = "INSERT INTO alunos (codProduto, nome, valor) VALUES (?, ?, ?)";
        try (Connection conn = conectar(); PreparedStatement stmt =
                conn.prepareStatement(sql)) {
            stmt.setInt(1, produto.codProduto());
            stmt.setString(2, produto.nome());
            stmt.setDouble(3, produto.valor());
            stmt.executeUpdate();
            System.out.println("[BANCO] Produto gravado na nuvem!");
        } catch (Exception e) {
            System.out.println("[ERRO BANCO] " + e.getMessage());
        }
    }
    @Override
    public List<ProdutoDTO> listarTodos() {
        List<List<ProdutoDTO>> lista = new ArrayList<>(); // Opa, correção abaixo:
        List<ProdutoDTO> listaProdutos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                listaProdutos.add(new ProdutoDTO(
                        rs.getInt("codProduto"),
                        rs.getString("nome"),
                        rs.getDouble("valor")
                ));
            }
        } catch (Exception e) {
            System.out.println("[ERRO BANCO] " + e.getMessage());
        }
        return listaProdutos;
    }
    @Override
    public void atualizarNome(int codProduto, String novoNome) {
        String sql = "UPDATE alunos SET nome = ? WHERE codProduto = ?";
        try (Connection conn = conectar(); PreparedStatement stmt =
                conn.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setInt(2, ra);
            stmt.executeUpdate();
            System.out.println("[BANCO] Nome atualizado no banco!");
        } catch (Exception e) {
            System.out.println("[ERRO BANCO] " + e.getMessage());
        }
    }
    @Override
    public void excluir(int ra) {
        String sql = "DELETE FROM alunos WHERE codProduto = ?";
        try (Connection conn = conectar(); PreparedStatement stmt =
                conn.prepareStatement(sql)) {
            stmt.setInt(1, ra);
            stmt.executeUpdate();
            System.out.println("[BANCO] Aluno deletado da nuvem!");
        } catch (Exception e) {
            System.out.println("[ERRO BANCO] " + e.getMessage());
        }
    }
}

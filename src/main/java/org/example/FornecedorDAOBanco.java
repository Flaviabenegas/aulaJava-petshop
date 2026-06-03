package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public final class FornecedorDAOBanco implements FornecedorDAO {

    private final String URL = "jdbc:postgresql://aws-1-sa-east-1.pooler.supabase.com:5432/postgres";
    private final String USER = "postgres.mecsmvvmevjryzlcnqqe";
    private final String PASSWORD = "Stilo@142690";

    private Connection conectar() throws Exception {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("[SUCESSO] Conexão estabelecida com o Supabase!");
        return conn;
    }

    @Override
    public void salvar(FornecedorDTO fornecedor) {
        String sql = "INSERT INTO fornecedor (cnpj, descricao, produto) VALUES (?, ?, ?)";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            // CORRIGIDO: índices alinhados à ordem do SQL (cnpj=1, descricao=2, produto=3)
            stmt.setInt(1, fornecedor.cnpj());
            stmt.setString(2, fornecedor.descricao());
            stmt.setString(3, fornecedor.produto());

            stmt.executeUpdate();
            System.out.println("[BANCO] Fornecedor gravado na nuvem com sucesso!");
        } catch (Exception e) {
            System.out.println("[ERRO BANCO] " + e.getMessage());
        }
    }

    @Override
    public List<FornecedorDTO> listarTodos() {
        List<FornecedorDTO> listaFornecedor = new ArrayList<>();
        String sql = "SELECT * FROM fornecedor";
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listaFornecedor.add(new FornecedorDTO(
                        rs.getInt("id"),
                        rs.getInt("cnpj"),
                        rs.getString("descricao"),
                        rs.getString("produto")
                ));
            }
        } catch (Exception e) {
            System.out.println("[ERRO BANCO] " + e.getMessage());
        }
        return listaFornecedor;
    }

    @Override
    // CORRIGIDO: assinatura alinhada à interface (int novoCNPJ antes de String novoProduto)
    public void atualizar(int id, String novaDescricao, int novoCNPJ, String novoProduto) {
        String sql = "UPDATE fornecedor SET descricao = ?, cnpj = ?, produto = ? WHERE id = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            // CORRIGIDO: tipos e índices alinhados ao SQL
            stmt.setString(1, novaDescricao);
            stmt.setInt(2, novoCNPJ);
            stmt.setString(3, novoProduto);
            stmt.setInt(4, id); // CORRIGIDO: faltava este bind para o WHERE id = ?
            stmt.executeUpdate();
            System.out.println("[BANCO] Fornecedor atualizado com sucesso no banco!");
        } catch (Exception e) {
            System.out.println("[ERRO BANCO] " + e.getMessage());
        }
    }

    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM fornecedor WHERE id = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("[BANCO] Fornecedor deletado da nuvem com sucesso!");
        } catch (Exception e) {
            System.out.println("[ERRO BANCO] " + e.getMessage());
        }
    }
}

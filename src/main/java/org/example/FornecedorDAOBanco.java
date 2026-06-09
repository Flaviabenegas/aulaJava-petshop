package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public final class FornecedorDAOBanco implements FornecedorDAO {

    @Override
    public void salvar(FornecedorDTO fornecedor) {
        String sql = "INSERT INTO fornecedor (cnpj, descricao, produto) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoBanco.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        try (Connection conn = ConexaoBanco.obterConexao();
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
    public void atualizar(int id, String novaDescricao, int novoCNPJ, String novoProduto) {
        String sql = "UPDATE fornecedor SET descricao = ?, cnpj = ?, produto = ? WHERE id = ?";
        try (Connection conn = ConexaoBanco.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novaDescricao);
            stmt.setInt(2, novoCNPJ);
            stmt.setString(3, novoProduto);
            stmt.setInt(4, id);
            stmt.executeUpdate();
            System.out.println("[BANCO] Fornecedor atualizado com sucesso no banco!");
        } catch (Exception e) {
            System.out.println("[ERRO BANCO] " + e.getMessage());
        }
    }

    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM fornecedor WHERE id = ?";
        try (Connection conn = ConexaoBanco.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("[BANCO] Fornecedor deletado da nuvem com sucesso!");
        } catch (Exception e) {
            System.out.println("[ERRO BANCO] " + e.getMessage());
        }
    }
}
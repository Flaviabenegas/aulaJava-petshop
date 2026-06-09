package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public final class ProdutoDAOBanco implements ProdutoDAO {

    @Override
    public void salvar(ProdutoDTO produto) {
        String sql = "INSERT INTO produtos (descricao, preco, qtd) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoBanco.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.descricao());
            stmt.setDouble(2, produto.preco());
            stmt.setInt(3, produto.qtd());
            stmt.executeUpdate();
            System.out.println("[BANCO] Produto gravado na nuvem com sucesso!");
        } catch (Exception e) {
            System.out.println("[ERRO BANCO] " + e.getMessage());
        }
    }

    @Override
    public List<ProdutoDTO> listarTodos() {
        List<ProdutoDTO> listaProdutos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";
        try (Connection conn = ConexaoBanco.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listaProdutos.add(new ProdutoDTO(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getDouble("preco"),
                        rs.getInt("qtd")
                ));
            }
        } catch (Exception e) {
            System.out.println("[ERRO BANCO] " + e.getMessage());
        }
        return listaProdutos;
    }

    @Override
    public void atualizar(int id, String novaDescricao, double novoPreco, int novaQtd) {
        String sql = "UPDATE produtos SET descricao = ?, preco = ?, qtd = ? WHERE id = ?";
        try (Connection conn = ConexaoBanco.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novaDescricao);
            stmt.setDouble(2, novoPreco);
            stmt.setInt(3, novaQtd);
            stmt.setInt(4, id);
            stmt.executeUpdate();
            System.out.println("[BANCO] Produto atualizado com sucesso no banco!");
        } catch (Exception e) {
            System.out.println("[ERRO BANCO] " + e.getMessage());
        }
    }

    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";
        try (Connection conn = ConexaoBanco.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("[BANCO] Produto deletado da nuvem com sucesso!");
        } catch (Exception e) {
            System.out.println("[ERRO BANCO] " + e.getMessage());
        }
    }
}
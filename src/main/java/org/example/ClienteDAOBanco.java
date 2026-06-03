package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public final class ClienteDAOBanco implements ClienteDAO {

    @Override
    public void salvar(ClienteDTO cliente) {
        String sql = "INSERT INTO cliente (nome, cpf, telefone) VALUES (?, ?, ?)";
        // Usa a ConexaoBanco aqui!
        try (Connection conn = ConexaoBanco.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.nome());
            stmt.setString(2, cliente.cpf());
            stmt.setString(3, cliente.telefone());
            stmt.executeUpdate();
            System.out.println("[BANCO] Cliente gravado na nuvem com sucesso!");
        } catch (Exception e) {
            System.out.println("[ERRO BANCO] " + e.getMessage());
        }
    }

    @Override
    public List<ClienteDTO> listarTodos() {
        List<ClienteDTO> listaCliente = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (Connection conn = ConexaoBanco.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listaCliente.add(new ClienteDTO(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone")
                ));
            }
        } catch (Exception e) {
            System.out.println("[ERRO BANCO] " + e.getMessage());
        }
        return listaCliente;
    }

    @Override
    public void atualizar(int id, String novoNome, String novoCpf, String novoTelefone) {
        String sql = "UPDATE cliente SET nome = ?, cpf = ?, telefone = ? WHERE id = ?";
        try (Connection conn = ConexaoBanco.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoNome);
            stmt.setString(2, novoCpf);
            stmt.setString(3, novoTelefone);
            stmt.setInt(4, id);
            stmt.executeUpdate();
            System.out.println("[BANCO] Cliente atualizado com sucesso no banco!");
        } catch (Exception e) {
            System.out.println("[ERRO BANCO] " + e.getMessage());
        }
    }

    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM cliente WHERE id = ?";
        try (Connection conn = ConexaoBanco.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("[BANCO] Cliente deletado da nuvem com sucesso!");
        } catch (Exception e) {
            System.out.println("[ERRO BANCO] " + e.getMessage());
        }
    }
}
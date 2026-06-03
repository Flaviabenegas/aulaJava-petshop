package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public final class ClienteDAOBanco implements ClienteDAO {

    private final String URL = "jdbc:postgresql://aws-1-sa-east-1.pooler.supabase.com:6543/postgres";
    private final String USER = "postgres.mecsmvvmevjryzlcnqqe";
    private final String PASSWORD = "Stilo@142690";

    private Connection conectar() throws Exception {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("[SUCESSO] Conexão estabelecida com o Supabase!");
        return conn;
    }

    @Override
    public void salvar(ClienteDTO cliente) {
        String sql = "INSERT INTO cliente (nome, cpf, telefone) VALUES (?, ?, ?)";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        try (Connection conn = conectar();
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
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("[BANCO] Cliente deletado da nuvem com sucesso!");
        } catch (Exception e) {
            System.out.println("[ERRO BANCO] " + e.getMessage());
        }
    }
}
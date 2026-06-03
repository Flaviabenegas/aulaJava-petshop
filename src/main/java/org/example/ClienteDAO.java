package org.example;

import java.util.List;

public sealed interface ClienteDAO permits ClienteDAOimpl, ClienteDAOBanco {
    void salvar(ClienteDTO cliente);
    List<ClienteDTO> listarTodos();
    void atualizar(int id, String novoNome, String novoCpf, String novoTelefone);
    void excluir(int id);
}
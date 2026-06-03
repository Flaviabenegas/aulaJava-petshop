package org.example;

import java.util.List;

public sealed interface ProdutoDAO permits ProdutoDAOBanco {
    void salvar(ProdutoDTO produto);
    List<ProdutoDTO> listarTodos();
    void atualizar(int id, String novaDescricao, double novoPreco, int novaQtd); // U (Update completo)
    void excluir(int id);
}
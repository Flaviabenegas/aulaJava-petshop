package org.example;

import java.util.List;

public sealed interface FornecedorDAO permits FornecedorDAOBanco {
    void salvar(FornecedorDTO fornecedor);
    List<FornecedorDTO> listarTodos();
    void atualizar(int id, String novaDescricao, int novoCNPJ, String novoProduto);
    void excluir(int id);
}

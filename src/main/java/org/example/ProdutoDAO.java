package org.example;

import java.util.List;
public sealed interface ProdutoDAO permits ProdutoDAOimpl,ProdutoDAOBanco {
    void salvar(ProdutoDTO produto); //C

    List<ProdutoDTO> listarTodos(); //R

    void atualizarNome(int codProduto, String novoNome);//U

    void excluir()
}
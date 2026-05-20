package org.example;
import java.util.List;
import java.util.ArrayList;
public final class AlunoDAOimpl implements ProdutoDAO {
    private List<Produto'DTO> bancoDeDados = new ArrayList<>();
    @Override
    public void salvar(ProdutoDTO produto) {
        bancoDeDados.add(produto);
        System.out.println("Aluno salvo com sucesso: "
                +produto.nome());
    }
    @Override
    public List<ProdutoDTO>listarTodos(){
        return new ArrayList<>(bancoDeDados);
    }
    @Override
    public void atualizarNome(int ra, String novoNome){
        for(int i=0;i<bancoDeDados.size();i++){
            ProdutoDTO a =bancoDeDados.get(i);
            if (a.codProduto()== codProduto()){
                bancoDeDados.set(i,new ProdutoDTO(codProduto,novoNome, a.valor()));
                System.out.println("nome Atualizado para: "+novoNome);
                return;
            }
        }
        System.out.println("{Erro} Aluno não encontrado");
    }
    @Override
    public void excluir(int codProduto){
        bancoDeDados.removeIf(produto->produto.codProduto()==codProduto);
        System.out.println("Removido com Sucesso!!!!");
    }
    public void salvar(int codProduto, String nome){
        this.salvar(new ProdutoDTO(codProduto,nome,valor));
    }
}
package org.example;

import java.sql.Connection;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try {
               Connection conexao = ConexaoBanco.obterConexao();
            System.out.println("[SUCESSO] Conexão estabelecida com o Supabase!");
            DatabaseInitializer.inicializarBanco(conexao);
        } catch (Exception e) {
            System.err.println("Erro crítico ao iniciar o sistema: " + e.getMessage());
        }


        Scanner teclado = new Scanner(System.in);

        System.out.println("                          .-.");
        System.out.println(" (_______________________()6 `-,");
        System.out.println(" (   Bem vindo ao pet shop      )");
        System.out.println(" (________________________/\"\"\"\"` ");
        System.out.println("  // \\\\             //  \\\\");
        System.out.println("  \"\" \"\"            \"\"    \"\"");


        FornecedorDAO servicoFornecedor = new FornecedorDAOBanco();
        ProdutoDAO servicoProduto = new ProdutoDAOBanco();
        ClienteDAO servicoCliente = new ClienteDAOBanco();

        int opcao = 0;

        while (opcao != 4) {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Gerenciar Fornecedores");
            System.out.println("2. Gerenciar Produtos");
            System.out.println("3. Gerenciar Clientes");
            System.out.println("4. Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(teclado.nextLine());

                switch (opcao) {
                    case 1:
                        menuFornecedor(teclado, servicoFornecedor);
                        break;
                    case 2:
                        menuProduto(teclado, servicoProduto);
                        break;
                    case 3:
                        menuCliente(teclado, servicoCliente);
                        break;
                    case 4:
                        System.out.println("\nEncerrando o sistema Pet Shop. Até logo!");
                        break;
                    default:
                        System.out.println("[AVISO] Opção inválida! Escolha entre 1 e 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Digite um número válido.");
            }
        }
        teclado.close();
    }

    private static void menuFornecedor(Scanner teclado, FornecedorDAO servicoFornecedor) {
        int opcao = 0;
        while (opcao != 5) {
            System.out.println("            __");
            System.out.println("      (___()'`; --- FORNECEDORES ---");
            System.out.println("      /,    /`");
            System.out.println("      \\\\\"--\\\\");
                        System.out.println("1. Cadastrar Fornecedor");
            System.out.println("2. Consultar Todos");
            System.out.println("3. Atualizar Fornecedor");
            System.out.println("4. Excluir Fornecedor");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Escolha: ");

            try {
                opcao = Integer.parseInt(teclado.nextLine());

                switch (opcao) {
                    case 1:
                        System.out.println("\n[NOVO FORNECEDOR]");
                        System.out.print("Digite o CNPJ do fornecedor (somente números): ");
                        int cnpj = Integer.parseInt(teclado.nextLine());
                        System.out.print("Digite a descrição do fornecedor: ");
                        String descricao = teclado.nextLine();
                        System.out.print("Digite o produto fornecido: ");
                        String produto = teclado.nextLine();

                        servicoFornecedor.salvar(new FornecedorDTO(0, cnpj, descricao, produto));
                        break;
                    case 2:
                        System.out.println("\n[CONSULTA DE FORNECEDORES]");
                        var lista = servicoFornecedor.listarTodos();
                        if (lista.isEmpty()) {
                            System.out.println("Nenhum fornecedor cadastrado até o momento.");
                        } else {
                            lista.forEach(f -> System.out.printf("ID: %d | CNPJ: %d | Descrição: %s | Produto: %s%n", f.id(), f.cnpj(), f.descricao(), f.produto()));
                        }
                        break;
                    case 3:
                        System.out.println("\n[ATUALIZAÇÃO DE FORNECEDOR]");
                        System.out.print("Digite o ID do fornecedor que deseja atualizar: ");
                        int idAtualizar = Integer.parseInt(teclado.nextLine());
                        System.out.print("Novo CNPJ (somente números): ");
                        int novoCnpj = Integer.parseInt(teclado.nextLine());
                        System.out.print("Nova descrição: ");
                        String novaDescricao = teclado.nextLine();
                        System.out.print("Novo produto fornecido: ");
                        String novoProduto = teclado.nextLine();

                        servicoFornecedor.atualizar(idAtualizar, novaDescricao, novoCnpj, novoProduto);
                        break;
                    case 4:
                        System.out.println("\n[EXCLUSÃO DE FORNECEDOR]");
                        System.out.print("Digite o ID do fornecedor que deseja remover: ");
                        int idRemover = Integer.parseInt(teclado.nextLine());
                        servicoFornecedor.excluir(idRemover);
                        break;
                    case 5:
                        System.out.println("Voltando ao menu principal...");
                        break;
                    default:
                        System.out.println("[AVISO] Opção inválida! Escolha entre 1 e 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Entrada inválida.");
            }
        }
    }

    private static void menuProduto(Scanner teclado, ProdutoDAO servicoProduto) {
        int opcao = 0;
        while (opcao != 5) {
            System.out.println("            __");
            System.out.println("      (___()'`; --- PRODUTOS ---");
            System.out.println("      /,    /`");
            System.out.println("      \\\\\"--\\\\");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Consultar Todos");
            System.out.println("3. Atualizar Produto");
            System.out.println("4. Excluir Produto");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Escolha: ");

            try {
                opcao = Integer.parseInt(teclado.nextLine());

                switch (opcao) {
                    case 1:
                        System.out.println("\n[NOVO PRODUTO]");
                        System.out.print("Digite a descrição do produto: ");
                        String descricao = teclado.nextLine();
                        System.out.print("Digite o preço do produto: ");
                        double preco = Double.parseDouble(teclado.nextLine());
                        System.out.print("Digite a quantidade em estoque: ");
                        int qtd = Integer.parseInt(teclado.nextLine());

                        servicoProduto.salvar(new ProdutoDTO(0, descricao, preco, qtd));
                        break;
                    case 2:
                        System.out.println("\n[CONSULTA DE PRODUTOS]");
                        var lista = servicoProduto.listarTodos();
                        if (lista.isEmpty()) {
                            System.out.println("Nenhum produto cadastrado até o momento.");
                        } else {
                            lista.forEach(p -> System.out.printf("ID: %d | Descrição: %s | Preço: R$ %.2f | Qtd: %d%n", p.id(), p.descricao(), p.preco(), p.qtd()));
                        }
                        break;
                    case 3:
                        System.out.println("\n[ATUALIZAÇÃO DE PRODUTO]");
                        System.out.print("Digite o ID do produto que deseja atualizar: ");
                        int idAtualizar = Integer.parseInt(teclado.nextLine());
                        System.out.print("Qual a nova descrição? ");
                        String novaDescricao = teclado.nextLine();
                        System.out.print("Qual o novo preço? ");
                        double novoPreco = Double.parseDouble(teclado.nextLine());
                        System.out.print("Qual a nova quantidade? ");
                        int novaQtd = Integer.parseInt(teclado.nextLine());

                        servicoProduto.atualizar(idAtualizar, novaDescricao, novoPreco, novaQtd);
                        break;
                    case 4:
                        System.out.println("\n[EXCLUSÃO DE PRODUTO]");
                        System.out.print("Digite o ID do produto que deseja remover: ");
                        int idRemover = Integer.parseInt(teclado.nextLine());
                        servicoProduto.excluir(idRemover);
                        break;
                    case 5:
                        System.out.println("Voltando ao menu principal...");
                        break;
                    default:
                        System.out.println("[AVISO] Opção inválida! Escolha entre 1 e 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Entrada inválida.");
            }
        }
    }

    private static void menuCliente(Scanner teclado, ClienteDAO servicoCliente) {
        int opcao = 0;
        while (opcao != 5) {
            System.out.println("            __");
            System.out.println("      (___()'`; --- CLIENTES ---");
            System.out.println("      /,    /`");
            System.out.println("      \\\\\"--\\\\");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Consultar Todos");
            System.out.println("3. Atualizar Cliente");
            System.out.println("4. Excluir Cliente");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Escolha: ");

            try {
                opcao = Integer.parseInt(teclado.nextLine());

                switch (opcao) {
                    case 1:
                        System.out.println("\n[NOVO CLIENTE]");
                        System.out.print("Digite o nome do cliente: ");
                        String nome = teclado.nextLine();
                        System.out.print("Digite o CPF do cliente: ");
                        String cpf = teclado.nextLine();
                        System.out.print("Digite o telefone do cliente: ");
                        String telefone = teclado.nextLine();

                        servicoCliente.salvar(new ClienteDTO(0, nome, cpf, telefone));
                        break;
                    case 2:
                        System.out.println("\n[CONSULTA DE CLIENTES]");
                        var lista = servicoCliente.listarTodos();
                        if (lista.isEmpty()) {
                            System.out.println("Nenhum cliente cadastrado até o momento.");
                        } else {
                            lista.forEach(c -> System.out.printf("ID: %d | Nome: %s | CPF: %s | Telefone: %s%n", c.id(), c.nome(), c.cpf(), c.telefone()));
                        }
                        break;
                    case 3:
                        System.out.println("\n[ATUALIZAÇÃO DE CLIENTE]");
                        System.out.print("Digite o ID do cliente que deseja atualizar: ");
                        int idAtualizar = Integer.parseInt(teclado.nextLine());
                        System.out.print("Novo nome: ");
                        String novoNome = teclado.nextLine();
                        System.out.print("Novo CPF: ");
                        String novoCpf = teclado.nextLine();
                        System.out.print("Novo telefone: ");
                        String novoTelefone = teclado.nextLine();

                        servicoCliente.atualizar(idAtualizar, novoNome, novoCpf, novoTelefone);
                        break;
                    case 4:
                        System.out.println("\n[EXCLUSÃO DE CLIENTE]");
                        System.out.print("Digite o ID do cliente que deseja remover: ");
                        int idRemover = Integer.parseInt(teclado.nextLine());
                        servicoCliente.excluir(idRemover);
                        break;
                    case 5:
                        System.out.println("Voltando ao menu principal...");
                        break;
                    default:
                        System.out.println("[AVISO] Opção inválida! Escolha entre 1 e 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Entrada inválida.");
            }
        }
    }
}
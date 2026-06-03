package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("                .-._     ");
        System.out.println("               {_}^ )o  ");
        System.out.println("      {\\________//~`     ");
        System.out.println("       (         )");
        System.out.println("       /||~~~~~||\\");
        System.out.println(" auu  |_\\\\_    \\\\_\\_   ");
        System.out.println("      \"' \"\"'    \"\"'\"'");
        System.out.println("                          .-.");
        System.out.println(" (_______________________()6 `-,");
        System.out.println(" (   Bem vindo ao pet shop      )");
        System.out.println(" (________________________/\"\"\"\"` ");
        System.out.println("  // \\\\             //  \\\\");
        System.out.println("  \"\" \"\"            \"\"    \"\"");

        FornecedorDAO servicoFornecedor = new FornecedorDAOBanco();
        ProdutoDAO servicoProduto = new ProdutoDAOBanco();
        ClienteDAO servicoCliente = new ClienteDAOBanco();

        System.out.println("[MODO] Banco de dados Supabase ativado.\n");

        int opcao = 0;
        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Gerenciar Fornecedores");
            System.out.println("2. Gerenciar Produtos");
            System.out.println("3. Gerenciar Clientes");
            System.out.println("4. Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Digite um número válido.");
                continue;
            }

            switch (opcao) {
                case 1 -> menuFornecedor(servicoFornecedor);
                case 2 -> menuProduto(servicoProduto);
                case 3 -> menuCliente(servicoCliente);
                case 4 -> System.out.println("\nEncerrando o sistema Pet Shop. Até logo!");
                default -> System.out.println("[AVISO] Opção inválida! Escolha entre 1 e 4.");
            }

        } while (opcao != 4);

        teclado.close();
    }

    private static void menuFornecedor(FornecedorDAO servico) {
        int opcao = 0;
        do {
            System.out.println("\n--- FORNECEDORES ---");
            System.out.println("1. Cadastrar Fornecedor");
            System.out.println("2. Consultar Todos");
            System.out.println("3. Atualizar Fornecedor");
            System.out.println("4. Excluir Fornecedor");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Digite um número válido.");
                continue;
            }

            switch (opcao) {
                case 1 -> {
                    System.out.println("\n[NOVO FORNECEDOR]");
                    System.out.print("Digite o CNPJ do fornecedor (somente números): ");
                    int cnpj;
                    try { cnpj = Integer.parseInt(teclado.nextLine()); }
                    catch (NumberFormatException e) { System.out.println("[ERRO] CNPJ inválido. Operação cancelada."); break; }

                    System.out.print("Digite a descrição do fornecedor: ");
                    String descricao = teclado.nextLine();
                    System.out.print("Digite o produto fornecido: ");
                    String produto = teclado.nextLine();

                    servico.salvar(new FornecedorDTO(0, cnpj, descricao, produto));
                }
                case 2 -> {
                    System.out.println("\n[CONSULTA DE FORNECEDORES]");
                    List<FornecedorDTO> lista = servico.listarTodos();
                    if (lista.isEmpty()) {
                        System.out.println("Nenhum fornecedor cadastrado até o momento.");
                    } else {
                        System.out.println("----------------------------------------");
                        lista.forEach(f -> System.out.printf(
                                "ID: %d | CNPJ: %d | Descrição: %s | Produto: %s%n",
                                f.id(), f.cnpj(), f.descricao(), f.produto()));
                        System.out.println("----------------------------------------");
                        System.out.println("Total: " + lista.size() + " fornecedor(es).");
                    }
                }
                case 3 -> {
                    System.out.println("\n[ATUALIZAÇÃO DE FORNECEDOR]");
                    System.out.print("Digite o ID do fornecedor que deseja atualizar: ");
                    int id;
                    try { id = Integer.parseInt(teclado.nextLine()); }
                    catch (NumberFormatException e) { System.out.println("[ERRO] ID inválido. Operação cancelada."); break; }

                    System.out.print("Nova descrição: ");
                    String novaDescricao = teclado.nextLine();
                    System.out.print("Novo CNPJ (somente números): ");
                    int novoCNPJ;
                    try { novoCNPJ = Integer.parseInt(teclado.nextLine()); }
                    catch (NumberFormatException e) { System.out.println("[ERRO] CNPJ inválido. Operação cancelada."); break; }

                    System.out.print("Novo produto fornecido: ");
                    String novoProduto = teclado.nextLine();

                    servico.atualizar(id, novaDescricao, novoCNPJ, novoProduto);
                }
                case 4 -> {
                    System.out.println("\n[EXCLUSÃO DE FORNECEDOR]");
                    System.out.print("Digite o ID do fornecedor que deseja remover: ");
                    try { servico.excluir(Integer.parseInt(teclado.nextLine())); }
                    catch (NumberFormatException e) { System.out.println("[ERRO] ID inválido. Operação cancelada."); }
                }
                case 5 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("[AVISO] Opção inválida! Escolha entre 1 e 5.");
            }

        } while (opcao != 5);
    }

    private static void menuProduto(ProdutoDAO servico) {
        int opcao = 0;
        do {
            System.out.println("\n--- PRODUTOS ---");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Consultar Todos");
            System.out.println("3. Atualizar Produto");
            System.out.println("4. Excluir Produto");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Digite um número válido.");
                continue;
            }

            switch (opcao) {
                case 1 -> {
                    System.out.println("\n[NOVO PRODUTO]");
                    System.out.print("Digite a descrição do produto: ");
                    String descricao = teclado.nextLine();

                    System.out.print("Digite o preço do produto: ");
                    double preco;
                    try { preco = Double.parseDouble(teclado.nextLine()); }
                    catch (NumberFormatException e) { System.out.println("[ERRO] Preço inválido. Operação cancelada."); break; }

                    System.out.print("Digite a quantidade em estoque: ");
                    int qtd;
                    try { qtd = Integer.parseInt(teclado.nextLine()); }
                    catch (NumberFormatException e) { System.out.println("[ERRO] Quantidade inválida. Operação cancelada."); break; }

                    servico.salvar(new ProdutoDTO(0, descricao, preco, qtd));
                }
                case 2 -> {
                    System.out.println("\n[CONSULTA DE PRODUTOS]");
                    List<ProdutoDTO> lista = servico.listarTodos();
                    if (lista.isEmpty()) {
                        System.out.println("Nenhum produto cadastrado até o momento.");
                    } else {
                        System.out.println("----------------------------------------");
                        lista.forEach(p -> System.out.printf(
                                "ID: %d | Descrição: %s | Preço: R$ %.2f | Qtd: %d%n",
                                p.id(), p.descricao(), p.preco(), p.qtd()));
                        System.out.println("----------------------------------------");
                        System.out.println("Total: " + lista.size() + " produto(s).");
                    }
                }
                case 3 -> {
                    System.out.println("\n[ATUALIZAÇÃO DE PRODUTO]");
                    System.out.print("Digite o ID do produto que deseja atualizar: ");
                    int id;
                    try { id = Integer.parseInt(teclado.nextLine()); }
                    catch (NumberFormatException e) { System.out.println("[ERRO] ID inválido. Operação cancelada."); break; }

                    System.out.print("Qual a nova descrição? ");
                    String novaDescricao = teclado.nextLine();
                    System.out.print("Qual o novo preço? ");
                    double novoPreco;
                    try { novoPreco = Double.parseDouble(teclado.nextLine()); }
                    catch (NumberFormatException e) { System.out.println("[ERRO] Preço inválido. Operação cancelada."); break; }

                    System.out.print("Qual a nova quantidade? ");
                    int novaQtd;
                    try { novaQtd = Integer.parseInt(teclado.nextLine()); }
                    catch (NumberFormatException e) { System.out.println("[ERRO] Quantidade inválida. Operação cancelada."); break; }

                    servico.atualizar(id, novaDescricao, novoPreco, novaQtd);
                }
                case 4 -> {
                    System.out.println("\n[EXCLUSÃO DE PRODUTO]");
                    System.out.print("Digite o ID do produto que deseja remover: ");
                    try { servico.excluir(Integer.parseInt(teclado.nextLine())); }
                    catch (NumberFormatException e) { System.out.println("[ERRO] ID inválido. Operação cancelada."); }
                }
                case 5 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("[AVISO] Opção inválida! Escolha entre 1 e 5.");
            }

        } while (opcao != 5);
    }

    private static void menuCliente(ClienteDAO servico) {
        int opcao = 0;
        do {
            System.out.println("\n--- CLIENTES ---");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Consultar Todos");
            System.out.println("3. Atualizar Cliente");
            System.out.println("4. Excluir Cliente");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Digite um número válido.");
                continue;
            }

            switch (opcao) {
                case 1 -> {
                    System.out.println("\n[NOVO CLIENTE]");
                    System.out.print("Digite o nome do cliente: ");
                    String nome = teclado.nextLine();
                    System.out.print("Digite o CPF do cliente: ");
                    String cpf = teclado.nextLine();
                    System.out.print("Digite o telefone do cliente: ");
                    String telefone = teclado.nextLine();

                    servico.salvar(new ClienteDTO(0, nome, cpf, telefone));
                }
                case 2 -> {
                    System.out.println("\n[CONSULTA DE CLIENTES]");
                    List<ClienteDTO> lista = servico.listarTodos();
                    if (lista.isEmpty()) {
                        System.out.println("Nenhum cliente cadastrado até o momento.");
                    } else {
                        System.out.println("----------------------------------------");
                        lista.forEach(c -> System.out.printf(
                                "ID: %d | Nome: %s | CPF: %s | Telefone: %s%n",
                                c.id(), c.nome(), c.cpf(), c.telefone()));
                        System.out.println("----------------------------------------");
                        System.out.println("Total: " + lista.size() + " cliente(s).");
                    }
                }
                case 3 -> {
                    System.out.println("\n[ATUALIZAÇÃO DE CLIENTE]");
                    System.out.print("Digite o ID do cliente que deseja atualizar: ");
                    int id;
                    try { id = Integer.parseInt(teclado.nextLine()); }
                    catch (NumberFormatException e) { System.out.println("[ERRO] ID inválido. Operação cancelada."); break; }

                    System.out.print("Novo nome: ");
                    String novoNome = teclado.nextLine();
                    System.out.print("Novo CPF: ");
                    String novoCpf = teclado.nextLine();
                    System.out.print("Novo telefone: ");
                    String novoTelefone = teclado.nextLine();

                    servico.atualizar(id, novoNome, novoCpf, novoTelefone);
                }
                case 4 -> {
                    System.out.println("\n[EXCLUSÃO DE CLIENTE]");
                    System.out.print("Digite o ID do cliente que deseja remover: ");
                    try { servico.excluir(Integer.parseInt(teclado.nextLine())); }
                    catch (NumberFormatException e) { System.out.println("[ERRO] ID inválido. Operação cancelada."); }
                }
                case 5 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("[AVISO] Opção inválida! Escolha entre 1 e 5.");
            }

        } while (opcao != 5);
    }
}
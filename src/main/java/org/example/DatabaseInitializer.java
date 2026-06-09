package org.example;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {

    public static void inicializarBanco(Connection conn) {


        String sqlCriaCliente = """
            CREATE TABLE IF NOT EXISTS cliente (
                id SERIAL PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                cpf VARCHAR(14) UNIQUE NOT NULL,
                telefone VARCHAR(20)
            );
        """;


        String sqlCriaFornecedor = """
            CREATE TABLE IF NOT EXISTS fornecedor (
                id SERIAL PRIMARY KEY,
                cnpj INTEGER UNIQUE NOT NULL,
                descricao VARCHAR(255) NOT NULL,
                produto VARCHAR(255) NOT NULL
            );
        """;


        String sqlCriaProduto = """
            CREATE TABLE IF NOT EXISTS produtos (
                id SERIAL PRIMARY KEY,
                descricao VARCHAR(255) NOT NULL,
                preco DOUBLE PRECISION NOT NULL,
                qtd INTEGER NOT NULL
            );
        """;

        try (Statement stmt = conn.createStatement()) {
            System.out.println("[SISTEMA] Verificando estrutura do banco de dados...");


            stmt.execute(sqlCriaCliente);
            System.out.println("[SISTEMA] Tabela 'cliente' verificada/criada com sucesso!");

            stmt.execute(sqlCriaFornecedor);
            System.out.println("[SISTEMA] Tabela 'fornecedor' verificada/criada com sucesso!");

            stmt.execute(sqlCriaProduto);
            System.out.println("[SISTEMA] Tabela 'produtos' verificada/criada com sucesso!");

        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao inicializar as tabelas do banco: " + e.getMessage());
        }
    }
}
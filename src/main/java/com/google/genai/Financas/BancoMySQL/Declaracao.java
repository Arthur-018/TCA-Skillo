package com.google.genai.Financas.BancoMySQL;

import java.sql.*;

public class Declaracao {
    private String comando;

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public void CadastraUsuario(String nome, String email, String senha) {
        nome = nome.trim().replaceAll("\\s+", " ");
        if (nome.isEmpty()) {
            System.out.println("O nome não pode ser vazio!");
            return;
        }

        email = email.trim().replaceAll("\\s+", " ");
        if (email.isEmpty()) {
            System.out.println("O email não pode ser vazio!");
            return;
        }

        senha = senha.replaceAll("\\s+", " ");
        if (senha.trim().isEmpty()) {
            System.out.println("A senha não pode ser vazia!");
            return;
        }

        String sql = "'" + nome + "','" + email + "','" + senha + "'";

        try (Connection conexao = Conexao.ConectaBanco();
             Statement declaracao = conexao.createStatement()) {

            declaracao.executeUpdate("insert into usuario (nome,email,senha) values (" + sql + ")");
            System.out.println("Um novo usuario foi cadastrado");

        } catch (SQLNonTransientConnectionException e) {
            System.out.println("Não foi possível conectar ao banco de dados.");

        } catch (SQLTimeoutException e) {
            System.out.println("O banco de dados demorou muito para responder.");

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("O email já esta cadastrado.");

        } catch (SQLException e) {
            System.out.println("Tivemos algum problema com o banco de dados:" + e);
        }
    }

    public void LogarUsuario(String email, String senha) {
        try (Connection conexao = Conexao.ConectaBanco();
             Statement declaracao = conexao.createStatement()) {

            String sql = "SELECT * FROM usuario WHERE email = '" + email + "';";
            ResultSet busca = declaracao.executeQuery(sql);

            if (busca.next()) {
                String emailUsuario = busca.getString("email");
                String senhaUsuario = busca.getString("senha");

                if (email.equals(emailUsuario) && senha.equals(senhaUsuario)) {
                    System.out.println("Seja bem vindo " + busca.getString("nome"));
                } else {
                    System.out.println("Senha ou email incorretos!");
                }
            } else {
                System.out.println("Senha ou email incorretos!");
            }

        } catch (SQLNonTransientConnectionException e) {
                System.out.println("Não foi possível conectar ao banco de dados.");

        } catch (SQLTimeoutException e) {
            System.out.println("O banco de dados demorou muito para responder.");

        } catch (SQLException e) {
            System.out.println("Tivemos algum problema com o banco de dados: " + e);
        }
    }
}
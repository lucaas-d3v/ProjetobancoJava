import java.sql.*;
import java.util.Scanner;

public class GerenciarBanco {

    protected static boolean verificarNoBanco(String cpf) {
        String sql = "SELECT 1 FROM usuarios WHERE cpf = ?";

        try (Connection conexao = DriverManager.getConnection("jdbc:sqlite:BancoPrincipal.db");
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            // Se tiver resultado, o CPF existe
            return rs.next();

        } catch (Exception e) {
            System.out.println("Erro: " + e);
            return false;
        }
    }

    protected static boolean adicionarAoBanco(String cpf, String nome, int idade, String senha) {
        String sqlCriarTabela = "CREATE TABLE IF NOT EXISTS usuarios (" +
                                "cpf TEXT PRIMARY KEY NOT NULL, " +
                                "nome TEXT NOT NULL, " +
                                "idade INTEGER NOT NULL, " +
                                "senha TEXT NOT NULL)";

        String sqlInserir = "INSERT INTO usuarios (cpf, nome, idade, senha) VALUES (?, ?, ?, ?)"; // Comando sql.

        try (Connection conexao = DriverManager.getConnection("jdbc:sqlite:BancoPrincipal.db"); // Tenta conectar ao banco.
             Statement stmt = conexao.createStatement();
             PreparedStatement inserir = conexao.prepareStatement(sqlInserir)) {

            // Cria a tabela se não existir
            stmt.executeUpdate(sqlCriarTabela);

            // Insere os dados
            inserir.setString(1, cpf);
            inserir.setString(2, nome);
            inserir.setInt(3, idade);
            inserir.setString(4, senha);
            inserir.executeUpdate();

            return true;

        } catch (Exception e) {
            System.out.println("Erro ao adicionar: " + e.getMessage());
            return false;
        }
    }


    protected static boolean removerDoBanco(String cpf)
    {
        // Tenta remover o usuário do banco a partir do id.
        try(
                Connection conexao = DriverManager.getConnection("jdbc:sqlite:BancoPrincipal.db"); // // Tenta se conectar ao banco.
                PreparedStatement apagar = conexao.prepareStatement("DELETE FROM usuarios WHERE cpf = ?") // Comando sql.
           ){

               apagar.setString(1, cpf); // Subistitui o "?" pelo cpf do usuário em questão.
               int linhasAfetadas = apagar.executeUpdate(); // Executa a atualização.

               return linhasAfetadas > 0; // Retorna true se der tudo certo, isso evita retornar true se ninguém foi deletado (tipo, CPF não encontrado).

            } catch(Exception e)

            {
                System.out.println("Erro ao remover: " + e);
                return false; // Retorna erro se o nome não existir no banco.
            }
    }


    protected static String puxarSenha(String cpf) {
        String sql = "SELECT senha FROM usuarios WHERE cpf = ?"; // Comando pra pegar a senha do usuário.

        try (Connection conexao = DriverManager.getConnection("jdbc:sqlite:BancoPrincipal.db"); // Tenta se conectar ao banco.
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, cpf); // Altera o "?" pelo cpf passado como parâmetro.
            ResultSet rs = stmt.executeQuery(); // Executa o comando sql.

            if (rs.next()) {
                return rs.getString("senha"); // Pega a senha, e retorna.
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }

        return null; // Caso o cpf esteja incorreto ou não exista, retorna null.
    }

}

import java.sql.*;
import java.util.Scanner;

public class GerenciarBanco {

    private static boolean adicionarAoBanco(Connection conexao, String nome, int idade)
    {
        try
        {
            // Tenta acessar o banco, casso não exista, ele cria.
            Statement criar_tabela = conexao.createStatement();

            String comando = "CREATE TABLE IF NOT EXISTS usuarios (" +
                             "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                             "nome TEXT NOT NULL, " +
                             "idade INTEGER NOT NULL" +
                             ")";

            criar_tabela.executeUpdate(comando);
            criar_tabela.close();

            // Adiciona os dados ao banco.
            comando = "INSERT INTO usuarios (nome, idade) VALUES (?, ?)";
            PreparedStatement inserir = conexao.prepareStatement(comando);

            inserir.setString(1, nome);
            inserir.setInt(2, idade);

            inserir.executeUpdate();

            inserir.close();
        }
        catch (Exception e)
        {
            System.out.println("Erro: " + e.getMessage());
            return false;
        }

        return true;
    }

    private static ResultSet puxarDoBanco(Connection conexao)
    {
        try {
            Statement puxar = conexao.createStatement();
            ResultSet dados = puxar.executeQuery("SELECT * FROM pessoas");

            puxar.close();

            return dados;
        }
        catch (Exception e)
            {
                return null;
            }
    }

    public static void main(String[] args) {
        Connection conexao = null;
        Scanner scanner = new Scanner(System.in);

        try {
            conexao = DriverManager.getConnection("jdbc:sqlite:BancoPrincipal.db");
            System.out.println("✅ Conectado com sucesso!");

            // Pede os dados do usuário
            System.out.print("Digite a sua idade: ");
            int idade = scanner.nextInt();
            scanner.nextLine(); // limpa o ENTER

            String nome;
            do {
                System.out.print("Digite o seu nome: ");
                nome = scanner.nextLine().trim();
                if (nome.isEmpty()) {
                    System.out.println("Nome não pode estar vazio.");
                }
            } while (nome.isEmpty());

            adicionarAoBanco(conexao, nome, idade);
            System.out.printf("%n", puxarDoBanco(conexao));

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}

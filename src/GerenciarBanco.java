import java.sql.*;
import java.util.Scanner;

public class GerenciarBanco
{

    protected static boolean verificarNoBanco(String nome) {
        boolean existe = false; // Variável pra verificar se o usuário ja existem começa em false por padrão.

        try {
            Connection conexao = DriverManager.getConnection("jdbc:sqlite:BancoPrincipal.db"); // Cria uma conexão com o banco.
            ResultSet usuarios = puxarBanco(); // Puxa todos os usuários do banco.

            while (usuarios.next()) // Itera sobre os usuários.
            {
                if (usuarios.getString("nome").equals(nome)) // Compara os nomes do banco, e se o usuário existir ele para o loop.
                {
                    existe = true; // Atualiza pra true se for o mesmo.
                    break;

                }
                else
                {
                    existe = false; // Atualiza pra false se n for o mesmo nome.

                }

            }

            conexao.close();

            return existe;
        }
        catch (Exception e )
        {
            System.out.println("Erro: " + e);

        }

        return existe;
    }

    protected static boolean adicionarAoBanco(String nome, int idade, String senha)
    {
        // Tenta adicionar um novo usuário ao banco.
        try
        {
            // Tenta acessar o banco, casso não exista, ele cria.
            Connection conexao = DriverManager.getConnection("jdbc:sqlite:BancoPrincipal.db");

            Statement criar_tabela = conexao.createStatement();

            String comando = "CREATE TABLE IF NOT EXISTS usuarios (" +
                             "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                             "nome TEXT NOT NULL, " +
                             "idade INTEGER NOT NULL, " +
                             "senha TEXT NOT NULL" + // ← sem vírgula aqui
                             ")";


            criar_tabela.executeUpdate(comando); // executa a criação do banco.
            criar_tabela.close();

            // Adiciona os dados ao banco.
            comando = "INSERT INTO usuarios (nome, idade, senha) VALUES (?, ?, ?)"; // Comando pra inserir uma nova linha (usuário) ao banco.
            PreparedStatement inserir = conexao.prepareStatement(comando);

            inserir.setString(1, nome); // Seta o 1º "?" como nome, e o 2º como a idade do usuário.
            inserir.setInt(2, idade);
            inserir.setString(3, senha);

            inserir.executeUpdate(); // Executa a atualização no banco.

            inserir.close();

            return true;
        }
        catch (Exception e)
        {
            System.out.println("Erro: " + e.getMessage());

            return false; // Retorna false, caso a ação não dê certo.

        }

    }

    protected static boolean removerDoBanco(String nome)
    {
        // Tenta selecionar o id de um nome específico.
        try
        {
            Connection conexao = DriverManager.getConnection("jdbc:sqlite:BancoPrincipal.db");

            String comando = "SELECT id FROM pessoas WHERE nome = ? ";
            PreparedStatement colocar = conexao.prepareStatement(comando);

            colocar.setString(1, nome); // Subistitui o "?" pelo nome passado como parâmetro.

            colocar.close();

            ResultSet resultado = colocar.executeQuery(); // Executa a busca.

            int id_a_pagar = resultado.getInt("id"); // Pega o id do usuário em questão

            try
            {
                // Tenta remover o usuário do banco a partir do id.
                String comando_apagar = "DELETE FROM pessoas WHERE id = ?";
                PreparedStatement apagar = conexao.prepareStatement(comando_apagar);

                apagar.setInt(1, id_a_pagar); // Subistitui o "?" pelo id do usuário em questão.
                apagar.executeUpdate(); // Executa a atualização.

                apagar.close();

                return true; // Retorna true se der tudo certo.
            }
            catch (Exception e)
            {
                System.out.println("Erro: " + e);

                return false; // Retorna erro se o nome não existir no banco.

            }

        }
        catch (Exception e)
        {
            System.out.println("Erro: " + e);
            return false; // Retorna erro se o nome não existir no banco.

        }

    }

    protected static ResultSet puxarBanco()
    {
        // Tenta puxar todos os usuários do banco.
        try
        {
            Connection conexao = DriverManager.getConnection("jdbc:sqlite:BancoPrincipal.db");

            Statement puxar = conexao.createStatement();
            ResultSet dados = puxar.executeQuery("SELECT * FROM pessoas"); // Seleciona todos os usuários do banco.

            puxar.close();

            return dados; // retorna os usuários.

        }
        catch (Exception e)
        {
            return null; // Retorna erro, se não der certo.

        }

    }

    protected static String puxarSenha(String nome) {
        try {
            ResultSet usuarios = puxarBanco(); // Puxa todos os usuários do banco.

            while (usuarios.next()) {
                String nomeAtual = usuarios.getString("nome"); // pega o valor da coluna "nome"

                if (nomeAtual.equals(nome)) {
                    return usuarios.getString("senha"); // pega o valor da coluna "senha"
                }

            }

        }
        catch (Exception e)
        {
            System.out.println("Erro: " + e);

        }
        return null; // ou lançar exceção se quiser tratar como erro

    }


    public static void menuPrincipal()
    {
        Scanner leitor = new Scanner(System.in);
        boolean deu_errado = true;

        do
        {
            System.out.println("====== Menu Principal ======"); // So estética
            System.out.print("[1] Se cadastrar\n[2] Logar\n[3] Sair\n\nDigite a sua ação: "); // Ja mostra as ações disponíveis, e pergunta oq o usuário quer fazer.

            String acao = leitor.nextLine(); // lê a entrada do usuário.

            switch (acao) // Manda o usuário pra onde tem que mandar.
            {
                case "1" -> { // Caso digite 1 ele vai pro cadastro.
                    cadastro();
                    deu_errado = false; // Para o loop.
                    break;

                }
                case "2" -> { // Caso digite 2 eele vai pro login..
                    logar();
                    deu_errado = false; // Para o loop.
                    break;

                }

                case "3" -> {
                    System.out.println("saindo...");
                    break;

                }

                default -> { // Informa que a ação não existe.
                    System.out.println("\nAção não existe!");
                    break;

                }

            }

        } while (deu_errado);

        leitor.close();

    }

    public static void cadastro() {
        Scanner leitor = new Scanner(System.in);

        System.out.println("====== Cadastro ======"); // Estética.

        try
        {
            // Pede os dados do usuário
            System.out.print("\nDigite a sua idade: "); // Pede a idade do usuário.
            int idade = leitor.nextInt(); // Lê o próximo inteiro digitado pelo usuáio.

            leitor.nextLine(); // limpa o ENTER

            String nome;
            do
            {
                System.out.print("\nDigite o seu nome: "); // Pede o nome do usuário.
                nome = leitor.nextLine().trim(); // Lê o próximo inteiro digitado pelo usuáio e limpa os espaços a direita e esquerda.

                // Se o nome for vazio ele avisa isso ao usuário e pede o nome novamente.
                if (nome.isEmpty())
                {
                    System.out.println("\nNome não pode estar vazio.");

                }

                // Se não for um nome, pede ao usuário novamente.
                else if (!Verificador.isName(nome))
                {
                    System.out.println("Nome inválido!");

                    // Esse .isName() foi feito por mim, basicamente usa umas regras de regex pra valir como um nome de pessoa.
                }
                
            } while (nome.isEmpty() || !Verificador.isName(nome));

            String senha;
            do
            {
                System.out.print("Digite uma boa senha para a sua conta: ");
                senha = leitor.nextLine();

                // Vê se a senha é válida.
                if (!Verificador.isPassword(senha)) // Esse .isPassword(), foi feito por mim, pra validar uma senha, de segurança básica.
                {
                    System.out.println("Senha inválida, seu senha precisa ter:\n\n- Pelo menos 8 caracteres\n- pelo menos um caractere especial\n- Pelo menos um número\n- Pelo menos uma letra\n");
                    continue;

                }

            } while (!Verificador.isPassword(senha));

            leitor.close();

            if (adicionarAoBanco(nome, idade, senha)) // Se a conta for adicionada com sucesso, ele informa.
            {
                System.out.println("\nSua conta foi criada com sucesso! :)");
            }
            else // Caso não, ele também informa.
            {
                System.out.println("\nOcorreu um erro ao criar a sua conta... :(");
            }

        }
        catch (Exception e)
        {
            System.out.println("Erro: " + e.getMessage());

        }

    }

    private static void logar() {
        Scanner leitor = new Scanner(System.in);

        System.out.println("====== Login ======");

        // Mesma coisa do cadastro aqui, so peço os dados do usuário, e verifico se estão de acordo.
        try
        {
            System.out.print("\nDigite a sua idade: ");
            int idade = leitor.nextInt();

            leitor.nextLine();

            String nome;
            do
            {
                System.out.print("\nDigite o seu nome: ");
                nome = leitor.nextLine().trim();

                if (nome.isEmpty())
                {
                    System.out.println("\nNome não pode estar vazio.");
                }
                else if (!Verificador.isName(nome))
                {
                    System.out.println("Nome inválido!");
                }

            } while (nome.isEmpty() || !Verificador.isName(nome));

            // Aqui as coisas mudam.
            boolean existe = verificarNoBanco(nome); // Verifico se a conta existe.
            String senha_conta = puxarSenha(nome); // Pego a senha associada a conta.
            boolean logou = false; // Verificar se conseguiu logar, false por padrão.

            do {
                if (existe) // Se existe
                {
                    System.out.print("Insira a sua senha para logar na sua conta: "); // Peço a senha ao usuário
                    String senha_digitada = leitor.nextLine(); // Lê as próximas strings digitadas na linha.

                    if (senha_digitada.equals(senha_conta))  // Se a senha digita for a mesma que esta cadastrada, o login é efetuado.
                    {
                        System.out.println("Login efetuado com sucesso, seja bem-vindo(a) " + nome); // Da boas vindas.
                        logou = true; // Atualiza para true.

                    }
                    else // Se a senha for diferente.
                    {
                        System.out.println("Senha incorreta, por favor tente novamente!"); // Infroma que a seja não bate com a cadastrada.
                        // logou continua false aqui, ou seja, continua reiterando até a senha correta ser informada.
                        //Claro que daria pra criar um sistema de recuperaçãao de senha, mas por agora esta ótimo.

                    }

                }
                else // Se a conta não existir
                {
                    // Informa que não existe, e pergunta se quer criar cadastrar uma nova conta.
                    System.out.print("Sua conta não existe, deseja criar uma (s/n)? ");
                    String continuar = leitor.nextLine(); // Lê as próximas strings digitadas na linha.

                    // Usa switch case pra verificar a entrada do usuário e mandar ele pra onde for necessário.
                    switch (continuar.toLowerCase())  // usei .toLowerCase() pra garantir compatibilidade, caso o usuário digite "S" ou "Sim" por exemplo.
                    {
                        case "s":
                        case "sim": cadastro(); // Se ele digitar "s" ou "sim" ele é levado pra o cadastro
                            break;

                        default: logou = false; // Se ele digitar outra coisa, reitera o loop.
                            break;

                    }

                }

            } while (!logou);

            leitor.close();
        }
        catch (Exception e)
        {
            System.out.println("Erro: " + e);

        }

    }

    public static void main(String[] args)
    {
        menuPrincipal();
    }

}

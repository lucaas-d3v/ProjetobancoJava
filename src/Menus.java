import java.util.Scanner;

public class Menus
{
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

                }
                case "2" -> { // Caso digite 2 eele vai pro login..
                    logar();
                    deu_errado = false; // Para o loop.

                }

                case "3" -> {
                    System.out.println("saindo...");

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

            // Pede a idade do usuário
            int idade;
            do {
                System.out.print("\nDigite a sua idade: "); // Pede a idade do usuário.
                idade = leitor.nextInt(); // Lê o próximo inteiro digitado pelo usuáio.

                if (!Verificador.isNumber(String.valueOf(idade)))
                {
                    System.out.println("A sua idade precisa ser um número!");
                }

            } while (!Verificador.isNumber(String.valueOf(idade)));

            // Pede o cpf ao usuário
            String cpf;
            do
            {
                System.out.print("Digite o seu cpf: ");
                cpf = leitor.nextLine();

                if(cpf.isEmpty())
                {
                    System.out.println("O cpf não pode ser vazio!");
                }

            } while (cpf.isEmpty());


            // Pede para o usuário criar uma senha
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

            if (GerenciarBanco.adicionarAoBanco(cpf, nome, idade, senha)) // Se a conta for adicionada com sucesso, ele informa.
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
            // Pede o cpf ao usuário
            String cpf;
            do
            {
                System.out.print("Digite o seu cpf: ");
                cpf = leitor.nextLine();

                if(cpf.isEmpty())
                {
                    System.out.println("O cpf não pode ser vazio!");
                }

            } while (cpf.isEmpty());

            // Aqui as coisas mudam.
            boolean existe = GerenciarBanco.verificarNoBanco(cpf); // Verifico se a conta existe.
            String senha_conta = GerenciarBanco.puxarSenha(cpf); // Pego a senha associada a conta.
            boolean nao_sair = false; // Verificar se conseguiu logar, false por padrão.
            boolean ir_tela_inicial = false;

            do
            {
                if (existe) // Se existe
                {
                    System.out.print("Insira a sua senha para logar na sua conta: "); // Peço a senha ao usuário
                    String senha_digitada = leitor.nextLine(); // Lê as próximas strings digitadas na linha.

                    if (senha_conta == null)
                    {
                        System.out.println("Erro: cpf é vazio!");
                    }

                    if (senha_digitada.equals(senha_conta))  // Se a senha digita for a mesma que esta cadastrada, o login é efetuado.
                    {
                        System.out.println("Login efetuado com sucesso!"); // Da boas vindas.
                        nao_sair = true; // Atualiza para true.
                        ir_tela_inicial = true;

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
                        case "sim":
                            nao_sair = true;
                            cadastro(); // Se ele digitar "s" ou "sim" ele é levado pra o cadastro.

                        case "n":
                        case "não": nao_sair = true;

                        default: nao_sair = false; // Se ele digitar outra coisa, reitera o loop.

                    }

                }

            } while (!nao_sair);

            if (ir_tela_inicial)
            {
                dashboard(cpf);
            }

        }
        catch (Exception e)
        {
            System.out.println("Erro: " + e);

        }

    }

    private static void dashboard(String cpf) {
        Scanner leitor = new Scanner(System.in);

        System.out.println("====== Seja bem-vindo(a) ======");

        boolean sair = false;

        int acao;
        do
        {
            System.out.print("[1] Excluir Conta\n[2] Sair\n\nDigite a sua ação: ");
            acao = leitor.nextInt();

            if (!Verificador.isNumber(String.valueOf(acao)) || String.valueOf(acao).isEmpty())
            {
                System.out.println("As ações são representados pelos números!");
                continue;

            }

            switch (acao)
            {
                case 1 -> {
                    boolean apagou = GerenciarBanco.removerDoBanco(cpf);

                    if (apagou)
                    {
                        System.out.println("Conta removida com sucesso!");
                        sair = true;
                    }
                    else
                    {
                        System.out.println("Não foi possível excluir sua conta!");
                        sair = false;

                    }

                }

                case 2 -> {
                    System.out.println("Saindo...");
                    sair = true;

                }

                default -> {
                    System.out.println("Ação não existe!");
                    sair = false;
                }

            }

        } while (!sair);

        leitor.close();

    }

    public static void main(String[] args)
    {
        menuPrincipal();

    }

}

public class Verificador
{

    public static boolean isAlpha(String palavra)
    {
        return palavra.trim().matches("^\\p{IsAlphabetic}");

    }

    public static boolean isCpf(String cpf)
    {
        return cpf.trim().matches("^\\d{11}$") || cpf.matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$");

    }

    public static boolean isEmail(String email)
    {
        return email.trim().matches("^[\\w._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    }

    public static boolean isName(String nome)
    {
        return nome.trim().matches("^[\\p{IsAlphabetic} ]+") && nome.length() > 4;

    }

    public static boolean isNumber(String n)
    {
        return !isAlpha(n);

    }

    public static boolean isPassword(String senha)
    {
        return senha.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$");

    }

}

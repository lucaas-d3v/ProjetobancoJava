import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Buscas
{
    public static boolean buscaBinaria(List<String> elementos, String nome) {
        List<String> ordenados = elementos.stream().sorted().toList();  // Garante ordenação

        int inicio = 0;
        int fim = ordenados.size() - 1;

        while (inicio <= fim)
        {
            int meio = (inicio + fim) / 2;
            String item = ordenados.get(meio);

            int comparacao = item.compareTo(nome); // compara alfabeticamente

            if (comparacao == 0)
            {
                return true; // Encontrou

            } else if (comparacao > 0)
            {
                fim = meio - 1; // Vai para a esquerda

            } else
            {
                inicio = meio + 1; // Vai para a direita

            }

        }

        return false; // Não encontrou
    }

    public static boolean buscaLinear(ArrayList<String> elementos, String nome)
    {

        for (String nome_lista : elementos)
        {
            if (nome.equals(nome_lista))
            {
                return true;

            }
            else
            {
                continue;

            }

        }
        return false;
    }

    public static ArrayList<Integer> gerarLista(int tamanho)
    {
        ArrayList<Integer> numeros = new ArrayList<>();

        for (int i = 1; i <= tamanho; i++)
        {
            numeros.add(i);
        }

        Collections.shuffle(numeros);

        return numeros;
    }
}



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    public static class Registro {
        int codigo;

        public Registro(int codigo) {
            this.codigo = codigo;
        }

        public int getCodigo() {
            return codigo;
        }
    }

    public interface FuncaoHash {
        int hash(int chave);
    }

    public static class HashDivisao implements FuncaoHash {
        int tamanho;

        public HashDivisao(int tamanho) {
            this.tamanho = tamanho;
        }

        public int hash(int chave) {
            return chave % tamanho;
        }
    }

    public static class HashMultiplicacao implements FuncaoHash {
        int tamanho;

        public HashMultiplicacao(int tamanho) {
            this.tamanho = tamanho;
        }

        public int hash(int chave) {
            double A = 0.6180339887;
            double val = chave * A;
            val = val - Math.floor(val);
            return (int) (tamanho * val);
        }
    }

    public static class HashDobramento implements FuncaoHash {
        int tamanho;

        public HashDobramento(int tamanho) {
            this.tamanho = tamanho;
        }

        public int hash(int chave) {
            String str = String.valueOf(chave);
            int soma = 0;
            for (int i = 0; i < str.length(); i += 2) {
                int grupo = Integer.parseInt(str.substring(i, Math.min(i + 2, str.length())));
                soma += grupo;
            }
            return soma % tamanho;
        }
    }

    public static class HashTable {
        LinkedList<Registro>[] tabela;
        FuncaoHash funcao;
        int colisoes = 0;

        public HashTable(int tamanho, FuncaoHash funcao) {
            this.funcao = funcao;
            tabela = new LinkedList[tamanho];
            for (int i = 0; i < tamanho; i++) {
                tabela[i] = new LinkedList<>();
            }
        }

        public void inserir(Registro r) {
            int pos = funcao.hash(r.getCodigo());
            if (!tabela[pos].isEmpty()) colisoes++;
            tabela[pos].add(r);
        }

        public boolean buscar(int codigo) {
            int pos = funcao.hash(codigo);
            for (Registro r : tabela[pos]) {
                if (r.getCodigo() == codigo) return true;
            }
            return false;
        }

        public int getColisoes() {
            return colisoes;
        }
    }

    // Gera os dados com seed
    public static List<Registro> gerarConjuntoDados(int quantidade, long seed) {
        Random rand = new Random(seed);
        List<Registro> lista = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            int codigo = 100_000_000 + rand.nextInt(900_000_000);
            lista.add(new Registro(codigo));
        }
        return lista;
    }

    // Writer para o arquivo CSV (aberto uma vez)
    static BufferedWriter writer;

    // Inicializa arquivo CSV e escreve cabeçalho
    public static void iniciarCSV() throws IOException {
        writer = new BufferedWriter(new FileWriter("resultados.csv"));
        writer.write("Operacao,TamanhoTabela,FuncaoHash,QtdRegistros,TempoMS,ColisoesOuAcertos\n");
    }

    // Salva resultado no CSV
    public static void salvarResultado(String operacao, int tamanhoTabela, String funcao, int qtdRegistros, long tempo, int colisoesOuEncontrados) throws IOException {
        String linha = String.format("%s,%d,%s,%d,%d,%d\n", operacao, tamanhoTabela, funcao, qtdRegistros, tempo, colisoesOuEncontrados);
        writer.write(linha);
    }

    public static void testarTabela(List<Registro> dados, int tamanhoTabela, FuncaoHash funcao, String nomeFuncao) throws IOException {
        HashTable tabela = new HashTable(tamanhoTabela, funcao);

        long inicio = System.currentTimeMillis();
        for (Registro r : dados) {
            tabela.inserir(r);
        }
        long fim = System.currentTimeMillis();
        long tempoInsercao = fim - inicio;

        salvarResultado("INSERCAO", tamanhoTabela, nomeFuncao, dados.size(), tempoInsercao, tabela.getColisoes());

        Random rand = new Random(42);
        int encontrados = 0;
        long inicioBusca = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            int idx = rand.nextInt(dados.size());
            if (tabela.buscar(dados.get(idx).getCodigo())) {
                encontrados++;
            }
        }
        long fimBusca = System.currentTimeMillis();
        long tempoBusca = fimBusca - inicioBusca;

        salvarResultado("BUSCA", tamanhoTabela, nomeFuncao, dados.size(), tempoBusca, encontrados);
    }

    public static void main(String[] args) {
        int[] tamanhosTabela = {1000, 10000, 100000};
        int[] tamanhosDados = {1_000_000, 5_000_000, 20_000_000};
        long seed = 12345L;

        try {
            iniciarCSV();

            for (int tamanhoTabela : tamanhosTabela) {
                for (int tamanhoDados : tamanhosDados) {
                    List<Registro> dados = gerarConjuntoDados(tamanhoDados, seed);

                    testarTabela(dados, tamanhoTabela, new HashDivisao(tamanhoTabela), "Divisao");
                    testarTabela(dados, tamanhoTabela, new HashMultiplicacao(tamanhoTabela), "Multiplicacao");
                    testarTabela(dados, tamanhoTabela, new HashDobramento(tamanhoTabela), "Dobramento");
                }
            }

            writer.close();
            System.out.println("Resultados salvos em resultados.csv");
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo CSV: " + e.getMessage());
        }
    }
}

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class ArquivoProduto {
    private static final String ARQUIVO = "produtos.txt";

    public static void salvarProdutos(List<Produto> produtos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Produto produto : produtos) {
                writer.write(produto.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar produtos: " + e.getMessage());
        }
    }

    public static List<Produto> carregarProdutos() {
        List<Produto> produtos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 3) {
                    String descricao = partes[0];
                    double precoCusto = Double.parseDouble(partes[1]);
                    double margemLucro = Double.parseDouble(partes[2]);
                    
                    if (partes.length == 4) {
                        LocalDate validade = LocalDate.parse(partes[3]);
                        produtos.add(new ProdutoPerecivel(descricao, precoCusto, margemLucro, validade));
                    } else {
                        produtos.add(new ProdutoNaoPerecivel(descricao, precoCusto, margemLucro));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar produtos: " + e.getMessage());
        }
        return produtos;
    }
}
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.Iterator;

public class App {
    public static void main(String[] args) {
        List<Produto> produtos = ArquivoProduto.carregarProdutos();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Listar produtos");
            System.out.println("3 - Excluir produto");
            System.out.println("4 - Finalizar");
            System.out.print("Escolha uma opção: ");
            
            int opcao = lerInteiro(scanner);
            scanner.nextLine(); 
            
            switch (opcao) {
                case 1:
                    cadastrarProduto(produtos, scanner);
                    ArquivoProduto.salvarProdutos(produtos);
                    break;
                case 2:
                    listarProdutos(produtos);
                    break;
                case 3:
                    excluirProduto(produtos, scanner);
                    ArquivoProduto.salvarProdutos(produtos);
                    break;
                case 4:
                    System.out.println("Sistema finalizado.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarProduto(List<Produto> produtos, Scanner scanner) {
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
        
        System.out.print("Preço de custo: ");
        double preco = lerDouble(scanner);
        scanner.nextLine(); 
        
        System.out.print("Margem de lucro (ex: 0.2 para 20%): ");
        double margem = lerDouble(scanner);
        scanner.nextLine(); 
        
        System.out.print("O produto é perecível? (s/n): ");
        char perecivel = scanner.next().charAt(0);
        scanner.nextLine(); 
        
        if (perecivel == 's' || perecivel == 'S') {
            LocalDate validade = lerData(scanner);
            if (validade != null) {
                produtos.add(new ProdutoPerecivel(nome, preco, margem, validade));
                System.out.println("Produto cadastrado com sucesso!");
            } else {
                System.out.println("Erro: Data inválida. Produto não cadastrado.");
            }
        } else {
            produtos.add(new ProdutoNaoPerecivel(nome, preco, margem));
            System.out.println("Produto cadastrado com sucesso!");
        }
    }

    private static void listarProdutos(List<Produto> produtos) {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            System.out.println("\nLista de Produtos:");
            for (Produto p : produtos) {
                System.out.println(p);
            }
        }
    }

    private static void excluirProduto(List<Produto> produtos, Scanner scanner) {
        System.out.print("Digite o nome do produto a ser excluído: ");
        String nome = scanner.nextLine();
        
        Iterator<Produto> iterator = produtos.iterator();
        while (iterator.hasNext()) {
            Produto p = iterator.next();
            if (p.descricao.equalsIgnoreCase(nome)) {
                iterator.remove();
                System.out.println("Produto removido com sucesso!");
                return;
            }
        }
        System.out.println("Produto não encontrado.");
    }

    private static int lerInteiro(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (Exception e) {
                System.out.print("Entrada inválida! Digite um número inteiro: ");
                scanner.next(); 
            }
        }
    }

    private static double lerDouble(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextDouble();
            } catch (Exception e) {
                System.out.print("Entrada inválida! Digite um número válido (ex: 10.50): ");
                scanner.next(); 
            }
        }
    }

    private static LocalDate lerData(Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            System.out.print("Data de validade (DD/MM/YYYY): ");
            String dataStr = scanner.nextLine();
            try {
                return LocalDate.parse(dataStr, formatter);
            } catch (Exception e) {
                System.out.println("Entrada inválida! Digite uma data válida no formato DD/MM/YYYY.");
            }
        }
    }
}
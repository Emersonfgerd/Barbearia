import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private Scanner scanner;
    private List<Funcionario> funcionarios; // Inicialize a lista de funcionários
    private List<Cliente> clientes;
    private Login login;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.clientes = new ArrayList<>();
        this.funcionarios = new ArrayList<>(); // Inicialize a lista de funcionários
        this.login = new Login(clientes, funcionarios);
    }

    public void exibirMenu() {
        while (true) {
            limparConsole();
            System.out.println("\n=== Menu ===");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Cadastrar Funcionario");
            System.out.println("3. Fazer Login");
            System.out.println("4. Exibir Clientes");
            System.out.println("5. Exibir Funcionarios");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            int opcao = scanner.nextInt(); // Ler a entrada como um número inteiro
            
            // Consumir a nova linha pendente
            scanner.nextLine();
            
            switch (opcao) {
                case 1:
                limparConsole();
                    // Adicionar o cliente cadastrado à lista de clientes
                    Cliente clienteCadastrado = Cliente.cadastrarCliente(scanner);
                    if (clienteCadastrado != null) {
                        clientes.add(clienteCadastrado);
                    }
                    break;
                case 2:
                limparConsole();
                    Funcionario funcionarioCadastrado = Funcionario.cadastrarFuncionario(scanner);
                    if (funcionarioCadastrado != null) {
                        funcionarios.add(funcionarioCadastrado);
                    }
                    break;
                case 3:
                limparConsole();
                    login.fazerLogin(); // Chama o método fazerLogin da classe Login
                    break;
                case 4:
                limparConsole();
                    Cliente.exibirClientes(clientes);
                    break;
                case 5:
                limparConsole();
                    Funcionario.exibirFuncionarios(funcionarios);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Por favor, escolha novamente.");
            }
        }
    }

    public static void limparConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.exibirMenu();
    }
}



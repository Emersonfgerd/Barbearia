import java.util.List;
import java.util.Scanner;

public class Login {
    private Scanner scanner;
    private List<Cliente> clientes;
    private List<Funcionario> funcionarios;

    // Modificando o construtor para aceitar as listas como parâmetros
    public Login(List<Cliente> clientes, List<Funcionario> funcionarios) {
        this.scanner = new Scanner(System.in);
        this.clientes = clientes;
        this.funcionarios = funcionarios;
    }

    public void fazerLogin() {
        System.out.println("\n=== Login ===");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Cliente cliente = validarClientePorSenha(email, senha);
        if (cliente != null) {
            exibirMenuCliente(cliente);
            return;
        }

        Funcionario funcionario = validarFuncionarioPorSenha(email, senha);
        if (funcionario != null) {
            exibirMenuFuncionario(funcionario);
            return;
        }

        System.out.println("Email ou senha incorretos. Tente novamente.");
    }

    private Cliente validarClientePorSenha(String email, String senha) {
        for (Cliente cliente : clientes) {
            if (cliente.getEmail().equals(email) && cliente.getSenha().equals(senha)) {
                return cliente;
            }
        }
        return null;
    }

    private Funcionario validarFuncionarioPorSenha(String email, String senha) {
        for (Funcionario funcionario : funcionarios) {
            if (funcionario.getEmail().equals(email) && funcionario.getSenha().equals(senha)) {
                return funcionario;
            }
        }
        return null;
    }

    public void exibirMenuCliente(Cliente cliente) {
        try {
            while (true) {
                limparConsole();
                System.out.println("\n=== Menu do Cliente ===");
                System.out.println("1. Agendar Horário");
                System.out.println("2. Ver Horários Disponíveis");
                System.out.println("3. Ver Meus Agendamentos");
                System.out.println("4. Editar Agendamento");
                System.out.println("5. Excluir Agendamento");
                System.out.println("0. Voltar ao menu principal");
    
                System.out.print("Escolha uma opção: ");
                String opcao = scanner.nextLine();
    
                switch (opcao) {
                    case "1":
                    limparConsole();
                        cliente.agendarHorario(Funcionario.getFuncionarios(), scanner);
                        break;
                    case "2":
                    limparConsole();
                        cliente.visualizarHorariosDisponiveis(Funcionario.getFuncionarios());
                        break;
                    case "3":
                    limparConsole();
                        cliente.visualizarAgendamento();
                        break;
                    case "4":
                    limparConsole();
                        cliente.editarAgendamento(funcionarios, scanner);
                        break;
                    case "5":
                    limparConsole();
                        cliente.excluirAgendamento(funcionarios, scanner);
                        break;
                    case "0":
                        return; // Retorna ao menu principal
                    default:
                        System.out.println("Opção inválida. Por favor, escolha novamente.");
                }
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao exibir o menu do cliente: " + e.getMessage());
        }
    }
    

    private void exibirMenuFuncionario(Funcionario funcionario) {
        try {
            while (true) {
                limparConsole();
                System.out.println("\n=== Menu do Funcionário ===");
                System.out.println("1. Abrir agendamento do dia");
                System.out.println("2. Exibir agendamentos do dia");
                System.out.println("0. Voltar ao menu principal");
    
                System.out.print("Escolha uma opção: ");
                String opcao = scanner.nextLine();
    
                switch (opcao) {
                    case "1":
                    limparConsole();
                        funcionario.abrirAgendamentoDoDia(scanner); // Passando o scanner como argumento
                        System.out.println("Agendamento do dia aberto com sucesso.");
                        break;
                    case "2":
                    limparConsole();
                        funcionario.exibirAgendamentosDoDia();
                        break;
                    case "0":
                        return; // Retorna ao menu principal
                    default:
                        System.out.println("Opção inválida. Por favor, escolha novamente.");
                }
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao exibir o menu do funcionário: " + e.getMessage());
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
}
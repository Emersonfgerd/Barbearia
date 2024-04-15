import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Funcionario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String contato;
    private static List<Funcionario> funcionarios = new ArrayList<>();
    private List<Agendamento> agendamentoDia;

    public Funcionario(int id, String nome, String email, String senha, String contato) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.contato = contato;
        this.agendamentoDia = new ArrayList<>();
    }

    // Métodos Getters e Setters

    public int getId() {
        return id;
    }

    public static void setFuncionarios(List<Funcionario> funcionarios) {
        Funcionario.funcionarios = funcionarios;
    }

    public void setAgendamentoDia(List<Agendamento> agendamentoDia) {
        this.agendamentoDia = agendamentoDia;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public static Funcionario cadastrarFuncionario(Scanner scanner) {
        System.out.println("Cadastro de Funcionário\n");

        while (true) {
            String nome;
            do {
                System.out.print("Nome: ");
                nome = scanner.nextLine();
                if (!nome.equals("0") && !nome.matches("[A-Za-z ]+")) {
                    System.out.println("Nome inválido. Use apenas letras e espaços.");
                }
            } while (!nome.equals("0") && !nome.matches("[A-Za-z ]+"));

            if (nome.equals("0")) {
                return null; // Retorna null para indicar que deseja voltar para o menu principal
            }

            String email;
            boolean emailExistente;
            do {
                emailExistente = false; // Inicializa como falso para cada nova entrada de email
                System.out.print("Email: ");
                email = scanner.nextLine();
                if (!email.equals("0") && !email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                    System.out.println("Email inválido. Insira um email válido.");
                    continue; // Volta ao início do loop para recomeçar o cadastro
                }
                // Verifica se o email já está sendo utilizado por outro funcionário
                for (Funcionario funcionario : funcionarios) {
                    if (funcionario.getEmail().equals(email)) {
                        emailExistente = true;
                        break;
                    }
                }
                if (emailExistente) {
                    System.out.println("Este email já está sendo utilizado por outro funcionário. Por favor, insira um email diferente.");
                }
            } while (emailExistente || (!email.equals("0") && !email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")));

            if (email.equals("0")) {
                return null; // Retorna null para indicar que deseja voltar para o menu principal
            }

            String senha;
            do {
                System.out.print("Senha (entre 4 e 6 dígitos): ");
                senha = scanner.nextLine();
                if (senha.length() < 4 || senha.length() > 6) {
                    System.out.println("A senha deve conter entre 4 e 6 dígitos.");
                }
            } while (senha.isEmpty() || senha.length() < 4 || senha.length() > 6);

            if (senha.equals("0")) {
                return null; // Retorna null para indicar que deseja voltar para o menu principal
            }

            String contato;
            do {
                System.out.print("Contato: ");
                contato = scanner.nextLine();
                if (!contato.equals("0") && !contato.matches("\\d{10,11}")) {
                    System.out.println("Número de telefone inválido. Insira um número de telefone válido (apenas dígitos, com 10 ou 11 dígitos).");
                }
            } while (!contato.equals("0") && !contato.matches("\\d{10,11}"));

            if (contato.equals("0")) {
                return null; // Retorna null para indicar que deseja voltar para o menu principal
            }

            // Exibe os dados do funcionário cadastrado
            Funcionario funcionario = new Funcionario(0, nome, email, senha, contato);
            funcionarios.add(funcionario);
            System.out.println("\nFuncionário cadastrado com sucesso:");
            System.out.println(funcionario.toString());

            // Pergunta se deseja cadastrar outro funcionário ou sair
            System.out.print("\nDeseja cadastrar outro funcionário? (S/N): ");
            String resposta = scanner.nextLine();
            if (!resposta.equalsIgnoreCase("S")) {
                return funcionario; // Retorna o funcionário cadastrado
            }
        }
    }

    // Método para exibir os funcionários cadastrados
    public static void exibirFuncionarios(List<Funcionario> funcionarios) {
        if (funcionarios.isEmpty()) {
            System.out.println("Não há funcionários cadastrados.");
        } else {
            System.out.println("Lista de Funcionários:");
            for (Funcionario funcionario : funcionarios) {
                System.out.println(funcionario);
                System.out.println("-------------");
            }
        }
    }

    // Método para abrir o agendamento do dia com entrada manual das horas disponíveis
    public void abrirAgendamentoDoDia(Scanner scanner) {
        agendamentoDia.clear(); // Limpa os agendamentos anteriores do dia
    
        // Horários disponíveis das 07:00 às 11:00 e das 13:00 às 17:00, com uma vaga por hora
        for (int hora = 7; hora <= 11; hora++) {
            System.out.println("Hora disponível: " + hora + ":00");
            System.out.print("Deseja adicionar esta hora? (S/N): ");
            String resposta = scanner.nextLine();
            if (resposta.equalsIgnoreCase("S")) {
            // Agendamento inicializado como disponível e sem cliente agendado
            agendamentoDia.add(new Agendamento(LocalTime.of(hora, 0), "", "", true, "", ""));
            }
        }
        for (int hora = 13; hora <= 17; hora++) {
            System.out.println("Hora disponível: " + hora + ":00");
            System.out.print("Deseja adicionar esta hora? (S/N): ");
            String resposta = scanner.nextLine();
            if (resposta.equalsIgnoreCase("S")) {
            // Agendamento inicializado como disponível e sem cliente agendado
            agendamentoDia.add(new Agendamento(LocalTime.of(hora, 0), "", "", true, "", ""));
            }
        }
    }

    // Método para exibir os agendamentos do dia
    public void exibirAgendamentosDoDia() {
        System.out.println("\n== Agendamentos do Dia: ==");
        for (Agendamento agendamento : agendamentoDia) {
            System.out.print("Horário: " + agendamento.getHorario() + " - ");
            if (agendamento.isDisponivel()) {
                System.out.println("Disponível: Sim");
            } else {
                System.out.println("Disponível: Não");
                System.out.println("Cliente: " + agendamento.getNomeCliente());
                System.out.println("Contato do Cliente: " + agendamento.getContatoCliente());
                System.out.println("Serviço: " + agendamento.getNomeServico());
                System.out.println("Descrição: " + agendamento.getDescricao());
            }
        }
    }


    // Método para obter os agendamentos do dia
    public List<Agendamento> getAgendamentoDia() {
        return agendamentoDia;
    }

    // Método para obter os horários disponíveis
    public List<LocalTime> obterHorariosDisponiveis() {
        List<LocalTime> horarios = new ArrayList<>();
        for (Agendamento agendamento : agendamentoDia) {
            if (agendamento.isDisponivel()) {
                horarios.add(agendamento.getHorario());
            }
        }
        return horarios;
    }
    
    // Método toString para exibir os dados do funcionário
    @Override
    public String toString() {
        return "Nome: " + nome + "\nEmail: " + email + "\nContato: " + contato;
    }

    // Método para obter a lista de funcionários cadastrados
    public static List<Funcionario> getFuncionarios() {
        return funcionarios;
    }
}
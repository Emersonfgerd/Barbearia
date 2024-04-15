import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalTime;

public class Cliente {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String contato;
    private List<LocalTime> horariosDisponiveis; // Lista de horários disponíveis para agendamento
    private static List<Cliente> clientes = new ArrayList<>(); // Lista estática para armazenar os clientes
    private List<Agendamento> agendamentos; // Lista de agendamentos do cliente


    // Construtor
    public Cliente(int id, String nome, String email, String senha, String contato) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.contato = contato;
        this.horariosDisponiveis = new ArrayList<>();
        this.agendamentos = new ArrayList<>(); // Inicializar a lista de agendamentos

    }

    // Métodos Getters e Setters
    public int getId() {
        return id;
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

    // Método para adicionar um horário disponível
    public void adicionarHorarioDisponivel(LocalTime horario) {
        this.horariosDisponiveis.add(horario);
    }

     // Método para visualizar os horários disponíveis
        public List<LocalTime> visualizarHorariosDisponiveis(List<Funcionario> funcionarios) {
            List<LocalTime> horariosDisponiveis = new ArrayList<>();
            for (Funcionario funcionario : funcionarios) {
                horariosDisponiveis.addAll(funcionario.obterHorariosDisponiveis());
            }

            if (horariosDisponiveis.isEmpty()) {
                System.out.println("Não há horários disponíveis para agendamento.");
            } else {
                System.out.println("Horários disponíveis para agendamento:");
                for (LocalTime horario : horariosDisponiveis) {
                    System.out.println(horario);
                }
                pressioneEnterParaContinuar();
            }
            return horariosDisponiveis; // Retornar a lista de horários disponíveis
        }

// Método para agendar um horário disponível
public void agendarHorario(List<Funcionario> funcionarios, Scanner scanner) {
    System.out.println("\n=== Agendamento de Horário ===");

    // Mostrar os horários disponíveis para o cliente escolher
    List<LocalTime> horariosDisponiveis = visualizarHorariosDisponiveis(funcionarios);

    // Verificar se existem horários disponíveis
    if (horariosDisponiveis.isEmpty()) {
        System.out.println("Não há horários disponíveis para agendamento.");
        return;
    }

    System.out.print("Escolha o horário desejado (formato HH:MM): ");
    String horarioStr = scanner.nextLine();

    // Validar o formato do horário inserido pelo cliente
    LocalTime horarioEscolhido;
    try {
        horarioEscolhido = LocalTime.parse(horarioStr);
    } catch (Exception e) {
        System.out.println("Formato de horário inválido. Use o formato HH:MM.");
        return;
    }

    // Verificar se o horário escolhido está disponível
    if (!horariosDisponiveis.contains(horarioEscolhido)) {
        System.out.println("Horário não disponível para agendamento.");
        return;
    }

    // Solicitar nome do serviço
    System.out.print("Nome do serviço: ");
    String nomeServico = scanner.nextLine();

    // Solicitar descrição opcional
    System.out.print("Descrição (opcional): ");
    String descricao = scanner.nextLine();

    // Realizar o agendamento
    boolean horarioDisponivel = false;
    for (Funcionario funcionario : funcionarios) {
        for (Agendamento agendamento : funcionario.getAgendamentoDia()) {
            if (agendamento.getHorario().equals(horarioEscolhido) && agendamento.isDisponivel()) {
                // Atualizar o estado do horário agendado no funcionário
                agendamento.setDisponivel(false);
                agendamento.setNomeCliente(this.nome);
                agendamento.setContatoCliente(this.contato);
                agendamento.setNomeServico(nomeServico);
                agendamento.setDescricao(descricao);
                System.out.println("Horário agendado com sucesso para: " + horarioEscolhido);
                System.out.println("Nome do serviço: " + nomeServico);
                if (!descricao.isEmpty()) {
                    System.out.println("Descrição: " + descricao);
                }
                // Adicionar o horário agendado à lista de agendamentos do cliente
                Agendamento novoAgendamento = new Agendamento(horarioEscolhido, nomeServico, descricao, false, this.nome, this.contato);
                agendamentos.add(novoAgendamento);

                horarioDisponivel = true;
                break;
            }
        }
        if (horarioDisponivel) {
            break;
        }
    }

    if (!horarioDisponivel) {
        System.out.println("Horário não disponível para agendamento.");
    }
}

// Método para editar um agendamento
public void editarAgendamento(List<Funcionario> funcionarios, Scanner scanner) {
    System.out.println("\n=== Edição de Agendamento ===");

    // Verificar se há agendamentos para editar
    if (agendamentos.isEmpty()) {
        System.out.println("Você não possui nenhum horário agendado para editar.");
        return;
    }

    // Mostrar os agendamentos do cliente
    visualizarAgendamento();

    // Solicitar o horário do agendamento a ser editado
    System.out.print("Digite o horário do agendamento que deseja editar (formato HH:MM): ");
    String horarioStr = scanner.nextLine();

    // Validar o formato do horário inserido pelo cliente
    LocalTime horarioEscolhido;
    try {
        horarioEscolhido = LocalTime.parse(horarioStr);
    } catch (Exception e) {
        System.out.println("Formato de horário inválido. Use o formato HH:MM.");
        return;
    }

    // Procurar o agendamento com o horário especificado
    boolean encontrado = false;
    for (Agendamento agendamento : agendamentos) {
        if (agendamento.getHorario().equals(horarioEscolhido)) {
            encontrado = true;

            // Solicitar novas informações para o agendamento
            System.out.println("Digite as novas informações para o agendamento:");

            System.out.print("Nome do serviço: ");
            String nomeServico = scanner.nextLine();
            agendamento.setNomeServico(nomeServico);

            System.out.print("Descrição (opcional): ");
            String descricao = scanner.nextLine();
            agendamento.setDescricao(descricao);

            System.out.println("Agendamento editado com sucesso.");

            // Atualizar o agendamento correspondente na lista de agendamentos do funcionário
            for (Funcionario funcionario : funcionarios) {
                for (Agendamento agendamentoFuncionario : funcionario.getAgendamentoDia()) {
                    if (agendamentoFuncionario.getHorario().equals(horarioEscolhido)) {
                        // Atualizar os dados do agendamento no funcionário
                        agendamentoFuncionario.setNomeCliente(this.nome);
                        agendamentoFuncionario.setContatoCliente(this.contato);
                        agendamentoFuncionario.setNomeServico(nomeServico);
                        agendamentoFuncionario.setDescricao(descricao);
                        break;
                    }
                }
            }
            break;
        }
    }

    if (!encontrado) {
        System.out.println("Não foi encontrado nenhum agendamento com o horário especificado.");
    }
}

// Método para excluir um agendamento
public void excluirAgendamento(List<Funcionario> funcionarios, Scanner scanner) {
    System.out.println("\n=== Exclusão de Agendamento ===");

    // Verificar se há agendamentos para excluir
    if (agendamentos.isEmpty()) {
        System.out.println("Você não possui nenhum horário agendado para excluir.");
        return;
    }

    // Mostrar os agendamentos do cliente
    visualizarAgendamento();

    // Solicitar o horário do agendamento a ser excluído
    System.out.print("Digite o horário do agendamento que deseja excluir (formato HH:MM): ");
    String horarioStr = scanner.nextLine();

    // Validar o formato do horário inserido pelo cliente
    LocalTime horarioEscolhido;
    try {
        horarioEscolhido = LocalTime.parse(horarioStr);
    } catch (Exception e) {
        System.out.println("Formato de horário inválido. Use o formato HH:MM.");
        return;
    }

    // Procurar o agendamento com o horário especificado e removê-lo
    boolean removido = false;
    for (Agendamento agendamento : agendamentos) {
        if (agendamento.getHorario().equals(horarioEscolhido)) {
            agendamentos.remove(agendamento);
            System.out.println("Agendamento excluído com sucesso.");

            // Atualizar o estado do agendamento na lista de agendamentos do funcionário
            for (Funcionario funcionario : funcionarios) {
                for (Agendamento agendamentoFuncionario : funcionario.getAgendamentoDia()) {
                    if (agendamentoFuncionario.getHorario().equals(horarioEscolhido)) {
                        agendamentoFuncionario.setDisponivel(true);
                        break;
                    }
                }
            }

            removido = true;
            break;
        }
    }

    if (!removido) {
        System.out.println("Não foi encontrado nenhum agendamento com o horário especificado.");
    }
}


     // Método para visualizar o agendamento do cliente
        public void visualizarAgendamento() {
            if (agendamentos.isEmpty()) {
                System.out.println("Você não possui nenhum horário agendado.");
            } else {
                System.out.println("Seus horários agendados são:");
                for (Agendamento agendamento : agendamentos) {
                    System.out.println("Horário: " + agendamento.getHorario());
                    System.out.println("Serviço: " + agendamento.getNomeServico());
                    System.out.println("Descrição: " + agendamento.getDescricao());
                    System.out.println("--------------------------");
                }
                pressioneEnterParaContinuar();
            }
        }


    // Método para cadastrar um cliente
    public static Cliente cadastrarCliente(Scanner scanner) {
        System.out.println("Cadastro de Cliente\n");

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
                // Verifica se o email já está sendo utilizado por outro cliente
                for (Cliente cliente : clientes) {
                    if (cliente.getEmail().equals(email)) {
                        emailExistente = true;
                        break;
                    }
                }
                if (emailExistente) {
                    System.out.println("Este email já está sendo utilizado por outro cliente. Por favor, insira um email diferente.");
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

            // Exibe os dados do cliente cadastrado
            Cliente cliente = new Cliente(0, nome, email, senha, contato);
            clientes.add(cliente); // Adiciona o cliente à lista de clientes
            System.out.println("\nCliente cadastrado com sucesso:");
            System.out.println(cliente.toString());

            // Pergunta se deseja cadastrar outro cliente ou sair
            System.out.print("\nDeseja cadastrar outro cliente? (S/N): ");
            String resposta = scanner.nextLine();
            if (!resposta.equalsIgnoreCase("S")) {
                return cliente; // Retorna o cliente cadastrado
            }
        }
    }

    // Método para exibir os clientes cadastrados
    public static void exibirClientes(List<Cliente> clientes) {
        if (clientes.isEmpty()) {
            System.out.println("\nNão há clientes cadastrados.");
        } else {
            System.out.println("\n == Lista de Clientes: ==");
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
                System.out.println("-------------------");
            }
        }
    }

    private static void pressioneEnterParaContinuar() {
        Scanner scanner = new Scanner(System.in);
        // Exibe uma mensagem instruindo o usuário a pressionar Enter para continuar
        System.out.println("\nPressione Enter para continuar...");

        // Aguarda até que o usuário pressione a tecla Enter
        scanner.nextLine();
    }

    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    // Método toString para exibir os dados do cliente
    @Override
    public String toString() {
        return "Nome: " + nome + "\nEmail: " + email + "\nContato: " + contato;
    }
}

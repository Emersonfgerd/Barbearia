import java.time.LocalTime;

public class Agendamento {
    private LocalTime horario;
    private String nomeServico;
    private String descricao;
    private boolean disponivel;
    private String nomeCliente;
    private String contatoCliente;

    

    
    public Agendamento(LocalTime horario, String nomeServico, String descricao, boolean disponivel, String nomeCliente,
    String contatoCliente) {
        this.horario = horario;
        this.nomeServico = nomeServico;
        this.descricao = descricao;
        this.disponivel = disponivel;
        this.nomeCliente = nomeCliente;
        this.contatoCliente = contatoCliente;
    }


    public String getNomeCliente() {
        return nomeCliente;
    }


    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }


    public String getContatoCliente() {
        return contatoCliente;
    }


    public void setContatoCliente(String contatoCliente) {
        this.contatoCliente = contatoCliente;
    }


    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
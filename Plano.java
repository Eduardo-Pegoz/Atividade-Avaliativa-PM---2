import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Plano {

    private String nome;
    private double valorMensal;
    private int quantidadeMaximaAtividades;
    private StatusPlano status;
    private final List<Atividade> atividades = new ArrayList<>();

    public Plano(String nome,
                 double valorMensal,
                 int quantidadeMaximaAtividades,
                 StatusPlano status) {

        this.nome = nome;
        this.valorMensal = valorMensal;
        this.quantidadeMaximaAtividades = quantidadeMaximaAtividades;
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValorMensal() {
        return valorMensal;
    }

    public void setValorMensal(double valorMensal) {
        this.valorMensal = valorMensal;
    }

    public int getQuantidadeMaximaAtividades() {
        return quantidadeMaximaAtividades;
    }

    public void setQuantidadeMaximaAtividades(int quantidadeMaximaAtividades) {
        this.quantidadeMaximaAtividades = quantidadeMaximaAtividades;
    }

    public StatusPlano getStatus() {
        return status;
    }

    public void setStatus(StatusPlano status) {
        this.status = status;
    }

    public List<Atividade> getAtividades() {
        return Collections.unmodifiableList(atividades);
    }

    public void associarAtividade(Atividade atividade) {
        if (atividade == null) {
            throw new IllegalArgumentException("A atividade nao pode ser nula.");
        }
        if (status != StatusPlano.ATIVO) {
            throw new IllegalStateException("Nao e possivel alterar um plano inativo.");
        }
        if (atividade.getStatus() == StatusAtividade.INATIVA) {
            throw new IllegalArgumentException("Nao e possivel associar atividade inativa.");
        }
        if (atividades.contains(atividade)) {
            throw new IllegalArgumentException("A atividade ja esta associada ao plano.");
        }
        if (!permiteAtividadeColetiva() && atividade.atividadeColetiva()) {
            throw new IllegalArgumentException("Este plano permite apenas atividades individuais.");
        }
        if (quantidadeMaximaAtividades > 0
                && atividades.size() >= quantidadeMaximaAtividades) {
            throw new IllegalStateException("O plano atingiu o limite de atividades.");
        }
        atividades.add(atividade);
    }

    public void removerAtividade(Atividade atividade) {
        atividades.remove(atividade);
    }

    public abstract void exibirDetalhes();

    public abstract boolean permiteAtividadeColetiva();
}

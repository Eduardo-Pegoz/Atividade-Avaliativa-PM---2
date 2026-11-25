import java.util.HashSet;
import java.util.Set;

public class Spinning extends Atividade implements ControladorPresenca {
    private final int quantidadeBicicletas;
    private final Set<String> participantesPresentes = new HashSet<>();

    public Spinning(String nome, String horario, int duracaoAula,
                    int quantidadeMaximaParticipantes, double valorMensal,
                    StatusAtividade status, int quantidadeBicicletas) {
        super(nome, horario, duracaoAula, quantidadeMaximaParticipantes,
                valorMensal, status);
        if (quantidadeBicicletas <= 0) {
            throw new IllegalArgumentException("A quantidade de bicicletas deve ser positiva.");
        }
        this.quantidadeBicicletas = quantidadeBicicletas;
    }

    public int getQuantidadeBicicletas() {
        return quantidadeBicicletas;
    }

    @Override
    public void registrarPresenca(String nomeParticipante) {
        if (nomeParticipante == null || nomeParticipante.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe o nome do participante.");
        }
        String nome = nomeParticipante.trim();
        if (getStatus() == StatusAtividade.INATIVA) {
            throw new IllegalStateException("A atividade esta inativa.");
        }
        if (participantesPresentes.contains(nome)) {
            throw new IllegalStateException("A presenca deste participante ja foi registrada.");
        }
        if (participantesPresentes.size() >= getQuantidadeMaximaParticipantes()) {
            setStatus(StatusAtividade.LOTADA);
            throw new IllegalStateException("A turma atingiu o limite de participantes.");
        }
        participantesPresentes.add(nome);
        if (participantesPresentes.size() >= getQuantidadeMaximaParticipantes()) {
            setStatus(StatusAtividade.LOTADA);
        }
        System.out.println("Presenca de " + nome + " registrada em " + getNome() + ".");
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("Spinning: " + getNome()
                + " | Horario: " + getHorario()
                + " | Duracao: " + getDuracaoAula() + " min"
                + " | Participantes maximos: " + getQuantidadeMaximaParticipantes()
                + " | Bicicletas: " + quantidadeBicicletas
                + " | Presentes: " + participantesPresentes.size()
                + " | Valor mensal: R$ " + String.format("%.2f", getValorMensal())
                + " | Status: " + getStatus());
    }

    @Override
    public boolean atividadeColetiva() {
        return true;
    }
}

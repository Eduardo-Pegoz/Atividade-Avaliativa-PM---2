import java.util.HashSet;
import java.util.Set;

public class Natacao extends Atividade implements Certificavel, ControladorPresenca {
    private final double profundidadePiscina;
    private final Set<String> participantesPresentes = new HashSet<>();

    public Natacao(String nome, String horario, int duracaoAula,
                   int quantidadeMaximaParticipantes, double valorMensal,
                   StatusAtividade status, double profundidadePiscina) {
        super(nome, horario, duracaoAula, quantidadeMaximaParticipantes,
                valorMensal, status);
        if (profundidadePiscina <= 0) {
            throw new IllegalArgumentException("A profundidade da piscina deve ser positiva.");
        }
        this.profundidadePiscina = profundidadePiscina;
    }

    public double getProfundidadePiscina() {
        return profundidadePiscina;
    }

    @Override
    public void registrarPresenca(String nomeParticipante) {
        String nome = validarNome(nomeParticipante);
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
    public void emitirCertificado(String nomeParticipante) {
        String nome = validarNome(nomeParticipante);
        if (!participantesPresentes.contains(nome)) {
            throw new IllegalStateException(
                    "O certificado so pode ser emitido apos o registro de presenca.");
        }
        System.out.println("Certificado emitido para " + nome
                + " pela participacao em " + getNome() + ".");
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("Natacao: " + getNome()
                + " | Horario: " + getHorario()
                + " | Duracao: " + getDuracaoAula() + " min"
                + " | Participantes maximos: " + getQuantidadeMaximaParticipantes()
                + " | Profundidade da piscina: " + profundidadePiscina + " m"
                + " | Presentes: " + participantesPresentes.size()
                + " | Certificado: sim"
                + " | Valor mensal: R$ " + String.format("%.2f", getValorMensal())
                + " | Status: " + getStatus());
    }

    @Override
    public boolean atividadeColetiva() {
        return true;
    }

    private String validarNome(String nomeParticipante) {
        if (nomeParticipante == null || nomeParticipante.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe o nome do participante.");
        }
        return nomeParticipante.trim();
    }
}

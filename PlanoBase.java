import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class PlanoBase extends Plano implements ControladorPresenca {
    private final Map<String, LocalDate> ultimoAcessoPorParticipante = new HashMap<>();

    public PlanoBase(String nome, double valorMensal, int quantidadeMaximaAtividades,
                     StatusPlano status) {
        super(nome, valorMensal, quantidadeMaximaAtividades, status);
    }

    @Override
    public void registrarPresenca(String nomeParticipante) {
        if (nomeParticipante == null || nomeParticipante.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe o nome do participante.");
        }
        if (getStatus() != StatusPlano.ATIVO) {
            throw new IllegalStateException("O plano nao esta ativo.");
        }

        String nome = nomeParticipante.trim();
        LocalDate hoje = LocalDate.now();
        if (hoje.equals(ultimoAcessoPorParticipante.get(nome))) {
            throw new IllegalStateException("O participante ja registrou o acesso de hoje.");
        }
        ultimoAcessoPorParticipante.put(nome, hoje);
        System.out.println("Acesso registrado para " + nome + " no plano "
                + getNome() + ".");
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("Plano basico: " + getNome()
                + " | Valor mensal: R$ " + String.format("%.2f", getValorMensal())
                + " | Status: " + getStatus()
                + " | Limite de atividades: " + getQuantidadeMaximaAtividades()
                + " | Atividades associadas: " + getAtividades().size()
                + " | Acesso: 1 por dia");
        exibirAtividadesAssociadas();
    }

    @Override
    public boolean permiteAtividadeColetiva() {
        return false;
    }

    private void exibirAtividadesAssociadas() {
        for (Atividade atividade : getAtividades()) {
            System.out.println("  - " + atividade.getNome());
        }
    }
}
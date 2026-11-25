public class Musculacao extends Atividade {
    private final int quantidadeAparelhos;

    public Musculacao(String nome, String horario, int duracaoAula,
                      int quantidadeMaximaParticipantes, double valorMensal,
                      StatusAtividade status, int quantidadeAparelhos) {
        super(nome, horario, duracaoAula, quantidadeMaximaParticipantes,
                valorMensal, status);
        if (quantidadeAparelhos <= 0) {
            throw new IllegalArgumentException("A quantidade de aparelhos deve ser positiva.");
        }
        this.quantidadeAparelhos = quantidadeAparelhos;
    }

    public int getQuantidadeAparelhos() {
        return quantidadeAparelhos;
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("Musculacao: " + getNome()
                + " | Horario: " + getHorario()
                + " | Duracao: " + getDuracaoAula() + " min"
                + " | Participantes maximos: " + getQuantidadeMaximaParticipantes()
                + " | Aparelhos: " + quantidadeAparelhos
                + " | Valor mensal: R$ " + String.format("%.2f", getValorMensal())
                + " | Status: " + getStatus());
    }

    @Override
    public boolean atividadeColetiva() {
        return false;
    }
}

public class PlanoPremium extends Plano {
    public PlanoPremium(String nome, double valorMensal, int quantidadeMaximaAtividades,
                        StatusPlano status) {
        super(nome, valorMensal, quantidadeMaximaAtividades, status);
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("Plano premium: " + getNome()
                + " | Valor mensal: R$ " + String.format("%.2f", getValorMensal())
                + " | Status: " + getStatus()
                + " | Limite de atividades: " + getQuantidadeMaximaAtividades()
                + " | Atividades associadas: " + getAtividades().size()
                + " | Acesso ilimitado");
        for (Atividade atividade : getAtividades()) {
            System.out.println("  - " + atividade.getNome());
        }
    }

    @Override
    public boolean permiteAtividadeColetiva() {
        return true;
    }
}

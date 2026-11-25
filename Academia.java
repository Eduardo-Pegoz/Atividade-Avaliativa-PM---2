import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Academia {
    private final List<Atividade> atividades = new ArrayList<>();
    private final List<Plano> planos = new ArrayList<>();

    public void cadastrarAtividade(Atividade atividade) {
        if (atividade == null) {
            throw new IllegalArgumentException("A atividade nao pode ser nula.");
        }
        atividades.add(atividade);
    }

    public void cadastrarPlano(Plano plano) {
        if (plano == null) {
            throw new IllegalArgumentException("O plano nao pode ser nulo.");
        }
        planos.add(plano);
    }

    public List<Atividade> getAtividades() {
        return Collections.unmodifiableList(atividades);
    }

    public List<Plano> getPlanos() {
        return Collections.unmodifiableList(planos);
    }

    public void associarAtividade(Plano plano, Atividade atividade) {
        if (!planos.contains(plano)) {
            throw new IllegalArgumentException("O plano nao esta cadastrado.");
        }
        if (!atividades.contains(atividade)) {
            throw new IllegalArgumentException("A atividade nao esta cadastrada.");
        }
        plano.associarAtividade(atividade);
    }

    public void exibirAtividades() {
        if (atividades.isEmpty()) {
            System.out.println("Nenhuma atividade cadastrada.");
            return;
        }
        for (int i = 0; i < atividades.size(); i++) {
            System.out.println("\n[" + (i + 1) + "]");
            atividades.get(i).exibirDetalhes();
        }
    }

    public void exibirPlanos(boolean apenasAtivos) {
        boolean encontrou = false;
        for (int i = 0; i < planos.size(); i++) {
            Plano plano = planos.get(i);
            if (!apenasAtivos || plano.getStatus() == StatusPlano.ATIVO) {
                System.out.println("\n[" + (i + 1) + "]");
                plano.exibirDetalhes();
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println(apenasAtivos
                    ? "Nenhum plano ativo cadastrado."
                    : "Nenhum plano cadastrado.");
        }
    }

    public int removerAtividadesInativas() {
        int removidas = 0;
        Iterator<Atividade> iterator = atividades.iterator();
        while (iterator.hasNext()) {
            Atividade atividade = iterator.next();
            if (atividade.getStatus() == StatusAtividade.INATIVA) {
                for (Plano plano : planos) {
                    plano.removerAtividade(atividade);
                }
                iterator.remove();
                removidas++;
            }
        }
        return removidas;
    }

    public Atividade getAtividade(int indice) {
        return atividades.get(indice);
    }

    public Plano getPlano(int indice) {
        return planos.get(indice);
    }
}
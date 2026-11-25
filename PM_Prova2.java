import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PM_Prova2 {
    public static void main(String[] args) {
        Academia academia = new Academia();
        inicializarDados(academia);

        try (Scanner scanner = new Scanner(System.in)) {
            int opcao;
            do {
                exibirMenu();
                opcao = lerInteiro(scanner, "Escolha uma opcao: ");
                try {
                    executarOpcao(opcao, scanner, academia);
                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("Nao foi possivel concluir a operacao: " + e.getMessage());
                }
            } while (opcao != 0);
        }
        System.out.println("Sistema encerrado.");
    }

    private static void inicializarDados(Academia academia) {
        Musculacao musculacao = new Musculacao(
                "Musculacao", "07:00", 60, 20, 89.90,
                StatusAtividade.ATIVA, 15);
        Natacao natacao = new Natacao(
                "Natacao", "08:00", 50, 12, 119.90,
                StatusAtividade.ATIVA, 2.0);
        Spinning spinning = new Spinning(
                "Spinning", "18:00", 45, 15, 99.90,
                StatusAtividade.ATIVA, 15);

        PlanoBase basico = new PlanoBase(
                "Basico", 99.90, 1, StatusPlano.ATIVO);
        PlanoPremium premium = new PlanoPremium(
                "Premium", 189.90, 0, StatusPlano.ATIVO);

        academia.cadastrarAtividade(musculacao);
        academia.cadastrarAtividade(natacao);
        academia.cadastrarAtividade(spinning);
        academia.cadastrarPlano(basico);
        academia.cadastrarPlano(premium);

        academia.associarAtividade(basico, musculacao);
        academia.associarAtividade(premium, musculacao);
        academia.associarAtividade(premium, natacao);
        academia.associarAtividade(premium, spinning);
    }

    private static void exibirMenu() {
        System.out.println("\n=== Sistema de Gerenciamento da Academia ===");
        System.out.println("1. Associar atividade a um plano");
        System.out.println("2. Exibir todas as atividades");
        System.out.println("3. Exibir todos os planos");
        System.out.println("4. Exibir apenas planos ativos");
        System.out.println("5. Emitir certificado");
        System.out.println("6. Registrar presenca em atividade");
        System.out.println("7. Remover atividades inativas");
        System.out.println("0. Sair");
    }

    private static void executarOpcao(int opcao, Scanner scanner, Academia academia) {
        switch (opcao) {
            case 1:
                associarAtividade(scanner, academia);
                break;
            case 2:
                academia.exibirAtividades();
                break;
            case 3:
                academia.exibirPlanos(false);
                break;
            case 4:
                academia.exibirPlanos(true);
                break;
            case 5:
                emitirCertificado(scanner, academia);
                break;
            case 6:
                registrarPresencaAtividade(scanner, academia);
                break;
            case 7:
                int removidas = academia.removerAtividadesInativas();
                System.out.println(removidas + " atividade(s) inativa(s) removida(s).");
                break;
            case 0:
                break;
            default:
                System.out.println("Opcao invalida.");
        }
    }

    private static void associarAtividade(Scanner scanner, Academia academia) {
        if (academia.getPlanos().isEmpty() || academia.getAtividades().isEmpty()) {
            System.out.println("Cadastre pelo menos um plano e uma atividade.");
            return;
        }
        System.out.println("\nPlanos disponiveis:");
        academia.exibirPlanos(false);
        Plano plano = academia.getPlano(lerIndice(scanner, academia.getPlanos().size(),
                "Numero do plano: "));

        System.out.println("\nAtividades disponiveis:");
        academia.exibirAtividades();
        Atividade atividade = academia.getAtividade(
                lerIndice(scanner, academia.getAtividades().size(), "Numero da atividade: "));
        academia.associarAtividade(plano, atividade);
        System.out.println("Atividade associada ao plano com sucesso.");
    }

    private static void emitirCertificado(Scanner scanner, Academia academia) {
        List<Atividade> certificaveis = new ArrayList<>();
        for (Atividade atividade : academia.getAtividades()) {
            if (atividade instanceof Certificavel) {
                certificaveis.add(atividade);
            }
        }
        if (certificaveis.isEmpty()) {
            System.out.println("Nao ha atividades certificaveis cadastradas.");
            return;
        }
        System.out.println("Atividades certificaveis:");
        exibirListaAtividades(certificaveis);
        Atividade atividade = certificaveis.get(lerIndice(scanner, certificaveis.size(),
                "Numero da atividade: "));
        String nome = lerTexto(scanner, "Nome do participante: ");
        ((Certificavel) atividade).emitirCertificado(nome);
    }

    private static void registrarPresencaAtividade(Scanner scanner, Academia academia) {
        List<Atividade> controladas = new ArrayList<>();
        for (Atividade atividade : academia.getAtividades()) {
            if (atividade instanceof ControladorPresenca) {
                controladas.add(atividade);
            }
        }
        if (controladas.isEmpty()) {
            System.out.println("Nao ha atividades com controle de presenca cadastradas.");
            return;
        }
        System.out.println("Atividades com controle de presenca:");
        exibirListaAtividades(controladas);
        Atividade atividade = controladas.get(lerIndice(scanner, controladas.size(),
                "Numero da atividade: "));
        String nome = lerTexto(scanner, "Nome do participante: ");
        ((ControladorPresenca) atividade).registrarPresenca(nome);
    }

    private static void exibirListaAtividades(List<Atividade> atividades) {
        for (int i = 0; i < atividades.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + atividades.get(i).getNome()
                    + " (" + atividades.get(i).getStatus() + ")");
        }
    }

    private static int lerIndice(Scanner scanner, int quantidade, String mensagem) {
        int indice;
        do {
            indice = lerInteiro(scanner, mensagem);
            if (indice < 1 || indice > quantidade) {
                System.out.println("Escolha um numero entre 1 e " + quantidade + ".");
            }
        } while (indice < 1 || indice > quantidade);
        return indice - 1;
    }

    private static int lerInteiro(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero inteiro valido.");
            }
        }
    }

    private static String lerTexto(Scanner scanner, String mensagem) {
        String texto;
        do {
            System.out.print(mensagem);
            texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("O texto nao pode ficar vazio.");
            }
        } while (texto.isEmpty());
        return texto;
    }
}

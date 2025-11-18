package br.com.votacao.app;

import br.com.votacao.service.VotacaoService;
import br.com.votacao.view.ConsoleView;

/**
 * Classe inicial da aplicação CLI.
 */
public final class Main {

    private Main() {
        // Evita instanciação
    }

    public static void main(String[] args) {
        VotacaoService service = VotacaoService.defaultService();
        ConsoleView view = new ConsoleView(service);
        view.start();
    }
}


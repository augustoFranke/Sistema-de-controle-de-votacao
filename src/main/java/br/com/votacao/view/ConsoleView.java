package br.com.votacao.view;

import br.com.votacao.exception.NenhumVotoException;
import br.com.votacao.model.ResultadoApuracao;
import br.com.votacao.service.VotacaoService;

import java.io.PrintStream;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Camada de View baseada em CLI responsável por toda interação com o usuário.
 * Mantém o menu restrito às duas funções solicitadas (cadastro e voto) e
 * apresenta a apuração apenas ao encerrar o programa.
 */
public class ConsoleView {

    private final VotacaoService service;
    private final Scanner scanner;
    private final PrintStream out;

    public ConsoleView(VotacaoService service) {
        this(service, new Scanner(System.in), System.out);
    }

    // Construtor extra para facilitar testes futuros
    public ConsoleView(VotacaoService service, Scanner scanner, PrintStream out) {
        this.service = service;
        this.scanner = scanner;
        this.out = out;
    }

    public void start() {
        out.println("=== Sistema de Votação CLI ===");
        boolean executando = true;
        while (executando) {
            mostrarMenu();
            String opcao = scanner.nextLine().trim();
            try {
                switch (opcao) {
                    case "1" -> cadastrarPessoa();
                    case "2" -> registrarVoto();
                    case "0" -> {
                        executando = false;
                        exibirApuracaoFinal();
                    }
                    default -> out.println("Opção inválida.");
                }
            } catch (RuntimeException e) {
                out.println("Erro: " + e.getMessage());
            }
        }
        out.println("Até logo!");
    }

    private void mostrarMenu() {
        out.println();
        out.println("1 - Cadastrar candidato ou eleitor");
        out.println("2 - Registrar voto");
        out.println("0 - Encerrar e apurar resultado");
        out.print("Escolha: ");
    }

    private void cadastrarPessoa() {
        out.print("Deseja cadastrar (C)andidato ou (E)leitor? ");
        String tipo = scanner.nextLine().trim().toUpperCase(Locale.ROOT);
        if ("C".equals(tipo)) {
            out.print("Nome do candidato: ");
            String nome = scanner.nextLine();
            out.print("Número do candidato: ");
            Integer numero = Integer.valueOf(scanner.nextLine());
            service.cadastrarCandidato(nome, numero);
            out.println("Candidato cadastrado.");
        } else if ("E".equals(tipo)) {
            out.print("Nome do eleitor: ");
            String nome = scanner.nextLine();
            out.print("Documento do eleitor: ");
            String documento = scanner.nextLine();
            service.cadastrarEleitor(nome, documento);
            out.println("Eleitor cadastrado.");
        } else {
            out.println("Tipo inválido.");
        }
    }

    private void registrarVoto() {
        out.print("Documento do eleitor: ");
        String documento = scanner.nextLine();
        out.print("Número do candidato: ");
        Integer numero = Integer.valueOf(scanner.nextLine());
        service.registrarVoto(documento, numero);
        out.println("Voto computado.");
    }

    private void exibirApuracaoFinal() {
        try {
            List<ResultadoApuracao> resultados = service.apurarResultados();
            out.println();
            out.println("=== Resultado Final ===");
            resultados.forEach(resultado -> out.printf(Locale.ROOT,
                    "Candidato %s (%d) - %d votos - %.2f%%%n",
                    resultado.candidato().getNome(),
                    resultado.candidato().getNumero(),
                    resultado.totalVotos(),
                    resultado.percentual()));
            ResultadoApuracao vencedor = resultados.get(0);
            out.printf("%nVencedor: %s com %d votos.%n",
                    vencedor.candidato().getNome(),
                    vencedor.totalVotos());
        } catch (NenhumVotoException e) {
            out.println();
            out.println("Nenhum voto registrado. Nada a apurar.");
        }
    }
}


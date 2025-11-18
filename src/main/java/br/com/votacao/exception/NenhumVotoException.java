package br.com.votacao.exception;

/**
 * Usada quando se tenta apurar resultados sem votos cadastrados.
 */
public class NenhumVotoException extends RuntimeException {
    public NenhumVotoException() {
        super("Não há votos registrados para realizar a apuração.");
    }
}


package br.com.votacao.exception;

/**
 * Lançada quando um eleitor tenta votar mais de uma vez.
 */
public class VotoDuplicadoException extends RuntimeException {
    public VotoDuplicadoException(String documento) {
        super("Eleitor com documento " + documento + " já registrou um voto.");
    }
}


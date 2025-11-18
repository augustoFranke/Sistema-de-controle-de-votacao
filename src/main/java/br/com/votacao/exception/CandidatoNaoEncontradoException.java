package br.com.votacao.exception;

/**
 * Indica a tentativa de votar em um candidato inexistente.
 */
public class CandidatoNaoEncontradoException extends RuntimeException {
    public CandidatoNaoEncontradoException(Integer numero) {
        super("Candidato com número " + numero + " não encontrado.");
    }
}


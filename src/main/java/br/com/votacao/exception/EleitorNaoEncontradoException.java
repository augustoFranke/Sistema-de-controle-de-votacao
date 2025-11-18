package br.com.votacao.exception;

/**
 * Indica que o eleitor requisitado não existe no cadastro.
 */
public class EleitorNaoEncontradoException extends RuntimeException {
    public EleitorNaoEncontradoException(String documento) {
        super("Eleitor com documento " + documento + " não encontrado.");
    }
}


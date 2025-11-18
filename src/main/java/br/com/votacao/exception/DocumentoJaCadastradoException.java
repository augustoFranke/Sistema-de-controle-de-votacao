package br.com.votacao.exception;

/**
 * Notifica duplicidade ao cadastrar eleitor.
 */
public class DocumentoJaCadastradoException extends RuntimeException {
    public DocumentoJaCadastradoException(String documento) {
        super("Documento " + documento + " já foi cadastrado para outro eleitor.");
    }
}


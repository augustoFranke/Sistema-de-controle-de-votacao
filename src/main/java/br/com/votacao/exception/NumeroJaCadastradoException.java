package br.com.votacao.exception;

/**
 * Notifica duplicidade de número ao cadastrar candidato.
 */
public class NumeroJaCadastradoException extends RuntimeException {
    public NumeroJaCadastradoException(Integer numero) {
        super("Número de candidato " + numero + " já foi cadastrado.");
    }
}


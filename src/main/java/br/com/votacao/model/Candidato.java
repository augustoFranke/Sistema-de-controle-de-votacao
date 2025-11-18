package br.com.votacao.model;

import java.util.Objects;

/**
 * Representa o candidato com número único conforme requisitos.
 */
public class Candidato extends Pessoa {

    private final Integer numero;

    public Candidato(String nome, Integer numero) {
        super(nome);
        this.numero = Objects.requireNonNull(numero, "número é obrigatório");
    }

    public Integer getNumero() {
        return numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Candidato candidato)) {
            return false;
        }
        return Objects.equals(numero, candidato.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    @Override
    public String toString() {
        return "Candidato{nome='" + getNome() + "', numero=" + numero + "}";
    }
}


package br.com.votacao.model;

import java.util.Objects;

/**
 * Representa o eleitor com documento único.
 */
public class Eleitor extends Pessoa {

    private final String documento;

    public Eleitor(String nome, String documento) {
        super(nome);
        this.documento = Objects.requireNonNull(documento, "documento é obrigatório").trim();
        if (this.documento.isEmpty()) {
            throw new IllegalArgumentException("Documento não pode ser vazio.");
        }
    }

    public String getDocumento() {
        return documento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Eleitor eleitor)) {
            return false;
        }
        return Objects.equals(documento, eleitor.documento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documento);
    }

    @Override
    public String toString() {
        return "Eleitor{nome='" + getNome() + "', documento='" + documento + "'}";
    }
}


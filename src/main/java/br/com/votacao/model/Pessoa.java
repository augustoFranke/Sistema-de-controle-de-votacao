package br.com.votacao.model;

import java.util.Objects;

/**
 * Classe base para modelar atributos comuns de atores do processo eleitoral.
 * Usada para demonstrar herança e reaproveitamento de lógica (POO).
 */
public abstract class Pessoa {

    private final String nome;

    protected Pessoa(String nome) {
        this.nome = Objects.requireNonNull(nome, "nome é obrigatório").trim();
        if (this.nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(nome, pessoa.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{nome='" + nome + "'}";
    }
}


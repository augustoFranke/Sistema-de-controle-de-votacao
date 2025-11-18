package br.com.votacao.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Modela o registro de voto persistido em arquivo.
 */
public class Voto {

    private final String documentoEleitor;
    private final Integer numeroCandidato;
    private final LocalDateTime horario;

    public Voto(String documentoEleitor, Integer numeroCandidato, LocalDateTime horario) {
        this.documentoEleitor = Objects.requireNonNull(documentoEleitor, "documento do eleitor é obrigatório").trim();
        if (this.documentoEleitor.isEmpty()) {
            throw new IllegalArgumentException("Documento do eleitor não pode ser vazio.");
        }
        this.numeroCandidato = Objects.requireNonNull(numeroCandidato, "número do candidato é obrigatório");
        this.horario = Objects.requireNonNull(horario, "horário do voto é obrigatório");
    }

    public String getDocumentoEleitor() {
        return documentoEleitor;
    }

    public Integer getNumeroCandidato() {
        return numeroCandidato;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Voto voto)) {
            return false;
        }
        return Objects.equals(documentoEleitor, voto.documentoEleitor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentoEleitor);
    }

    @Override
    public String toString() {
        return "Voto{documentoEleitor='" + documentoEleitor + "', numeroCandidato=" + numeroCandidato + ", horario=" + horario + "}";
    }
}


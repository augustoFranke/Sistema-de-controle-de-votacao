package br.com.votacao.model;

/**
 * Record para expor dados já calculados da apuração dentro da View.
 */
public record ResultadoApuracao(Candidato candidato, long totalVotos, double percentual) {
}


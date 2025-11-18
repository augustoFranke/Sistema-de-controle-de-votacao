package br.com.votacao.service;

import br.com.votacao.exception.CandidatoNaoEncontradoException;
import br.com.votacao.exception.EleitorNaoEncontradoException;
import br.com.votacao.exception.NenhumVotoException;
import br.com.votacao.model.Candidato;
import br.com.votacao.model.Eleitor;
import br.com.votacao.model.ResultadoApuracao;
import br.com.votacao.model.Voto;
import br.com.votacao.repository.CandidatoRepository;
import br.com.votacao.repository.EleitorRepository;
import br.com.votacao.repository.RepositoryFactory;
import br.com.votacao.repository.VotoRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Camada de serviço responsável por orquestrar regras entre View e Repository.
 */
public class VotacaoService {

    private final CandidatoRepository candidatoRepository;
    private final EleitorRepository eleitorRepository;
    private final VotoRepository votoRepository;

    public VotacaoService(CandidatoRepository candidatoRepository,
                          EleitorRepository eleitorRepository,
                          VotoRepository votoRepository) {
        this.candidatoRepository = Objects.requireNonNull(candidatoRepository);
        this.eleitorRepository = Objects.requireNonNull(eleitorRepository);
        this.votoRepository = Objects.requireNonNull(votoRepository);
    }

    /**
     * Facilita o uso no CLI ao encapsular o acesso ao Singleton RepositoryFactory.
     */
    public static VotacaoService defaultService() {
        RepositoryFactory factory = RepositoryFactory.getInstance();
        return new VotacaoService(factory.getCandidatoRepository(), factory.getEleitorRepository(), factory.getVotoRepository());
    }

    public Candidato cadastrarCandidato(String nome, Integer numero) {
        return candidatoRepository.save(new Candidato(nome, numero));
    }

    public Eleitor cadastrarEleitor(String nome, String documento) {
        return eleitorRepository.save(new Eleitor(nome, documento));
    }

    public Voto registrarVoto(String documentoEleitor, Integer numeroCandidato) {
        Eleitor eleitor = eleitorRepository.findById(documentoEleitor)
                .orElseThrow(() -> new EleitorNaoEncontradoException(documentoEleitor));
        Candidato candidato = candidatoRepository.findById(numeroCandidato)
                .orElseThrow(() -> new CandidatoNaoEncontradoException(numeroCandidato));
        Voto voto = new Voto(eleitor.getDocumento(), candidato.getNumero(), LocalDateTime.now());
        return votoRepository.save(voto);
    }

    public List<ResultadoApuracao> apurarResultados() {
        List<Voto> votos = votoRepository.findAll();
        if (votos.isEmpty()) {
            throw new NenhumVotoException();
        }
        long total = votos.size();
        return votoRepository.contarVotosPorNumero().entrySet().stream()
                .map(entry -> {
                    Integer numero = entry.getKey();
                    long qtd = entry.getValue();
                    Candidato candidato = candidatoRepository.findById(numero)
                            .orElseThrow(() -> new CandidatoNaoEncontradoException(numero));
                    double percentual = (qtd * 100.0) / total;
                    return new ResultadoApuracao(candidato, qtd, percentual);
                })
                .sorted(Comparator.comparingLong(ResultadoApuracao::totalVotos).reversed())
                .collect(Collectors.toList());
    }

    public List<Candidato> listarCandidatos() {
        return candidatoRepository.findAll();
    }

    public List<Eleitor> listarEleitores() {
        return eleitorRepository.findAll();
    }
}


package br.com.votacao.repository;

import br.com.votacao.repository.adapter.FileDataAdapter;
import br.com.votacao.repository.adapter.StorageAdapter;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Singleton responsável por fornecer instâncias únicas dos repositórios.
 * Centraliza o ponto de acesso à camada de persistência conforme indicado
 * nos requisitos e evita múltiplos manipuladores de arquivo concorrentes.
 */
public final class RepositoryFactory {

    public static final String DATA_DIR_PROPERTY = "votacao.data.dir";

    private final CandidatoRepository candidatoRepository;
    private final EleitorRepository eleitorRepository;
    private final VotoRepository votoRepository;

    private RepositoryFactory() {
        Path baseDir = resolveBaseDir();
        StorageAdapter adapter = new FileDataAdapter();
        this.candidatoRepository = new CandidatoRepository(adapter, baseDir.resolve("candidatos.txt"));
        this.eleitorRepository = new EleitorRepository(adapter, baseDir.resolve("eleitores.txt"));
        this.votoRepository = new VotoRepository(adapter, baseDir.resolve("votos.txt"));
    }

    public static RepositoryFactory getInstance() {
        return Holder.INSTANCE;
    }

    public CandidatoRepository getCandidatoRepository() {
        return candidatoRepository;
    }

    public EleitorRepository getEleitorRepository() {
        return eleitorRepository;
    }

    public VotoRepository getVotoRepository() {
        return votoRepository;
    }

    private Path resolveBaseDir() {
        String customDir = System.getProperty(DATA_DIR_PROPERTY);
        if (customDir != null && !customDir.isBlank()) {
            return Paths.get(customDir).toAbsolutePath();
        }
        return Paths.get("data").toAbsolutePath();
    }

    private static class Holder {
        private static final RepositoryFactory INSTANCE = new RepositoryFactory();
    }
}


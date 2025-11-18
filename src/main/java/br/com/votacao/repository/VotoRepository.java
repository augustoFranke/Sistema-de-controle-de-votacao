package br.com.votacao.repository;

import br.com.votacao.exception.VotoDuplicadoException;
import br.com.votacao.model.Voto;
import br.com.votacao.repository.adapter.StorageAdapter;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Gerencia votos armazenados em arquivo texto.
 */
public class VotoRepository extends FileRepository<Voto, String> {

    public VotoRepository(StorageAdapter storageAdapter, Path path) {
        super(storageAdapter, path);
    }

    @Override
    public Voto save(Voto entity) {
        findById(entity.getDocumentoEleitor()).ifPresent(voto -> {
            throw new VotoDuplicadoException(entity.getDocumentoEleitor());
        });
        return super.save(entity);
    }

    public boolean eleitorJaVotou(String documento) {
        return findById(documento).isPresent();
    }

    public Map<Integer, Long> contarVotosPorNumero() {
        return findAll().stream()
                .collect(Collectors.groupingBy(Voto::getNumeroCandidato, Collectors.counting()));
    }

    @Override
    protected Voto mapToEntity(String line) {
        String[] parts = line.split("\\|");
        String documento = parts[0];
        Integer numero = Integer.valueOf(parts[1]);
        LocalDateTime horario = LocalDateTime.parse(parts[2]);
        return new Voto(documento, numero, horario);
    }

    @Override
    protected String mapToLine(Voto entity) {
        return entity.getDocumentoEleitor() + "|" + entity.getNumeroCandidato() + "|" + entity.getHorario();
    }

    @Override
    protected boolean matchesId(Voto entity, String id) {
        return entity.getDocumentoEleitor().equals(id);
    }
}


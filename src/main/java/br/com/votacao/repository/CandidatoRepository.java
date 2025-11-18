package br.com.votacao.repository;

import br.com.votacao.exception.NumeroJaCadastradoException;
import br.com.votacao.model.Candidato;
import br.com.votacao.repository.adapter.StorageAdapter;

import java.nio.file.Path;

/**
 * Repositório de candidatos persistido em arquivo texto conforme exigido.
 */
public class CandidatoRepository extends FileRepository<Candidato, Integer> {

    public CandidatoRepository(StorageAdapter storageAdapter, Path path) {
        super(storageAdapter, path);
    }

    @Override
    public Candidato save(Candidato entity) {
        findById(entity.getNumero()).ifPresent(c -> {
            throw new NumeroJaCadastradoException(entity.getNumero());
        });
        return super.save(entity);
    }

    @Override
    protected Candidato mapToEntity(String line) {
        String[] parts = line.split("\\|");
        Integer numero = Integer.valueOf(parts[0]);
        String nome = parts[1];
        return new Candidato(nome, numero);
    }

    @Override
    protected String mapToLine(Candidato entity) {
        return entity.getNumero() + "|" + entity.getNome();
    }

    @Override
    protected boolean matchesId(Candidato entity, Integer id) {
        return entity.getNumero().equals(id);
    }
}


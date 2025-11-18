package br.com.votacao.repository;

import br.com.votacao.exception.DocumentoJaCadastradoException;
import br.com.votacao.model.Eleitor;
import br.com.votacao.repository.adapter.StorageAdapter;

import java.nio.file.Path;

/**
 * Persiste eleitores em arquivo texto usando o Adapter de dados.
 */
public class EleitorRepository extends FileRepository<Eleitor, String> {

    public EleitorRepository(StorageAdapter storageAdapter, Path path) {
        super(storageAdapter, path);
    }

    @Override
    public Eleitor save(Eleitor entity) {
        findById(entity.getDocumento()).ifPresent(e -> {
            throw new DocumentoJaCadastradoException(entity.getDocumento());
        });
        return super.save(entity);
    }

    @Override
    protected Eleitor mapToEntity(String line) {
        String[] parts = line.split("\\|");
        String documento = parts[0];
        String nome = parts[1];
        return new Eleitor(nome, documento);
    }

    @Override
    protected String mapToLine(Eleitor entity) {
        return entity.getDocumento() + "|" + entity.getNome();
    }

    @Override
    protected boolean matchesId(Eleitor entity, String id) {
        return entity.getDocumento().equals(id);
    }
}


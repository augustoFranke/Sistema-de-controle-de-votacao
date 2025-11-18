package br.com.votacao.repository;

import br.com.votacao.repository.adapter.StorageAdapter;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Superclasse utilitária para todos os repositórios baseados em arquivos.
 * Concentra o uso do Adapter para manter o restante das classes independente
 * da API de arquivos e demonstra encapsulamento.
 */
public abstract class FileRepository<T, ID> implements Repository<T, ID> {

    private final StorageAdapter storageAdapter;
    private final Path filePath;

    protected FileRepository(StorageAdapter storageAdapter, Path filePath) {
        this.storageAdapter = storageAdapter;
        this.filePath = filePath.toAbsolutePath();
    }

    @Override
    public List<T> findAll() {
        return readEntities();
    }

    @Override
    public Optional<T> findById(ID id) {
        return readEntities().stream()
                .filter(entity -> matchesId(entity, id))
                .findFirst();
    }

    @Override
    public T save(T entity) {
        storageAdapter.append(filePath, mapToLine(entity));
        return entity;
    }

    @Override
    public void clear() {
        storageAdapter.overwrite(filePath, List.of());
    }

    protected List<T> readEntities() {
        return storageAdapter.readAll(filePath).stream()
                .filter(line -> !line.isBlank())
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }

    protected void rewriteEntities(List<T> entities) {
        List<String> lines = entities.stream()
                .map(this::mapToLine)
                .toList();
        storageAdapter.overwrite(filePath, lines);
    }

    protected abstract T mapToEntity(String line);

    protected abstract String mapToLine(T entity);

    protected abstract boolean matchesId(T entity, ID id);

    protected StorageAdapter adapter() {
        return storageAdapter;
    }

    protected Path getFilePath() {
        return filePath;
    }
}


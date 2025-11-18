package br.com.votacao.repository.adapter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter estrutural que converte chamadas de {@link StorageAdapter} em
 * operações concretas de {@link Files}. Permite trocar a origem dos dados sem
 * reescrever os repositórios e encapsula todas as decisões de I/O.
 */
public class FileDataAdapter implements StorageAdapter {

    @Override
    public List<String> readAll(Path path) {
        ensureParentExists(path);
        if (Files.notExists(path)) {
            return new ArrayList<>();
        }
        try {
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Falha ao ler arquivo " + path.toAbsolutePath(), e);
        }
    }

    @Override
    public void overwrite(Path path, List<String> lines) {
        ensureParentExists(path);
        try {
            Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new IllegalStateException("Falha ao sobrescrever arquivo " + path.toAbsolutePath(), e);
        }
    }

    @Override
    public void append(Path path, String line) {
        ensureParentExists(path);
        try {
            Files.writeString(path, line + System.lineSeparator(), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new IllegalStateException("Falha ao escrever em arquivo " + path.toAbsolutePath(), e);
        }
    }

    private void ensureParentExists(Path path) {
        try {
            Path parent = path.toAbsolutePath().getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível preparar diretório para " + path.toAbsolutePath(), e);
        }
    }
}


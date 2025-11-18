package br.com.votacao.repository.adapter;

import java.nio.file.Path;
import java.util.List;

/**
 * Alvo do padrão Adapter: descreve operações de armazenamento que os
 * repositórios esperam. Diferentes implementações podem redirecionar para
 * arquivos, memória ou outros meios sem alterar os repositórios.
 */
public interface StorageAdapter {

    List<String> readAll(Path path);

    void overwrite(Path path, List<String> lines);

    void append(Path path, String line);
}


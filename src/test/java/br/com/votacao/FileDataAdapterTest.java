package br.com.votacao;

import br.com.votacao.repository.adapter.FileDataAdapter;
import br.com.votacao.repository.adapter.StorageAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileDataAdapterTest {

    @TempDir
    Path tempDir;

    @Test
    void leituraEEscritaFunciona() {
        StorageAdapter adapter = new FileDataAdapter();
        Path arquivo = tempDir.resolve("dados.txt");

        adapter.append(arquivo, "linha1");
        adapter.append(arquivo, "linha2");
        assertEquals(List.of("linha1", "linha2"), adapter.readAll(arquivo));

        adapter.overwrite(arquivo, List.of("final"));
        assertEquals(List.of("final"), adapter.readAll(arquivo));
    }
}


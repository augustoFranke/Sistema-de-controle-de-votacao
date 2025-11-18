package br.com.votacao;

import br.com.votacao.repository.RepositoryFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class RepositoryFactoryTest {

    @TempDir
    Path tempDir;

    @Test
    void garanteSingletonEInicializaRepositorios() {
        System.setProperty(RepositoryFactory.DATA_DIR_PROPERTY, tempDir.toString());
        RepositoryFactory primeiro = RepositoryFactory.getInstance();
        RepositoryFactory segundo = RepositoryFactory.getInstance();

        assertSame(primeiro, segundo, "Singleton deve retornar a mesma instância");
        assertNotNull(primeiro.getCandidatoRepository());
        assertNotNull(primeiro.getEleitorRepository());
        assertNotNull(primeiro.getVotoRepository());
    }
}


package br.com.votacao;

import br.com.votacao.exception.CandidatoNaoEncontradoException;
import br.com.votacao.exception.DocumentoJaCadastradoException;
import br.com.votacao.exception.NenhumVotoException;
import br.com.votacao.exception.NumeroJaCadastradoException;
import br.com.votacao.exception.VotoDuplicadoException;
import br.com.votacao.model.ResultadoApuracao;
import br.com.votacao.repository.CandidatoRepository;
import br.com.votacao.repository.EleitorRepository;
import br.com.votacao.repository.VotoRepository;
import br.com.votacao.repository.adapter.FileDataAdapter;
import br.com.votacao.repository.adapter.StorageAdapter;
import br.com.votacao.service.VotacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VotacaoServiceTest {

    @TempDir
    Path tempDir;

    private VotacaoService service;

    @BeforeEach
    void setUp() {
        service = criarService();
    }

    @Test
    void deveCadastrarCandidatoEEleitor() {
        service.cadastrarCandidato("Alice", 10);
        service.cadastrarEleitor("Bruno", "123");

        assertEquals(1, service.listarCandidatos().size());
        assertEquals(1, service.listarEleitores().size());
    }

    @Test
    void naoPermiteNumeroDuplicado() {
        service.cadastrarCandidato("Alice", 10);
        assertThrows(NumeroJaCadastradoException.class, () -> service.cadastrarCandidato("Ana", 10));
    }

    @Test
    void naoPermiteDocumentoDuplicado() {
        service.cadastrarEleitor("Bruno", "123");
        assertThrows(DocumentoJaCadastradoException.class, () -> service.cadastrarEleitor("Bia", "123"));
    }

    @Test
    void naoPermiteVotarEmCandidatoInexistente() {
        service.cadastrarEleitor("Bruno", "123");
        assertThrows(CandidatoNaoEncontradoException.class, () -> service.registrarVoto("123", 99));
    }

    @Test
    void naoPermiteVotoDuplicado() {
        service.cadastrarCandidato("Alice", 10);
        service.cadastrarEleitor("Bruno", "123");
        service.registrarVoto("123", 10);
        assertThrows(VotoDuplicadoException.class, () -> service.registrarVoto("123", 10));
    }

    @Test
    void apuracaoCalculaPercentual() {
        service.cadastrarCandidato("Alice", 10);
        service.cadastrarCandidato("Carlos", 11);
        service.cadastrarEleitor("Bruno", "123");
        service.cadastrarEleitor("Bia", "456");
        service.registrarVoto("123", 10);
        service.registrarVoto("456", 10);

        List<ResultadoApuracao> resultados = service.apurarResultados();
        assertEquals(1, resultados.size());
        ResultadoApuracao resultado = resultados.get(0);
        assertEquals(100.0, resultado.percentual(), 0.0001);
        assertEquals("Alice", resultado.candidato().getNome());
    }

    @Test
    void apuracaoSemVotosLancaExcecao() {
        assertThrows(NenhumVotoException.class, () -> service.apurarResultados());
    }

    @Test
    void dadosPersistemEntreInstancias() {
        service.cadastrarCandidato("Alice", 10);
        service.cadastrarEleitor("Bruno", "123");
        service.registrarVoto("123", 10);

        VotacaoService novaInstancia = criarService();
        List<ResultadoApuracao> resultados = novaInstancia.apurarResultados();
        assertEquals(1, resultados.size());
    }

    private VotacaoService criarService() {
        StorageAdapter adapter = new FileDataAdapter();
        Path candidatos = tempDir.resolve("candidatos.txt");
        Path eleitores = tempDir.resolve("eleitores.txt");
        Path votos = tempDir.resolve("votos.txt");
        return new VotacaoService(
                new CandidatoRepository(adapter, candidatos),
                new EleitorRepository(adapter, eleitores),
                new VotoRepository(adapter, votos)
        );
    }
}


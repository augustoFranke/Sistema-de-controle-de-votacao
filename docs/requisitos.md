# Requisitos - Sistema de Votação

## Funcionalidades Principais

### 1. Cadastro
- Cadastrar candidatos (nome + número único)
- Cadastrar eleitores (nome + documento único)

### 2. Votação
- Eleitor vota informando documento e número do candidato
- Cada eleitor vota apenas uma vez
- Votos são salvos automaticamente

### 3. Apuração
- Contar votos por candidato
- Calcular percentuais
- Mostrar vencedor

## Requisitos Técnicos

- **Linguagem:** Java 8+
- **Interface:** CLI (linha de comando)
- **Persistência:** Arquivos texto (formato: campo|campo|campo)
- **Arquitetura:** MRV (Model-Repository-View)

## Padrões de Projeto

1. **Singleton:** `RepositoryFactory` - garante instância única dos repositórios
2. **Adapter:** `FileDataAdapter` - abstrai leitura/escrita de arquivos

## Exceções

- `VotoDuplicadoException` - eleitor já votou
- `CandidatoNaoEncontradoException` - candidato não existe
- `EleitorNaoEncontradoException` - eleitor não existe
- `NumeroJaCadastradoException` - número de candidato duplicado
- `DocumentoJaCadastradoException` - documento de eleitor duplicado
- `NenhumVotoException` - tentativa de apurar sem votos

package br.com.votacao.repository;

import java.util.List;
import java.util.Optional;

/**
 * Contrato genérico para ilustrar uso de Generics e facilitar testes.
 */
public interface Repository<T, ID> {

    List<T> findAll();

    Optional<T> findById(ID id);

    T save(T entity);

    void clear();
}


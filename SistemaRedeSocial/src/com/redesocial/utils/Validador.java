package com.redesocial.utils;

import com.redesocial.exception.ValidacaoException;

/**
 * Interface funcional que representa um validador genérico para validar valores de tipo {@link T}.
 *
 * @param <T> O tipo de dado a ser validado.
 */
@FunctionalInterface
public interface Validador<T> {

    /**
     * Método que valida um valor do tipo {@link T}.
     * Caso o valor não seja válido, deve lançar uma {@link ValidacaoException}.
     *
     * @param valor O valor a ser validado.
     * @throws ValidacaoException Se o valor não for válido, uma exceção de validação será lançada.
     */
    void validar(T valor) throws ValidacaoException;
}

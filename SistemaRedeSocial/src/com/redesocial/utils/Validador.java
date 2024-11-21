package com.redesocial.utils;

import com.redesocial.exception.ValidacaoException;

@FunctionalInterface
public interface Validador<T> {
    void validar(T valor) throws ValidacaoException;
}

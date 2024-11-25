package com.redesocial.exception;

/**
 * Exceção personalizada para tratar erros de autenticação no sistema.
 *
 * Esta classe herda de {@link RuntimeException}, permitindo que seja tratada como uma exceção
 * não verificada, ou seja, não é necessário declarar ou capturar explicitamente no código.
 */
public class AutenticacaoException extends RuntimeException {

    /**
     * Construtor para criar uma exceção de autenticação com uma mensagem de erro específica.
     *
     * @param message A mensagem de erro que descreve o motivo da falha na autenticação.
     */
    public AutenticacaoException(String message) {
        super(message);
    }

}

package com.redesocial.exception;

/**
 * Exceção personalizada para tratar erros relacionados a posts no sistema.
 * Esta exceção é lançada quando ocorre um erro ao criar, excluir, editar ou interagir com posts,
 * como quando um post não pode ser encontrado ou há um problema ao processar as ações relacionadas ao post.
 *
 * Esta classe herda de {@link RuntimeException}, permitindo que seja tratada como uma exceção
 * não verificada, ou seja, não é necessário declarar ou capturar explicitamente no código.
 */
public class PostException extends RuntimeException {

    /**
     * Construtor para criar uma exceção de post com uma mensagem de erro específica.
     *
     * @param message A mensagem de erro que descreve o motivo do erro ao manipular o post.
     */
    public PostException(String message) {
        super(message);
    }
}

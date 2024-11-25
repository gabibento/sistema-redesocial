package com.redesocial.exception;

/**
 * Exceção personalizada para tratar erros relacionados a usuários no sistema.
 * Esta exceção é lançada quando ocorre um erro relacionado à manipulação de usuários,
 * como problemas ao cadastrar, autenticar, atualizar ou excluir usuários.
 *
 * Esta classe herda de {@link RuntimeException}, permitindo que seja tratada como uma exceção
 * não verificada, ou seja, não é necessário declarar ou capturar explicitamente no código.
 */
public class UsuarioException extends RuntimeException {

    /**
     * Construtor para criar uma exceção de usuário com uma mensagem de erro específica.
     *
     * @param mensagem A mensagem de erro que descreve o motivo do erro ao manipular o usuário.
     */
    public UsuarioException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor para criar uma exceção de usuário com uma mensagem de erro específica e uma causa.
     *
     * @param mensagem A mensagem de erro que descreve o motivo do erro ao manipular o usuário.
     * @param causa A causa original do erro (outra exceção).
     */
    public UsuarioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

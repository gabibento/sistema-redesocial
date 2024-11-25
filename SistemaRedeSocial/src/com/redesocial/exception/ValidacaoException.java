package com.redesocial.exception;

/**
 * Exceção personalizada para tratar erros de validação no sistema.
 * Esta exceção é lançada quando os dados fornecidos por um usuário não atendem a critérios
 * ou regras de validação, como quando um campo obrigatório está vazio, o formato de um valor está incorreto
 * ou os dados fornecidos não são válidos de alguma outra forma.
 *
 * Esta classe herda de {@link RuntimeException}, permitindo que seja tratada como uma exceção
 * não verificada, ou seja, não é necessário declarar ou capturar explicitamente no código.
 */
public class ValidacaoException extends RuntimeException {

    /**
     * Construtor para criar uma exceção de validação com uma mensagem de erro específica.
     *
     * @param mensagem A mensagem de erro que descreve o motivo da falha na validação dos dados.
     */
    public ValidacaoException(String mensagem) {
        super(mensagem);
    }
}

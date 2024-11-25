package com.redesocial.utils;

/**
 * Classe utilitária para aplicar cores aos textos exibidos no console.
 * A classe define constantes de códigos de cores ANSI para diferentes tipos de mensagens
 * (erro, sucesso, aviso, título, informação) e fornece métodos para aplicar essas cores aos textos.
 *
 */
public class CoresConsole {

    // Códigos ANSI para cores
    public static final String RESET = "\033[0m"; // Reseta a cor para o padrão
    public static final String VERMELHO_NEGRITO = "\033[1;31m"; // Vermelho Negrito (usado para erros)
    public static final String VERDE_NEGRITO = "\033[1;32m"; // Verde Negrito (usado para sucessos)
    public static final String AMARELO_NEGRITO = "\033[1;33m"; // Amarelo Negrito (usado para avisos)
    public static final String AZUL_NEGRITO = "\033[1;34m"; // Azul Negrito (usado para títulos)
    public static final String CIANO = "\033[0;36m"; // Ciano (usado para informações)

    /**
     * Formata um texto com a cor vermelha para indicar erro.
     *
     * @param texto O texto a ser colorido
     * @return O texto formatado em vermelho
     */
    public static String erro(String texto) {
        return VERMELHO_NEGRITO + texto + RESET;
    }

    /**
     * Formata um texto com a cor verde para indicar sucesso.
     *
     * @param texto O texto a ser colorido
     * @return O texto formatado em verde
     */
    public static String sucesso(String texto) {
        return VERDE_NEGRITO + texto + RESET;
    }

    /**
     * Formata um texto com a cor amarela para indicar um aviso.
     *
     * @param texto O texto a ser colorido
     * @return O texto formatado em amarelo
     */
    public static String aviso(String texto) {
        return AMARELO_NEGRITO + texto + RESET;
    }

    /**
     * Formata um texto com a cor azul para indicar um título.
     *
     * @param texto O texto a ser colorido
     * @return O texto formatado em azul
     */
    public static String titulo(String texto) {
        return AZUL_NEGRITO + texto + RESET;
    }

    /**
     * Formata um texto com a cor ciano para indicar uma informação.
     *
     * @param texto O texto a ser colorido
     * @return O texto formatado em ciano
     */
    public static String info(String texto) {
        return CIANO + texto + RESET;
    }
}

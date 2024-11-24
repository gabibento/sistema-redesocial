package com.redesocial.utils;

public class CoresConsole {
    public static final String RESET = "\033[0m";
    public static final String VERMELHO_NEGRITO = "\033[1;31m";
    public static final String VERDE_NEGRITO = "\033[1;32m";
    public static final String AMARELO_NEGRITO = "\033[1;33m";
    public static final String AZUL_NEGRITO = "\033[1;34m";
    public static final String ROXO_NEGRITO = "\033[1;35m";
    public static final String CIANO = "\033[0;36m";
    /**
     * Formata um texto com uma cor específica.
     * @param texto O texto a ser colorido
     * @param cor O código ANSI da cor
     * @return O texto formatado com a cor
     */
    public static String colorir(String texto, String cor) {
        return cor + texto + RESET;
    }

    /**
     * Formata um texto em vermelho para erros.
     * @param texto O texto do erro
     * @return O texto formatado em vermelho
     */
    public static String erro(String texto) {
        return VERMELHO_NEGRITO + texto + RESET;
    }

    /**
     * Formata um texto em verde para sucesso.
     * @param texto O texto de sucesso
     * @return O texto formatado em verde
     */
    public static String sucesso(String texto) {
        return VERDE_NEGRITO + texto + RESET;
    }

    /**
     * Formata um texto em amarelo para avisos.
     * @param texto O texto de aviso
     * @return O texto formatado em amarelo
     */
    public static String aviso(String texto) {
        return AMARELO_NEGRITO + texto + RESET;
    }

    /**
     * Formata um texto em azul para títulos.
     * @param texto O texto do título
     * @return O texto formatado em azul
     */
    public static String titulo(String texto) {
        return AZUL_NEGRITO + texto + RESET;
    }

    /**
     * Formata um texto em ciano para informações.
     * @param texto O texto informativo
     * @return O texto formatado em ciano
     */
    public static String info(String texto) {
        return CIANO + texto + RESET;
    }

    /**
     * Formata um texto em roxo para destaques.
     * @param texto O texto a ser destacado
     * @return O texto formatado em roxo
     */
    public static String destaque(String texto) {
        return ROXO_NEGRITO + texto + RESET;
    }

    /**
     * Cria uma linha divisória colorida.
     * @param tamanho O tamanho da linha
     * @param cor A cor da linha
     * @return A linha divisória formatada
     */
    public static String linha(int tamanho, String cor) {
        return colorir("=".repeat(tamanho), cor);
    }
}

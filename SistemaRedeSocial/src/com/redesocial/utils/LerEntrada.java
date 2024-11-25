package com.redesocial.utils;

import java.util.Scanner;

/**
 * Classe utilitária para ler entradas do usuário no console.
 * Esta classe fornece métodos para capturar entradas de texto e números inteiros.
 *
 * Utiliza a classe {@link Scanner} para ler as entradas do console e oferece métodos específicos
 * para garantir que o usuário insira valores válidos.
 */
public class LerEntrada {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Lê uma entrada do usuário e garante que não seja vazia.
     * Caso o usuário não insira nenhum valor, a mensagem de erro será exibida
     * e o usuário será solicitado a tentar novamente.
     *
     * @param mensagem A mensagem a ser exibida ao usuário.
     * @return O valor digitado pelo usuário, sem espaços em branco no início ou fim.
     */
    public static String lerEntradaString(String mensagem){
        String entrada;
        do {
            System.out.print(mensagem); // Exibe a mensagem solicitando entrada.
            entrada = scanner.nextLine().trim(); // Lê e remove espaços extras.

            // Se a entrada for vazia, exibe uma mensagem de erro.
            if (entrada.isEmpty()) {
                System.out.println("O valor não pode ser vazio. Tente novamente!");
            }

        } while (entrada.isEmpty()); // Continua pedindo até que uma entrada válida seja fornecida.

        return entrada;
    }

    /**
     * Lê uma entrada opcional do usuário.
     * Esse método permite que o usuário insira um valor ou simplesmente pressione Enter.
     * Se o usuário pressionar Enter sem digitar nada, será retornada uma string vazia.
     *
     * @param mensagem A mensagem a ser exibida ao usuário.
     * @return O valor digitado pelo usuário, sem espaços em branco no início ou fim.
     */
    public static String lerEntradaStringOpcional(String mensagem){
        System.out.println(mensagem);
        return scanner.nextLine().trim();
    }

    /**
     * Lê uma entrada do usuário e garante que seja um número inteiro.
     * Se o usuário fornecer um valor inválido (não numérico), uma mensagem de erro será exibida
     * e o usuário será solicitado a tentar novamente até fornecer um número válido.
     *
     * @param mensagem A mensagem a ser exibida ao usuário.
     * @return O número inteiro fornecido pelo usuário.
     */
    public static int lerEntradaInteira(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem); // Exibe a mensagem solicitando entrada.
                return Integer.parseInt(scanner.nextLine().trim()); // Tenta converter a entrada para um inteiro.
            } catch (NumberFormatException e) {
                // Se ocorrer uma exceção (não foi um número inteiro), exibe uma mensagem de erro.
                System.out.println("Por favor, digite um número inteiro válido.");
            }
        }
    }
}

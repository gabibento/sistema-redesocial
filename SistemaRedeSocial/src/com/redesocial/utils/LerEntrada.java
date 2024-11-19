package com.redesocial.utils;

import java.util.Scanner;

public class LerEntrada {
    private static final Scanner scanner = new Scanner(System.in);
    public static String lerEntradaString(String mensagem){
        String entrada;
        do{
            System.out.println(mensagem);
            entrada = scanner.nextLine().trim();

            if(entrada.isEmpty()){
                System.out.println("O valor não pode ser vazio. Tente novamente!");
            }

        }while(entrada.isEmpty());

        return entrada;
    }
    public static int lerEntradaInteira(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número inteiro válido.");
            }
        }
    }
}

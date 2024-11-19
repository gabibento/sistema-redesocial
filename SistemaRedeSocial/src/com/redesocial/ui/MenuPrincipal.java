package com.redesocial.ui;

import com.redesocial.gerenciador.GerenciadorUsuarios;
import com.redesocial.modelo.Usuario;

import java.util.Scanner;

public class MenuPrincipal {
    private final Scanner scanner;
    private final GerenciadorUsuarios gerenciadorUsuarios;

    public MenuPrincipal(){
        scanner = new Scanner(System.in);
        gerenciadorUsuarios = new GerenciadorUsuarios();
    }

    private void cadastrarUsuario(){
        try{
            String nome = lerEntradaString("Digite seu nome: ");
            String username = lerEntradaString("Digite seu username: ");
            String email = lerEntradaString("Digite seu email");
            String senha = lerEntradaString("Digite sua senha");

            gerenciadorUsuarios.cadastrar(new Usuario(nome, username, email, senha));
            System.out.println("Usuário cadastrado com sucesso!");
        }catch(Exception e){
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    private String lerEntradaString(String mensagem){
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

}

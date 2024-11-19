package com.redesocial.ui;

import com.redesocial.exception.AutenticacaoException;
import com.redesocial.exception.UsuarioException;
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
    public void exibirMenu(){
        System.out.println("=== Menu Principal ===");
        System.out.println("\n1. Cadastrar \n2. Fazer Login");

        int opcao = lerEntradaInteira("");

        switch (opcao){
            case 1 -> cadastrarUsuario();
            case 2 -> fazerLogin();
        }

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
    private void fazerLogin(){
        try{
            String username = lerEntradaString("Digite seu username: ");
            String senha = lerEntradaString("Digite sua senha: ");
            exibirMenuLogado(autenticar(username, senha));
        }catch (AutenticacaoException e){
            throw e;
        }catch (Exception e){
            throw new UsuarioException("Erro ao fazer login: " + e.getMessage() + e);
        }
    }
    private void exibirMenuLogado(Usuario usuario){
        MenuUsuario menu = new MenuUsuario(usuario);
        menu.exibirMenu();
    }
    private Usuario autenticar(String username, String senha){
        Usuario usuarioEncontrado = gerenciadorUsuarios.listarUsuarios()
                .stream()
                .filter(usuario -> usuario.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
        if(usuarioEncontrado == null){
            throw new AutenticacaoException("Usuário com username " + username + " não existe");
        }
        if(!usuarioEncontrado.getSenha().equals(senha)){
            throw new AutenticacaoException("Username ou senha inválidos");
        }
        return usuarioEncontrado;
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
    private int lerEntradaInteira(String mensagem) {
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

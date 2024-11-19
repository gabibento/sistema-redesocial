package com.redesocial.ui;

import com.redesocial.exception.AutenticacaoException;
import com.redesocial.exception.UsuarioException;
import com.redesocial.gerenciador.GerenciadorUsuarios;
import com.redesocial.modelo.Usuario;
import com.redesocial.utils.LerEntrada;

import java.util.Scanner;

public class MenuPrincipal {
    private final GerenciadorUsuarios gerenciadorUsuarios;

    public MenuPrincipal(){
        gerenciadorUsuarios = new GerenciadorUsuarios();
    }
    public void exibirMenu(){
        boolean continuar = true;

        while(continuar){
            System.out.println("=== Menu Principal ===");
            System.out.println("\n1. Cadastrar \n2. Fazer Login \n3. Sair");

            int opcao = LerEntrada.lerEntradaInteira("");

            switch (opcao){
                case 1 -> cadastrarUsuario();
                case 2 -> fazerLogin();
                case 3 -> continuar = false;
            }
        }

    }

    private void cadastrarUsuario(){
        try{
            String nome = LerEntrada.lerEntradaString("Digite seu nome: ");
            String username = LerEntrada.lerEntradaString("Digite seu username: ");
            String email = LerEntrada.lerEntradaString("Digite seu email");
            String senha = LerEntrada.lerEntradaString("Digite sua senha");

            gerenciadorUsuarios.cadastrar(new Usuario(nome, username, email, senha));
            System.out.println("Usuário cadastrado com sucesso!");
        }catch(Exception e){
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }
    private void fazerLogin(){
        try{
            String username = LerEntrada.lerEntradaString("Digite seu username: ");
            String senha = LerEntrada.lerEntradaString("Digite sua senha: ");
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



}

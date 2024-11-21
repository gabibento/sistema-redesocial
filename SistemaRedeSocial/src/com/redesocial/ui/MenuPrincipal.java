package com.redesocial.ui;

import com.redesocial.exception.AutenticacaoException;
import com.redesocial.exception.UsuarioException;
import com.redesocial.gerenciador.GerenciadorPosts;
import com.redesocial.gerenciador.GerenciadorUsuarios;
import com.redesocial.modelo.Usuario;
import com.redesocial.utils.LerEntrada;

import java.util.Optional;


public class MenuPrincipal {
    private final GerenciadorUsuarios gerenciadorUsuarios;
    private final GerenciadorPosts gerenciadorPosts;

    public MenuPrincipal(){
        gerenciadorUsuarios = new GerenciadorUsuarios();
        gerenciadorPosts = new GerenciadorPosts(gerenciadorUsuarios);
    }
    public void exibirMenu(){
        boolean continuar = true;

        while(continuar){
            System.out.println("=== Menu Principal ===");
            System.out.println("\n1. Cadastrar \n2. Fazer Login \n3. Sair");

            int opcao = LerEntrada.lerEntradaInteira("Escolha uma opção: ");

            switch (opcao){
                case 1 -> cadastrarUsuario();
                case 2 -> fazerLogin();
                case 3 -> continuar = false;
                default -> System.out.println("Opção inválida");
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
        boolean logado = false;
        while(!logado){
            try{
                String username = LerEntrada.lerEntradaString("Digite seu username: ");
                String senha = LerEntrada.lerEntradaString("Digite sua senha: ");
                Usuario usuario = autenticar(username, senha);
                logado = true;
                exibirMenuLogado(usuario);
            } catch (Exception e){
                System.out.println(e.getMessage());
                int opcao = LerEntrada.lerEntradaInteira("1. Tentar novamente \n2. Voltar \nEscolha uma opção: ");

                if(opcao == 2){
                    logado = true;
                }
            }
        }

    }
    private void exibirMenuLogado(Usuario usuario){
        MenuUsuario menu = new MenuUsuario(usuario, gerenciadorUsuarios, gerenciadorPosts);
        menu.exibirMenu();
    }
    private Usuario autenticar(String username, String senha){
        try{
            Optional<Usuario> usuarioEncontrado = Optional.ofNullable(gerenciadorUsuarios.buscarPorUsername(username));

            if(usuarioEncontrado.isEmpty()){
                throw new AutenticacaoException("Usuário com username " + username + " não existe");
            }
            if(!usuarioEncontrado.get().getSenha().equals(senha)){
                throw new AutenticacaoException("Username ou senha inválidos");
            }
            return usuarioEncontrado.get();
        }catch (Exception e){
            throw new UsuarioException("Erro ao fazer login: " + e.getMessage());
        }
    }

    public GerenciadorUsuarios getGerenciadorUsuarios() {
        return gerenciadorUsuarios;
    }

    public GerenciadorPosts getGerenciadorPosts() {
        return gerenciadorPosts;
    }
}

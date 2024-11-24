package com.redesocial.ui;

import com.redesocial.exception.AutenticacaoException;
import com.redesocial.exception.UsuarioException;
import com.redesocial.exception.ValidacaoException;
import com.redesocial.gerenciador.GerenciadorPosts;
import com.redesocial.gerenciador.GerenciadorUsuarios;
import com.redesocial.modelo.Usuario;
import com.redesocial.utils.LerEntrada;
import com.redesocial.utils.Validador;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
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

    private void cadastrarUsuario() {
        String nome = obterEntradaValida("Digite seu nome: ", this::validarNome);
        String username = obterEntradaValida("Digite seu username: ", this::validarUsername);
        String email = obterEntradaValida("Digite seu email: ", this::validarEmail);
        String senha = obterEntradaValidaSenha("Digite sua senha: ", this::validarSenha);

        Usuario usuario = new Usuario(nome, username, email, BCrypt.hashpw(senha, BCrypt.gensalt()));
        try {
            gerenciadorUsuarios.cadastrar(usuario);
            System.out.println("Usuário cadastrado com sucesso!");
            exibirMenuLogado(usuario);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }private String obterEntradaValida(String mensagem, Validador<String> validador) {
        while (true) {
            try {
                String entrada = LerEntrada.lerEntradaString(mensagem);
                validador.validar(entrada);
                return entrada;
            } catch (ValidacaoException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private String obterEntradaValidaSenha(String mensagem, Validador<String> validador) {
        // Usando JPasswordField para exibir * ao digitar a senha
        JPasswordField passwordField = new JPasswordField(20); // Defina o tamanho do campo
        Object[] message = {
                mensagem, passwordField
        };

        // Mostra o painel de senha
        int option = JOptionPane.showConfirmDialog(null, message, "Cadastro de Senha", JOptionPane.OK_CANCEL_OPTION);

        // Se o usuário clicar em OK
        if (option == JOptionPane.OK_OPTION) {
            char[] senhaArray = passwordField.getPassword(); // Captura a senha digitada
            String senha = new String(senhaArray); // Converte para String

            try {
                validador.validar(senha); // Valida a senha
                return senha;
            } catch (ValidacaoException e) {
                System.out.println("Erro: " + e.getMessage());
                return obterEntradaValidaSenha(mensagem, validador); // Repetir se a senha for inválida
            }
        } else {
            return null; // Se o usuário cancelar
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
            if (!BCrypt.checkpw(senha, usuarioEncontrado.get().getSenha())) {
                throw new AutenticacaoException("Senha incorreta");
            }
            return usuarioEncontrado.get();
        }catch (Exception e){
            throw new UsuarioException("Erro ao fazer login: " + e.getMessage());
        }
    }
    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidacaoException("O nome não pode ser vazio.");
        }
    }

    private void validarUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidacaoException("O username não pode ser vazio.");
        }
        if (gerenciadorUsuarios.listarUsuarios().stream()
                .anyMatch(u -> u.getUsername().equals(username))) {
            throw new ValidacaoException("O username '" + username + "' já está em uso.");
        }
    }

    private void validarEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new ValidacaoException("Email inválido.");
        }
    }

    private void validarSenha(String senha) {
        if (senha == null || senha.length() < 6) {
            throw new ValidacaoException("A senha deve conter pelo menos 6 caracteres.");
        }
    }

    public GerenciadorUsuarios getGerenciadorUsuarios() {
        return gerenciadorUsuarios;
    }

    public GerenciadorPosts getGerenciadorPosts() {
        return gerenciadorPosts;
    }
}

package com.redesocial.ui;

import com.redesocial.gerenciador.GerenciadorPosts;
import com.redesocial.gerenciador.GerenciadorUsuarios;
import com.redesocial.modelo.Post;
import com.redesocial.modelo.Usuario;
import com.redesocial.utils.LerEntrada;

import java.util.List;

public class MenuUsuario {
    private final Usuario usuario;
    private final GerenciadorPosts gerenciadorPosts;
    private final GerenciadorUsuarios gerenciadorUsuarios;

    public MenuUsuario(Usuario usuario){
        this.usuario = usuario;
        gerenciadorPosts = new GerenciadorPosts();
        gerenciadorUsuarios = new GerenciadorUsuarios();
    }
    public void exibirMenu(){
        System.out.println("=== Menu ===");
        System.out.println("1. Criar Post");
        System.out.println("2. Ver Meu Perfil");
        System.out.println("3. Buscar Usuários");
    }
    private void criarPost(){
       try{
           System.out.println("=== Novo Post ===");

           String conteudo = LerEntrada.lerEntradaString("Digite seu post:\n");

           gerenciadorPosts.criar(new Post(usuario, conteudo));
           System.out.println("Post publicado com sucesso");
       }catch (Exception e){
           System.out.println("Erro ao criar post: " + e.getMessage());
       }
    }
    private void verPerfil(){
        System.out.println("=== Meu Perfil ===");
        System.out.println(usuario);
        int opcao = LerEntrada.lerEntradaInteira("1. Editar perfil \n2. Voltar");

        if (opcao == 1) {
            editarPerfil();
        }

    }
    private void verOutroPerfil(Usuario usuario){

        System.out.println(usuario);

        boolean ehAmigo = this.usuario.getAmigos().contains(usuario);

        int opcao = LerEntrada.lerEntradaInteira("1. " + (ehAmigo ? "Remover amizade" : "Adicionar Amizade") + "\n2. Voltar");
        if(opcao == 1){
            if(ehAmigo){
                gerenciadorUsuarios.adicionarAmizade(this.usuario.getId(), usuario.getId());
            }else{
                gerenciadorUsuarios.removerAmizade(this.usuario.getId(), usuario.getId());
            }
        }
    }
    private void gerenciarAmizades(){
        int opcao = LerEntrada.lerEntradaInteira("1. Adicionar novo amigo \n2. Remover amigo \n3. Voltar");
         switch (opcao){
             case 1 -> adicionarAmigo();
             case 2 -> removerAmigo();
         }
    }
    private void adicionarAmigo(){
        try{
            String username = LerEntrada.lerEntradaString("Digite o username do usuário a ser adicionado: ");
            gerenciadorUsuarios.adicionarAmizade(usuario.getId(), gerenciadorUsuarios.buscarPorUsername(username).getId());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void removerAmigo(){
        try{
            String username = LerEntrada.lerEntradaString("Digite o username do usuário a ser removido: ");
            gerenciadorUsuarios.removerAmizade(usuario.getId(), gerenciadorUsuarios.buscarPorUsername(username).getId());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void editarPerfil(){
        try{
            String nome = LerEntrada.lerEntradaString("Digite seu nome: ");
            String username = LerEntrada.lerEntradaString("Digite seu username: ");
            String email = LerEntrada.lerEntradaString("Digite seu email");
            String senha = LerEntrada.lerEntradaString("Digite sua senha");

            gerenciadorUsuarios.atualizar(new Usuario(nome, username, email, senha));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void buscarUsuarios(){
        try{
            String busca = LerEntrada.lerEntradaString("Digite um nome ou username: ");

            List<Usuario> usuariosBusca = gerenciadorUsuarios.buscarPorNome(busca);
            Usuario usuarioBusca = gerenciadorUsuarios.buscarPorUsername(busca);

            if(!usuariosBusca.isEmpty()){
                usuariosBusca.forEach(usuario1 -> System.out.println("Nome: " + usuario1.getNome() + " Username: " + usuario1.getUsername()));
            }else if(usuarioBusca != null){
                System.out.println(usuarioBusca);
            }else{
                System.out.println("Não há usuários com esse nome/username");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


}

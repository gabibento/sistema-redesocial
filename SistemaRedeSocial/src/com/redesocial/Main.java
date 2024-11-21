package com.redesocial;

import com.redesocial.gerenciador.GerenciadorPosts;
import com.redesocial.gerenciador.GerenciadorUsuarios;
import com.redesocial.modelo.Comentario;
import com.redesocial.modelo.Post;
import com.redesocial.modelo.Usuario;
import com.redesocial.ui.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Rede Social ===");

        try{
            MenuPrincipal menu = new MenuPrincipal();
            inicializarSistema(menu.getGerenciadorPosts(), menu.getGerenciadorUsuarios());
            menu.exibirMenu();
        }catch (Exception e){
            System.out.println("Erro ao inicializar o sistema: " + e.getMessage());
        }
    }
    private static void inicializarSistema(GerenciadorPosts gerenciadorPosts, GerenciadorUsuarios gerenciadorUsuarios){

        // Criando Usuários
        Usuario usuario1 = new Usuario("João Silva", "joao123", "joao@email.com", "senha123");
        Usuario usuario2 = new Usuario("Maria Oliveira", "maria123", "maria@email.com", "senha123");
        Usuario usuario3 = new Usuario( "Ana Lima", "ana.lima", "ana@email.com", "senha123");

        // Cadastrando Usuários
        gerenciadorUsuarios.cadastrar(usuario1);
        gerenciadorUsuarios.cadastrar(usuario2);
        gerenciadorUsuarios.cadastrar(usuario3);

        // Criando Posts
        Post post1 = new Post(usuario1, "Olá, este é meu primeiro post!");
        Post post2 = new Post(usuario2, "Aproveitando um lindo dia de sol!");

        // Publicando Posts
        gerenciadorPosts.criar(post1);
        gerenciadorUsuarios.adicionarPost(post1.getAutor(), post1);
        gerenciadorPosts.criar(post2);
        gerenciadorUsuarios.adicionarPost(post2.getAutor(), post2);

        // Criando Comentários
        Comentario comentario1 = new Comentario(usuario3, "Que legal, João! Parabéns!", post1);
        Comentario comentario2 = new Comentario(usuario1, "Adorei seu post, Maria!", post2);

        // Adicionando Comentários aos Posts
        post1.adicionarComentario(comentario1);
        post2.adicionarComentario(comentario2);

        // Adicionando Curtidas aos Posts
        post1.adicionarCurtida(usuario2);
        post1.adicionarCurtida(usuario3);
        post2.adicionarCurtida(usuario1);
    }
}
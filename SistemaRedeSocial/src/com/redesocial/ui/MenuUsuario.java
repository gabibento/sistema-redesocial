package com.redesocial.ui;

import com.redesocial.gerenciador.GerenciadorPosts;
import com.redesocial.modelo.Post;
import com.redesocial.modelo.Usuario;
import com.redesocial.utils.LerEntrada;

public class MenuUsuario {
    private final Usuario usuario;
    private final GerenciadorPosts gerenciadorPosts;

    public MenuUsuario(Usuario usuario){
        this.usuario = usuario;
        gerenciadorPosts = new GerenciadorPosts();
    }
    public void exibirMenu(){

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

}

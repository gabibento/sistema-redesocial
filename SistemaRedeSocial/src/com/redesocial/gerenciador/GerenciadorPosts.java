package com.redesocial.gerenciador;

import com.redesocial.exception.ValidacaoException;
import com.redesocial.modelo.Post;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorPosts {
    private List<Post> posts;
    private int proximoId;

    public GerenciadorPosts(){
        posts = new ArrayList<>();
        proximoId = 1;
    }

    private void validarPost(Post post){
        if(post.getConteudo().isEmpty() || post.getConteudo() == null){
            throw new ValidacaoException("Conteúdo do post não pode ser vazio");
        }
    }


}

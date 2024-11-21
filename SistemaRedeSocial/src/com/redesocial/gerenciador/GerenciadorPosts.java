package com.redesocial.gerenciador;

import com.redesocial.exception.PostException;
import com.redesocial.exception.UsuarioException;
import com.redesocial.exception.ValidacaoException;
import com.redesocial.modelo.Comentario;
import com.redesocial.modelo.Post;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorPosts {
    private List<Post> posts;
    private int proximoId;
    private int idComentario;
    private GerenciadorUsuarios gerenciadorUsuarios;

    public GerenciadorPosts(){
        posts = new ArrayList<>();
        proximoId = 1;
        idComentario = 1;
        gerenciadorUsuarios = new GerenciadorUsuarios();
    }

    public void criar(Post post){
        try{
            validarPost(post);
            post.setId(proximoId++);
            posts.add(post);
        }catch (ValidacaoException e){
            throw e;
        }catch (Exception e){
            throw new UsuarioException("Erro ao criar post: " + e.getMessage() + e);
        }
    }
    public List<Post> listarPosts(){
        return posts;
    }

    public Post buscarPorId(int id){
        try{
            return posts.stream()
                    .filter(post -> post.getId() == id)
                    .findFirst()
                    .orElse(null);
        }catch (Exception e){
            throw new PostException("Erro ao buscar post: " + e.getMessage() + e);
        }
    }
    public List<Post> listarPorUsuario(int idUsuario){
        try{
            return posts.stream()
                    .filter(post -> post.getAutor().getId() == idUsuario)
                    .toList();
        }catch (Exception e){
            throw new PostException("Erro ao listar por usuário: " + e.getMessage() + e);
        }
    }
    public void curtir(int idPost, int idUsuario){
       try{
           buscarPorId(idPost).adicionarCurtida(gerenciadorUsuarios.buscarPorId(idUsuario));
       }catch (Exception e){
           throw new PostException("Erro ao curtir post: " + e.getMessage() + e);
       }
    }
    public void descurtir(int idPost, int idUsuario){
        try{
            buscarPorId(idPost).removerCurtida(gerenciadorUsuarios.buscarPorId(idUsuario));
        }catch (Exception e){
            throw new PostException("Erro ao descurtir post: " + e.getMessage() + e);
        }
    }
    public void comentar(Comentario comentario){
        try{
            comentario.setId(idComentario++);
            comentario.getPost().adicionarComentario(comentario);
        }catch (Exception e){
            throw new PostException("Erro ao comentar post: " + e.getMessage() + e);
        }
    }
    public boolean deletar(int id){
        try{
            return posts.removeIf(post -> post.getId() == id);
        }catch (Exception e){
            throw new UsuarioException("Erro ao deletar post: " + e.getMessage() + e);
        }
    }

    private void validarPost(Post post){
        if(post.getConteudo().isEmpty()){
            throw new ValidacaoException("Conteúdo do post não pode ser vazio");
        }

        if(!gerenciadorUsuarios.listarUsuarios().contains(post.getAutor())){
            throw new ValidacaoException("Autor inválido");
        }
        if(post.getConteudo().length() > 280){
            throw new ValidacaoException("Limite de caracteres atingido");
        }

    }


}

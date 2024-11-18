package com.redesocial.modelo;

import java.time.LocalDateTime;

public class Comentario {

    private Integer id;
    private Usuario autor;
    private String conteudo;
    private LocalDateTime dataComentario;
    private Post post;

    public Comentario(Integer id, Usuario autor, String conteudo, LocalDateTime dataComentario, Post post) {
        this.id = id;
        this.autor = autor;
        this.conteudo = conteudo;
        this.dataComentario = dataComentario;
        this.post = post;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataComentario() {
        return dataComentario;
    }

    public void setDataComentario(LocalDateTime dataComentario) {
        this.dataComentario = dataComentario;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Comentario{" +
                "id=" + id +
                ", autor=" + autor +
                ", conteudo='" + conteudo + '\'' +
                ", dataComentario=" + dataComentario +
                ", post=" + post +
                '}';
    }
}

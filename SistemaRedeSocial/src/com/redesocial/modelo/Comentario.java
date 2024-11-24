package com.redesocial.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa um comentário em um post na rede social.
 * Cada comentário está associado a um autor, conteúdo textual,
 * data de criação e ao post em que foi feito.
 */
public class Comentario {

    private Integer id; // Identificador único do comentário
    private Usuario autor; // Usuário que fez o comentário
    private String conteudo; // Conteúdo textual do comentário
    private LocalDateTime dataComentario; // Data e hora em que o comentário foi feito
    private Post post; // Post ao qual o comentário está associado

    /**
     * Construtor para criar um novo comentário.
     * A data do comentário é definida como o momento atual.
     *
     * @param autor    O autor do comentário.
     * @param conteudo O conteúdo textual do comentário.
     * @param post     O post ao qual o comentário está associado.
     */
    public Comentario(Usuario autor, String conteudo, Post post) {
        this.autor = autor;
        this.conteudo = conteudo;
        this.dataComentario = LocalDateTime.now();
        this.post = post;
    }

    /*
    Getters e Setters
     */
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

    /**
     * Retorna uma representação textual detalhada do comentário.
     * Inclui informações do autor, conteúdo e data do comentário.
     *
     * @return Uma string formatada com os dados do comentário.
     */
    @Override
    public String toString() {
        return "Autor: " + autor.getNome() + " (" + autor.getUsername() + ")\n" +
                "Conteúdo: " + conteudo + "\n" +
                "Data do Comentário: " + dataComentario.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n";
    }
}

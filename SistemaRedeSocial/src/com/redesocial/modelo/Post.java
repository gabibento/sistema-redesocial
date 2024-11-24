package com.redesocial.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma publicação (post) na rede social.
 * Um post possui um autor, conteúdo textual, data de publicação,
 * lista de curtidas e comentários associados.
 */
public class Post {
    private Integer id; // Identificador único do post
    private Usuario autor; // Usuário autor do post
    private String conteudo; // Conteúdo textual do post
    private LocalDateTime dataPublicacao; // Data e hora da publicação
    private List<Usuario> curtidas; // Lista de usuários que curtiram o post
    private List<Comentario> comentarios; // Lista de comentários no post

    /**
     * Construtor para criar um novo post.
     * A data de publicação é definida como o momento atual.
     *
     * @param autor    O autor do post.
     * @param conteudo O conteúdo textual do post.
     */
    public Post(Usuario autor, String conteudo) {
        this.autor = autor;
        this.conteudo = conteudo;
        this.dataPublicacao = LocalDateTime.now();
        this.curtidas = new ArrayList<>();
        this.comentarios = new ArrayList<>();
    }

    /**
     * Adiciona uma curtida ao post.
     * Um usuário só pode curtir o post uma vez.
     *
     * @param usuario O usuário que curtiu o post.
     */
    public void adicionarCurtida(Usuario usuario) {
        if (!curtidas.contains(usuario)) {
            curtidas.add(usuario);
        }
    }

    /**
     * Remove a curtida de um usuário no post.
     *
     * @param usuario O usuário que teve sua curtida removida.
     */
    public void removerCurtida(Usuario usuario) {
        curtidas.remove(usuario);
    }

    /**
     * Adiciona um comentário ao post.
     *
     * @param comentario O comentário a ser adicionado.
     */
    public void adicionarComentario(Comentario comentario) {
        if (!comentarios.contains(comentario)) {
            comentarios.add(comentario);
        }
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

    public LocalDateTime getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDateTime dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public List<Usuario> getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(List<Usuario> curtidas) {
        this.curtidas = curtidas;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * Retorna uma representação textual detalhada do post.
     * Inclui informações do autor, conteúdo, data de publicação,
     * quantidade de curtidas e de comentários.
     *
     * @return Uma string formatada com os dados do post.
     */
    @Override
    public String toString() {
        return "ID: " + id + "\n" +
                "Autor: " + autor.getNome() + " (" + autor.getUsername() + ")\n" +
                "Conteúdo: " + conteudo + "\n" +
                "Data de Publicação: " + dataPublicacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n" +
                "Curtidas: " + (curtidas != null ? curtidas.size() : 0) +
                "   Comentários: " + (comentarios != null ? comentarios.size() : 0) + "\n";
    }
}

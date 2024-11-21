package com.redesocial.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Post {
    private Integer id;
    private Usuario autor;
    private String conteudo;
    private LocalDateTime dataPublicacao;
    private List<Usuario> curtidas;
    private List<Comentario> comentarios;

    public Post(Usuario autor, String conteudo) {
        this.autor = autor;
        this.conteudo = conteudo;
        this.dataPublicacao = LocalDateTime.now();
        this.curtidas = new ArrayList<>();
        this.comentarios = new ArrayList<>();
    }

    public void adicionarCurtida(Usuario usuario){
        if(!curtidas.contains(usuario)) {
            curtidas.add(usuario);
        }
    }
    public void removerCurtida(Usuario usuario){
        curtidas.remove(usuario);
    }
    public void adicionarComentario(Comentario comentario){
        if(!comentarios.contains(comentario)){
            comentarios.add(comentario);
        }
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

    @Override
    public String toString() {
        return  "ID: " + id + "\n" +
                "Autor: " + autor.getNome() + " (" + autor.getUsername() + ")\n" +
                "Conteúdo: " + conteudo + "\n" +
                "Data de Publicação: " + dataPublicacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n" +
                "Curtidas: " + (curtidas != null ? curtidas.size() : 0) + "\n" +
                "Comentários: " + (comentarios != null ? comentarios.size() : 0) + "\n";
    }

}

package com.redesocial.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {

    private Integer id;
    private String nome;
    private String username;
    private String email;
    private String senha;
    private LocalDateTime dataCadastro;
    private List<Usuario> amigos;
    private List<Usuario> solicitacoesPendentes = new ArrayList<>();
    private List<Post> posts;

    public Usuario(String nome, String username, String email, String senha) {
        this.nome = nome;
        this.username = username;
        this.email = email;
        this.senha = senha;
        this.dataCadastro = LocalDateTime.now();
        amigos = new ArrayList<>();
        solicitacoesPendentes = new ArrayList<>();
        posts = new ArrayList<>();
    }
    public void adicionarAmigo(Usuario amigo){
        if (amigo != null && !amigos.contains(amigo)) {
            amigos.add(amigo);
            amigo.getAmigos().add(this); // Adiciona o amigo em ambas as listas
            removerSolicitacao(amigo);
        }
    }
    public void removerAmigo(Usuario amigo){
        if(amigos.contains(amigo)){
            amigos.remove(amigo);
            amigo.removerAmigo(this);
        }
    }
    public void adicionarSolicitacao(Usuario usuario) {
        if (!solicitacoesPendentes.contains(usuario)) {
            solicitacoesPendentes.add(usuario);
        }
    }

    public void removerSolicitacao(Usuario usuario) {
        solicitacoesPendentes.remove(usuario);
    }

    public void adicionarPost(Post post){
        if(post != null && !posts.contains(post)){
            posts.add(post);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public List<Usuario> getAmigos() {
        return amigos;
    }

    public void setAmigos(List<Usuario> amigos) {
        this.amigos = amigos;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Usuario> getSolicitacoesPendentes() {
        return solicitacoesPendentes;
    }

    public void setSolicitacoesPendentes(List<Usuario> solicitacoesPendentes) {
        this.solicitacoesPendentes = solicitacoesPendentes;
    }

    @Override
    public String toString() {
        StringBuilder perfil = new StringBuilder();
        perfil.append("=== Perfil ==== \n");
        perfil.append("Informações Básicas:\n");
        perfil.append("ID: ").append(id).append("\n");
        perfil.append("Nome: ").append(nome).append("\n");
        perfil.append("Username: ").append(username).append("\n");
        perfil.append("Email: ").append(email).append("\n");
        perfil.append("Data de Cadastro: ").append(dataCadastro.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("\n");
        perfil.append("\n");

        perfil.append("Lista de Amigos:\n");
        if (amigos == null || amigos.isEmpty()) {
            perfil.append("Nenhum amigo adicionado.\n");
        } else {
            for (Usuario amigo : amigos) {
                perfil.append("- ").append(amigo.getNome()).append(" (").append(amigo.getUsername()).append(")\n");
            }
        }
        perfil.append("\n");

        perfil.append("Lista de Posts:\n");
        if (posts == null || posts.isEmpty()) {
            perfil.append("Nenhum post adicionado.\n");
        } else {
            for (Post post : posts) {
                perfil.append("- ").append(post.toString()).append("\n");
            }
        }
        perfil.append("=====================================\n");

        return perfil.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

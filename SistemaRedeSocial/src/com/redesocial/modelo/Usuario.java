package com.redesocial.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Representa um usuário da rede social.
 * Cada usuário possui informações básicas, uma lista de amigos, posts e solicitações de amizade pendentes.
 */
public class Usuario {

    private Integer id; // Identificador único do usuário
    private String nome; // Nome do usuário
    private String username; // Nome de usuário para login e identificação
    private String email; // Email do usuário
    private String senha; // Senha do usuário (armazenada criptografada)
    private LocalDateTime dataCadastro; // Data e hora do cadastro do usuário
    private List<Usuario> amigos; // Lista de amigos do usuário
    private List<Usuario> solicitacoesPendentes; // Lista de solicitações de amizade pendentes
    private List<Post> posts; // Lista de posts feitos pelo usuário

    /**
     * Construtor para criar um novo usuário.
     * A data de cadastro é definida como o momento atual.
     *
     * @param nome     Nome do usuário.
     * @param username Nome de usuário para identificação.
     * @param email    Email do usuário.
     * @param senha    Senha do usuário.
     */
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

    /**
     * Adiciona um novo amigo à lista de amigos do usuário.
     * Também remove a solicitação pendente enviada por esse amigo.
     *
     * @param amigo O amigo a ser adicionado.
     */
    public void adicionarAmigo(Usuario amigo) {
        if (amigo != null && !amigos.contains(amigo)) {
            amigos.add(amigo);
            amigo.getAmigos().add(this); // Adiciona o amigo em ambas as listas
            removerSolicitacao(amigo);
        }
    }

    /**
     * Remove um amigo da lista de amigos do usuário.
     * A remoção é mútua: o amigo também perde o usuário como amigo.
     *
     * @param amigo O amigo a ser removido.
     */
    public void removerAmigo(Usuario amigo) {
        if (amigos.contains(amigo)) {
            amigos.remove(amigo);
            amigo.removerAmigo(this);
        }
    }

    /**
     * Adiciona uma nova solicitação de amizade enviada por outro usuário.
     *
     * @param usuario O usuário que enviou a solicitação.
     */
    public void adicionarSolicitacao(Usuario usuario) {
        if (!solicitacoesPendentes.contains(usuario)) {
            solicitacoesPendentes.add(usuario);
        }
    }

    /**
     * Remove uma solicitação de amizade recebida.
     *
     * @param usuario O usuário cuja solicitação será removida.
     */
    public void removerSolicitacao(Usuario usuario) {
        solicitacoesPendentes.remove(usuario);
    }

    /**
     * Adiciona um post à lista de posts do usuário.
     *
     * @param post O post a ser adicionado.
     */
    public void adicionarPost(Post post) {
        if (post != null && !posts.contains(post)) {
            posts.add(post);
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

    /**
     * Retorna uma representação textual detalhada do perfil do usuário,
     * incluindo informações básicas, amigos e posts.
     *
     * @return Uma string formatada com os dados do perfil do usuário.
     */
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
    /**
     * Verifica se dois usuários são iguais, comparando seus IDs.
     *
     * @param o O objeto a ser comparado.
     * @return True se os IDs forem iguais; caso contrário, false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    /**
     * Gera um hash code baseado no ID do usuário.
     *
     * @return O hash code do usuário.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

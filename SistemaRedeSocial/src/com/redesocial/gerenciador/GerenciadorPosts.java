package com.redesocial.gerenciador;

import com.redesocial.exception.PostException;
import com.redesocial.exception.UsuarioException;
import com.redesocial.exception.ValidacaoException;
import com.redesocial.modelo.Comentario;
import com.redesocial.modelo.Post;
import com.redesocial.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Gerenciador responsável por administrar operações relacionadas a posts na rede social.
 * Inclui criação, listagem, busca, curtidas, comentários e validação de posts.
 */
public class GerenciadorPosts {
    private List<Post> posts; // Lista de todos os posts
    private int proximoId; // ID sequencial para posts
    private int idComentario; // ID sequencial para comentários
    private final GerenciadorUsuarios gerenciadorUsuarios; // Gerenciador de usuários

    /**
     * Construtor do Gerenciador de Posts.
     *
     * @param gerenciadorUsuarios Gerenciador de usuários para validação e operações relacionadas.
     */
    public GerenciadorPosts(GerenciadorUsuarios gerenciadorUsuarios) {
        posts = new ArrayList<>();
        proximoId = 1;
        idComentario = 1;
        this.gerenciadorUsuarios = gerenciadorUsuarios;
    }

    /**
     * Cria um novo post e adiciona à lista de posts.
     *
     * @param post O post a ser criado.
     */
    public void criar(Post post) {
        try {
            validarPost(post);
            post.setId(proximoId++);
            posts.add(post);
        } catch (ValidacaoException e) {
            throw e;
        } catch (Exception e) {
            throw new UsuarioException("Erro ao criar post: " + e.getMessage(), e);
        }
    }

    /**
     * Lista todos os posts cadastrados.
     *
     * @return A lista de posts.
     */
    public List<Post> listarPosts() {
        return posts;
    }

    /**
     * Busca um post pelo seu ID.
     *
     * @param id O ID do post a ser buscado.
     * @return O post encontrado ou null se não existir.
     */
    public Post buscarPorId(int id) {
        try {
            return posts.stream()
                    .filter(post -> post.getId() == id)
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            throw new PostException("Erro ao buscar post: " + e.getMessage(), e);
        }
    }

    /**
     * Lista todos os posts de um usuário específico.
     *
     * @param idUsuario O ID do usuário.
     * @return A lista de posts do usuário.
     */
    public List<Post> listarPorUsuario(int idUsuario) {
        try {
            return posts.stream()
                    .filter(post -> post.getAutor().getId() == idUsuario)
                    .toList();
        } catch (Exception e) {
            throw new PostException("Erro ao listar por usuário: " + e.getMessage(), e);
        }
    }

    /**
     * Adiciona uma curtida a um post.
     *
     * @param idPost    O ID do post.
     * @param idUsuario O ID do usuário que curtiu.
     */
    public void curtir(int idPost, int idUsuario) {
        Post post = buscarPorId(idPost);
        Usuario usuario = gerenciadorUsuarios.buscarPorId(idUsuario);

        if (post != null && usuario != null) {
            post.adicionarCurtida(usuario);
            System.out.println("Post curtido com sucesso!");
        } else {
            throw new PostException("Erro ao curtir post: usuário ou post inválido.");
        }
    }

    /**
     * Remove uma curtida de um post.
     *
     * @param idPost    O ID do post.
     * @param idUsuario O ID do usuário que removeu a curtida.
     */
    public void descurtir(int idPost, int idUsuario) {
        try {
            Post post = buscarPorId(idPost);
            Usuario usuario = gerenciadorUsuarios.buscarPorId(idUsuario);
            if (post != null && usuario != null) {
                post.removerCurtida(usuario);
                System.out.println("Post descurtido com sucesso!");
            }
        } catch (Exception e) {
            throw new PostException("Erro ao descurtir post: " + e.getMessage(), e);
        }
    }

    /**
     * Adiciona um comentário a um post.
     *
     * @param comentario O comentário a ser adicionado.
     */
    public void comentar(Comentario comentario) {
        try {
            comentario.setId(idComentario++);
            comentario.getPost().adicionarComentario(comentario);
        } catch (Exception e) {
            throw new PostException("Erro ao comentar post: " + e.getMessage(), e);
        }
    }

    /**
     * Deleta um post pelo seu ID.
     *
     * @param id O ID do post a ser deletado.
     * @return True se o post foi removido com sucesso; False caso contrário.
     */
    public boolean deletar(int id) {
        try {
            return posts.removeIf(post -> post.getId() == id);
        } catch (Exception e) {
            throw new UsuarioException("Erro ao deletar post: " + e.getMessage(), e);
        }
    }

    /**
     * Valida um post antes de adicioná-lo.
     *
     * @param post O post a ser validado.
     * @throws ValidacaoException Se o post for inválido.
     */
    private void validarPost(Post post) {
        if (post.getConteudo().isEmpty()) {
            throw new ValidacaoException("Conteúdo do post não pode ser vazio.");
        }
        if (!gerenciadorUsuarios.listarUsuarios().contains(post.getAutor())) {
            throw new ValidacaoException("Autor inválido.");
        }
        if (post.getConteudo().length() > 280) {
            throw new ValidacaoException("Limite de caracteres atingido.");
        }
    }
}

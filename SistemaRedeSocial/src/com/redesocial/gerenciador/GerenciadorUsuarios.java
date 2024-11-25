package com.redesocial.gerenciador;

import com.redesocial.exception.UsuarioException;
import com.redesocial.exception.ValidacaoException;
import com.redesocial.modelo.Post;
import com.redesocial.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Classe responsável por gerenciar as operações relacionadas a usuários na rede social.
 * Inclui funcionalidades como cadastro, atualização, remoção, gestão de amizades
 * e envio de solicitações.
 */
public class GerenciadorUsuarios {
    private List<Usuario> usuarios; // Lista de usuários cadastrados
    private int proximoId; // ID sequencial para novos usuários

    /**
     * Construtor para inicializar o gerenciador de usuários.
     * Inicializa a lista de usuários e define o próximo ID como 1.
     */
    public GerenciadorUsuarios() {
        usuarios = new ArrayList<>();
        proximoId = 1;
    }

    /**
     * Cadastra um novo usuário na rede social.
     * O ID é gerado automaticamente e a senha é criptografada antes do armazenamento.
     *
     * @param usuario O usuário a ser cadastrado.
     * @throws UsuarioException Se ocorrer um erro durante o cadastro.
     */
    public void cadastrar(Usuario usuario) {
        try {
            validarUsuario(usuario);
            usuario.setId(proximoId++);
            usuarios.add(usuario);
        } catch (ValidacaoException e) {
            throw e;
        } catch (Exception e) {
            throw new UsuarioException("Erro ao cadastrar usuário: " + e.getMessage(), e);
        }
    }

    /**
     * Busca um usuário pelo seu ID.
     *
     * @param id O ID do usuário.
     * @return O usuário encontrado.
     * @throws UsuarioException Se o usuário não for encontrado.
     */
    public Usuario buscarPorId(int id) {
        return usuarios.stream()
                .filter(usuario -> usuario.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UsuarioException("Usuário não encontrado"));
    }

    /**
     * Busca um usuário pelo seu username.
     *
     * @param username O username do usuário.
     * @return O usuário encontrado ou null se não existir.
     */
    public Usuario buscarPorUsername(String username) {
        try {
            return usuarios.stream()
                    .filter(usuario -> usuario.getUsername().equals(username))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            throw new UsuarioException("Erro ao buscar usuário: " + e.getMessage(), e);
        }
    }

    /**
     * Busca usuários pelo nome.
     *
     * @param nome O nome a ser buscado.
     * @return Uma lista de usuários cujo nome contém o termo informado.
     * @throws ValidacaoException Se o nome for inválido.
     */
    public List<Usuario> buscarPorNome(String nome) {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                throw new ValidacaoException("Nome não pode ser vazio");
            }
            return usuarios.stream()
                    .filter(usuario -> usuario.getNome().toLowerCase().contains(nome.toLowerCase()))
                    .collect(Collectors.toList());
        } catch (ValidacaoException e) {
            throw e;
        } catch (Exception e) {
            throw new UsuarioException("Erro ao buscar usuários por nome: " + e.getMessage(), e);
        }
    }

    /**
     * Atualiza os dados de um usuário.
     *
     * @param usuario O usuário atualizado.
     * @return True se a atualização for bem-sucedida, false caso contrário.
     * @throws UsuarioException Se ocorrer um erro durante a atualização.
     */
    public boolean atualizar(Usuario usuario) {
        try {
            validarUsuario(usuario);

            for (int i = 0; i < usuarios.size(); i++) {
                if (Objects.equals(usuarios.get(i).getId(), usuario.getId())) {
                    usuarios.set(i, usuario);
                    return true;
                }
            }
            return false;
        } catch (ValidacaoException e) {
            throw e;
        } catch (Exception e) {
            throw new UsuarioException("Erro ao atualizar usuário: " + e.getMessage(), e);
        }
    }

    /**
     * Remove um usuário pelo seu ID.
     *
     * @param id O ID do usuário a ser removido.
     * @return True se o usuário foi removido com sucesso.
     */
    public boolean deletar(int id) {
        try {
            return usuarios.removeIf(usuario -> usuario.getId() == id);
        } catch (Exception e) {
            throw new UsuarioException("Erro ao deletar usuário: " + e.getMessage(), e);
        }
    }

    /**
     * Envia uma solicitação de amizade para outro usuário.
     *
     * @param idRemetente   O ID do usuário que envia a solicitação.
     * @param idDestinatario O ID do usuário que receberá a solicitação.
     * @throws UsuarioException Se a solicitação já foi enviada ou se ocorrer outro erro.
     */
    public void enviarSolicitacaoAmizade(int idRemetente, int idDestinatario) {
        Usuario remetente = buscarPorId(idRemetente);
        Usuario destinatario = buscarPorId(idDestinatario);

        if (destinatario.getSolicitacoesPendentes().contains(remetente)) {
            throw new UsuarioException("Você já enviou uma solicitação para este usuário.");
        }
        destinatario.adicionarSolicitacao(remetente);
        System.out.println("Solicitação enviada para " + destinatario.getUsername());
    }

    /**
     * Adiciona uma amizade entre dois usuários.
     *
     * @param idUsuario1 O ID do usuário que aceita a solicitação.
     * @param idUsuario2 O ID do remetente da solicitação.
     * @throws UsuarioException Se a solicitação não for encontrada.
     */
    public void adicionarAmizade(int idUsuario1, int idUsuario2) {
        Usuario usuario = buscarPorId(idUsuario1);
        Usuario remetente = buscarPorId(idUsuario2);

        if (!usuario.getSolicitacoesPendentes().contains(remetente)) {
            throw new UsuarioException("Solicitação de amizade não encontrada.");
        }
        usuario.adicionarAmigo(remetente);
        System.out.println("Agora você é amigo de " + remetente.getUsername());
    }

    /**
     * Remove uma amizade entre dois usuários.
     *
     * @param idUsuario1 O ID do primeiro usuário.
     * @param idUsuario2 O ID do segundo usuário.
     */
    public void removerAmizade(int idUsuario1, int idUsuario2) {
        try {
            buscarPorId(idUsuario1).removerAmigo(buscarPorId(idUsuario2));
        } catch (Exception e) {
            throw new UsuarioException("Erro ao remover amizade: " + e.getMessage(), e);
        }
        System.out.println("Amigo removido com sucesso!");
    }

    /**
     * Recusa uma solicitação de amizade.
     *
     * @param idUsuario   O ID do usuário que recusa a solicitação.
     * @param idRemetente O ID do remetente da solicitação.
     */
    public void recusarSolicitacaoAmizade(int idUsuario, int idRemetente) {
        Usuario usuario = buscarPorId(idUsuario);
        Usuario remetente = buscarPorId(idRemetente);

        if (!usuario.getSolicitacoesPendentes().contains(remetente)) {
            throw new UsuarioException("Solicitação de amizade não encontrada.");
        }
        usuario.removerSolicitacao(remetente);
        System.out.println("Você recusou a solicitação de amizade de " + remetente.getUsername());
    }

    /**
     * Adiciona um post a um usuário.
     *
     * @param usuario O usuário que fez o post.
     * @param post    O post a ser adicionado.
     */
    public void adicionarPost(Usuario usuario, Post post) {
        usuario.adicionarPost(post);
    }

    /**
     * Valida os dados de um usuário.
     *
     * @param usuario O usuário a ser validado.
     * @throws ValidacaoException Se os dados forem inválidos.
     */
    private void validarUsuario(Usuario usuario) {
        for (Usuario usuario1 : usuarios) {
            if (!usuario1.getId().equals(usuario.getId()) && usuario1.getUsername().equals(usuario.getUsername())) {
                throw new ValidacaoException("Usuário " + usuario.getUsername() + " já existe");
            }
        }
        if (!usuario.getEmail().contains("@")) {
            throw new ValidacaoException("Email inválido");
        }
        if (usuario.getSenha().length() < 6) {
            throw new ValidacaoException("A senha precisa conter no mínimo 6 caracteres");
        }
        if (usuario.getNome().isEmpty()) {
            throw new ValidacaoException("O nome não pode ser vazio");
        }
    }

    /**
     * Lista todos os usuários cadastrados.
     *
     * @return A lista de usuários.
     */
    public List<Usuario> listarUsuarios() {
        return usuarios;
    }
}

package com.redesocial.gerenciador;

import com.redesocial.exception.UsuarioException;
import com.redesocial.exception.ValidacaoException;
import com.redesocial.modelo.Post;
import com.redesocial.modelo.Usuario;

import org.mindrot.jbcrypt.BCrypt;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GerenciadorUsuarios {
    private List<Usuario> usuarios;
    private int proximoId;

    public GerenciadorUsuarios(){
        usuarios = new ArrayList<>();
        proximoId = 1;
    }

    public void cadastrar(Usuario usuario){
        try{
            validarUsuario(usuario);
            usuario.setId(proximoId++);
            String senhaCriptografada = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
            usuario.setSenha(senhaCriptografada);

            usuarios.add(usuario);
        }catch (ValidacaoException e){
            throw e;
        }catch (Exception e){
            throw new UsuarioException("Erro ao cadastrar usuário: " + e.getMessage() + e);
        }

    }
    public Usuario buscarPorId(int id){
        return usuarios.stream()
                .filter(usuario -> usuario.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UsuarioException("Usuário não encontrado"));
    }
    public Usuario buscarPorUsername(String username){
        try{
            return usuarios.stream()
                    .filter(usuario -> usuario.getUsername().equals(username))
                    .findFirst()
                    .orElse(null);
        }catch (Exception e){
            throw new UsuarioException("Erro ao buscar usuário: " + e.getMessage() + e);
        }
    }
    public List<Usuario> buscarPorNome(String nome){
        try{
            if (nome == null || nome.trim().isEmpty()) {
                throw new ValidacaoException("Nome não pode ser vazio");
            }
            return usuarios.stream()
                    .filter(usuario -> usuario.getNome().toLowerCase().contains(nome))
                    .collect(Collectors.toList());
        }catch (ValidacaoException e){
            throw e;
        }catch (Exception e){
            throw new UsuarioException("Erro ao buscar usuários por nome: " + e.getMessage() + e);
        }
    }
    public boolean atualizar(Usuario usuario){
        try{
            validarUsuario(usuario);

            for(int i = 0; i < usuarios.size(); i++){
                if(Objects.equals(usuarios.get(i).getId(), usuario.getId())){
                    usuarios.set(i, usuario);
                    return true;
                }
            }
            return  false;
        }catch (ValidacaoException e){
            throw e;
        }catch (Exception e){
            throw new UsuarioException("Erro ao atualizar usuário: " + e.getMessage() + e);
        }
    }
    public boolean deletar(int id){
        try{
            return usuarios.removeIf(usuario -> usuario.getId() == id);
        }catch (Exception e){
            throw new UsuarioException("Erro ao deletar usuário: " + e.getMessage() + e);
        }
    }
    public void adicionarAmizade(int idUsuario1, int idUsuario2){
      try{
          buscarPorId(idUsuario1).adicionarAmigo(buscarPorId(idUsuario2));
          System.out.println("Amigo adicionado com sucesso!");
      }catch (Exception e){
          throw new UsuarioException("Erro ao adicionar amizade: " + e.getMessage() + e);
      }
    }
    public void removerAmizade(int idUsuario1, int idUsuario2){
        try{
            buscarPorId(idUsuario1).removerAmigo(buscarPorId(idUsuario2));
        }catch (Exception e){
            throw new UsuarioException("Erro ao remover amizade: " + e.getMessage() + e);
        }
        System.out.println("Amigo removido com sucesso!");
    }
    public List<Usuario> listarUsuarios(){
        return usuarios;
    }

    private void validarUsuario(Usuario usuario){
        for (Usuario usuario1 : usuarios) {
            if (!usuario1.getId().equals(usuario.getId()) && usuario1.getUsername().equals(usuario.getUsername())) {
                throw new ValidacaoException("Usuário " + usuario.getUsername() + " já existe");
            }
        }
        if(!usuario.getEmail().contains("@")){
            throw new ValidacaoException("Email inválido");
        }
        if(usuario.getSenha().length() < 6){
            throw  new ValidacaoException("A senha precisa conter no mínimo 6 caracteres");
        }
        if(usuario.getNome().isEmpty()){
            throw new ValidacaoException("O nome não pode ser vazio");
        }
    }
    public void adicionarPost(Usuario usuario, Post post){
        usuario.adicionarPost(post);
    }


}

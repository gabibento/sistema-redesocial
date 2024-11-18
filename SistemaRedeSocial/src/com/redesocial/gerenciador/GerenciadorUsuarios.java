package com.redesocial.gerenciador;

import com.redesocial.exception.UsuarioException;
import com.redesocial.exception.ValidacaoException;
import com.redesocial.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

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
            usuarios.add(usuario);
        }catch (ValidacaoException e){
            throw e;
        }catch (Exception e){
            throw new UsuarioException("Erro ao cadastrar usuário: " + e.getMessage() + e);
        }

    }
    public void validarUsuario(Usuario usuario){
        for(Usuario usuario1 : usuarios){
            if(usuario1.getUsername().equals(usuario.getUsername())){
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


}

package com.redesocial.gerenciador;

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

    }
    public void validarUsuario(Usuario usuario){
        for(Usuario usuario1 : usuarios){
            if(usuario1.getUsername().equals(usuario.getUsername())){
                throw new ValidacaoException("Usuário " + usuario.getUsername() + " já existe");
            }
        }
    }


}

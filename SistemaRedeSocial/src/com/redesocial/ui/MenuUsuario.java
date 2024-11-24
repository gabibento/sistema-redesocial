package com.redesocial.ui;

import com.redesocial.exception.PostException;
import com.redesocial.exception.UsuarioException;
import com.redesocial.gerenciador.GerenciadorPosts;
import com.redesocial.gerenciador.GerenciadorUsuarios;
import com.redesocial.modelo.Comentario;
import com.redesocial.modelo.Post;
import com.redesocial.modelo.Usuario;
import com.redesocial.utils.LerEntrada;

import java.util.List;

public class MenuUsuario {
    private Usuario usuario;
    private final GerenciadorPosts gerenciadorPosts;
    private final GerenciadorUsuarios gerenciadorUsuarios;

    public MenuUsuario(Usuario usuario, GerenciadorUsuarios gerenciadorUsuarios, GerenciadorPosts gerenciadorPosts) {
        this.usuario = usuario;
        this.gerenciadorUsuarios = gerenciadorUsuarios;
        this.gerenciadorPosts = gerenciadorPosts;
    }
    public void exibirMenu(){
       boolean continuar = true;

       while(continuar){
           System.out.println("=== Menu ===");
           System.out.println("1. Criar Post");
           System.out.println("2. Ver Meu Perfil");
           System.out.println("3. Buscar Usuários");
           System.out.println("4. Gerenciar Amizades");
           System.out.println("5. Ver Feed de Notícias");
           System.out.println("6. Logout");
           int opcao = LerEntrada.lerEntradaInteira("Escolha uma opção: ");

           switch (opcao){
               case 1 -> criarPost();
               case 2 -> verPerfil();
               case 3 -> buscarUsuarios();
               case 4 -> gerenciarAmizades();
               case 5 -> verFeedNoticias();
               case 6 -> continuar = false;
               default -> System.out.println("Opção inválida");
           }
           if(usuario == null){
               continuar = false;
           }
       }
    }
    private void criarPost(){
       try{
           System.out.println("=== Novo Post ===");

           String conteudo = LerEntrada.lerEntradaString("Digite seu post:\n");

           Post post = new Post(usuario, conteudo);
           gerenciadorPosts.criar(post);
           gerenciadorUsuarios.adicionarPost(usuario, post);
           System.out.println("Post publicado com sucesso");
       }catch (Exception e){
           System.out.println("Erro ao criar post: " + e.getMessage());
       }
    }
    private void verPerfil(){
        System.out.println("=== Meu Perfil ===");
        System.out.println(usuario);
        int opcao = LerEntrada.lerEntradaInteira("1. Editar perfil \n2. Excluir conta \n3. Excluir post \n4. Voltar \nEscolha uma opção: ");

        switch (opcao){
            case 1 -> editarPerfil();
            case 2 -> excluirConta();
            case 3 -> excluirPost();
        }

    }
    private void verOutroPerfil(Usuario usuario){

        System.out.println(usuario);

        boolean ehAmigo = this.usuario.getAmigos().contains(usuario);

        int opcao = LerEntrada.lerEntradaInteira("1. " + (ehAmigo ? "Remover amizade" : "Adicionar Amizade") + "\n2. Voltar \nEscolha uma opção: ");
        if(opcao == 1){
            if(!ehAmigo){
                gerenciadorUsuarios.adicionarAmizade(this.usuario.getId(), usuario.getId());
            }else{
                gerenciadorUsuarios.removerAmizade(this.usuario.getId(), usuario.getId());
            }
        }
    }
    private void exibirUsuariosEncontrados(List<Usuario> usuarios){
        usuarios.forEach(usuario1 -> System.out.println("Nome: " + usuario1.getNome() + " Username: " + usuario1.getUsername()));
    }
    private void gerenciarAmizades(){
        boolean continuar = true;
        while(continuar){
            int opcao = LerEntrada.lerEntradaInteira("1. Adicionar novo amigo \n2. Remover amigo \n3. Ver amigos \n4. Voltar \nEscolha uma opção: ");
            switch (opcao){
                case 1 -> adicionarAmigo();
                case 2 -> removerAmigo();
                case 3 -> verAmigos();
                case 4 -> continuar = false;
            }
        }
    }
    private void adicionarAmigo(){
        boolean continuar = true;
        while (continuar){
            try{
                String username = LerEntrada.lerEntradaString("Digite o username do usuário a ser adicionado: ");
                if(username.equals("0")){
                    continuar = false;
                }else{
                    gerenciadorUsuarios.adicionarAmizade(usuario.getId(), gerenciadorUsuarios.buscarPorUsername(username).getId());
                    continuar = false;
                }

            }catch (Exception e){
                System.out.println("Usuário não encontrado. Tente novamente ou digite 0 para voltar");
            }
        }
    }
    private void removerAmigo(){
        boolean continuar = true;
        while(continuar){
            try{
                String username = LerEntrada.lerEntradaString("Digite o username do usuário a ser removido: ");
                if(username.equals("0")){
                    continuar = false;
                }else{
                    gerenciadorUsuarios.removerAmizade(usuario.getId(), gerenciadorUsuarios.buscarPorUsername(username).getId());
                }
            }catch (Exception e){
                System.out.println("Usuário não encontrado. Tente novamente ou digite 0 para voltar");
            }
        }
    }
    private void verAmigos(){
        if(usuario.getAmigos().isEmpty()){
            System.out.println("Nenhum amigo adicionado");
        }else{
            exibirUsuariosEncontrados(usuario.getAmigos());
        }
    }
    private void editarPerfil(){
        System.out.println("=== Editar Perfil ===");
        System.out.println("Digite os novos dados (ou pressione ENTER para manter o valor atual):");
        while(true){
            try{
                String nome = LerEntrada.lerEntradaStringOpcional("Nome [" + usuario.getNome() + "]: ");
                String username = LerEntrada.lerEntradaStringOpcional("Username [" + usuario.getUsername() + "]: ");
                String email = LerEntrada.lerEntradaStringOpcional("Email [" + usuario.getEmail() + "]: ");
                String senha = LerEntrada.lerEntradaStringOpcional("Senha [" + usuario.getSenha() + "]: ");

                if(!nome.isEmpty()) usuario.setNome(nome);
                if(!username.isEmpty()) usuario.setUsername(username);
                if(!email.isEmpty()) usuario.setEmail(email);
                if(!senha.isEmpty()) usuario.setSenha(senha);

                gerenciadorUsuarios.atualizar(usuario);
                System.out.println("Perfil atualizado com sucesso!");
                break;
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
    private void buscarUsuarios() {
        try {
            String busca = LerEntrada.lerEntradaString("Digite um nome ou username: ");

            // Busca de usuários
            List<Usuario> usuarios = gerenciadorUsuarios.buscarPorNome(busca);
            Usuario usuarioExato = gerenciadorUsuarios.buscarPorUsername(busca);

            // Combina resultados de busca por nome e username (evita duplicação)
            if (usuarioExato != null && !usuarios.contains(usuarioExato)) {
                usuarios.add(usuarioExato);
            }

            if (usuarios.isEmpty()) {
                System.out.println("Nenhum usuário encontrado com o termo: " + busca);
                return;
            }

            // Exibe a lista de usuários encontrados
            exibirUsuariosEncontrados(usuarios);
            String opcao = LerEntrada.lerEntradaString("Escolha o username do usuário para ver o perfil ou digite 0 para voltar: ");

            // Opção escolhida
            if (!opcao.equals("0")) {
                Usuario usuarioSelecionado = gerenciadorUsuarios.buscarPorUsername(opcao);
                if (usuarioSelecionado != null) {
                    verOutroPerfil(usuarioSelecionado);
                } else {
                    System.out.println("Usuário com username '" + opcao + "' não encontrado.");
                }
            }
        } catch (UsuarioException e) {
            System.out.println("Erro ao buscar usuários: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }

    private void verFeedNoticias(){
        try{
            System.out.println("=== Feed de Notícias ===");

            List<Post> posts = gerenciadorPosts.listarPosts().stream()
                    .filter(post -> usuario.getAmigos().contains(post.getAutor()) || post.getAutor().equals(usuario))
                    .sorted((p1, p2) -> p2.getDataPublicacao().compareTo(p1.getDataPublicacao()))
                    .toList();

            if(!posts.isEmpty()){
                posts.forEach(System.out::println);
                int opcao = LerEntrada.lerEntradaInteira("Digite o número do post para interagir ou pressione 0 para voltar.");

                if(opcao != 0){
                    interagirPost(opcao);
                }
            }else{
                System.out.println("Não há posts no feed de notícias");
            }


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void interagirPost(int id){
       boolean continuar = true;

       while(continuar){
           System.out.println("=== Detalhes do post ===");
           Post post = gerenciadorPosts.buscarPorId(id);
           System.out.println("Autor: " + post.getAutor().getUsername());
           System.out.println("Conteúdo: " + post.getConteudo());
           System.out.println("Curtidas: " + post.getCurtidas().size());
           System.out.println("Comentários: " + post.getComentarios().size());
           System.out.println();
           System.out.println("1. Ver curtidas. \n2. Ver comentários.");
           boolean estaCurtido = post.getCurtidas().contains(usuario);
           System.out.println("3. " + (estaCurtido ? "Descurtir." : "Curtir"));
           System.out.println("4. Comentar \n5. Voltar");

           try{
               int opcao = LerEntrada.lerEntradaInteira("Escolha uma opção: ");
               switch (opcao){
                   case 1 -> exibirUsuariosEncontrados(post.getCurtidas());
                   case 2 -> listarComentarios(post);
                   case 3 -> {
                       if (estaCurtido) {
                           gerenciadorPosts.descurtir(post.getId(), usuario.getId());
                       } else {
                           gerenciadorPosts.curtir(post.getId(), usuario.getId());
                       }
                   }
                   case 4 -> comentar(post);
                   case 5 -> continuar = false;
                   default -> System.out.println("Opção inválida");
               }
           }catch (Exception e){
               System.out.println(e.getMessage());
           }
       }
    }
    private void listarComentarios(Post post){
        post.getComentarios().forEach(System.out::println);
    }
    private void comentar(Post post){
        try{
            String conteudo = LerEntrada.lerEntradaString("Digite o comentário: ");

            gerenciadorPosts.comentar(new Comentario(usuario, conteudo, post));
            System.out.println("Comentário adicionado com sucesso!");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void excluirConta() {
        System.out.println("=== Excluir Conta ===");
        int opcao = LerEntrada.lerEntradaInteira("Tem certeza que deseja excluir sua conta? (1 - Sim / 2 - Não): ");
        if (opcao == 1) {
            try {
                if(gerenciadorUsuarios.deletar(usuario.getId())){
                    usuario = null;
                    System.out.println("Conta excluída com sucesso!");
                }else{
                    throw new UsuarioException("Erro ao excluir conta");
                }
            } catch(UsuarioException e){
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro ao excluir conta: " + e.getMessage());
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }
    private void excluirPost(){
      try{
          int id = LerEntrada.lerEntradaInteira("Escolha o id do post a ser excluído: ");
          Post post = gerenciadorPosts.buscarPorId(id);

          if(usuario.getPosts().contains(post)){
              int opcao = LerEntrada.lerEntradaInteira("Tem certeza que deseja excluir o post? (1 - Sim / 2 - Não): ");
              if (opcao == 1) {
                  if (gerenciadorPosts.deletar(id)) {
                      usuario.getPosts().remove(post);
                      System.out.println("Post excluído com sucesso!");
                  } else {
                      throw new PostException("Erro ao excluir post");
                  }
              }else{
                  System.out.println("Operação cancelada!");
              }
          }else{
              System.out.println("ID inválido");
          }

      }catch (Exception e){
          System.out.println(e.getMessage());
      }
    }


}

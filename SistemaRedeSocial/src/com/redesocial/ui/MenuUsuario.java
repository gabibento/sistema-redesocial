package com.redesocial.ui;

import com.redesocial.exception.PostException;
import com.redesocial.exception.UsuarioException;
import com.redesocial.gerenciador.GerenciadorPosts;
import com.redesocial.gerenciador.GerenciadorUsuarios;
import com.redesocial.modelo.Comentario;
import com.redesocial.modelo.Post;
import com.redesocial.modelo.Usuario;
import com.redesocial.utils.CoresConsole;
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
    public void exibirMenu() throws InterruptedException {
       boolean continuar = true;

       while(continuar){
           Thread.sleep(2000);
           System.out.println("=== Menu ===");
           System.out.println("1. Criar Post");
           System.out.println("2. Ver Meu Perfil");
           System.out.println("3. Buscar Usuários");
           System.out.println("4. Gerenciar Amizades");
           System.out.println("5. Ver Feed de Notícias");
           System.out.println("6. Ver posts por usuário");
           System.out.println("7. Logout");
           int opcao = LerEntrada.lerEntradaInteira(CoresConsole.info("Escolha uma opção: "));


           switch (opcao){
               case 1 -> criarPost();
               case 2 -> verPerfil();
               case 3 -> buscarUsuarios();
               case 4 -> gerenciarAmizades();
               case 5 -> verFeedNoticias();
               case 6 -> listarPorUsuario();
               case 7 -> continuar = false;
               default ->  System.out.println(CoresConsole.erro("Opção inválida"));
           }
           if(usuario == null){
               continuar = false;
           }
       }
    }
    private void criarPost(){
       try{
           System.out.println(CoresConsole.titulo("=== Novo Post ==="));

           String conteudo = LerEntrada.lerEntradaString(CoresConsole.info("Digite seu post:\n"));
           Post post = new Post(usuario, conteudo);
           gerenciadorPosts.criar(post);
           gerenciadorUsuarios.adicionarPost(usuario, post);
           System.out.println(CoresConsole.sucesso("Post publicado com sucesso"));
       }catch (Exception e){
           System.out.println(CoresConsole.erro("Erro ao criar post: " + e.getMessage()));
       }
    }
    private void verPerfil(){
        System.out.println(CoresConsole.titulo("=== Meu Perfil ==="));
        System.out.println(usuario);
        int opcao = LerEntrada.lerEntradaInteira(
                CoresConsole.info("1. Editar perfil \n2. Excluir conta \n3. Excluir post \n4. Voltar \nEscolha uma opção: ")
        );
        switch (opcao){
            case 1 -> editarPerfil();
            case 2 -> excluirConta();
            case 3 -> excluirPost();
            default -> System.out.println(CoresConsole.aviso("Voltando ao menu principal..."));
        }

    }
    private void verOutroPerfil(Usuario usuario){
        System.out.println(CoresConsole.titulo("=== Perfil de " + usuario.getUsername() + " ==="));

        System.out.println(usuario);

        boolean ehAmigo = this.usuario.getAmigos().contains(usuario);

        int opcao = LerEntrada.lerEntradaInteira(CoresConsole.aviso("1. " + (ehAmigo ? "Remover amizade" : "Adicionar Amizade") + "\n2. Voltar \nEscolha uma opção: "));
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
        System.out.println(CoresConsole.titulo("=== Gerenciar Amizades ==="));
        boolean continuar = true;
        while(continuar){
            int opcao = LerEntrada.lerEntradaInteira(CoresConsole.info("1. Adicionar novo amigo \n2. Remover amigo \n3. Ver amigos \n4. Voltar \nEscolha uma opção: "));
            switch (opcao){
                case 1 -> adicionarAmigo();
                case 2 -> removerAmigo();
                case 3 -> verAmigos();
                case 4 -> continuar = false;
            }
        }
    }
    private void adicionarAmigo(){
        System.out.println(CoresConsole.titulo("=== Adicionar Amigo ==="));
        boolean continuar = true;
        while (continuar){
            try{
                String username = LerEntrada.lerEntradaString(CoresConsole.info("Digite o username do usuário a ser adicionado: "));
                if (!username.equals("0")) {
                    gerenciadorUsuarios.adicionarAmizade(usuario.getId(), gerenciadorUsuarios.buscarPorUsername(username).getId());
                    System.out.println(CoresConsole.sucesso("Amigo adicionado com sucesso!"));
                }
                continuar = false;

            }catch (Exception e){
                System.out.println(CoresConsole.aviso("Usuário não encontrado. Tente novamente ou digite 0 para voltar"));
            }
        }
    }
    private void removerAmigo(){
        System.out.println(CoresConsole.titulo("=== Remover Amigo ===="));
        boolean continuar = true;
        while(continuar){
            try{
                String username = LerEntrada.lerEntradaString(CoresConsole.info("Digite o username do usuário a ser removido: "));
                if(!username.equals("0")){
                    gerenciadorUsuarios.removerAmizade(usuario.getId(), gerenciadorUsuarios.buscarPorUsername(username).getId());
                    System.out.println(CoresConsole.sucesso("Amigo removido com sucesso"));
                }
                continuar = false;
            }catch (Exception e){
                System.out.println(CoresConsole.aviso("Usuário não encontrado. Tente novamente ou digite 0 para voltar"));
            }
        }
    }
    private void verAmigos(){
        System.out.println(CoresConsole.titulo("=== Seus amigos ==="));
        if(usuario.getAmigos().isEmpty()){
            System.out.println(CoresConsole.aviso("Nenhum amigo adicionado"));
        }else{
            exibirUsuariosEncontrados(usuario.getAmigos());
        }
    }
    private void editarPerfil(){
        System.out.println(CoresConsole.titulo("=== Editar Perfil ==="));
        System.out.println(CoresConsole.info("Digite os novos dados (ou pressione ENTER para manter o valor atual):"));
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
                System.out.println(CoresConsole.sucesso("Perfil atualizado com sucesso!"));
                break;
            }catch (Exception e){
                System.out.println(CoresConsole.erro(e.getMessage()));
            }
        }
    }
    private void buscarUsuarios() {
        System.out.println(CoresConsole.titulo("=== Busca de Usuários ==="));
        try {
            String busca = LerEntrada.lerEntradaString(CoresConsole.info("Digite um nome ou username: "));

            // Busca de usuários
            List<Usuario> usuarios = gerenciadorUsuarios.buscarPorNome(busca);
            Usuario usuarioExato = gerenciadorUsuarios.buscarPorUsername(busca);

            // Combina resultados de busca por nome e username (evita duplicação)
            if (usuarioExato != null && !usuarios.contains(usuarioExato)) {
                usuarios.add(usuarioExato);
            }

            if (usuarios.isEmpty()) {
                System.out.println(CoresConsole.aviso("Nenhum usuário encontrado com o termo: " + busca));
                return;
            }

            // Exibe a lista de usuários encontrados
            exibirUsuariosEncontrados(usuarios);
            String opcao = LerEntrada.lerEntradaString(CoresConsole.info("Escolha o username do usuário para ver o perfil ou digite 0 para voltar: "));

            // Opção escolhida
            if (!opcao.equals("0")) {
                Usuario usuarioSelecionado = gerenciadorUsuarios.buscarPorUsername(opcao);
                if (usuarioSelecionado != null) {
                    verOutroPerfil(usuarioSelecionado);
                } else {
                    System.out.println(CoresConsole.aviso("Usuário com username '" + opcao + "' não encontrado."));
                }
            }
        } catch (UsuarioException e) {
            System.out.println(CoresConsole.erro("Erro ao buscar usuários: " + e.getMessage()));
        } catch (Exception e) {
            System.out.println(CoresConsole.erro("Erro inesperado: " + e.getMessage()));
        }
    }

    private void verFeedNoticias(){
        try{
            System.out.println(CoresConsole.titulo("=== Feed de Notícias ==="));

            List<Post> posts = gerenciadorPosts.listarPosts().stream()
                    .filter(post -> usuario.getAmigos().contains(post.getAutor()) || post.getAutor().equals(usuario))
                    .sorted((p1, p2) -> p2.getDataPublicacao().compareTo(p1.getDataPublicacao()))
                    .toList();

            if(!posts.isEmpty()){
                posts.forEach(System.out::println);
                int opcao = LerEntrada.lerEntradaInteira(CoresConsole.info("Digite o número do post para interagir ou pressione 0 para voltar."));

                if(opcao != 0){
                    interagirPost(opcao);
                }
            }else{
                System.out.println(CoresConsole.aviso("Não há posts no feed de notícias"));
            }


        }catch (Exception e){
            System.out.println(CoresConsole.erro(e.getMessage()));
        }
    }
    private void listarPorUsuario(){
       while(true){
           try{
               String username = LerEntrada.lerEntradaString(CoresConsole.info("Username do usuário: "));
               if(!username.equals("0")){
                   Usuario usuario = gerenciadorUsuarios.buscarPorUsername(username);
                   if(usuario != null){
                       List<Post> posts = gerenciadorPosts.listarPorUsuario(usuario.getId());
                       if(!posts.isEmpty()){
                           System.out.println(posts);
                       }else{
                           System.out.println(CoresConsole.aviso("Este usuário não possui posts"));
                       }
                   }else{
                       throw new UsuarioException("Username inválido");
                   }
               }
               break;
           }catch (Exception e){
               System.out.println(CoresConsole.erro(e.getMessage()));
               System.out.println(CoresConsole.aviso("Tente novamente ou digite 0 para voltar"));
           }
       }
    }
    private void interagirPost(int id){
       boolean continuar = true;

       while(continuar){
           System.out.println(CoresConsole.titulo("=== Detalhes do post ==="));
           Post post = gerenciadorPosts.buscarPorId(id);
           System.out.println("Autor: " + post.getAutor().getUsername());
           System.out.println("Conteúdo: " + post.getConteudo());
           System.out.println("Curtidas: " + post.getCurtidas().size());
           System.out.println("Comentários: " + post.getComentarios().size());
           System.out.println();
           System.out.println(CoresConsole.info("1. Ver curtidas. \n2. Ver comentários."));
           boolean estaCurtido = post.getCurtidas().contains(usuario);
           System.out.println(CoresConsole.info("3. " + (estaCurtido ? "Descurtir." : "Curtir")));
           System.out.println(CoresConsole.info("4. Comentar \n5. Voltar"));

           try{
               int opcao = LerEntrada.lerEntradaInteira(CoresConsole.info("Escolha uma opção: "));
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
                   default -> System.out.println(CoresConsole.aviso("Opção inválida"));
               }
           }catch (Exception e){
               System.out.println(CoresConsole.erro(e.getMessage()));
           }
       }
    }
    private void listarComentarios(Post post){
        System.out.println(CoresConsole.titulo("=== Comentarios ===="));
        post.getComentarios().forEach(System.out::println);
    }
    private void comentar(Post post){
        System.out.println(CoresConsole.titulo("=== Comentar ==="));
        try{
            String conteudo = LerEntrada.lerEntradaString(CoresConsole.info("Digite o comentário: "));

            gerenciadorPosts.comentar(new Comentario(usuario, conteudo, post));
            System.out.println(CoresConsole.sucesso("Comentário adicionado com sucesso!"));
        }catch (Exception e){
            System.out.println(CoresConsole.erro(e.getMessage()));
        }
    }
    private void excluirConta() {
        System.out.println(CoresConsole.titulo("=== Excluir Conta ==="));
        int opcao = LerEntrada.lerEntradaInteira(CoresConsole.aviso("Tem certeza que deseja excluir sua conta? (1 - Sim / 2 - Não): "));
        if (opcao == 1) {
            try {
                if(gerenciadorUsuarios.deletar(usuario.getId())){
                    usuario = null;
                    System.out.println(CoresConsole.sucesso("Conta excluída com sucesso!"));
                }else{
                    throw new UsuarioException("Erro ao excluir conta");
                }
            } catch(UsuarioException e){
                System.out.println(CoresConsole.erro(e.getMessage()));
            } catch (Exception e) {
                System.out.println(CoresConsole.erro("Erro ao excluir conta: " + e.getMessage()));
            }
        } else {
            System.out.println(CoresConsole.aviso("Operação cancelada."));
        }
    }
    private void excluirPost(){
        System.out.println(CoresConsole.titulo("=== Excluir Post ==="));
      try{
          int id = LerEntrada.lerEntradaInteira(CoresConsole.info("Escolha o id do post a ser excluído: "));
          Post post = gerenciadorPosts.buscarPorId(id);

          if(usuario.getPosts().contains(post)){
              int opcao = LerEntrada.lerEntradaInteira(CoresConsole.aviso("Tem certeza que deseja excluir o post? (1 - Sim / 2 - Não): "));
              if (opcao == 1) {
                  if (gerenciadorPosts.deletar(id)) {
                      usuario.getPosts().remove(post);
                      System.out.println(CoresConsole.sucesso("Post excluído com sucesso!"));
                  } else {
                      throw new PostException("Erro ao excluir post");
                  }
              }else{
                  System.out.println(CoresConsole.aviso("Operação cancelada!"));
              }
          }else{
              System.out.println(CoresConsole.aviso("ID inválido"));
          }

      }catch (Exception e){
          System.out.println(CoresConsole.erro(e.getMessage()));
      }
    }


}

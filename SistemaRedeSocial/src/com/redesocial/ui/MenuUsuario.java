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

/**
 * Classe responsável por gerenciar o menu do usuário logado.
 * Permite realizar diversas operações, como criar posts, gerenciar amizades,
 * visualizar posts de outros usuários, e editar ou excluir informações.
 */
public class MenuUsuario {
    private Usuario usuario; // Usuário atualmente logado
    private final GerenciadorPosts gerenciadorPosts; // Gerenciador de posts
    private final GerenciadorUsuarios gerenciadorUsuarios; // Gerenciador de usuários

    /**
     * Construtor da classe MenuUsuario.
     *
     * @param usuario            O usuário logado.
     * @param gerenciadorUsuarios O gerenciador responsável pelos usuários.
     * @param gerenciadorPosts    O gerenciador responsável pelos posts.
     */
    public MenuUsuario(Usuario usuario, GerenciadorUsuarios gerenciadorUsuarios, GerenciadorPosts gerenciadorPosts) {
        this.usuario = usuario;
        this.gerenciadorUsuarios = gerenciadorUsuarios;
        this.gerenciadorPosts = gerenciadorPosts;
    }
    /**
     * Exibe o menu principal com as opções disponíveis para o usuário logado.
     *
     * @throws InterruptedException Se houver interrupção na execução.
     */
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
           System.out.println("6. Ver Posts por Usuário");
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
    /**
     * Permite ao usuário criar um novo post.
     */
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
    /**
     * Exibe o perfil do usuário logado e permite realizar ações como editar ou excluir o perfil e posts.
     */
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
    /**
     * Exibe o perfil de outro usuário, permitindo enviar solicitação de amizade ou remover amizade existente.
     *
     * @param usuario O usuário cujo perfil será exibido.
     */
    private void verOutroPerfil(Usuario usuario){
        System.out.println(CoresConsole.titulo("=== Perfil de " + usuario.getUsername() + " ==="));

        System.out.println(usuario);

        boolean ehAmigo = this.usuario.getAmigos().contains(usuario);

        int opcao = LerEntrada.lerEntradaInteira(CoresConsole.aviso("1. " + (ehAmigo ? "Remover amizade" : "Enviar Solicitação de amizade") + "\n2. Voltar \nEscolha uma opção: "));
        if(opcao == 1){
            if(!ehAmigo){
                gerenciadorUsuarios.enviarSolicitacaoAmizade(this.usuario.getId(), usuario.getId());
            }else{
                gerenciadorUsuarios.removerAmizade(this.usuario.getId(), usuario.getId());
            }
        }
    }
    /**
     * Exibe uma lista de usuários encontrados.
     *
     * @param usuarios Lista de usuários a ser exibida.
     */
    private void exibirUsuariosEncontrados(List<Usuario> usuarios){
        usuarios.forEach(usuario1 -> System.out.println("Nome: " + usuario1.getNome() + " Username: " + usuario1.getUsername()));
    }
    /**
     * Permite ao usuário gerenciar suas amizades, incluindo enviar solicitações, visualizar solicitações pendentes e ver amigos.
     */
    private void gerenciarAmizades() {
        System.out.println(CoresConsole.titulo("=== Gerenciar Amizades ==="));
        boolean continuar = true;

        while (continuar) {
            int opcao = LerEntrada.lerEntradaInteira(CoresConsole.info(
                      "1. Enviar solicitação\n" +
                            "2. Ver solicitações pendentes\n" +
                            "3. Ver amigos\n" +
                            "4. Voltar\nEscolha uma opção: "
            ));

            switch (opcao) {
                case 1 -> enviarSolicitacaoAmizade();
                case 2 -> gerenciarSolicitacoesPendentes();
                case 3 -> verAmigos();
                case 4 -> continuar = false;
                default -> System.out.println(CoresConsole.aviso("Opção inválida!"));
            }
        }
    }
    /**
     * Envia uma solicitação de amizade para outro usuário.
     */
    private void enviarSolicitacaoAmizade() {
        try {
            System.out.println(CoresConsole.titulo("=== Enviar Solicitação ==="));
            String username = LerEntrada.lerEntradaString(CoresConsole.info("Digite o username do usuário: "));
            Usuario destinatario = gerenciadorUsuarios.buscarPorUsername(username);

            if (destinatario == null) {
                System.out.println(CoresConsole.erro("Usuário não encontrado."));
            } else if (usuario.getAmigos().contains(destinatario)) {
                System.out.println(CoresConsole.aviso("Você já é amigo deste usuário."));
            } else {
                gerenciadorUsuarios.enviarSolicitacaoAmizade(usuario.getId(), destinatario.getId());
            }
        } catch (Exception e) {
            System.out.println(CoresConsole.erro(e.getMessage()));
        }
    }
    /**
     * Permite ao usuário gerenciar suas solicitações de amizade pendentes.
     */
    private void gerenciarSolicitacoesPendentes() {
        System.out.println(CoresConsole.titulo("=== Solicitações de Amizade ==="));

        if (usuario.getSolicitacoesPendentes().isEmpty()) {
            System.out.println(CoresConsole.aviso("Você não tem solicitações pendentes."));
            return;
        }

        exibirUsuariosEncontrados(usuario.getSolicitacoesPendentes());
        String username = LerEntrada.lerEntradaString(CoresConsole.info(
                "Digite o username para aceitar/recusar ou digite 0 para voltar: "
        ));

        if (!username.equals("0")) {
            try {
                Usuario remetente = gerenciadorUsuarios.buscarPorUsername(username);

                int opcao = LerEntrada.lerEntradaInteira(CoresConsole.info(
                        "1. Aceitar\n2. Recusar\nEscolha uma opção: "
                ));

                if (opcao == 1) {
                    gerenciadorUsuarios.adicionarAmizade(usuario.getId(), remetente.getId());
                } else if (opcao == 2) {
                    gerenciadorUsuarios.recusarSolicitacaoAmizade(usuario.getId(), remetente.getId());
                } else {
                    System.out.println(CoresConsole.aviso("Opção inválida!"));
                }
            } catch (Exception e) {
                System.out.println(CoresConsole.erro(e.getMessage()));
            }
        }
    }
    /**
     * Exibe a lista de amigos do usuário.
     */
    private void verAmigos(){
        System.out.println(CoresConsole.titulo("=== Seus amigos ==="));
        if(usuario.getAmigos().isEmpty()){
            System.out.println(CoresConsole.aviso("Nenhum amigo adicionado"));
        }else{
            exibirUsuariosEncontrados(usuario.getAmigos());
        }
    }
    /**
     * Permite ao usuário editar seu perfil.
     */
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

    /**
     * Busca usuários com base no nome ou username
     */
    private void buscarUsuarios() {
        System.out.println(CoresConsole.titulo("=== Busca de Usuários ==="));
        try {
            String busca = LerEntrada.lerEntradaString(CoresConsole.info("Digite um nome ou username: "));

            List<Usuario> usuarios = gerenciadorUsuarios.buscarPorNome(busca);
            Usuario usuarioExato = gerenciadorUsuarios.buscarPorUsername(busca);

            if (usuarioExato != null && !usuarios.contains(usuarioExato)) {
                usuarios.add(usuarioExato);
            }

            if (usuarios.isEmpty()) {
                System.out.println(CoresConsole.aviso("Nenhum usuário encontrado com o termo: " + busca));
                return;
            }

            exibirUsuariosEncontrados(usuarios);
            String opcao = LerEntrada.lerEntradaString(CoresConsole.info("Escolha o username do usuário para ver o perfil ou digite 0 para voltar: "));

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

    /**
     * Exibe feed de notícias com os posts do usuário e de seus amigos
     */
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

    /**
     * Lista post por usuário com base no username
     */
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
    /**
     * Permite ao usuário interagir com um post, incluindo ver curtidas, comentários, curtir ou comentar.
     *
     * @param id O ID do post com o qual interagir.
     * @throws InterruptedException Se houver interrupção na execução.
     */
    private void interagirPost(int id) throws InterruptedException {
       boolean continuar = true;

       while(continuar){
           Thread.sleep(2000);
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

    /**
     * Lista comentários de um post
     * @param post post a ser exibido os comentários
     */
    private void listarComentarios(Post post){
        System.out.println(CoresConsole.titulo("=== Comentarios ===="));
        post.getComentarios().forEach(System.out::println);
    }

    /**
     * Permite o usuário comentar em um post
     * @param post post a ser comentado
     */
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

    /**
     * Exclui conta do usuário
     */
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
    /**
     * Exclui um post do usuário.
     */
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

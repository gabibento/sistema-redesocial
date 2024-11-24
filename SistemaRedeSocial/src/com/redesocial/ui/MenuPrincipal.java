package com.redesocial.ui;

import com.redesocial.exception.AutenticacaoException;
import com.redesocial.exception.UsuarioException;
import com.redesocial.exception.ValidacaoException;
import com.redesocial.gerenciador.GerenciadorPosts;
import com.redesocial.gerenciador.GerenciadorUsuarios;
import com.redesocial.modelo.Usuario;
import com.redesocial.utils.CoresConsole;
import com.redesocial.utils.LerEntrada;
import com.redesocial.utils.Validador;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

/**
 * Classe responsável por exibir e gerenciar o menu principal da aplicação.
 * Permite o cadastro de novos usuários, login, e acesso às funcionalidades
 * logadas através de um menu específico.
 */
public class MenuPrincipal {
    private final GerenciadorUsuarios gerenciadorUsuarios; // Gerenciador de usuários
    private final GerenciadorPosts gerenciadorPosts; // Gerenciador de posts

    /**
     * Construtor do MenuPrincipal.
     * Inicializa os gerenciadores de usuários e posts.
     */
    public MenuPrincipal() {
        gerenciadorUsuarios = new GerenciadorUsuarios();
        gerenciadorPosts = new GerenciadorPosts(gerenciadorUsuarios);
    }

    /**
     * Exibe o menu principal da aplicação.
     * O usuário pode cadastrar uma conta, fazer login ou sair.
     */
    public void exibirMenu() {
        boolean continuar = true;

        while (continuar) {
            System.out.println(CoresConsole.titulo("=== Menu Principal ==="));
            System.out.println("\n1. Cadastrar \n2. Fazer Login \n3. Sair");

            int opcao = LerEntrada.lerEntradaInteira(CoresConsole.info("Escolha uma opção: "));

            switch (opcao) {
                case 1 -> cadastrarUsuario();
                case 2 -> fazerLogin();
                case 3 -> continuar = false;
                default -> System.out.println(CoresConsole.aviso("Opção inválida"));
            }
        }
    }

    /**
     * Permite ao usuário cadastrar uma nova conta.
     * O cadastro requer informações válidas, como nome, username, email e senha.
     */
    private void cadastrarUsuario() {
        System.out.println(CoresConsole.titulo("=== Cadastrar ==="));

        String nome = obterEntradaValida("Digite seu nome: ", this::validarNome);
        String username = obterEntradaValida("Digite seu username: ", this::validarUsername);
        String email = obterEntradaValida("Digite seu email: ", this::validarEmail);
        String senha = obterEntradaValida("Digite sua senha: ", this::validarSenha);

        Usuario usuario = new Usuario(nome, username, email, BCrypt.hashpw(senha, BCrypt.gensalt()));
        try {
            gerenciadorUsuarios.cadastrar(usuario);
            System.out.println(CoresConsole.sucesso("Usuário cadastrado com sucesso!"));
            exibirMenuLogado(usuario);
        } catch (Exception e) {
            System.out.println(CoresConsole.erro("Erro ao cadastrar usuário: " + e.getMessage()));
        }
    }

    /**
     * Solicita e valida a entrada de dados do usuário.
     * Exibe mensagens de erro em caso de entrada inválida.
     *
     * @param mensagem  Mensagem exibida ao usuário.
     * @param validador Função de validação para verificar a entrada.
     * @return Entrada válida fornecida pelo usuário.
     */
    private String obterEntradaValida(String mensagem, Validador<String> validador) {
        while (true) {
            try {
                String entrada = LerEntrada.lerEntradaString(mensagem);
                validador.validar(entrada);
                return entrada;
            } catch (ValidacaoException e) {
                System.out.println(CoresConsole.erro("Erro: " + e.getMessage()));
            }
        }
    }

    /**
     * Permite ao usuário fazer login no sistema.
     * Verifica as credenciais fornecidas e exibe um menu logado em caso de sucesso.
     */
    private void fazerLogin() {
        System.out.println(CoresConsole.titulo("=== Login ==="));
        boolean logado = false;
        while (!logado) {
            try {
                String username = LerEntrada.lerEntradaString("Digite seu username: ");
                String senha = LerEntrada.lerEntradaString("Digite sua senha: ");
                Usuario usuario = autenticar(username, senha);
                logado = true;
                exibirMenuLogado(usuario);
            } catch (Exception e) {
                System.out.println(CoresConsole.erro(e.getMessage()));
                int opcao = LerEntrada.lerEntradaInteira(CoresConsole.info("1. Tentar novamente \n2. Voltar \nEscolha uma opção: "));

                if (opcao == 2) {
                    logado = true;
                }
            }
        }
    }

    /**
     * Exibe o menu específico para usuários logados.
     *
     * @param usuario Usuário logado.
     * @throws InterruptedException Se ocorrer uma interrupção durante a exibição.
     */
    private void exibirMenuLogado(Usuario usuario) throws InterruptedException {
        MenuUsuario menu = new MenuUsuario(usuario, gerenciadorUsuarios, gerenciadorPosts);
        menu.exibirMenu();
    }

    /**
     * Autentica o usuário com base em seu username e senha.
     *
     * @param username O username fornecido.
     * @param senha    A senha fornecida.
     * @return O objeto do usuário autenticado.
     * @throws UsuarioException Se as credenciais forem inválidas.
     */
    private Usuario autenticar(String username, String senha) {
        try {
            Optional<Usuario> usuarioEncontrado = Optional.ofNullable(gerenciadorUsuarios.buscarPorUsername(username));

            if (usuarioEncontrado.isEmpty()) {
                throw new AutenticacaoException("Usuário com username " + username + " não existe");
            }
            if (!BCrypt.checkpw(senha, usuarioEncontrado.get().getSenha())) {
                throw new AutenticacaoException("Senha incorreta");
            }
            return usuarioEncontrado.get();
        } catch (Exception e) {
            throw new UsuarioException("Erro ao fazer login: " + e.getMessage());
        }
    }

    /**
     * Valida o nome fornecido pelo usuário.
     *
     * @param nome O nome a ser validado.
     * @throws ValidacaoException Se o nome for vazio ou nulo.
     */
    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidacaoException("O nome não pode ser vazio.");
        }
    }

    /**
     * Valida o username fornecido pelo usuário.
     * Garante que o username não esteja vazio ou já em uso.
     *
     * @param username O username a ser validado.
     * @throws ValidacaoException Se o username for inválido.
     */
    private void validarUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidacaoException("O username não pode ser vazio.");
        }
        if (gerenciadorUsuarios.listarUsuarios().stream()
                .anyMatch(u -> u.getUsername().equals(username))) {
            throw new ValidacaoException("O username '" + username + "' já está em uso.");
        }
    }

    /**
     * Valida o email fornecido pelo usuário.
     * Garante que o email seja válido e único.
     *
     * @param email O email a ser validado.
     * @throws ValidacaoException Se o email for inválido.
     */
    private void validarEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new ValidacaoException("Email inválido.");
        }
        if (gerenciadorUsuarios.listarUsuarios().stream()
                .anyMatch(u -> u.getEmail().equals(email))) {
            throw new ValidacaoException("Já há uma conta com este email");
        }
    }

    /**
     * Valida a senha fornecida pelo usuário.
     * Garante que a senha tenha pelo menos 6 caracteres.
     *
     * @param senha A senha a ser validada.
     * @throws ValidacaoException Se a senha for inválida.
     */
    private void validarSenha(String senha) {
        if (senha == null || senha.length() < 6) {
            throw new ValidacaoException("A senha deve conter pelo menos 6 caracteres.");
        }
    }

    /**
     * Obtém o gerenciador de usuários.
     *
     * @return O gerenciador de usuários.
     */
    public GerenciadorUsuarios getGerenciadorUsuarios() {
        return gerenciadorUsuarios;
    }

    /**
     * Obtém o gerenciador de posts.
     *
     * @return O gerenciador de posts.
     */
    public GerenciadorPosts getGerenciadorPosts() {
        return gerenciadorPosts;
    }
}

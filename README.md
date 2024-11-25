# Sistema de Rede Social

## Descrição do Projeto

Este projeto é uma rede social simples desenvolvida em Java, onde usuários podem criar contas, fazer login, criar posts, enviar e aceitar solicitações de amizade, curtir ou comentar publicações. Toda a interação acontece através de um menu no console, com destaque em cores para melhorar a experiência.

Os dados dos usuários, posts, curtidas e comentários são armazenados em listas na memória, permitindo um gerenciamento rápido e simples durante a execução. O sistema conta com classes específicas para gerenciar usuários e posts, além de validações e tratamento de erros para garantir a consistência das informações.

## Funcionalidades

O sistema oferece as seguintes funcionalidades:

1. **Cadastrar Usuário**: Permite que novos usuários se registrem no sistema com nome, username, email e senha criptografada.
2. **Fazer Login**: Usuários podem acessar suas contas utilizando username e senha.
3. **Criar Post**: Usuários podem criar posts com conteúdo no sistema.
4. **Curtir Post**: Permite que usuários curtam publicações.
5. **Descurtir Post**: Usuários podem descurtir posts previamente curtidos.
6. **Comentar Post**: Usuários podem comentar em posts.
7. **Gerenciar Amizades**: Enviar solicitações de amizade, aceitar ou recusar solicitações, e remover amigos.
8. **Editar Perfil**: Usuários podem atualizar seus dados de perfil, como nome, email, username e senha.
9. **Excluir Conta**: Usuários podem excluir suas contas do sistema.
10. **Ver Feed de Notícias**: Exibe o feed de posts do usuário e de seus amigos.
11. **Buscar Usuários**: Permite buscar outros usuários por nome ou username.
12. **Ver Perfil de Outro Usuário**: Exibe o perfil de outros usuários, com opções de adicionar/remover amizade.
13. **Sair**: Finaliza a execução do programa.

## Instruções de Execução Passo a Passo
1. **Importar o Projeto para sua IDE**:
   - **IntelliJ IDEA**:
     1. Abra o IntelliJ IDEA.
     2. Clique em `File` -> `Open...` e selecione a pasta `RedeSocial`.
   - **Eclipse**:
     1. Abra o Eclipse.
     2. Clique em `File` -> `Import...` -> `Existing Projects into Workspace` e selecione a pasta `RedeSocial`.

2. **Configurar o JDK**:
   - Certifique-se de ter o **JDK 11 ou superior** instalado.
   - Configure o JDK na sua IDE:
     - **IntelliJ IDEA**: `File` -> `Project Structure` -> `Project` -> Selecione o JDK.
     - **Eclipse**: `Window` -> `Preferences` -> `Java` -> `Installed JREs` -> Selecione o JDK.

3. **Rodar o Projeto**:
   - No arquivo `Main.java` (`src/com/redesocial/Main.java`), clique com o botão direito e selecione **Run 'Main'**.

## Exemplos de Uso
1. Cadastro de um novo usuário
   
![{7A6AE645-338F-4F6F-AD43-4E7DA754EB8E}](https://github.com/user-attachments/assets/6b144d09-7b1f-4523-8484-189dfb364e98)
![{2BEA423F-B8A4-4F2E-B1C5-617E7C9B0744}](https://github.com/user-attachments/assets/229c4fe7-ed5d-49df-8de2-464766391f9d)

2. Criar um Post
   
![{C4A5F810-D7AE-4ED5-86CA-5A8A9A278A15}](https://github.com/user-attachments/assets/3f56a3ff-e9d7-41ab-8347-955aa920e1dd)


3. Enviar Solicitação de Amizade

![{8796C8E8-5D11-40DD-96E1-E56B099BAD08}](https://github.com/user-attachments/assets/3844a79d-6601-4749-8868-cb46a1d04009)

4. Aceitar Solicitação de Amizade

![{2BB8B52E-DF13-4435-8E15-FB95F7BD136D}](https://github.com/user-attachments/assets/636b7ec9-98f3-4ddb-a820-1daa70bdfc92)


5. Ver Feed de Notícias

![{324A9164-4A6E-448B-B782-CCC8B156A916}](https://github.com/user-attachments/assets/e66bb96b-d107-42b1-8b15-72b7232aedec)


6. Adicionar comentário
   
![{F858DEE3-D073-43EC-8DE3-54961E90D571}](https://github.com/user-attachments/assets/acf3b17d-7a21-49e7-84e4-2d3069c4c82a)

     
## Decisões de Implementação
1. Estrutura de Classes
   
O projeto segue uma arquitetura organizada e modular, com as classes distribuídas em pacotes de acordo com suas responsabilidades.
```
  com.redesocial/
     ├── modelo/ # Classes de modelo/entidades
     ├── gerenciador/ # Classes de gerenciamento de dados
     ├── ui/ # Interface com usuário (console)
     ├── util/ # Classes utilitárias
     └── exception/ # Exceções personalizadas
```

- Entidades:
   - Usuario: Armazena informações do usuário, como nome, email, amigos, posts e solicitações de amizade.
   - Post: Representa publicações feitas pelos usuários, com recursos de curtidas e comentários.
   - Comentario: Permite interação nos posts, armazenando o autor e conteúdo do comentário.

- Gerenciadores:
   - GerenciadorUsuarios: Gerencia usuários, realizando operações como cadastro, login e gerência de amizades.
   - GerenciadorPosts: Gerencia posts, permitindo ações como criar, curtir, descurtir e comentar.

- Menus:
   - MenuPrincipal: Apresenta as opções de cadastro, login e acesso ao sistema.
   - MenuUsuario: Exibe o menu após o login, permitindo interações como criação de posts e gerenciamento de amigos.

2. Uso de Interfaces Funcionais
   
- A interface Validador foi usada para aplicar validações em dados inseridos pelo usuário, como nome, username e senha. A utilização de uma interface funcional permite que a validação seja configurável e modular, facilitando a aplicação de diferentes regras de validação sem acoplar o código.

4. Armazenamento Simples em Memória
   
- Os dados dos usuários, posts e comentários são armazenados em listas dentro da memória. Este foi um design simplificado, adequado para a atividade, da qual tem como objetivo a prática de manipulações com listas.

6. Autenticação e Criptografia de Senha
   
- A autenticação é realizada verificando o nome de usuário e a senha do usuário. Para a segurança da senha, é utilizado o algoritmo de criptografia bcrypt, fornecido pela biblioteca BCrypt para garantir que as senhas armazenadas sejam seguras, evitando que sejam armazenadas em texto claro.

8. Interatividade no Console com Cores
   
- O uso de cores no console, através da classe CoresConsole, foi implementado para melhorar a interação com o usuário e tornar a experiência mais agradável, destacando mensagens de erro, sucesso e outros tipos de informações.

10. Validação e Tratamento de Exceções
    
- O sistema utiliza exceções personalizadas para tratar erros de forma clara e precisa:

   - `ValidacaoException`: Lançada quando os dados fornecidos pelo usuário são inválidos, como username já existente ou senha menor que 6 caracteres

   - `AutenticacaoException`: Usada em falhas de autenticação, como erro de username ou senha incorretos.

   - `UsuarioException`: Relacionada a erros ao manipular usuários, como ao adicionar ou buscar um usuário.

   - `PostException`: Captura erros relacionados a posts, como falhas na criação ou na interação com posts.

## Autor
<div align="left">
  <a href="https://github.com/gabibento">
    <img alt="Gabriella Maurea Bento" src="https://avatars.githubusercontent.com/u/143539144?v=4" width="115" style="border-radius:50%">
  </a>
  <br>
  <sub><b>Gabriella Maurea Bento</b></sub><br>
  <sub>RA: 1788213</sub><br>
</div>

## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes.

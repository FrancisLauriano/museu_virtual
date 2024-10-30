<h1>Museu Virtual de Personagens Históricos de Pernambuco</h1> 

<p align="center"> 
<img src="https://img.shields.io/static/v1?label=Java&message=22&color=3776AB&style=for-the-badge&logo=java"/> 
<img src="http://img.shields.io/static/v1?label=Draw.io&message=24.6.4&color=f08705&style=for-the-badge&logo=diagramsdotnet"/> 
<img src="http://img.shields.io/static/v1?label=MySQL&message=8.0.38&color=4479a1&style=for-the-badge&logo=mysql&logoColor=f5f5f5"/> 
<img src="http://img.shields.io/static/v1?label=Hibernate&message=6.6&color=2d3748&style=for-the-badge&logo=hibernate"/> 
<img src="http://img.shields.io/static/v1?label=Git&message=2.45.2&color=f05032&style=for-the-badge&logo=git"/> 
<img src="http://img.shields.io/static/v1?label=GitHub&message=2024&color=181717&style=for-the-badge&logo=github"/> 
<img src="http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=yellow&style=for-the-badge"/> 
<img src="http://img.shields.io/static/v1?label=License&message=MIT&color=green&style=for-the-badge"/> 
</p>

> Status do Projeto: :heavy_check_mark: (concluido) | :warning: (em desenvolvimento) | :x: (não iniciada)

### Tópicos 

:small_blue_diamond: [Descrição do projeto](#descrição-do-projeto-writing_hand) :heavy_check_mark:

:small_blue_diamond: [Objetivos do projeto](#objetivos-do-projeto-dart) :heavy_check_mark:

:small_blue_diamond: [Funcionalidades](#funcionalidades-video_game) :heavy_check_mark:

:small_blue_diamond: [Arquitetura do Backend](#Arquitetura-do-Backend-triangular_ruler-straight_ruler) :heavy_check_mark:

:small_blue_diamond: [Diagrama de Classes](#Diagrama-de-Classes) :heavy_check_mark:

:small_blue_diamond: [Casos de Uso](#diagrama-de-casos-de-uso-(UC)-technologist) :heavy_check_mark:

:small_blue_diamond: [Descrição das Telas do Sistema](#Descrição-das-Telas-do-Sistema-art) :heavy_check_mark:


:small_blue_diamond: [Linguagens, tecnologias, dependências e libs utilizadas](#linguagens-tecnologias-dependências-e-libs-utilizadas-hammer_and_wrench-gear-books)

:small_blue_diamond: [Desenvolvedores/Contribuintes](#desenvolvedorescontribuintes-octocat)

... 

## Descrição do projeto :writing_hand:

<p align="justify">
  O projeto Museu Virtual de Personagens Históricos de Pernambuco foi desenvolvido para atender à demanda de um professor de História que busca uma ferramenta interativa para realizar estudos e difundir o conhecimento histórico regional entre seus alunos e a comunidade.  
</p>


## Objetivos do projeto :dart:

<p align="justify">

**1. Preservar e divulgar a história e cultura local de Pernambuco:**
- Promover a memória de personagens históricos que contribuíram para o desenvolvimento cultural, social e político da região.
- Facilitar o acesso ao conhecimento histórico por meio de uma plataforma digital interativa.

**2. Facilitar o acesso de estudantes e professores a conteúdos históricos relevantes:**
- Oferecer uma alternativa prática e moderna ao material didático tradicional, permitindo que os usuários explorem personagens históricos de forma interativa e visual.
- Garantir o acesso ao conteúdo, especialmente para alunos de escolas em áreas com acesso limitado a museus e bibliotecas físicas.

**3. Incentivar o uso de tecnologias digitais no ensino da história regional:**
- Aplicar uma abordagem inovadora no ensino de história, utilizando uma interface gráfica interativa.
- Engajar alunos e professores em um ambiente de aprendizado prático e colaborativo, onde a tecnologia se torna uma ferramenta de pesquisa e ensino.

**4. Oferecer uma plataforma diferenciada para cadastro e consulta de personagens históricos:**
- Disponibilizar funcionalidades de gestão de personagens e usuários, permitindo que o conteúdo seja atualizado de forma dinâmica e eficiente por administradores.
  
</p>


## Funcionalidades :video_game:

### Funcionalidades para Administradores

**1. Autenticação e Login**:
- Administradores realizam login com e-mail e senha. Após o login, são redirecionados para um painel administrativo.

**2. Gestão de Personagens Históricos (CRUD):**
- Cadastrar Personagem: Inserir nome, biografia, tipo de personagem (Artista ou Político) e imagem.
- Listar Personagens: Exibir uma lista com todos os personagens cadastrados, incluindo seus detalhes.
- Atualizar Personagem: Modificar informações já cadastradas, como nome, biografia ou imagem.
- Deletar Personagem: Remover personagens que não são mais relevantes.

**3. Gestão de Usuários (CRUD):**
- Cadastrar Usuário: Criar novos usuários (administradores ou alunos) com nome, e-mail, senha e tipo de usuário.
- Listar Usuários: Exibir todos os usuários cadastrados, com detalhes como nome e tipo.
- Atualizar Usuário: Modificar dados dos usuários, incluindo nome e tipo.
- Deletar Usuário: Remover usuários do sistema

### Funcionalidades para Alunos

**1. Autenticação e Login:**
- Os alunos realizam login com e-mail e senha e são direcionados para uma interface específica para consulta.

**2. Consulta de Personagens:**
- Listar Personagens: Visualizar a lista completa de personagens históricos cadastrados.
- Buscar Personagem Específico: Consultar informações detalhadas sobre um personagem específico, incluindo nome, biografia e imagem.



## Arquitetura :triangular_ruler: :straight_ruler:

```plaintext
MuseuVirtual/
├── src/
│   ├── annotations/
│   │   ├── TipoPersonagem.java
│   │   └── TipoUsuario.java
│   │
│   ├── controller/
│   │   ├── PersonagemController.java
│   │   └── UsuarioController.java
│   │
│   ├── DAO/
│   │   ├── InstanceDAO.java
│   │   ├── PersonagemDAO.java
│   │   └── UsuarioDAO.java
│   │
│   ├── model/
│   │   ├── Personagem.java
│   │   └── Usuario.java
│   │
│   ├── service/
│   │   └── AuthService.java
│   │
│   ├── view/
│   │   ├── LoginView.java
│   │   ├── AdminPainelView.java
│   │   ├── AlunoPainelView.java
│   │   ├── AdicionarUsuarioDialog
│   │   ├── AdicionarPersonagemDialog.java
│   │   ├── AdicionarPersonagemDialog.java
│   │   ├── EditarUsuarioDialog
│   │   └── EditarPersonagemDialog.java
│   │
│   ├── validators/
│   │   ├── TipoPersonagemValidator.java
│   │   └── TipoUsuarioValidator.java
│   │
│   ├── utils/
│   │   ├── HibernateUtil.java
│   │   ├── ImageStorageUtil.java
|   |   ├── JPAUtils.java
│   │   └── PasswordUtil.java
│   │
│   │
│   └── Main.java
│
├── resources/
|   └── META-INF/
│       └── persistence.xml
│
└── pom.xml (Para gerenciamento com Maven)
```

...

## Diagrama de Classes


<img src="/diagramacao/DiagramaDeClassesMuseuVirtual.png">


...

## Diagrama de Casos de Uso (UC) :technologist:


<img src="/diagramacao/DiagramaDeCasosDeUsoMuseuVirtual.png">



## Descrição das Telas do Sistema :art:
1. Tela de Login
- Descrição:
  - O usuário (aluno ou administrador) acessa esta tela para entrar no sistema.
  - Os campos incluem:
  - Email: Campo de texto para o e-mail.
  - Senha: Campo oculto para senha.
  - Botão "Login": Inicia o processo de autenticação.

- Interação:
  1. O usuário insere email e senha.
  2. Ao clicar no botão "Login", é feita a validação:
  - Se o login for bem-sucedido:
    - Administrador é redirecionado para o Painel Administrativo.
    - Aluno é direcionado para a Tela de Consulta de Personagens.
  - Se falhar, é exibida uma mensagem de erro informando "Email ou senha incorretos".

2. Painel Administrativo
- Descrição:
  - Esta tela é exclusiva para administradores e permite gerenciar usuários e personagens.
  - Há um banner de boas-vindas e abas para facilitar a navegação:
    - Aba "Usuários": Gerenciamento completo de usuários (CRUD).
    - Aba "Personagens": Gerenciamento de personagens históricos (CRUD).
  
- Interação:
  1. Aba "Usuários":
    - Listar todos os usuários, buscar por email, editar, excluir ou adicionar um novo usuário.
    - A escolha do tipo de usuário pode ser feita em um diálogo específico.
  2. Aba "Personagens":
    - Listar personagens, buscar por nome, adicionar, editar ou excluir personagens existentes.
  3. Botão "Sair" no canto superior direito permite que o administrador faça logout e volte para a tela de login.
  
3. Painel do Aluno
- Descrição:
  - Esta tela é destinada aos alunos para consultar os personagens históricos cadastrados.
  - Contém um banner com boas-vindas e uma aba para listar e buscar personagens.

- Interação:
  1. Botões de Ação:
    - Listar Personagens: Exibe todos os personagens no sistema.
    - Listar por Tipo: Abre um diálogo para o aluno escolher um tipo e listar os personagens correspondentes.
    - Buscar Personagem: Permite ao aluno buscar um personagem específico pelo nome.
  2. Tabela: Exibe os detalhes dos personagens encontrados.

4. Tela de Adicionar Personagem
- Descrição:
  - Permite que o administrador adicione um novo personagem ao sistema, inserindo:
    - Nome, Biografia, Tipo, e Imagem.

- Interação:
  1. O administrador preenche os campos obrigatórios.
  2. A imagem pode ser selecionada através de um selecionador de arquivos com visualização em miniatura.
  3. Ao clicar em "Adicionar", o personagem é salvo no sistema.

5. Tela de Editar Personagem
- Descrição:
  - Permite editar informações de um personagem existente.

- Interação:
  1. O administrador pode modificar nome, biografia, tipo e imagem.
  2. Após salvar, o sistema atualiza o personagem e exibe uma mensagem de confirmação.

6. Tela de Adicionar Usuário
- Descrição:
  - Permite cadastrar novos usuários, definindo:
    - Nome, Email, Senha, e Tipo (administrador ou aluno).

- Interação:
  1. O administrador insere os dados necessários e salva o novo usuário.
  2. Caso o email já esteja em uso, uma mensagem de erro é exibida.

7. Tela de Editar Usuário
- Descrição:
  - Permite atualizar as informações de um usuário existente.

- Interação:
  1. O administrador modifica os dados e salva as alterações.
  2. Se um novo email for inserido e já estiver em uso, o sistema exibe uma mensagem de erro.

**Fluxo Geral de Interação:**
  1. Usuário realiza login.
  2. Administrador acessa o painel com abas para gerenciar usuários e personagens.
  3. Aluno acessa a tela de consulta para listar ou buscar personagens.
  4. A partir das interfaces administrativas, personagens e usuários podem ser criados, atualizados, ou excluídos.

... 

## Linguagens, tecnologias, dependências e libs utilizadas :hammer_and_wrench: :gear: :books:


- [Java](https://docs.oracle.com/en/java/)
- [Draw.io](https://www.drawio.com/)
- [MySQL](https://dev.mysql.com/doc/)
- [Hibernate](https://hibernate.org/orm/)
- [Jakarta](https://jakarta.ee/learn/docs/jakartaee-tutorial/current/index.html)
- [Javax Swing](https://docs.oracle.com/javase/8/docs/api/index.html?javax/swing/package-summary.html)
- [Git](https://git-scm.com/downloads)
- [GitHub](https://github.com/)

...

## Desenvolvedores/Contribuintes :octocat:

Time responsável pelo desenvolvimento do projeto
| [<img src="https://avatars.githubusercontent.com/u/130801505?v=4" width=115><br><sub>Francis Lauriano</sub>](https://github.com/FrancisLauriano) 
| :---: 


## Licença 

The [MIT License]() (MIT)

Copyright :copyright: 2024 - Museu Virtual de Personagens Históricos de Pernambuco

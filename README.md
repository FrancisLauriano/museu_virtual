<h1>Museu Virtual de Personagens Históricos de Pernambuco</h1> 

<p align="center"> 
<img src="https://img.shields.io/static/v1?label=Java&message=8&color=3776AB&style=for-the-badge&logo=java"/> 
<img src="http://img.shields.io/static/v1?label=Draw.io&message=24.6.4&color=f08705&style=for-the-badge&logo=diagramsdotnet"/> 
<img src="http://img.shields.io/static/v1?label=Firebase&message=10.13.0&color=DD2C00&style=for-the-badge&logo=firebase"/> 
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

:small_blue_diamond: [Casos de Uso](#casos-de-uso-technologist) :warning:

:small_blue_diamond: [Arquitetura do Backend](#Arquitetura-do-Backend-triangular_ruler-straight_ruler) :warning:

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
- Cadastrar Personagem: Inserir nome, biografia, tipo de personagem (Artista ou Político) e imagem. O upload da imagem é realizado no Firebase.
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



## Casos de Uso :technologist:

**Diagrama de Caso de USO (UC)**

<img src="">


## Arquitetura do Backend :triangular_ruler: :straight_ruler:

```plaintext
/museu-virtual-personagens-historicos
│
├── /src
│   ├── /main
│   │   ├── /java
│   │   │   └── /com/museuvirtual
│   │   │       ├── /controllers
│   │   │       │   ├── PersonagemController.java
│   │   │       │   └── UsuarioController.java
│   │   │       ├── /models
│   │   │       │   ├── PersonagemHistorico.java
│   │   │       │   ├── Artista.java
│   │   │       │   ├── Politico.java
│   │   │       │   └── Usuario.java
│   │   │       ├── /repositories
│   │   │       │   ├── PersonagemRepository.java
│   │   │       │   └── UsuarioRepository.java
│   │   │       ├── /views
│   │   │       │   ├── PersonagemView.java
│   │   │       │   ├── UsuarioView.java
│   │   │       │   └── LoginView.java
│   │   │       ├── /utils
│   │   │       │   ├── FirebaseUtil.java
│   │   │       │   └── HibernateUtil.java
│   │   └── /resources
│   │       └── META-INF/persistence.xml
└── /pom.xml
```

... 



## Linguagens, tecnologias, dependências e libs utilizadas :hammer_and_wrench: :gear: :books:


- [Java](https://docs.oracle.com/en/java/)
- [Draw.io](https://www.drawio.com/)
- [Firebase](https://firebase.google.com/)
- [MySQL](https://dev.mysql.com/doc/)
- [Hibernate](https://hibernate.org/orm/)
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

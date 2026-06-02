# Sistema de Pareceres Escolares

## Introdução

Este projeto é o trabalho final da disciplina de Desenvolvimento de Software III. O trabalho foi desenvolvido utilizando as ferramentas Java Server Faces (JSF), PrimeFaces e o template Manhattan, baseando-se nos conceitos e práticas aprendidos durante as aulas.

## Ideia

O projeto consiste em um sistema web para facilitar a criação de pareceres escolares para professores do ensino fundamental. O objetivo é centralizar e organizar o processo de elaboração de pareceres individuais por aluno, permitindo que professores registrem avaliações, atividades e pareceres de forma prática e eficiente.

## Funcionalidades previstas

- Login e autenticação de professores
- Cadastro e gerenciamento de turmas
- Cadastro e gerenciamento de alunos
- Registro de atividades individuais por aluno
- Criação e gerenciamento de pareceres escolares
- Filtro administrativo com controle de sessão

## Tecnologias utilizadas

| Tecnologia | Descrição |
|---|---|
| Java EE / Jakarta EE 10 | Plataforma base da aplicação |
| Java Server Faces (JSF) | Framework MVC para desenvolvimento web |
| PrimeFaces 13 | Biblioteca de componentes visuais para JSF |
| Template Manhattan 7 | Template de layout para PrimeFaces |
| GlassFish 7 | Servidor de aplicação Java EE |
| PostgreSQL | Banco de dados relacional |
| JPA / EclipseLink | Persistência de dados com mapeamento objeto-relacional |
| Maven | Gerenciador de dependências e build |
| NetBeans 21 | IDE de desenvolvimento |
| JDK 21 | Plataforma Java |

## Modelo de dados

O sistema é composto pelas seguintes entidades e relacionamentos:

### Entidades

**Professor**
- `id` — identificador único
- `nome` — nome do professor
- `email` — e-mail para login
- `senha` — senha de acesso

**Turma**
- `id` — identificador único
- `serie` — série/ano da turma
- `dataInicio` — data de início do ano letivo

**Aluno**
- `id` — identificador único
- `nome` — nome do aluno
- `dataNascimento` — data de nascimento

**Atividade**
- `id` — identificador único
- `descricao` — descrição da atividade
- `conteudo` — conteúdo trabalhado na atividade

**Parecer**
- `id` — identificador único
- `conteudo` — texto do parecer
- `periodo` — período/bimestre de referência
- `dataEmissao` — data de emissão do parecer

### Relacionamentos

```
Professor  (1,n) ──── (1,1)  Turma
Turma      (1,1) ──── (1,n)  Aluno
Aluno      (1,1) ──── (1,n)  Atividade
Aluno      (1,1) ──── (1,n)  Parecer
Professor  (1,1) ──── (1,n)  Parecer
```

- Um **professor** pode ser responsável por várias turmas
- Uma **turma** possui vários alunos
- Um **aluno** possui várias atividades, cada atividade pertence a um único aluno
- Um **aluno** pode ter vários pareceres ao longo do tempo
- Cada **parecer** é escrito por exatamente um professor

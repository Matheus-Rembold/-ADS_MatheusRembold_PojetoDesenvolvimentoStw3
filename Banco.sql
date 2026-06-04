CREATE TABLE professor(
    id SERIAL PRIMARY KEY,
    nome VARCHAR(60) NOT NULL,
    senha VARCHAR(60) NOT NULL,
    dataNascimento DATE NOT NULL
);

CREATE TABLE turma(
    id SERIAL PRIMARY KEY,
    dataInicio DATE NOT NULL,
    serie INT NOT NULL,
    idProfessor INT NOT NULL,
    FOREIGN KEY (idProfessor) REFERENCES professor(id)
);

CREATE TABLE aluno(
    id SERIAL PRIMARY KEY,
    dataNascimento DATE NOT NULL,
    nome VARCHAR(100) NOT NULL,
    idTurma INT NOT NULL,
    FOREIGN KEY (idTurma) REFERENCES turma(id)
);

CREATE TABLE atividade(
    id SERIAL PRIMARY KEY,
    descricao VARCHAR(100),
    conteudo VARCHAR(1000),
    idAluno INT NOT NULL,          -- FK aqui, não no aluno
    FOREIGN KEY (idAluno) REFERENCES aluno(id)
);

CREATE TABLE parecer(
    id SERIAL PRIMARY KEY,
    dataEmissao DATE NOT NULL,
    conteudo VARCHAR(1000),
    periodo VARCHAR(20),           -- ex: "1° Bimestre"
    idAluno INT NOT NULL,          -- de quem é o parecer
    idProfessor INT NOT NULL,      -- quem escreveu
    FOREIGN KEY (idAluno) REFERENCES aluno(id),
    FOREIGN KEY (idProfessor) REFERENCES professor(id)
);
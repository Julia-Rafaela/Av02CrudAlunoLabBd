USE master 
DROP DATABASE CrudAluno

CREATE DATABASE CrudAluno 
GO 
USE CrudAluno

--CRUD PROFESSOR 

CREATE TABLE Professor (
    codigo INT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    PRIMARY KEY (codigo)
);


CREATE FUNCTION fn_professores()
RETURNS TABLE
AS
RETURN
(
    SELECT 
        p.codigo AS codigo,
        p.nome AS nome
    FROM Professor p
);


CREATE PROCEDURE GerenciarProfessor (
    @op VARCHAR(10),
    @codigo INT,
    @nome VARCHAR(100),
    @saida VARCHAR(100) OUTPUT
)
AS
BEGIN

    IF @op = 'I' 
    BEGIN
        IF @codigo IS NOT NULL AND @nome IS NOT NULL 
        BEGIN
            IF NOT EXISTS (SELECT 1 FROM Professor WHERE codigo = @codigo)
            BEGIN
                INSERT INTO Professor (codigo, nome)
                VALUES (@codigo, @nome);
                
                SET @saida = 'Professor inserido com sucesso.';
            END
            ELSE
            BEGIN
                SET @saida = 'O código do professor já existe na base de dados.';
            END
        END
        ELSE
        BEGIN
            SET @saida = 'Parâmetros incompletos para inserção.';
        END
    END
    ELSE IF @op = 'U' 
    BEGIN
        IF @codigo IS NOT NULL AND @nome IS NOT NULL 
        BEGIN
            IF EXISTS (SELECT 1 FROM Professor WHERE codigo = @codigo)
            BEGIN
                UPDATE Professor
                SET nome = @nome
                WHERE codigo = @codigo;

                SET @saida = 'Professor atualizado com sucesso.';
            END
            ELSE
            BEGIN
                SET @saida = 'O código do professor não foi encontrado na base de dados.';
            END
        END
        ELSE
        BEGIN
            SET @saida = 'Parâmetros incompletos para atualização.';
        END
    END
    ELSE IF @op = 'D'
    BEGIN
        IF @codigo IS NOT NULL
        BEGIN
            IF EXISTS (SELECT 1 FROM Professor WHERE codigo = @codigo)
            BEGIN
                DELETE FROM Professor WHERE codigo = @codigo;
                SET @saida = 'Professor excluído com sucesso.';
            END
            ELSE
            BEGIN
                SET @saida = 'O código do Professor não foi encontrado na base de dados.';
            END
        END
        ELSE
        BEGIN
            SET @saida = 'Parâmetros incompletos para exclusão.';
        END
    END
    ELSE
    BEGIN
        SET @saida = 'Operação inválida.';
    END
END;


DECLARE @saida VARCHAR(100);
EXEC GerenciarProfessor 'I', 1, 'João Silva', @saida OUTPUT;
PRINT @saida;

SELECT * FROM Professor

--CRUD DE CURSO, COM VERIFICAÇÃO DE CODIGO;

SELECT * FROM Curso

CREATE TABLE Curso (
    codigo INT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    carga_horaria INT NOT NULL,
    sigla VARCHAR(10) NOT NULL,
    nota_enade DECIMAL(5, 2)
);

CREATE FUNCTION fn_cursos()
RETURNS TABLE
AS
RETURN
(
    SELECT 
        c.codigo AS codigo,
        c.nome AS nome,
		c.carga_horaria AS carga_horaria,
		c.sigla AS sigla,
		c.nota_enade AS nota_enade
    FROM Curso c
);




CREATE PROCEDURE GerenciarCurso (
    @op VARCHAR(10),
    @codigo INT,
    @nome VARCHAR(100),
    @carga_horaria INT,
    @sigla VARCHAR(10),
    @nota_enade DECIMAL(5, 2),
    @saida VARCHAR(100) OUTPUT
)
AS
BEGIN
 IF @codigo < 0 OR @codigo > 100
    BEGIN
        SET @saida = 'O código do curso deve estar entre 0 e 100.';
        RETURN; 
    END
    IF @op = 'I' 
    BEGIN
        IF @codigo IS NOT NULL AND @nome IS NOT NULL AND @carga_horaria IS NOT NULL AND @sigla IS NOT NULL
        BEGIN
            IF NOT EXISTS (SELECT 1 FROM Curso WHERE codigo = @codigo)
            BEGIN
                INSERT INTO Curso (codigo, nome, carga_horaria, sigla, nota_enade)
                VALUES (@codigo, @nome, @carga_horaria, @sigla, @nota_enade);
                
                SET @saida = 'Curso inserido com sucesso.';
            END
            ELSE
            BEGIN
                SET @saida = 'O código do curso já existe na base de dados.';
            END
        END
        ELSE
        BEGIN
            SET @saida = 'Parâmetros incompletos para inserção.';
        END
    END
    ELSE IF @op = 'U' 
    BEGIN
        IF @codigo IS NOT NULL AND @nome IS NOT NULL AND @carga_horaria IS NOT NULL AND @sigla IS NOT NULL
        BEGIN
            IF EXISTS (SELECT 1 FROM Curso WHERE codigo = @codigo)
            BEGIN
                UPDATE Curso
                SET nome = @nome,
                    carga_horaria = @carga_horaria,
                    sigla = @sigla,
                    nota_enade = @nota_enade
                WHERE codigo = @codigo;

                SET @saida = 'Curso atualizado com sucesso.';
            END
            ELSE
            BEGIN
                SET @saida = 'O código do curso não foi encontrado na base de dados.';
            END
        END
        ELSE
        BEGIN
            SET @saida = 'Parâmetros incompletos para atualização.';
        END
    END
    ELSE IF @op = 'D'
    BEGIN
        IF @codigo IS NOT NULL
        BEGIN
            IF EXISTS (SELECT 1 FROM Curso WHERE codigo = @codigo)
            BEGIN
                DELETE FROM Curso WHERE codigo = @codigo;
                SET @saida = 'Curso excluído com sucesso.';
            END
            ELSE
            BEGIN
                SET @saida = 'O código do curso não foi encontrado na base de dados.';
            END
        END
        ELSE
        BEGIN
            SET @saida = 'Parâmetros incompletos para exclusão.';
        END
    END
    ELSE
    BEGIN
        SET @saida = 'Operação inválida.';
    END
END;


DECLARE @saida VARCHAR(100);
EXEC GerenciarCurso 'I', 100, 'Engenharia de Software', 4000, 'ES', 4.5, @saida OUTPUT;
PRINT @saida;

DECLARE @saida VARCHAR(100);
EXEC GerenciarCurso 'U', 101, 'Engenharia de Software', 4200, 'ES', 4.8, @saida OUTPUT;
PRINT @saida;

DECLARE @saida VARCHAR(100);
EXEC GerenciarCurso 'D', 101, NULL, NULL, NULL, NULL, @saida OUTPUT;
PRINT @saida;



-- CRUD DE ALUNO, COM VALIDAÇÃO DE CPF, IDADE, CRIA O RA E FORMATA O TELEFONE INSERIDO;

CREATE FUNCTION fn_alunos()
RETURNS TABLE
AS
RETURN
(
    SELECT A.ra, A.cpf, A.nome, A.nome_social, A.data_nascimento, A.email_pessoal, A.email_corporativo, A.conclusao_segundo_grau,
	A.instituicao_conclusao, A.pontuacao_vestibular, A.posicao_vestibular, A.ano_ingresso, A.ano_limite_graduacao, 
	A.semestre_ingresso, A.semestre_limite_graduacao, A.curso AS codigoCurso, C.nome AS nomeCurso 
    FROM Aluno A 
    INNER JOIN Curso C ON C.codigo = A.curso
)

CREATE TABLE Aluno (
    RA INT,
    CPF CHAR(11) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    nome_social VARCHAR(100),
    data_nascimento VARCHAR(10) NOT NULL,
    email_pessoal VARCHAR(100) NOT NULL,
    email_corporativo VARCHAR(100) NOT NULL,
    conclusao_segundo_grau VARCHAR(10) NOT NULL,
    instituicao_conclusao VARCHAR(100) NOT NULL,
    pontuacao_vestibular DECIMAL(5,2) NOT NULL,
    posicao_vestibular INT NOT NULL,
    ano_ingresso INT NOT NULL,
    ano_limite_graduacao INT NOT NULL,
    semestre_ingresso INT NOT NULL,
    semestre_limite_graduacao INT NOT NULL,
	curso INT
	PRIMARY KEY (cpf)
	FOREIGN KEY (curso) REFERENCES Curso(codigo)
)



CREATE PROCEDURE ValidarCPF (
    @CPF CHAR(11),
    @cpfValido CHAR(10) OUTPUT
)
AS
BEGIN
    DECLARE @primeiroDigito INT;
    DECLARE @segundoDigito INT;
    DECLARE @i INT;
    DECLARE @soma INT;
    DECLARE @resto INT;

    SET @cpfValido = 'Válido';

    IF @CPF NOT LIKE '%[^0-9]%'
    BEGIN
        SET @soma = 0;
        SET @i = 10;
        WHILE @i >= 2
        BEGIN
            SET @soma = @soma + (CAST(SUBSTRING(@CPF, 11 - @i, 1) AS INT) * @i);
            SET @i = @i - 1;
        END;
        SET @resto = @soma % 11;
        SET @primeiroDigito = IIF(@resto < 2, 0, 11 - @resto);

        SET @soma = 0;
        SET @i = 11;
        SET @CPF = @CPF + CAST(@primeiroDigito AS NVARCHAR(1));
        WHILE @i >= 2
        BEGIN
            SET @soma = @soma + (CAST(SUBSTRING(@CPF, 12 - @i, 1) AS INT) * @i);
            SET @i = @i - 1;
        END;
        SET @resto = @soma % 11;
        SET @segundoDigito = IIF(@resto < 2, 0, 11 - @resto);

        IF LEN(@CPF) <> 11 OR @CPF IN ('00000000000', '11111111111', '22222222222', '33333333333', '44444444444', '55555555555', '66666666666', '77777777777', '88888888888', '99999999999')
            OR SUBSTRING(@CPF, 10, 1) != CAST(@primeiroDigito AS NVARCHAR(1)) OR SUBSTRING(@CPF, 11, 1) != CAST(@segundoDigito AS NVARCHAR(1))
        BEGIN
            SET @cpfValido = 'Inválido';
        END;
    END
    ELSE
    BEGIN
        SET @cpfValido = 'Inválido';
    END;
END;


CREATE PROCEDURE validarIdade (
    @data_nascimento VARCHAR(10)
)
AS
BEGIN
    DECLARE @idade INT;
    SET @idade = DATEDIFF(YEAR, CAST(@data_nascimento AS DATE), GETDATE());
    IF @idade < 16
    BEGIN
        RAISERROR('Idade deve ser igual ou superior a 16 anos', 16, 1);
    END;
END;


CREATE PROCEDURE gerarRA (
    @ano_ingresso INT,
    @semestre_ingresso INT,
    @ra VARCHAR(10) OUTPUT
)
AS
BEGIN
    DECLARE @parte_numerica VARCHAR(4);
    SET @parte_numerica = RIGHT('000' + CAST(FLOOR(RAND() * 10000) AS VARCHAR(4)), 4);
    SET @ra = CONCAT(CAST(@ano_ingresso AS VARCHAR(4)), CAST(@semestre_ingresso AS VARCHAR(2)), @parte_numerica);
END;

CREATE PROCEDURE GerenciarMatricula (
    @op VARCHAR(100),
    @CPF CHAR(11),
    @nome VARCHAR(100),
    @nome_social VARCHAR(100),
    @data_nascimento VARCHAR(10),
    @email_pessoal VARCHAR(100),
    @email_corporativo VARCHAR(100),
    @conclusao_segundo_grau VARCHAR(10),
    @instituicao_conclusao VARCHAR(100),
    @pontuacao_vestibular DECIMAL(5,2),
    @posicao_vestibular INT,
    @ano_ingresso INT,
    @semestre_ingresso INT,
    @semestre_limite_graduacao INT,
    @curso INT,
    @saida VARCHAR(100) OUTPUT
)
AS
BEGIN
    DECLARE @cpf_valido CHAR(10);
    DECLARE @idade INT;
    DECLARE @ano_limite_graduacao INT;
    DECLARE @ra VARCHAR(10);
    DECLARE @mensagem VARCHAR(100);
    DECLARE @curso_existe INT;

    SELECT @curso_existe = COUNT(*) FROM Curso WHERE codigo = @curso;

    IF @curso_existe = 0
    BEGIN
        SET @saida = 'O curso informado não existe na base de dados.';
        RETURN;
    END

    IF @op = 'I'
    BEGIN
        IF @CPF IS NOT NULL AND @nome IS NOT NULL AND @data_nascimento IS NOT NULL AND @ano_ingresso IS NOT NULL AND @semestre_ingresso IS NOT NULL
        BEGIN
            EXEC ValidarCPF @CPF, @cpf_valido OUTPUT;

            SET @idade = DATEDIFF(YEAR, CAST(@data_nascimento AS DATE), GETDATE());
            IF @cpf_valido = 'Válido' AND @idade >= 16
            BEGIN
                SET @ano_limite_graduacao = @ano_ingresso + 5;
                EXEC gerarRA @ano_ingresso, @semestre_ingresso, @ra OUTPUT;

                IF @ano_limite_graduacao >= YEAR(GETDATE()) OR (@ano_limite_graduacao = YEAR(GETDATE()) AND @semestre_limite_graduacao >= CASE WHEN MONTH(GETDATE()) <= 6 THEN 1 ELSE 2 END)
                BEGIN
                    -- Inserir o aluno na tabela Aluno
                    INSERT INTO Aluno (RA, CPF, nome, nome_social, data_nascimento, email_pessoal, email_corporativo, conclusao_segundo_grau,
                    instituicao_conclusao, pontuacao_vestibular, posicao_vestibular, ano_ingresso, ano_limite_graduacao, semestre_ingresso, semestre_limite_graduacao, curso)
                    VALUES (@ra, @CPF, @nome, @nome_social, @data_nascimento, @email_pessoal, @email_corporativo,
                    @conclusao_segundo_grau, @instituicao_conclusao, @pontuacao_vestibular, @posicao_vestibular,
                    @ano_ingresso, @ano_limite_graduacao, @semestre_ingresso, @semestre_limite_graduacao, @curso);

              DECLARE @codigo_matricula INT;
              SELECT @codigo_matricula = ISNULL(MAX(codigo) + 1, 1) FROM Matricula;

            WHILE EXISTS (SELECT 1 FROM Matricula WHERE codigo = @codigo_matricula)
            BEGIN
   
           SET @codigo_matricula = @codigo_matricula + 1;
           END

          DECLARE @disciplina INT;
          DECLARE @codigo_matricula_atual INT; 

        DECLARE disciplina_cursor CURSOR FOR 
        SELECT disciplina FROM Grade WHERE curso = @curso;

       OPEN disciplina_cursor;
       FETCH NEXT FROM disciplina_cursor INTO @disciplina;

        WHILE @@FETCH_STATUS = 0
        BEGIN
    
    SELECT @codigo_matricula_atual = ISNULL(MAX(codigo) + 1, @codigo_matricula) FROM Matricula;

   
    INSERT INTO Matricula (aluno, disciplina, data_m, status_m)
    VALUES ( @CPF, @disciplina, GETDATE(), 'Cursando');

    
    FETCH NEXT FROM disciplina_cursor INTO @disciplina;
  END

    CLOSE disciplina_cursor;
    DEALLOCATE disciplina_cursor;


                    SET @saida = 'Matrícula inserida com sucesso.';
                END
                ELSE
                BEGIN
                    SET @saida = 'Data limite de graduação inválida.';
                END
            END
            ELSE
            BEGIN
                SET @saida = 'CPF inválido ou idade inferior a 16 anos. Matrícula não realizada.';
            END
        END
        ELSE
        BEGIN
            SET @saida = 'Parâmetros incompletos para a operação de inserção.';
        END
    END
    ELSE IF @op = 'U'
    BEGIN
        IF @CPF IS NOT NULL AND @nome IS NOT NULL AND @data_nascimento IS NOT NULL AND @ano_ingresso IS NOT NULL AND @semestre_ingresso IS NOT NULL
        BEGIN
            EXEC ValidarCPF @CPF, @cpf_valido OUTPUT;

            SET @idade = DATEDIFF(YEAR, CAST(@data_nascimento AS DATE), GETDATE());

            IF @cpf_valido = 'Válido' AND @idade >= 16
            BEGIN
                SET @ano_limite_graduacao = @ano_ingresso + 5;

                IF @ano_limite_graduacao >= YEAR(GETDATE()) OR (@ano_limite_graduacao = YEAR(GETDATE()) AND @semestre_limite_graduacao >= CASE WHEN MONTH(GETDATE()) <= 6 THEN 1 ELSE 2 END)
                BEGIN
                    IF EXISTS (SELECT 1 FROM Aluno WHERE CPF = @CPF)
                    BEGIN
                        UPDATE Aluno
                        SET nome = @nome,
                            nome_social = @nome_social,
                            data_nascimento = @data_nascimento,
                            email_pessoal = @email_pessoal,
                            email_corporativo = @email_corporativo,
                            conclusao_segundo_grau = @conclusao_segundo_grau,
                            instituicao_conclusao = @instituicao_conclusao,
                            pontuacao_vestibular = @pontuacao_vestibular,
                            posicao_vestibular = @posicao_vestibular,
                            ano_ingresso = @ano_ingresso,
                            ano_limite_graduacao = @ano_limite_graduacao,
                            semestre_ingresso = @semestre_ingresso,
                            semestre_limite_graduacao = @semestre_limite_graduacao,
                            curso = @curso
                        WHERE CPF = @CPF;

                        SET @saida = 'Matrícula atualizada com sucesso.';
                    END
                    ELSE
                    BEGIN
                        SET @saida = 'CPF não encontrado na base de dados.';
                    END
                END
                ELSE
                BEGIN
                    SET @saida = 'Data limite de graduação inválida.';
                END
            END
            ELSE
            BEGIN
                SET @saida = 'CPF inválido ou idade inferior a 16 anos. Atualização não realizada.';
            END
        END
        ELSE
        BEGIN
            SET @saida = 'Parâmetros incompletos para a operação de atualização.';
        END
    END
    ELSE IF @op = 'D'
    BEGIN
        IF @CPF IS NOT NULL
        BEGIN
            IF EXISTS (SELECT 1 FROM Aluno WHERE CPF = @CPF)
            BEGIN
                DELETE FROM Aluno WHERE CPF = @CPF;
                SET @saida = 'Matrícula excluída com sucesso.';
            END
            ELSE
            BEGIN
                SET @saida = 'CPF não encontrado na base de dados.';
            END
        END
        ELSE
        BEGIN
            SET @saida = 'Parâmetros incompletos para a operação de exclusão.';
        END
    END
    ELSE
    BEGIN
        SET @saida = 'Operação inválida.';
    END
END;


DECLARE @saida VARCHAR(100);
EXEC GerenciarMatricula 'U', '91345626053' , 'João da Silva', NULL, '2000-01-03', 'joao.silva@example.com', 'joao.corp@example.com', '11979699883', 'Completo', 'Escola A', 800.00, 1, 2022, 1, 2027, 101, @saida OUTPUT;
SELECT @saida AS Resultado;

DECLARE @saida VARCHAR(100);
EXEC GerenciarMatricula 'I', '91345626053', 'João da Silva', NULL, '2000-08-10', 'joao.silva@example.com', 'joao.corp@example.com','Completo', 'Escola A', 800.00, 1, 2022, 1, 2027, 100, @saida OUTPUT;
SELECT @saida AS Resultado;

DECLARE @saida VARCHAR(100);
EXEC GerenciarMatricula 'D', '91345626053' , 'João da Silva', NULL, '2000-01-01', 'joao.silva@example.com', 'joao.corp@example.com', '12345676543', 'Completo', 'Escola A', 800.00, 1, 2022, 1, 2027, 101, @saida OUTPUT;
SELECT @saida AS Resultado;


SELECT * FROM Aluno



--CRUD DE DESCIPLINA VERIFICANDO O HORARIO INSERIDO E DIA DA SEMANA;

CREATE TABLE Disciplina (
    codigo INT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    horas_inicio VARCHAR(07) NOT NULL,
	duracao INT NOT NULL,
	dia_semana VARCHAR(30) NOT NULL,
	professor INT NOT NULL
	FOREIGN KEY (professor) REFERENCES Professor(codigo)
)


CREATE FUNCTION fn_disciplinas()
RETURNS TABLE
AS
RETURN
(
    SELECT 
        D.codigo AS codigo,
        D.nome AS nome,
        D.horas_inicio AS horas_inicio,
        D.duracao AS duracao,
        D.dia_semana AS dia_semana,
		D.professor AS codigoProfessor,
        P.nome AS nomeProfessor
    FROM Disciplina D
    LEFT JOIN Professor P ON P.codigo = D.professor
);



CREATE PROCEDURE VerificarHorariosDisponiveis (
    @inicio VARCHAR(07),
    @duracao INT,
    @men VARCHAR(100) OUTPUT
)
AS
BEGIN
    DECLARE @fim TIME;

    IF @inicio NOT IN ('13:00', '16:40', '14:50') OR (@duracao != 2 AND @duracao != 4)
    BEGIN
        PRINT 'Horário de início ou duração inválidos.';
		SET @men = 'Invalido';
        RETURN;
    END;

    PRINT 'Os horários disponíveis são:';
    IF @inicio = '13:00' AND @duracao = 4
    BEGIN
        PRINT 'Iniciando às 13:00 com 4 aulas de duração (Até 16h30)';
		SET @men = 'Valido'
    END
    IF @inicio = '13:00' AND @duracao = 2
    BEGIN
        PRINT 'Iniciando às 13:00 com 2 aulas de duração (Até 14h40)';
		SET @men = 'Valido'
    END
    IF @inicio = '14:50' AND @duracao = 4
    BEGIN
        PRINT 'Iniciando às 14:50 com 4 aulas de duração (Até 19h30)';
		SET @men = 'Valido'
    END
    IF @inicio = '14:50' AND @duracao = 2
    BEGIN
        PRINT 'Iniciando às 14:50 com 2 aulas de duração (Até 16h30)';
		SET @men = 'Valido'
    END
    IF @inicio = '16:40' AND @duracao = 2
    BEGIN
        PRINT 'Iniciando às 16:40 com 2 aulas de duração (Até 18h20)';
		SET @men = 'Valido'
    END
END;

DECLARE @men VARCHAR(100);
EXEC VerificarHorariosDisponiveis '13:00', 4, @men OUTPUT;
PRINT @men; -- Deve imprimir 'Valido'

DECLARE @men VARCHAR(100);
EXEC VerificarHorariosDisponiveis '13:00', 2, @men OUTPUT;
PRINT @men; -- Deve imprimir 'Valido'

DECLARE @men VARCHAR(100);
EXEC VerificarHorariosDisponiveis '14:50', 4, @men OUTPUT;
PRINT @men; -- Deve imprimir 'Valido'

DECLARE @men VARCHAR(100);
EXEC VerificarHorariosDisponiveis '14:50', 2, @men OUTPUT;
PRINT @men; -- Deve imprimir 'Valido'

DECLARE @men VARCHAR(100);
EXEC VerificarHorariosDisponiveis '16:40', 2, @men OUTPUT;
PRINT @men; -- Deve imprimir 'Valido'




CREATE PROCEDURE GerenciarDisciplina (
    @op VARCHAR(100),
    @codigo INT,
    @nome VARCHAR(100),
    @horas_inicio VARCHAR(07),
    @duracao INT,
    @dia_semana VARCHAR(30),
    @professor INT,
    @saida VARCHAR(200) OUTPUT
)
AS
BEGIN
    IF @op = 'I'
    BEGIN
        IF @dia_semana NOT IN ('Segunda-feira', 'Terça-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sábado', 'Domingo')
        BEGIN
            SET @saida = 'Dia da semana inválido. Por favor, insira um dos seguintes valores: Segunda-feira, Terça-feira, Quarta-feira, Quinta-feira, Sexta-feira, Sábado, Domingo.';
            RETURN;
        END;

        DECLARE @men VARCHAR(100);

        EXEC VerificarHorariosDisponiveis @horas_inicio, @duracao, @men OUTPUT;

        IF @men = 'Valido' 
        BEGIN
            INSERT INTO Disciplina (codigo, nome, horas_inicio, duracao, dia_semana, professor)
            VALUES (@codigo, @nome, @horas_inicio, @duracao, @dia_semana, @professor);

            SET @saida = 'Disciplina inserida com sucesso.';
        END
        ELSE
        BEGIN
            SET @saida = 'Horario ou duração inválido';
        END
    END
    ELSE IF @op = 'U'
    BEGIN 
        IF @dia_semana NOT IN ('Segunda-feira', 'Terça-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sábado', 'Domingo')
        BEGIN
            SET @saida = 'Dia da semana inválido. Por favor, insira um dos seguintes valores: Segunda-feira, Terça-feira, Quarta-feira, Quinta-feira, Sexta-feira, Sábado, Domingo.';
            RETURN;
        END;

        EXEC VerificarHorariosDisponiveis @horas_inicio, @duracao, @men OUTPUT;

        IF @men = 'Valido' 
        BEGIN
            IF EXISTS (SELECT 1 FROM Disciplina WHERE codigo = @codigo)
            BEGIN
                UPDATE Disciplina
                SET nome = @nome,
                    horas_inicio = @horas_inicio,
                    duracao = @duracao,
                    dia_semana = @dia_semana,
                    professor = @professor
                WHERE codigo = @codigo;

                SET @saida = 'Disciplina atualizada com sucesso.';
            END
            ELSE
            BEGIN
                SET @saida = 'Código não encontrado na base de dados.';
            END
        END
        ELSE
        BEGIN
            SET @saida = @men;
        END
    END
    ELSE IF @op = 'D'
    BEGIN
        IF @codigo IS NOT NULL
        BEGIN
            IF EXISTS (SELECT 1 FROM Disciplina WHERE codigo = @codigo)
            BEGIN
                DELETE FROM Disciplina WHERE codigo = @codigo;
                SET @saida = 'Disciplina excluída com sucesso.';
            END
            ELSE
            BEGIN
                SET @saida = 'Código não encontrado na base de dados.';
            END
        END
        ELSE
        BEGIN
            SET @saida = 'Parâmetros incompletos para a operação de exclusão.';
        END
    END
    ELSE
    BEGIN
        SET @saida = 'Operação inválida.';
    END
END;


DECLARE @saida VARCHAR(100);
EXEC GerenciarDisciplina 'I', 3, 'Matemática', '13:00', 4, 'Segunda-feira', 1, @saida OUTPUT;
PRINT @saida;

DECLARE @saida VARCHAR(100);
EXEC GerenciarDisciplina 'U', 1, 'Matemática Avançada', '14:50', 2, 'Segunda-feira', 200, @saida OUTPUT;
PRINT @saida;

DECLARE @saida VARCHAR(100);
EXEC GerenciarDisciplina 'D', 1, NULL, NULL, NULL, NULL, NULL, @saida OUTPUT;
PRINT @saida;

SELECT * FROM Disciplina


--CRUD DA GRADE, VINCULANDO O DISCIPLINA COM O CURSO 

CREATE TABLE Grade (
    codigo INT,
    curso INT,
    disciplina INT,
	PRIMARY KEY (codigo),
    FOREIGN KEY (curso) REFERENCES Curso(codigo),
    FOREIGN KEY (disciplina) REFERENCES Disciplina(codigo)
);

CREATE PROCEDURE GerenciarGrade (
    @opcao CHAR(1),
    @codigo INT,
    @curso INT,
    @disciplina INT,
    @saida VARCHAR(200) OUTPUT
)
AS
BEGIN
    IF @opcao = 'I'
    BEGIN
        IF EXISTS (SELECT 1 FROM Grade WHERE codigo = @codigo)
        BEGIN
            SET @saida = 'Erro: Já existe uma grade com o código fornecido.';
            RETURN;
        END

        INSERT INTO Grade (codigo, curso, disciplina)
        VALUES (@codigo, @curso, @disciplina);

        SET @saida = 'Grade inserida com sucesso.';
    END
    ELSE IF @opcao = 'U'
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM Grade WHERE codigo = @codigo)
        BEGIN
            SET @saida = 'Erro: Não existe uma grade com o código fornecido.';
            RETURN;
        END

        UPDATE Grade
        SET curso = @curso,
            disciplina = @disciplina
        WHERE codigo = @codigo;

        SET @saida = 'Grade atualizada com sucesso.';
    END
    ELSE IF @opcao = 'D'
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM Grade WHERE codigo = @codigo)
        BEGIN
            SET @saida = 'Erro: Não existe uma grade com o código fornecido.';
            RETURN;
        END

        DELETE FROM Grade WHERE codigo = @codigo;

        SET @saida = 'Grade excluída com sucesso.';
    END
    ELSE
    BEGIN
        SET @saida = 'Erro: Opção inválida.';
    END
END;

CREATE FUNCTION fn_grade(@codigo_curso INT)
RETURNS TABLE
AS
RETURN
(
    SELECT 
        g.codigo AS codigo,
        c.nome AS curso,
        d.nome AS disciplina
    FROM Grade g
    INNER JOIN Curso c ON g.curso = c.codigo
    INNER JOIN Disciplina d ON g.disciplina = d.codigo
    WHERE c.codigo = @codigo_curso
);



DECLARE @saida VARCHAR(100);
EXEC GerenciarGrade 'I', 100, 1, @saida OUTPUT;
SELECT @saida AS Resultado;


DECLARE @saida VARCHAR(100);
EXEC GerenciarGrade 'U', 100, 1, @saida OUTPUT;
PRINT @saida;

DECLARE @saida VARCHAR(100);
EXEC GerenciarGrade 'D', 101, 1, @saida OUTPUT;
PRINT @saida;


SELECT * FROM Grade


--CRUD MATRICULA, VINCULANDO O ALUNO E A DISCIPLINA;


CREATE TABLE Matricula (
    codigo INT IDENTITY(1,1) PRIMARY KEY, 
    aluno CHAR(11),
    disciplina INT,
    data_m VARCHAR(10),
    status_m VARCHAR(30),
    FOREIGN KEY (aluno) REFERENCES Aluno(CPF),
    FOREIGN KEY (disciplina) REFERENCES Disciplina(codigo)
)

CREATE PROCEDURE GerenciarMatriculaD (
    @opcao VARCHAR(10),
    @codigo INT = NULL,
    @aluno CHAR(11),
    @disciplina INT,
    @data_m VARCHAR(10),
    @saida VARCHAR(100) OUTPUT
)
AS
BEGIN
    IF @opcao = 'I'
    BEGIN
        DECLARE @matriculaExistente BIT;
        SELECT @matriculaExistente = CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END
        FROM Matricula
        WHERE aluno = @aluno AND disciplina = @disciplina;

        DECLARE @todasReprovadas BIT;
        SELECT @todasReprovadas = CASE WHEN COUNT(*) = 0 THEN 1 ELSE 0 END
        FROM Matricula
        WHERE aluno = @aluno AND disciplina = @disciplina AND status_m != 'Reprovado';

        IF @matriculaExistente = 0 OR @todasReprovadas = 1
        BEGIN
            INSERT INTO Matricula (aluno, disciplina, data_m, status_m)
            VALUES (@aluno, @disciplina, @data_m, 'Cursando');
        
            SET @saida = 'Matrícula inserida com sucesso.';
        END
        ELSE
        BEGIN
            SET @saida = 'O aluno já está matriculado nesta disciplina e está cursando ou foi aprovado.';
            RETURN;
        END
    END
    ELSE IF @opcao = 'U'
    BEGIN
        -- Atualiza a matrícula
        UPDATE Matricula
        SET aluno = @aluno,
            disciplina = @disciplina,
            data_m = @data_m
        WHERE codigo = @codigo;

        SET @saida = 'Matrícula atualizada com sucesso.';
    END
    ELSE
    BEGIN
        SET @saida = 'Opção inválida.';
        RETURN;
    END

    DECLARE @media VARCHAR(5);
    SELECT @media = NULL;
    IF (SELECT status_m FROM Matricula WHERE aluno = @aluno AND disciplina = @disciplina) != 'Cursando'
    BEGIN
        SELECT @media = CONVERT(VARCHAR(5), AVG((CONVERT(DECIMAL(5, 2), Nota1) + CONVERT(DECIMAL(5, 2), Nota2) + ISNULL(CONVERT(DECIMAL(5, 2), NotaRecuperacao), 0)) / 2.0))
        FROM Notas
        WHERE Aluno = @aluno AND Disciplina = @disciplina;
    END

    IF @media IS NOT NULL
    BEGIN
        UPDATE Matricula
        SET status_m = CASE 
                            WHEN CONVERT(DECIMAL(5, 2), @media) >= 6 THEN 'Aprovado'
                            ELSE 'Reprovado'
                      END
        WHERE aluno = @aluno AND disciplina = @disciplina;
    END
    ELSE
    BEGIN
        IF (SELECT status_m FROM Matricula WHERE aluno = @aluno AND disciplina = @disciplina) = 'Cursando'
        BEGIN
            SET @saida = 'Não há notas para este aluno nesta disciplina.';
        END
    END
END;


DECLARE @saida VARCHAR(100);
EXEC GerenciarMatriculaD 'I', @aluno = '91345626053', @disciplina = 3, @data_m = '2024-05-10', @saida = @saida OUTPUT;
PRINT @saida;


SELECT * FROM  fn_matricula('91345626053')

CREATE FUNCTION fn_matricula(@aluno CHAR(11))
RETURNS TABLE
AS
RETURN
(
    SELECT 
        m.codigo AS codigo,
        a.nome AS aluno,
        d.nome AS disciplina,
        m.data_m AS data_m,
        m.status_m AS status_m,
        CASE 
            WHEN n.nota_final = 'D' THEN 'D' 
            ELSE CONVERT(VARCHAR(5), n.media) 
        END AS nota_final
    FROM Matricula m
    INNER JOIN Aluno a ON m.aluno = a.CPF
    INNER JOIN Disciplina d ON m.disciplina = d.codigo
    LEFT JOIN (
        SELECT 
            aluno,
            disciplina,
            AVG((Nota1 + Nota2 + ISNULL(NotaRecuperacao, 0)) / 2.0) AS media,
            CASE 
                WHEN MIN(Media) = 'D' THEN 'D' 
                ELSE CONVERT(VARCHAR(5), AVG((Nota1 + Nota2 + ISNULL(NotaRecuperacao, 0)) / 2.0))
            END AS nota_final
        FROM Notas
        GROUP BY aluno, disciplina
    ) n ON m.aluno = n.aluno AND m.disciplina = n.disciplina
    WHERE a.CPF = @aluno
);

select * from aluno
select * from disciplina


DECLARE @saida VARCHAR(100);
EXEC GerenciarMatriculaD 'I', 3, '91345626053', 3, '2024-03-20', 'Cursando', @saida OUTPUT;
PRINT @saida;


DECLARE @saida VARCHAR(100);
EXEC GerenciarMatriculaD 'U', 1, '91345626053', 1, '2024-03-20', 'Reprovado', @saida OUTPUT;
PRINT @saida;


select * from Matricula;

--CRUD NOTA 

CREATE TABLE Notas (
    Codigo INT IDENTITY(1,1),
    Disciplina INT,
    Aluno CHAR(11),
    Nota1 FLOAT,
    Nota2 FLOAT,
    Media VARCHAR(20),
    NotaRecuperacao FLOAT
	PRIMARY KEY (codigo),
	FOREIGN KEY (aluno) REFERENCES Aluno(CPF),
    FOREIGN KEY (disciplina) REFERENCES Disciplina(codigo)
)


CREATE  PROCEDURE InserirNotas (
    @Opcao CHAR(1), 
    @Disciplina INT,
    @Aluno CHAR(11),
    @Nota1 FLOAT,
    @Nota2 FLOAT,
    @NotaRecuperacao FLOAT = NULL,
    @saida VARCHAR(100) OUTPUT
)
AS
BEGIN
    DECLARE @Media FLOAT
    
    SET @saida = ''

    IF @Opcao = 'U' AND EXISTS (SELECT 1 FROM Notas WHERE Disciplina = @Disciplina AND Aluno = @Aluno)
    BEGIN
        UPDATE Notas 
        SET Nota1 = @Nota1, 
            Nota2 = @Nota2,
            NotaRecuperacao = @NotaRecuperacao, -- Atualizar a nota de recuperação
            Media = CASE WHEN @NotaRecuperacao IS NOT NULL THEN (@Nota1 + @Nota2 + @NotaRecuperacao) / 2 ELSE (@Nota1 + @Nota2) / 2 END
        WHERE Disciplina = @Disciplina AND Aluno = @Aluno

        SET @saida = 'Notas atualizadas com sucesso.'
    END
    ELSE IF @Opcao = 'I' AND NOT EXISTS (SELECT 1 FROM Notas WHERE Disciplina = @Disciplina AND Aluno = @Aluno)
    BEGIN
        INSERT INTO Notas (Disciplina, Aluno, Nota1, Nota2, Media, NotaRecuperacao)
        VALUES (@Disciplina, @Aluno, @Nota1, @Nota2, CASE WHEN @NotaRecuperacao IS NOT NULL THEN (@Nota1 + @Nota2 + @NotaRecuperacao) / 2 ELSE (@Nota1 + @Nota2) / 2 END, @NotaRecuperacao)

        SET @saida= 'Notas cadastradas com sucesso.'
    END
    ELSE IF @Opcao = 'I' AND EXISTS (SELECT 1 FROM Notas WHERE Disciplina = @Disciplina AND Aluno = @Aluno)
    BEGIN
        SET  @saida = 'Já existem notas cadastradas para este aluno nesta disciplina. Utilize a opção "U" para atualizar as notas.'
    END

    SET @Media = (SELECT TOP 1 Media FROM Notas WHERE Disciplina = @Disciplina AND Aluno = @Aluno ORDER BY (SELECT NULL))
    IF @Media < 6 AND @Opcao = 'I'
    BEGIN
        SET @saida = 'Aluno em recuperação.'
    END

    IF @Media IS NOT NULL AND @Opcao = 'I' 
    BEGIN
        UPDATE Matricula
        SET status_m = CASE 
                            WHEN @Media >= 6 THEN 'Aprovado'
                            ELSE 'Reprovado'
                      END
        WHERE aluno = @Aluno AND disciplina = @Disciplina;
    END
    ELSE IF @Opcao = 'U' AND @Media >= 6 
    BEGIN
        UPDATE Matricula
        SET status_m = 'Aprovado'
        WHERE aluno = @Aluno AND disciplina = @Disciplina;
    END
END;


CREATE FUNCTION ListarNotasEMedias (@disciplina INT)
RETURNS TABLE
AS
RETURN
(
    SELECT n.codigo,
           n.Aluno,
           n.Nota1,
           n.Nota2,
           n.NotaRecuperacao,
           n.Media
    FROM Notas n
    INNER JOIN Disciplina d ON n.Disciplina = d.codigo
    WHERE n.disciplina = @disciplina
);

-- CRUD TEEFONE 


CREATE TABLE Telefone (
    codigo INT,
    aluno CHAR(11),
    telefone VARCHAR(20),
    PRIMARY KEY(codigo),
    FOREIGN KEY (aluno) REFERENCES Aluno(CPF)
);



CREATE FUNCTION fn_telefones(@cpf CHAR(11))
RETURNS TABLE
AS
RETURN
(
    SELECT 
        t.codigo AS codigo,
        t.telefone AS telefone,
		a.nome AS aluno
    FROM telefone t
	INNER JOIN Aluno a ON t.aluno = a.cpf
    WHERE a.cpf = @cpf
);


CREATE PROCEDURE GerenciarTelefone (
    @opcao VARCHAR(10),
	@codigo INT,
	@cpfAluno CHAR(11),
    @telefone VARCHAR(20),
    @saida VARCHAR(100) OUTPUT
)
AS
BEGIN

    IF @opcao = 'I'
    BEGIN
   
        IF EXISTS (SELECT 1 FROM Aluno WHERE CPF = @cpfAluno)
        BEGIN
       
            IF NOT EXISTS (SELECT 1 FROM Telefone WHERE telefone = @telefone AND aluno = @cpfAluno)
            BEGIN
               
                INSERT INTO Telefone (codigo, aluno, telefone)
                VALUES (@codigo, @cpfAluno, @telefone);
                
                SET @saida = 'Telefone inserido com sucesso.';
            END
            ELSE
            BEGIN
                SET @saida = 'O telefone já existe na base de dados para este aluno.';
            END
        END
        ELSE
        BEGIN
            SET @saida = 'O aluno com o CPF especificado não foi encontrado na base de dados.';
        END
    END
    ELSE IF @opcao = 'D'
    BEGIN
        
        DELETE FROM Telefone
        WHERE codigo = @codigo;
        
        SET @saida = 'Telefone excluído com sucesso.';
    END
    ELSE
    BEGIN
        SET @saida = 'Opção inválida.';
    END
END;


SELECT * from telefone


DECLARE @saida VARCHAR(100);
EXEC GerenciarTelefone 'D', 1, '91345626053', '11979669482', @saida OUTPUT;
PRINT @saida;


--CRUD CHAMADA 

CREATE TABLE Chamada (
    codigo INT,
    disciplina INT,
    aluno CHAR(11),
    falta INT,
	data VARCHAR(10)
    FOREIGN KEY (disciplina) REFERENCES Disciplina(codigo),
    FOREIGN KEY (aluno) REFERENCES Aluno(cpf)
);



CREATE PROCEDURE GerenciarChamada (
    @opcao VARCHAR(10),
    @codigo INT,
    @codigoDisciplina INT,
    @cpfAluno VARCHAR(30),
    @falta INT,
    @data VARCHAR(10),
    @saida VARCHAR(100) OUTPUT
)
AS
BEGIN
    IF @opcao = 'I'
    BEGIN
        DECLARE @duracaoMatricula INT;

        -- Obtém a duração da matrícula do aluno na disciplina
        SELECT @duracaoMatricula = duracao
        FROM Disciplina
        WHERE codigo = @codigoDisciplina;

        -- Verifica se o número de faltas é maior que a duração da matrícula
        IF @falta <= @duracaoMatricula
        BEGIN
            -- Insere as chamadas se o número de faltas for válido
        INSERT INTO Chamada (codigo, disciplina, aluno, falta, data)
        VALUES (@codigo,  @codigoDisciplina,  @cpfAluno, @falta, @data)

            SET @saida = 'Chamadas inseridas com sucesso.';
        END
        ELSE
        BEGIN
            SET @saida = 'Número de faltas excede a duração da matrícula.';
        END
    END
    ELSE IF @opcao = 'U'
    BEGIN
        -- Atualiza a quantidade de faltas para uma chamada existente
        UPDATE Chamada
        SET falta = @falta
        WHERE codigo = @codigo AND aluno = @cpfAluno;

        SET @saida = 'Falta atualizada com sucesso.';
    END
    ELSE
    BEGIN
        SET @saida = 'Opção inválida.';
    END
END;



CREATE FUNCTION fn_chamadas(@codigoDisciplina INT)
RETURNS TABLE
AS
RETURN
(
    SELECT 
        m.disciplina AS disciplina,
        a.nome AS aluno,
		m.aluno AS nomeAluno
    FROM Matricula m
	INNER JOIN Disciplina d ON d.codigo = m.disciplina
	INNER JOIN Aluno a ON a.cpf = m.aluno
    WHERE m.disciplina = @codigoDisciplina AND m.status_m = 'Cursando'
);


CREATE FUNCTION fn_chamadasAlter(@codigo int)
RETURNS TABLE
AS
RETURN
(
    SELECT 
	    c.codigo AS codigo,
	    c.disciplina AS disciplina,
        c.aluno AS aluno,
		a.nome AS nomeAluno,
        c.falta AS falta
    FROM Chamada c
    INNER JOIN Matricula m ON c.aluno = m.aluno AND c.disciplina = m.disciplina
	INNER JOIN Aluno a ON a.CPF = c.aluno
    WHERE c.codigo = @codigo
);


CREATE FUNCTION fn_consultaC()
RETURNS TABLE
AS
RETURN
(
    SELECT 
	    c.codigo AS codigo,
	    c.disciplina AS disciplina,
        c.aluno AS aluno,
		a.nome AS nomeAluno,
        c.falta AS falta
    FROM Chamada c
    INNER JOIN Matricula m ON c.aluno = m.aluno AND c.disciplina = m.disciplina
	INNER JOIN Aluno a ON a.CPF = c.aluno
);


SELECT * FROM fn_chamadasAlter(9);

SELECT * FROM fn_chamadas(1);


SELECT * from Curso
SELECT * from Professor
SELECT * from Disciplina
select  * from Grade
SELECT * from Aluno
select * from matricula
select * from Chamada
select * from dispensa
select * from notas


--HISTORICO DO ALUNO 

CREATE TABLE historico_aluno(
aluno       CHAR(11),
matricula   INT,
curso       INT 
PRIMARY KEY (aluno), 
FOREIGN KEY (aluno) REFERENCES Aluno(CPF),
FOREIGN KEY (matricula) REFERENCES Matricula(codigo),
FOREIGN KEY (curso) REFERENCES curso(codigo)
)


CREATE FUNCTION ConsultarHistoricoAluno (
    @CPF CHAR(11)
)
RETURNS TABLE
AS
RETURN
(
    SELECT 
        A.RA,
        A.nome AS NomeCompleto,
        C.nome AS NomeCurso,
        M.data_m AS DataPrimeiraMatricula,
        A.pontuacao_vestibular AS PontuacaoVestibular,
        A.posicao_vestibular AS PosicaoVestibular
    FROM 
        Aluno A
    INNER JOIN 
        Matricula M ON A.CPF = M.aluno
    INNER JOIN 
        Curso C ON A.curso = C.codigo
    WHERE 
        A.CPF = @CPF
)

--CRUD DISPENSA 

CREATE TABLE Dispensa (
    codigo INT IDENTITY(1,1),
    aluno CHAR(11),
    disciplina INT,
    data_s VARCHAR(10),
    instituicao VARCHAR(100),
	PRIMARY KEY (codigo),
    FOREIGN KEY (aluno) REFERENCES Aluno(CPF),
    FOREIGN KEY (disciplina) REFERENCES Disciplina(codigo)
)

CREATE PROCEDURE iudDispensa (
    @opcao CHAR(1),
    @aluno CHAR(11),
    @disciplina INT,
    @data_s VARCHAR(10),
    @instituicao VARCHAR(100),
    @saida VARCHAR(100) OUTPUT
)
AS
BEGIN
    IF @opcao = 'I'
    BEGIN
        BEGIN TRY
            INSERT INTO Dispensa (aluno, disciplina, data_s, instituicao)
            VALUES (@aluno, @disciplina, @data_s, @instituicao);

            UPDATE Matricula
            SET status_m = 'Em análise'
            WHERE aluno = @aluno AND disciplina = @disciplina;

            SET @saida = 'Solicitação de dispensa feita com sucesso.';
        END TRY
        BEGIN CATCH
            SET @saida = 'Erro ao solicitar dispensa.';
        END CATCH;
    END
    ELSE
    BEGIN
        SET @saida = 'Opção inválida.';
    END
END;

DECLARE @saida VARCHAR(100);
EXEC iudDispensa 'I', '91345626053' ,3 , '2024-05-05', 'Fatec ZL', @saida OUTPUT;
PRINT @saida;

DECLARE @saida VARCHAR(100);
EXEC iudDispensa 'D', '91345626053', NULL, NULL, NULL, 'banco de dados', @saida OUTPUT;
SELECT @saida AS Mensagem;


CREATE FUNCTION fn_solitacaoDispensa(@cpf_aluno CHAR(11))
RETURNS TABLE
AS
RETURN
(
    SELECT 
	    dis.codigo AS codigo,
        a.nome AS nome,
        dis.nome AS disciplina,
        d.data_s AS data_solicitacao,
        d.instituicao AS instituicao
    FROM Dispensa d
    INNER JOIN Aluno a ON d.aluno = a.CPF
    INNER JOIN Disciplina dis ON d.disciplina = dis.codigo
    WHERE d.aluno = @cpf_aluno
)


--HISTORICO DE MATRICULAS APROVADAS 

CREATE FUNCTION ListarMatriculasAprovadasAluno (
    @CodigoAluno CHAR(11)
)
RETURNS TABLE
AS
RETURN
(
    SELECT 
        D.codigo AS CodigoDisciplina,
        D.nome AS NomeDisciplina,
        P.nome AS NomeProfessor,
        N.media AS NotaFinal,
        SUM(C.falta) AS QuantidadeFaltas
    FROM 
        Matricula M
    INNER JOIN Disciplina D ON M.disciplina = D.codigo
	INNER JOIN Notas N ON N.disciplina = D.codigo
	INNER JOIN Chamada C ON C.disciplina = D.codigo AND C.aluno = M.aluno
	INNER JOIN Professor P ON P.codigo = D.professor 
    WHERE 
        M.aluno = @CodigoAluno
        AND M.status_m = 'Aprovado'
    GROUP BY
        M.disciplina, D.nome, D.professor, N.media, P.nome, D.codigo
)

SELECT * FROM ListarMatriculasAprovadasAluno(36280987000);


CREATE FUNCTION fn_DispensaSecretaria()
RETURNS TABLE
AS
RETURN
(
    SELECT 
	    dis.codigo AS codigo,
		d.aluno AS aluno,
        a.nome AS nomeAluno,
		d.disciplina AS disciplina,
        dis.nome AS nomeDisciplina,
        d.data_s AS data_solicitacao,
        d.instituicao AS instituicao
    FROM Dispensa d
    INNER JOIN Aluno a ON d.aluno = a.CPF
    INNER JOIN Disciplina dis ON d.disciplina = dis.codigo
   
)


DROP TABLE Notas
DROP TABLE matricula
DROP TABLE dispensa


-- CRUD SECRETARIA DISPENSA

CREATE PROCEDURE iudSolicitacao (
    @opcao CHAR(1),
    @codigo_dispensa INT,
    @codigo_aluno CHAR(11), 
    @saida VARCHAR(100) OUTPUT
)
AS
BEGIN
    IF @opcao = 'U'
    BEGIN
        BEGIN TRY
            UPDATE Matricula
            SET status_m = 'Dispensado'
            WHERE disciplina = @codigo_dispensa AND aluno = @codigo_aluno; -- Adicionando a condição para o código do aluno

            INSERT INTO Notas (Disciplina, Aluno, Media) 
            VALUES (@codigo_dispensa, @codigo_aluno, CAST('D' AS VARCHAR(5)));

            SET @saida = 'Solicitação atualizada com sucesso.';
        END TRY
        BEGIN CATCH
            SET @saida = 'Erro ao atualizar a solicitação.';
        END CATCH;
    END
    ELSE
    BEGIN
        SET @saida = 'Opção inválida.';
    END
END;


DECLARE @saida VARCHAR(100);
EXEC iudSolicitacao 'U', 1,  @saida OUTPUT;
PRINT @saida;


CREATE FUNCTION fn_solitacao()
RETURNS TABLE
AS
RETURN
(
    SELECT 
	    d.codigo AS codigo,
        a.cpf AS aluno,
        dis.nome AS disciplina,
        d.data_s AS data_solicitacao,
        d.instituicao AS instituicao
    FROM Dispensa d
    INNER JOIN Aluno a ON d.aluno = a.CPF
    INNER JOIN Disciplina dis ON d.disciplina = dis.codigo
)

CREATE FUNCTION Listar_matricula()
RETURNS TABLE
AS
RETURN
(
    SELECT 
        m.codigo AS codigo,
        a.nome AS aluno,
        d.nome AS disciplina,
        m.data_m AS data_m,
        m.status_m AS status_m,
		n.media AS nota_final
    FROM Matricula m
    INNER JOIN Aluno a ON m.aluno = a.CPF
    INNER JOIN Disciplina d ON m.disciplina = d.codigo
	INNER JOIN Notas n ON m.disciplina = n.disciplina
);

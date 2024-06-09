Atividade 2 Laboratório de Banco de Dados
Prof. Leandro Colevati FATEC-ZL
No sistema acadêmico AGIS, se propôs algumas evoluções.
A primeira determina que, todos os alunos, na matrícula pós vestibular devem estar 
matriculados, imediatamente, em todas as disciplinas do curso escolhido. Alguns alunos 
podem até pedir eliminação de matérias, o que permitirá armazenar um conceito para 
determinar que aquela disciplina foi cursada em outra instituição, mas, não poderá, em 
hipótese alguma, excluir a matrícula de um aluno em uma disciplina.
As matrículas devem possibilitar ao sistema gerar duas telas a partir de UDFs.
A primeira é a lista de chamada. As disciplinas podem ter 2 ou 4 horas aula e deve-se fazer 
chamada para cada hora aula. Os professores devem poder lançar a chamada e atualizar a 
chamada a qualquer momento, mas, nunca excluir uma chamada realizada. 
A segunda é o histórico do aluno que deve ser possível de ser consultado pela secretaria, que 
mostre um cabeçalho com as principais informações do aluno:
• RA, nome completo, nome do curso, data da primeira matrícula, pontuação do 
vestibular, posição no vestibular
E, na sequência, uma lista das matrículas que o aluno foi aprovado
• Código da disciplina, nome da disciplina, nome do professor, nota final, quantidade 
de faltas
Caso o aluno tenha sido dispensado, deve-se apresentar a nota final D.
Para a avaliação deve se fazer, utilizando Java Web (Spring Web, JSP e JSTL), com SQL 
Server:
Um protótipo do AGIS:
• A atualização da modelagem do sistema pedido, com as diagramações 
pertinentes;
• Adequar o sistema para implementar os módulos das UDFs
• Aplicar os gatilhos em situações que se apresentem como necessárias
Serão avaliados, além da solução do código:
A qualidade do desenvolvimento e as boas práticas
A qualidade da usabilidade do sistema pelo usuário
A qualidade da modelagem do sistema
O projeto deve ser carregado no Github e o link encaminhado na tarefa correspondente no 
prazo estipulado.
O projeto será apresentado, rodando e questionamentos sobre o código, ao professor na 
aula determinada. A apresentação sem o projeto entregue antecipadamente na tarefa 
incorre em desconto da avaliação.
As modelagens devem estar em uma pasta chamada doc na pasta WEB-INF do projeto Java 
Web.
Códigos Java ou SQL com semelhanças geram grandes descontos

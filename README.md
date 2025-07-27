# Courses API REST - Spring Boot

Esse projeto tem como objetivo contemplar o desafio de projeto do Bootcamp Back-End com Java da Santander em parceria com a DIO.

## Tecnologias e Ferramentas 🖥️
- **Spring Boot**
- **JPA Repository**
- **Swagger OpenAPI**
- **MySQL**
- **Postman**

## Conteúdo 📖
O projeto implementa um CRUD completo (Create, Read, Update e Delete) com o tema de cursos.
<br>Nele é possível cadastrar:
- Estudantes
- Endereços
- Cursos

Com esses dados, é possível relacionar:
- Um **curso a vários estudantes** (relação 1:N)
- Um **endereço a vários estudantes** (relação 1:N)

## 📦 Funcionalidades
- Cadastro, edição, inativação, exclusão e listagem de estudantes e cursos.
- Autenticação de CEP via API Externa - [ViaCep](https://viacep.com.br/).
- Documentação via Swagger - acessar pelo **localhost:8080/swagger-ui/index.html**.

## 🛢 Conexão com Banco de Dados
A conexão com Banco de Dados ocorre graças a ajuda da depedência **mysql-conector-j**, presente no pom.xml do projeto. Caso queira modificar o banco do projeto, é local que precisa ser modificado.<br>
Há também o **application.properties** dentro do diretório **resources**, que possui as configurações do banco MySQL e onde você deve modificar caso escolha trocar o Banco de Dados da aplicação.

### applications.properties:
- **URL do Banco de Dados**
```
spring.datasource.url=jdbc:mysql://localhost:3306/<seubanco>?useSSL=false&serverTimezone=UTC

## string de conexão com a porta (3306) e nome do banco
```

- **User e Password do Banco de Dados**
```
spring.datasource.username=<seusuario>
## usuário do banco

spring.datasource.password=<suasenha>
## senha do banco
```

Com essas informações em mãos, é possível customizar a aplicação para o seu gosto.<br>
Caso haja dúvidas consulte a documentação - [SQL Databases :: Spring Boot](https://docs.spring.io/spring-boot/reference/data/sql.html).

## 📬 Endpoints

### Estudantes
|Verbo|Rota|Descrição|
|-----|----|---------|
|POST|/studens|Cria um estudante|
|POST|/students/{studentId}/courses/{courseId}|Cadastra um estudante em um curso, pelo id do estudante e o id do curso|
|PUT|/students/{id}|Modifica dados de um estudante|
|DELETE|/students/{id}|Deixa um estudante inativo no sistema|
|DELETE|/students/delete/{id}|Deleta de fato o estudante do banco de dados|
|PUT|/students/active/{id}|Ativa o estudante no sistema|
|GET|/students|Lista todos os estudantes ativos|
|GET|/students/{id}|Exibe um estudante por id *obs: contém mais informações sobre o estudante|
|GET|students/inactives|Lista todos estudantes inativos|

### Cursos
|Verbo|Rota|Descrição|
|-----|----|---------|
|POST|/courses|Cria um curso|
|PUT|/courses/{id}|Modifica dados de um curso|
|DELETE|/courses/{id}|Deixa um curso inativo no sistema|
|DELETE|/courses/delete/{id}|Deleta de fato o curso do banco de dados|
|PUT|/courses/active/{id}|Ativa o curso no sistema|
|GET|/courses|Lista todos os cursos ativos|
|GET|/courses/{id}|Exibe um curso por id *obs: contém mais informações sobre o curso|
|GET|courses/inactives|Lista todos cursos inativos|

## 📌 Observações

- Este projeto tem fins educativos e pode servir de base para estudos e portfólio.
- Sinta-se à vontade para contribuir, sugerir melhorias ou relatar problemas!

## 🧑‍💻 Autor

- Matheus Rodrigues — [GitHub](https://github.com/MatheusPRodrigues) — [LinkedIn](https://www.linkedin.com/in/matheusp-rodrigues19/)

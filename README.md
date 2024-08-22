# AuthTransaction
## Projeto de autorização de transações
Projeto foi desenvolvido com Java 17, Maven 4.0, SpringBoot 3.3 e Redis 3.3 essencialmente.

## Decisões Técnicas e Arquiteturais
### Escolha da Linguagem: Java
Java foi escolhido dentro das linguagens propostas, pelo meu domínio com a mesma e facilitade de implementação e atendimento às regras propostas.

### Estrutura do Código
Utilizei uma estrutura bem comum como MVC. Onde inicialmente recebemos a requisição via controller, acionamos um service para avaliar as regras de negócio que o desafio proporcionou, fazer validações e alterações de banco e em seguida retornar a requisição conforme solicitada.

### Uso de Maven
A escolha se deu por ter conhecimento com a ferramenta, mas poderia ter utilizado outras como por exemplo: Gradle.

### Uso de Java
Escolhi por domínio da linguagem e entendimento de todo o ecossistema.

### Uso de SpringBoot
Sempre trabalhei com Java + Spring, para fazer o gerenciamento das classes da aplicação. Poderia também utilizar outra ferramenta como Micronaut, por exemplo.

### Uso de Redis
Principalmente pela rapidez de implementação tendo em vista os cenários do case propostos. Adicionar um banco de dados mais robusto como MySQL ou Mongo seria interessante também, mas tendo em vista que não precisariamos nos preocupar para o case com modelagem ou fluxo de dados, utilizei redis para ficar mais coerente com os cenários propostos.

## Instruções de Compilação e Execução
### Requisitos
* Java 17
* Maven 4.0
* Redis 3.3
* SpringBoot 3.3

### Passos para Compilar e Executar
1. Clone o repositório:
```
git clone https://github.com/BrunoLCReis/AuthTransaction.git
```
2. Execute o projeto na sua IDEA de preferência:
```
lein run < caminho-do-arquivo-de-entrada >
```
3. Dentro da estrutura foram desenvolvidos testes "integrados" e unitários que demonstram a cobertura dos cenários propostos pelo case.
4. Pode-se também gerar uma conexão via Postman ou outro client para chamar a aplicação diretamente e simular os cenários necessários e até mesmo algum outro cenário não escrito como teste de aplicação.

## Notas Adicionais
* Este projeto foi desenvolvido com foco em atender os cenários solicitados no link: https://www.notion.so/caju/Desafio-T-cnico-para-fazer-em-casa-218d49808fe14a4189c3ca664857de72
* A modularidade do código está simples, porém permite escala e evolução tendo em vista as classes já separadas por responsabilidade.
* Vejo que teríamos muitos aspectos incrementais nessa aplicação avaliativa, como por exemplo tratamento de exceções e cenários que diferenciados. Espero que possamos discutir essas melhorias numa próxima fase caso necessário.

# Questão L4
 [L4_CAJU_CASE.pdf](https://github.com/user-attachments/files/16715823/L4_CAJU_CASE.pdf)


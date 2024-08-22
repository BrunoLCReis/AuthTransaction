# AuthTransaction
## Projeto de Autorização de Transações
Este projeto foi desenvolvido com Java 17, Maven 4.0, Spring Boot 3.3 e Redis 3.3.

## Decisões Técnicas e Arquiteturais
### Escolha da Linguagem: Java
Java foi escolhida entre as linguagens propostas devido ao meu domínio sobre a mesma, facilitando a implementação e o atendimento às regras estabelecidas.

### Estrutura do Código
Utilizei uma estrutura comum, baseada no padrão MVC, onde a requisição é inicialmente recebida pelo controller, que aciona um serviço (service) para avaliar as regras de negócio, realizar validações e operações no banco de dados, e, em seguida, retornar a resposta conforme solicitado.

### Uso do Maven
A escolha do Maven se deu pelo meu conhecimento prévio da ferramenta, embora outras alternativas, como Gradle, também pudessem ter sido utilizadas.

### Uso do Java
Escolhi Java devido ao meu domínio da linguagem e ao entendimento de todo o seu ecossistema.

### Uso do Spring Boot
Sempre trabalhei com Java em conjunto com o Spring para gerenciar as classes da aplicação. Outra ferramenta, como o Micronaut, poderia ter sido utilizada, mas optei por seguir com o Spring Boot.

### Uso do Redis
Optei pelo Redis principalmente pela rapidez de implementação, considerando os cenários propostos no desafio. Um banco de dados mais robusto, como MySQL ou MongoDB, seria interessante, mas como não precisávamos nos preocupar com modelagem ou fluxo de dados para este caso, o Redis se mostrou mais coerente com os cenários propostos.

## Instruções de Compilação e Execução
### Requisitos
* Java 17
* Maven 4.0
* Redis 3.3
* Spring Boot 3.3

### Passos para Compilar e Executar
1. Clone o repositório:
    ```bash
    git clone https://github.com/BrunoLCReis/AuthTransaction.git
    ```
2. Execute o projeto na sua IDE de preferência conforme a run padrão do Java
3. Foram desenvolvidos testes integrados e unitários que demonstram a cobertura dos cenários propostos pelo desafio.
4. Também é possível estabelecer uma conexão via Postman ou outro cliente para chamar a aplicação diretamente, simulando os cenários necessários ou mesmo explorando cenários adicionais não cobertos pelos testes.

## Notas Adicionais
* Este projeto foi desenvolvido com foco em atender os cenários solicitados no seguinte link: [Desafio Técnico](https://www.notion.so/caju/Desafio-T-cnico-para-fazer-em-casa-218d49808fe14a4189c3ca664857de72)
* A modularidade do código está simples, mas permite escalabilidade e evolução, considerando as classes já separadas por responsabilidade.
* Acredito que existem muitos aspectos que podem ser incrementados nesta aplicação avaliativa, como tratamento de exceções e cenários diferenciados. Espero que possamos discutir essas melhorias em uma próxima fase, caso necessário.

# Questão L4
* Documento onde discorro inicialmente sobre o que foi pedido na questão 4:
  * [L4_CAJU_CASE.pdf](https://github.com/user-attachments/files/16715823/L4_CAJU_CASE.pdf)
* Imagem que ilustra uma das abordagens que eu poderia utilizar quando falamos de pontos como concorrência, resiliência e garantia de integridade no fluxo do desafio técnico:

![image](https://github.com/user-attachments/assets/268158f6-4aa8-448f-9f3e-c6269729a297)

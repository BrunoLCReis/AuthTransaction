# AuthTransaction
## Projeto de Autorização de Transações
Este projeto foi desenvolvido com Java 17, Maven 4.0, Spring Boot 3.3 e Redis 3.3.

## Decisões Técnicas e Arquiteturais
### Escolha da Linguagem: Java
Java foi escolhida entre as linguagens propostas devido ao meu domínio sobre a mesma, facilitando a implementação e o atendimento às regras estabelecidas.

### Estrutura do Código
Utilizei uma estrutura comum, baseada no padrão MVC, onde a requisição é inicialmente recebida pelo controller, que aciona um serviço (service) para avaliar as regras de negócio, realizar validações e operações no banco de dados, e, em seguida, retornar a resposta conforme solicitado.

1. Package domain: Modelei meus objetos de domínio, que no caso são Transaction e Account
2. Package entities: Modelei minhas entidades que no caso serão persistidas para e posteriormente resgatadas com intuituo de atender as questões do projeto.
3. Package enums: Utilizei dois enums para melhor a visualização e segregação dos MCC e das Categorias. Isso ajudou a deixar o código mais limpo e legível.
4. Package infrastructure: Criei a inicialização e configuração do meu Redis, onde alem de iniciar criei uma massa de dados para facilitar testes e visualização da aplicação rodando em um ambiente produtivo.
5. Package repository: Fiz a criação básica do meu repositório que é utilizado na hora de resgatar os dados que estão presentes no meu Redis.
6. Package response: Objeto de resposta para ser utilizado pela API.
7. Package service: Dentro desse pacote está toda a regra de negócio para o processamento. Inicialmente utilizamos o AuthorizationService para recceber a requisição e conectar os dados de Transaction (request) com os demais dados a serem consultados no banco (Account). Após toda a regra ser processada, chamamos o DebitService para efetuar o processamento financeiro e concluir tudo que foi pedido.
8. Package test: Dentro desse pacote estão duas classes de testes, separadas por service e controller, que tem a função de testar a chamada pela API e também a chamada dos serviços (service) e validar as suas regras de negócio.

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
2. Execute o projeto na sua IDE de preferência conforme a run do Java+Spring. A aplicação ficará de pé e será possível acessa-la da seguinte forma:
   ![image](https://github.com/user-attachments/assets/c7b63fe8-4201-472e-8b0b-82bc346e10a5)

*Curl exemplo:*
```
curl --location 'http://localhost:8080/authorize' \
--header 'Content-Type: application/json' \
--data '{
    "id": "1",
    "accountId": "500",
    "amount": 100.0,
    "mcc": "5811",
    "merchant": "PADARIA DO ZE - SAO PAULO BR"
}'
```

### Testes de Controller
1 - Criei testes de controller para verificar a comunicação com a API criada, validação de endpoint e também de requisição de entrada e a saída esperada.
2 - Para executa-los, eu sugeriria usar o comando: *mvn test* ou *mvn verify*. Utilizando o maven para fazer a verificação dos testes.
3 - Outra sugestão, seria rodar diretamente pelo run da sua IDEA. Exemplo de execução no IntelliJ:
![image](https://github.com/user-attachments/assets/81546bfc-12d0-4dc5-b007-1197081e4d6a)


### Testes de Service
1 - Criei os testes de service com intuito de validar detalhadamente as regras de negócio que foram propostas no desafio técnico.
2 - A ideia foi criar diversos cenários e executa-los um por vez, garantindo que dentro dos mesmos services conseguimos receber diversas requisições e ter o resultado esperado.
3 - Exemplo dos testes de serviço:
![image](https://github.com/user-attachments/assets/3d5589c5-40d6-4d79-a09e-4367e6ae678a)



## Notas Adicionais
* Este projeto foi desenvolvido com foco em atender os cenários solicitados no seguinte link: [Desafio Técnico](https://www.notion.so/caju/Desafio-T-cnico-para-fazer-em-casa-218d49808fe14a4189c3ca664857de72)
* A modularidade do código está simples, mas permite escalabilidade e evolução, considerando as classes já separadas por responsabilidade.
* Acredito que existem muitos aspectos que podem ser incrementados nesta aplicação avaliativa, como tratamento de exceções e cenários diferenciados. Espero que possamos discutir essas melhorias em uma próxima fase, caso necessário.

# Questão L4
* Documento onde discorro inicialmente sobre o que foi pedido na questão 4:
    * [Questão4.pdf](https://github.com/user-attachments/files/16719574/Questao4.pdf)

* Acima falo de pontos como concorrência, resiliência e garantia de integridade no fluxo do desafio técnico:
* Discorro no PDF acima também, um dos modelos de minha preferência para trabalhar o que desejamos.

![image](https://github.com/user-attachments/assets/268158f6-4aa8-448f-9f3e-c6269729a297)

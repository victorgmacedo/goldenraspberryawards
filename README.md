
## Architecture
O projeto foi escrito utilizando a abordagem da estrutura hexagonal,
não foi utilizada a estrutura de modulos para separacao das camadas, mas sim 
a divisão entre pacotes o que organiza o projeto da seguinte forma:

### application 
Pacote que contem a camada responsavel pela exposição dos dados.
### config 
Pacote responsavel pela configuração dos Bean personalizados.
### domain 
Pacote que contem as regras de negocio do projeto.

**Não existe nenhuma dependencia externa de bibliotecas dentro deste pacote apenas codigo java**
### infra  
Pacote que contem a camada responsavel pelo gerenciamento de infra como 
obtenção e armazenamento dos dados  



## Dependencias
* [Spring Boot](https://docs.spring.io/spring-boot/docs/2.7.4/reference/html/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.4/reference/htmlsingle/#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.7.4/reference/htmlsingle/#data.sql.jpa-and-spring-data)
* [Liquibase Migration](https://docs.spring.io/spring-boot/docs/2.7.4/reference/htmlsingle/#howto.data-initialization.migration-tool.liquibase)

## Start Application
``./gradlew bootRun``

Os dados podem ser vizualizados pelo seguinte link http://localhost:8080/producer-rank

## Run tests
``./gradlew test``

$${\color{red} ATENÇÃO}$$
<p align="center">
  PARA RODAR O PROJETO É NECESSÁRIO ESTAR COM A VERSÃO 17 DO JAVA INSTALADO
</p>

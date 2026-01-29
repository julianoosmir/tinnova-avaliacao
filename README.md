# Projeto de Avaliação Tinnova

Este projeto utiliza Quarkus, o Framework Java Supersonico e Subatômico.

Se você quiser saber mais sobre o Quarkus, por favor visite o site: <https://quarkus.io/>.

## Endpoints Disponíveis

A documentação completa dos endpoints da API está disponível através do Swagger UI. Uma vez que a aplicação esteja em execução, você pode acessá-la em:

[http://localhost:8080/q/swagger-ui/](http://localhost:8080/q/swagger-ui/)


## Executando a aplicação em modo de desenvolvimento

Você pode executar sua aplicação em modo de desenvolvimento, que ativa o live coding, usando:

```shell script
./mvnw quarkus:dev
```


## Empacotando e executando a aplicação

A aplicação pode ser empacotada usando:

```shell script
./mvnw package
```

Isso produz o arquivo `quarkus-run.jar` no diretório `target/quarkus-app/`.
Este não é um _über-jar_, pois as dependências são copiadas para o diretório `target/quarkus-app/lib/`.

A aplicação pode ser executada usando `java -jar target/quarkus-app/quarkus-run.jar`.

Se você quiser construir um _über-jar_, execute o seguinte comando:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

A aplicação, empacotada como um _über-jar_, pode ser executada usando `java -jar target/*-runner.jar`.

## Como executar os testes

Para executar os testes unitários e de integração do projeto, utilize o comando abaixo na raiz do diretório:

```shell script
./mvnw test
```

**Importante:** É necessário ter o Docker instalado e em execução para que os testes funcionem corretamente, pois eles iniciam um banco de dados de teste em um contêiner.

## Criando um executável nativo

Você pode criar um executável nativo usando:

```shell script
./mvnw package -Dnative
```

Ou, se você não tiver o GraalVM instalado, pode construir o executável nativo em um contêiner usando:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

Você pode então executar seu executável nativo com: `./target/tinnova-1.0-SNAPSHOT-runner`

Se você quiser saber mais sobre a construção de executáveis nativos, consulte <https://quarkus.io/guides/maven-tooling>.
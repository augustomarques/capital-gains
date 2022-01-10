# Capital Gains

A aplicação recebe uma lista de transações e verifica se estas são passiveis de tributação, e em caso positivo, calcula
o valor do tributo para cada uma das transações abatendo possíveis perdas anteriores.

O projeto foi desenvolvido utilizando:

- [Java](https://www.oracle.com/java/) :: linguagem utilizando no desenvolvimento da aplicação.
- [Maven](https://maven.apache.org/) :: ferramenta para gestão de dependências e construção da aplicação.
- [Jackson](https://github.com/FasterXML/jackson-docs) :: processador JSON. Utilizado na conversão de JSON para
  entidades do projeto e vice-versa.
- [Lombok](https://projectlombok.org/) :: redução de boilarplate.
- [Junit](https://junit.org/junit5/) :: implementação de testes.
- [AssertJ](https://assertj.github.io/doc/) :: escrita de testes de maneiras mais "fluente".
- [Docker](https://www.docker.com/) :: utilizado na conteinerização da aplicação.

## Executando os testes

Os testes pode ser executados utilizando o Maven.  
Para executar os testes unitários, basta executar o comando abaixo na raiz do projeto:

```
mvn test
```

Para executar os testes de integração, basta executar o comando abaixo na raiz do projeto:

```
mvn failsafe:integration-test
```

## Rodando a aplicação

### Docker

Para rodar a aplicação utilizando o Docker precisamos construir a imagem executando o comando abaixo na raiz do projeto:

```
docker build -t capital-gains .
```

E seguida basta executar:

```
docker run -it capital-gains
```

### Maven / Jar

Para executar a aplicação utilizando o arquivo jar, precisamos construí-lo utilizando o Maven. Para isso basta executar
o comando abaixo na raiz do projeto:

```
mvn clean package -DskipTests
```

Em seguida podemos executar o arquivo jar utilizando o seguinte comando:

```
java -jar ./target/capital-gains-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Utilizando a aplicação

Ao executar a aplicação o sistema vai solicitar como entrada de dados uma lista de transações em formato JSON, segue
exemplo:

```
[{"operation":"buy", "unit-cost":10, "quantity": 10000},{"operation":"sell","unit-cost":5, "quantity": 5000},{"operation":"sell", "unit-cost":20, "quantity":5000}]
```

O retorno será uma lista de impostos a serem pagos (também em formato JSON), segue exemplo:

```
[{"tax": 0},{"tax": 0},{"tax": 5000}]
```

A aplicação permite que o procedimento seja executado quantas vezes o usuários desejar.  
Para encerrar basta informar um "linha em branco".

## Decisões técnicas

A aplicação realiza o cálculo de prejuízo/imposto a cada transação da lista que é adicionada a "Carteira de ações" do
usuário. Para identificar se a transação precisa realizar algum destes calculos, foi utilizado o pattern Chain of
Responsibility. O percentual de imposto a ser pago e a faixa a que ele está atrelado, foram isolados em uma classe. Caso uma
nova faixa de imposto venha a ser criada, basta adicionar a mesma a esta classe e a regra passara a ser aplicada.
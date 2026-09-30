# Pedidos de delivery com Observer

Este exercício simula um pedido de delivery. Quando o status muda, o aplicativo do cliente, o painel do restaurante e o aplicativo do entregador recebem uma atualização automaticamente.

## Estrutura dos arquivos

```text
PedidoObserver/
├── pom.xml
├── README.md
├── .gitignore
└── src/
    ├── StatusPedido.java
    ├── Subject.java
    ├── Observer.java
    ├── Pedido.java
    ├── ClienteApp.java
    ├── RestaurantePainel.java
    ├── EntregadorApp.java
    └── Main.java
```

## Como executar no IntelliJ

1. Extraia o ZIP e abra a pasta `PedidoObserver` no IntelliJ.
2. Importe o `pom.xml` como projeto Maven, caso a IDE solicite.
3. Selecione um JDK 17 ou superior como SDK do projeto.
4. Abra `src/Main.java` e execute o método `main`.

O projeto não utiliza bibliotecas externas. Também é possível executar pelo terminal, dentro da pasta do projeto:

```shell
javac -encoding UTF-8 -d out src/*.java
java -cp out Main
```

## Saída esperada

```text
Cliente recebeu notificação: Pedido está PREPARANDO
Restaurante recebeu atualização: Pedido está PREPARANDO
Entregador recebeu atualização: Pedido está PREPARANDO
Cliente recebeu notificação: Pedido está SAIU_PARA_ENTREGA
Restaurante recebeu atualização: Pedido está SAIU_PARA_ENTREGA
Entregador recebeu atualização: Pedido está SAIU_PARA_ENTREGA
Cliente recebeu notificação: Pedido está ENTREGUE
Restaurante recebeu atualização: Pedido está ENTREGUE
Entregador recebeu atualização: Pedido está ENTREGUE
```

O pedido começa em `RECEBIDO`. A criação do pedido e o registro dos observadores não enviam notificações. No `Main`, as notificações começam quando o status passa para `PREPARANDO`.

## Como o padrão aparece no código?

`Subject` define as operações de registrar, remover e notificar observadores. A classe `Pedido` é o Subject concreto: guarda o status e a lista de interessados nas mudanças.

`Observer` define o método `update(Pedido pedido)`. As classes `ClienteApp`, `RestaurantePainel` e `EntregadorApp` são os observadores concretos. Cada uma recebe o pedido, consulta seu status e imprime sua própria mensagem.

O `Main` conecta essas partes. Ele cria o pedido, registra os três observadores e simula as mudanças de estado. Já o enum `StatusPedido` reúne os quatro estados possíveis, evitando depender de textos digitados em vários lugares.

## O que acontece quando o status muda?

Quando chamamos `pedido.setStatus(StatusPedido.PREPARANDO)`, o pedido primeiro atualiza seu atributo `status`. Depois, chama `notifyObservers()`, que percorre a lista e executa `update(this)` em cada observador. Por isso, todos consultam o estado já atualizado.

As notificações acontecem na ordem de registro, dentro da mesma execução do programa. Não há servidor, aplicativo real ou envio pela internet: as mensagens no console representam essas atualizações.

Seguindo o enunciado, toda chamada de `setStatus` atualiza o atributo e notifica os observadores, mesmo se receber novamente o mesmo status. O exercício também não exige validar a sequência dos estados.

## Qual problema o Observer resolve?

Ele resolve a necessidade de avisar vários interessados quando um objeto muda. Aqui, cliente, restaurante e entregador precisam acompanhar o mesmo pedido. Em vez de cada um consultar o status o tempo todo, eles se registram e recebem as atualizações.

## Por que não chamar cada aplicativo diretamente dentro de Pedido?

Isso funcionaria neste exemplo, mas faria o pedido depender de cada classe concreta. Se surgisse um painel de atendimento, precisaríamos alterar `Pedido` para chamar esse painel também.

Com Observer, o pedido só precisa conhecer o contrato `Observer`. Ele não decide se está avisando um cliente, um restaurante ou outro componente. Os observadores também não precisam conhecer uns aos outros.

## Como adicionar um novo interessado?

Basta criar uma classe que implemente `Observer` e registrar uma instância com `registerObserver`. Por exemplo, um painel de atendimento poderia implementar `update(Pedido pedido)` para exibir o status. A classe `Pedido` e os observadores existentes continuariam iguais; apenas o código que monta a aplicação precisaria registrar o novo componente.

## Como deixar de receber notificações?

Use `removeObserver` com o objeto que foi registrado. Por exemplo, dentro do `Main`, após o registro:

```java
pedido.removeObserver(entregador);
pedido.setStatus(StatusPedido.ENTREGUE);
```

Nessa atualização, somente o cliente e o restaurante seriam avisados. Esse exemplo não foi colocado no fluxo principal para manter a saída pedida no exercício.

## Qual é a diferença em relação ao exercício de Strategy?

No exercício do frete, Strategy permitia escolher qual algoritmo calculava o valor. O pedido delegava o cálculo para uma estratégia por vez.

Aqui, Observer permite avisar vários objetos sobre uma mudança. O foco é distribuir a atualização para os interessados registrados. Por isso, `Pedido` mantém uma lista de observadores, enquanto no exercício anterior mantinha uma referência para a estratégia de frete escolhida.

## Onde mais poderíamos usar Observer?

Alguns exemplos são avisar sobre mudanças no estoque, atualizar telas quando os dados mudam e acompanhar eventos de um jogo. Em todos esses casos, há um objeto que muda e outros componentes interessados nessa mudança.

As explicações acima complementam o código; o PDF não traz uma lista separada de perguntas discursivas.

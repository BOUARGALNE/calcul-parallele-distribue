# Activité pratique N°1

## Sommaire
- [x] Partie 1 : Kafka
- - [x] 1.1 Installation de Kafka
- - [x] 1.2 Lancement de Kafka et Zookeeper
- - [x] 1.3 Test de Kafka
- [x] Partie 2 : Docker
- - [x] 2.1 Installation des composants
- - [x] 2.2 Création d'un environnnment
- [x] Partie 3 : Spring cloud
- - [x] 3.1 Initialisation du projet
- - [x] 3.2 Service Producer KAFKA via un Rest Controler 
- - [x] 3.3 Service Consumer KAFKA
- - [x] 3.4 Service Supplier KAFKA
- - [x] 3.5 Service de Data Analytics Real Time Stream Processing avec Kaflka Streams
- - [x] 3.6 Application Web qui permet d'afficher les résultats du Stream Data Analytics en temps réel
---

## Partie 1 : Kafka

### 1.1 Installation de Kafka
Sur le site officiel de Kafka, on peut télécharger la dernière version de Kafka. Pour ce TP, nous avons utilisé la version 2.12-2.3.0. Après avoir téléchargé l'archive, il faut la décompresser et se placer dans le dossier créé. 

### 1.2 Lancement de Kafka et Zookeeper
Il faut lancer le serveur Zookeeper et le serveur Kafka. Pour cela, il faut lancer les commandes suivantes dans deux terminaux différents :

```bash
start .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
```

```bash
start .\bin\windows\kafka-server-start.bat .\config\server.properties
```

![Exécution des commandes](img/image.png)

### 1.3 Test de Kafka
Pour tester Kafka, il faut créer un topic et envoyer des messages. Pour créer un topic, il faut lancer la commande suivante :

```bash
start .\bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test
```

Pour envoyer des messages, il faut lancer la commande suivante :

```bash
start .\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic test
```
Pour saisir un message, il faut appuyer sur la touche Entrée. Puis pour quitter, il faut taper Ctrl+C.


Pour lire les messages, il faut lancer la commande suivante :

```bash
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning
```

![Test de Kafka](img/image-1.png)


![Test Kafka 2](img/image-2.png)


## Patie 2 : Docker 

### 2.1 Installation des composants
Pour installer Docker, il faut suivre les instructions du site officiel de Docker. Pour ce TP, nous avons utilisé la version Docker Desktop 4.24.1  (Windows 10 Home).


![Docker Desktop Interface](img/image-3.png)

### 2.2 Création d'un environnnment 

Pour ceci, on utilisera la documentation **Confluent** : https://developer.confluent.io/quickstart/kafka-docker/ . 

![Confluent guide](img/image-10.png)

Voici le fichier [`docker-compose.yml`](./kafka-java-getting-started/docker-compose.yml) utilisé : 

```yml
---
version: '3'
services:
  broker:
    img/image: confluentinc/cp-kafka:7.5.0
    container_name: broker
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://broker:29092,PLAINTEXT_HOST://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS: 0
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
      KAFKA_PROCESS_ROLES: broker,controller
      KAFKA_NODE_ID: 1
      KAFKA_CONTROLLER_QUORUM_VOTERS: 1@broker:29093
      KAFKA_LISTENERS: PLAINTEXT://broker:29092,CONTROLLER://broker:29093,PLAINTEXT_HOST://0.0.0.0:9092
      KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
      KAFKA_CONTROLLER_LISTENER_NAMES: CONTROLLER
      KAFKA_LOG_DIRS: /tmp/kraft-combined-logs
      CLUSTER_ID: MkU3OEVBNTcwNTJENDM2Qk
```

Pour compiler et démarrer l'environnement :

```bash
docker-compose up -d
```

![Trminal](img/image-6.png)

Ce fichier génère le conteneur suivant :

![Docker Desktop](img/image-4.png)

![Docker ps](img/image-5.png)

Maintenant pour tester la communication on utilise les commandes suivantes :

```bash
docker exec --interactive --tty broker kafka-console-consumer --bootstrap-server broker:9092 --topic R2

docker exec --interactive --tty broker kafka-console-producer --bootstrap-server broker:9092 --topic R2

docker exec --interactive --tty broker kafka-console-consumer --bootstrap-server broker:9092 --topic R66 --property print.key=true --property print.value=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer

docker exec --interactive --tty broker kafka-topics --bootstrap-server broker:9092 --list
```

Exécutées séparemment dans 4 terminaux.

- Envoi de messages de test

![Envoi de messages de test ](img/image-8.png)

- Récepzion des messages de test

![Messages de test](img/image-7.png)

- Liste des topics

![Topics list](img/image-9.png)


## Partie 3 : Spring cloud 
### 3.1 Initialisation du projet 
On initialise le projet avec Spring Initializr en ajoutant les dépendances suivantes :
- Spring Web
- Spring for Apache Kafka
- Spring for Apache Kafka Streams
- Spring Cloud 
- Lombok

![Dependencies](img/image-11.png)

On peut voir que parmi les dépendnace, Stream Cloud Binder Kafka est déjà inclu. 

```xml	
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-stream-test-binder</artifactId>
	<scope>test</scope>
</dependency>
```

### 3.2 Service Producer KAFKA via un Rest Controler
On crée les Page Event :
- [`PageEvent.java`](./spring-cloud-kafka/src/main/java/com/example/springcloudkafka/entities/PageEvent.java) : représente un événement de page
- [`PageEventRestController.java`](./spring-cloud-kafka/src/main/java/com/example/springcloudkafka/web/PageEventRestController.java) : représente un contrôleur REST pour les événements de page

On lance zookeeper et kafka :

![img.png](img/img.png)

On lance le projet et dans un navigateur on tape l'url suivante :
http://localhost:8080/publish/test/blog

avec test le nom du topic et blog le nom de la page.

![img/img_1.png](img/img_1.png)

Résultat :
```json
{
  "name": "blog",
  "user": "U2",
  "date": "2023-10-14T21:21:30.690+00:00",
  "duration": 8892
}
```

### 3.3 Service Consumer KAFKA
Oncréer le service 
- [`PageEventService.java`](./spring-cloud-kafka/src/main/java/com/example/springcloudkafka/services/PageEventService.java) qui va consommer les événements de page.

Puis ajoute la configuration suivante dans le fichier [`application.properties`](./spring-cloud-kafka/src/main/resources/application.properties) :

```properties
spring.cloud.stream.bindings.pageEvents-in-0.destination=test
```
Puis on teste le service en lançant le projet, on envoie un message grace au controlleur REST et on vérifie que le message est bien consommé par le service.

![img/img_2.png](img/img_2.png)

### 3.4 Service Supplier KAFKA
Dans le terminal on ajoute un topic `test2`

Puis on définit la méthode ``Supplier<PageEvent>`` dans [PageEventSupplier.java](./spring-cloud-kafka/src/main/java/com/example/springcloudkafka/services/PageEventSupplier.java) qui va envoyer des événements de page à un topic Kafka.

On ajoute la configuration suivante dans le fichier [`application.properties`](./spring-cloud-kafka/src/main/resources/application.properties) :

```properties
spring.cloud.stream.bindings.pageEventConsumer-in-0.destination=test2
spring.cloud.stream.bindings.pageEventSupplier-out-0.destination=test

spring.cloud.function.definition=pageEventSupplier;pageEventConsumer
```

On lance le projet et on vérifie que le message est bien envoyé dans le topic `test2`

![img/img_3.png](img/img_3.png)

**Note** : on ajoute ``spring.cloud.stream.poller.fixed-delay=2000`` dans le fichier [`application.properties`](./spring-cloud-kafka/src/main/resources/application.properties) pour envoyer un message toutes les 2 secondes.


Dans le terminal on ajoute un topic `test3`
![img/img_4.png](img/img_4.png)

Dans [``PageEventService.java``](./spring-cloud-kafka/src/main/java/com/example/springcloudkafka/services/PageEventService.java) on ajoute la méthode ``Function<PageEvent,PageEvent>`` qui va consommer les événements de page et les envoyer à un autre topic Kafka.
 Dans le fichier [`application.properties`](./spring-cloud-kafka/src/main/resources/application.properties) on ajoute la configuration suivante :

```properties
spring.cloud.stream.bindings.pageEventFunction-in-0.destination=test2
spring.cloud.stream.bindings.pageEventFunction-out-0.destination=test3
```

### 3.5 Service de Data Analytics Real Time Stream Processing avec Kaflka Streams

Dans [`PageEventService.java`](./spring-cloud-kafka/src/main/java/com/example/springcloudkafka/services/PageEventService.java) on ajoute la méthode ``Function<KStream<String, PageEvent>, KStream<String, Long>>`` qui va consommer les événements de page et renvoie le nombre de pages vues par utilisateur.


Dans le fichier [`application.properties`](./spring-cloud-kafka/src/main/resources/application.properties) on ajoute la configuration suivante :

```properties
spring.cloud.stream.bindings.kStreamFunction-in-0.destination=test2
spring.cloud.stream.bindings.kStreamFunction-out-0.destination=test4
```
On lance le consumer de test4 avec les propriétés suivantes :
```bash
bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test4 --property print.key=true --property print.value=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer

```

On lance le projet et on vérifie que le message est bien envoyé dans le topic `test4`

![img/img_5.png](img/img_5.png)

Sur [``PageEventRestController.java``](./spring-cloud-kafka/src/main/java/com/example/springcloudkafka/web/PageEventRestController.java) on ajoute la méthode ``Flux<Map<String,Long>> analytics()`` qui va consommer les événements de page et renvoie le nombre de pages vues par utilisateur.

On lance le projet et on teste la méthode en accédant à l'url suivante : http://localhost:8080/analytics

![img/img_6.png](img/img_6.png)

### 3.6 Application Web qui permet d'afficher les résultats du Stream Data Analytics en temps réel

Dans Ressources/static on crée un fichier [index.html](./spring-cloud-kafka/src/main/resources/static/index.html) qui va afficher les résultats du Stream Data Analytics en temps réel en utilisant le framework [Smoothie Charts](http://smoothiecharts.org/).

Il faut préciser la source du flux :
```javascript
var stockEventSource = new EventSource("/analytics");
  stockEventSource.addEventListener("message", function (event) {
    pages.forEach(function (v) {
      val = JSON.parse(event.data)[v];
      courbe[v].append(new Date().getTime(), val);
    });
  });
```

On lance le projet et on teste l'application en accédant à l'url suivante : http://localhost:8080

![img/img_7.png](img/img_7.png)

On peut toujours personnalisé l'application en ajoutant analytics pour une page 
```java
@GetMapping(path = "/analytics/{page}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<Map<String,Long>> analytics(@PathVariable String page){...}
```
et en ajoutant la source du flux dans le fichier [index.html](./spring-cloud-kafka/src/main/resources/static/index.html) :
```javascript
var stockEventSource = new EventSource("/analytics/"+page);
```


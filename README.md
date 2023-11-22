Kalah, also called Kalaha or Mancala, is a game in the mancala family imported in the United States by William Julius Champion, Jr. in 1940. <br/>This game is sometimes also called "Kalahari", possibly by false etymology from the Kalahari desert in Namibia.<br/><br/>
The game provides a Kalah board and a number of stones or counters. The board has 6 small pits, called houses, on each side; and a big pit, called a kalaha, at each end. The objective of the game is to capture more stones than one's opponent - [Wikipedia](https://www.wikiwand.com/en/Kalah)

## Board Setup

* `2 Players`
* `12 pits` - 6 on each player's side
* `72 stones` - 6 stones in each pit at the start of the game
* `2 kalahas` - 1 on each players side, empty at the start of the game

## Game Play
The player who begins with the first move picks up all the stones in anyone of his own six pits, and sows the stones on to the right, one in each of the following pits, including his own Kalaha. No stones are put in the opponents' Kalaha. If the player's last stone lands in his own Kalaha, he gets another turn. This can be repeated several times before it's the other player's turn.

## Capturing Stones
During the game the pits are emptied on both sides. Always when the last stone lands in an own empty pit, the player captures his own stone and all stones in the opposite pit (the other players' pit) and puts them in his own Kalaha.

## The Game Ends
The game is over as soon as one of the sides run out of stones. The player who still has stones in his pits keeps them and puts them in his/hers Kalaha. Winner of the game is the player who has the most stones in his Kalaha.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

- [JDK 21](https://openjdk.org/projects/jdk/21/)
- [Maven 3+](https://maven.apache.org/install.html)
- (optional) Docker [Ubuntu](https://docs.docker.com/install/linux/docker-ce/ubuntu/), [MacOS](https://docs.docker.com/docker-for-mac/install/#install-and-run-docker-for-mac), [Windows](https://docs.docker.com/docker-for-windows/install/)


### Installing
Navigate to the Kalaha project
```
cd ~/Kalaha
```

To create the runnable `JAR` file , the following steps should be followed:<br/>
Execute a `maven clean package`, to clean all previous builds and take the compiled code and package it in a `JAR` file.<br/>
After the code has been packaged, execute the maven install command, to install the package into the local repository, for use as a dependency in other projects locally.

```
mvn clean package
mvn install
```
## Running the tests

During the maven `clean` `install` and `package` lifecycles unit tests are executed, but if you would like to execute them seperately you can execute the following command.

```
mvn test
```
All unit test results can be found on the console output <br/>

## Execution

### JAR

Navigate to the location of the `kalaha-web-0.0.1-SNAPSHOT.jar` file

```
cd ~/Kalaha/kalaha-web/target
```
To start up the `Kalaha Web Application`, execute the `kalaha-web-0.0.1-SNAPSHOT.jar` file
```
java -jar kalaha-web-0.0.1-SNAPSHOT.jar
```

### DOCKER
#### Build the image locally
* Navigate to the root `Kalaha` directory
```
cd Kalaha/
```
* Build the `JAR` files by executing the steps mentioned above 
```
mvn clean package
mvn install
```
* Execute the `docker` build
```
docker build -t 'kalaha' .
```
* Creating the docker container is just as easy
```
docker run -d --restart=always -p 8080:8080 kalaha
  ```
In your web browser of choice, navigate to http://localhost:8080/ and ENJOY!

To stop and destroy the container you can execute the following commands
* List active containers
```
docker ps
```
* Stop the `Kalaha` container 
```
docker stop <CONTAINER ID>
```
* Remove the `Kalaha` container
```
docker rm <CONTAINtER ID>
```
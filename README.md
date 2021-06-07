#Axon Event Store Event Initializer
Simple application showing how to push events into an event store without having to go through the whole command side processing

## Prerequisites

- Java 11

## Running the application(s) locally

**Requirements**

> You can [download](https://download.axoniq.io/axonserver/AxonServer.zip) a ZIP file with AxonServer as a standalone JAR. This will also give you the AxonServer CLI and information on how to run and configure the server.
>
> Alternatively, you can run the following command to start AxonServer in a Docker container:
>
> ```
> docker run -d --name axonserver -p 8024:8024 -p 8124:8124 axoniq/axonserver
> ```

```shell script
mvn spring-boot:run
```
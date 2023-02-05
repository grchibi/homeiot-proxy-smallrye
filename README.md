# homeiot-proxy-smallrye

## Build prerequisites

Before building, install the following software.
- OpenJDK 11
- Maven

After installing the software, execute the following command.
```
$ mvn wrapper:wrapper
```

## Prepare the client certificate file

To subscribe the MQTT broker(AWS IoT Core), need to identify the client.
You have to copy the certificates to the resource directory.
- AmazonRootCA1.pem: AWS CA Certificate
- [ClientCert].pem.crt: the client certificate
- [ClientCert].pem.key: the private key for the client certificate

## Modify the application's property file

Modify the application.properties file.
- mp.messaging.incoming.mqtt-broker.host
- mp.messaging.incoming.mqtt-broker.client-id
- mp.messaging.incoming.mqtt-broker.ssl.keystore.location
- mp.messaging.incoming.mqtt-broker.ssl.keystore.password
- quarkus.rest-client.homeiot-api.url

## Build

```
$ ./mvnw package
```

## Run the service

To run the proxy service, execute this command.
```
$ /usr/bin/java -jar <BASE_DIR>/target/quarkus-app/quarkus-run.jar
```

## Register to Systemd

```
$ cp -a  homeiot-proxy.service /etc/systemd/system/
$ systemctl enable homeiot-proxy
```

To run as a systemd service, execute the following command.
```
$ systemctl start homeiot-proxy
```

## Check the logs

You can view the journal logs.
```
<<<<<<< HEAD
$ journalctl -u homeiot-proxy.service --no-pager --since="2023-01-27 18:00:00"
=======
$ journalctl --no-pager -u homeiot-proxy.service
>>>>>>> refs/remotes/origin/main
```


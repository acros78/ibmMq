# IBM-MQ estadisticas
Es una sencilla aplicación para extraer la siguiente información  de un MQ:
* Queue:
* Profundidad de la queue
* Numero de entradas abiertas
* Fecha del último trasferencia
* Hora del último trasferencia
* Numero de salidas abiertas
* Fecha de la último obtención
* Hora de la último obtención

## Compilar 
```
git clone https://github.com/acros78/ibmMq.git
cd ibmMq
mvn package
```

## Ejecutar
```
java -Djava.library.path=<path> -jar mq.jar qMngrStr queueName hostName port channel user 
```
nota: las librias se en encuntran en el entorno del producto mq de ibm `<path>/mqm/java/lib`,mas informacion en [IBM MQ](https://www.ibm.com/us-en/marketplace/secure-messaging)
* lib = entorno de 32 bits
* lib64 = entorno de 64 bis

## Licencia

Copyright © 2018 acros78

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

# Eureka Server
En una arquitectura de microservicios, las instancias de los servicios son dinámicas: pueden escalar (crearse nuevas), morir o cambiar de dirección IP/puerto constantemente (especialmente en la nube).

- **Problema:** Si el Servicio A necesita llamar al Servicio B, no puede tener la IP `192.168.1.50:8081` grabada en el código (hardcoded), porque esa IP mañana podría no existir.
- **Solución:** Funciona como las "Páginas Amarillas". Es un Service Registry. Cada microservicio, al arrancar, se registra en Eureka diciendo: "Hola, soy el 'Servicio-Ventas' y estoy en esta IP:Puerto". Cuando otro servicio quiere hablar con él, le pregunta a Eureka: "¿Dónde está 'Servicio-Ventas'?" y Eureka le da la dirección actual.

## Implementación Paso a Paso
Para que esto funcione, necesitamos dos componentes: el Servidor (directorio) y los Clientes (microservicios).

### 1. El Servidor Eureka (Discovery Server)
Este es un proyecto Spring Boot independiente que servirá como orquestador, se recomienda que por default que el Eureka Server tenga el puerto `8761`.

#### Dependencia (Maven)
````xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
````

#### Habilitar Servidor
````java
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication { ... }
````

#### Configuración (`application.yml`)
````yaml
server:
  port: 8761 # Puerto estándar de Eureka

eureka:
  client:
    registerWithEureka: false # No se registra a sí mismo
    fetchRegistry: false      # No busca otros registros
````

### 2. Clientes (Microservicios)
Cualquier microservicio que quieras que sea "descubrible" debe ser un cliente.

#### Dependencia (Maven)
````xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
````

#### Habilitar Cliente
En versiones modernas de Spring Cloud esto es automático.

#### Configuración (`application.yml`)
````yaml
spring:
  application:
    name: inventory-service # Nombre con el que otros te buscarán

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
````
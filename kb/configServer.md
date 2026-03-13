# Spring Cloud Config Server
El Config Server es el corazón de la configuración en microservicios. En lugar de tener archivos application.yml dentro de cada proyecto, los guardamos en un repositorio de GitHub. Si cambias algo en Git, todos tus microservicios pueden recibir el cambio sin ser recompilados.

## Implementación
### 1. Repositorio de Configuración (GitHub)
Primero, crea un repositorio en GitHub (ej. mi-proyecto-configs).
Dentro del repo, crea archivos para cada microservicio. El nombre debe coincidir con el spring.application.name del servicio:

- `inventory-service.yml`
- `order-service.yml`
- `application.yml` ((Este es especial: lo que pongas aquí lo heredan todos los servicios).

### 2. Server (Config Server)
Este es el microservicio que hace de "puente" entre GitHub y tus apps.

#### Dependencias (`pom.xml`)
````xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
</dependency>
````

#### Habilitar Servidor
````java
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
````

#### Configuración (`application.yml`)
````yaml
server:
  port: 8888

spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/tu-usuario/tu-repo-configs
          default-label: main # Rama por defecto
````

### 3. Clientes (Microservicios)
Ahora, tus servicios deben configurarse para ir a pedirle sus datos al servidor.

#### Dependencias (`pom.xml`)
````xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
````

#### El archivo "Bootstrap"
En el microservicio cliente, dentro de `src/main/resources/`, crea un archivo llamado `application.yml` (o `bootstrap.yml`) con esto:

````yaml
spring:
  application:
    name: inventory-service # DEBE coincidir con el nombre en GitHub
  config:
    import: "optional:configserver:http://localhost:8888"
````

### Flujo de Datos
1. Microservicio arranca: "Hola, me llamo inventory-service".
2. Config Server recibe: "Entendido, voy a buscar inventory-service.yml a GitHub".
3. Config Server entrega: "Aquí tienes tus credenciales de base de datos y tu puerto".
4. Microservicio funciona: Se conecta a la DB y levanta con los datos de Git.
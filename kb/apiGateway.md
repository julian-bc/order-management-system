# API Gateway
El API Gateway actúa como un Proxy Inverso. Su trabajo es recibir una petición, entender a dónde va, y redirigirla al microservicio correspondiente.

Para más información, consultar en la documentación oficial:
[Spring Cloud Gateway](https://docs.spring.io/spring-cloud-gateway/reference/index.html)

## ¿Qué problemas soluciona?
- **Punto de entrada único:** El cliente solo habla con un servidor.
- **Seguridad centralizada:** Puedes validar el JWT (token) aquí antes de que la petición llegue a los servicios.
- **Abstracción:** Puedes cambiar tus servicios de puerto o IP y el cliente nunca se entera.
- **Cross-cutting concerns:** Manejo de logs, límites de peticiones (Rate Limiting) y CORS en un solo lugar.

## Implementación
### Dependencias
Necesitas el starter de Gateway y, como ya tienes Eureka, el cliente de descubrimiento para que el Gateway sepa dónde están los servicios automáticamente.
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

### Configuración de Rutas (`application.yml`)
Aquí es donde ocurre la "magia". Vamos a configurar el Gateway para que cuando alguien llame a `/products/**`, lo mande al servicio de productos.

````yaml
server:
  port: 8080 # Puerto del Gateway

spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      routes:
        # Ruta para el servicio de Productos
        - id: product-service
          uri: lb://product-service # "lb://" le dice que use Load Balancer de Eureka
          predicates:
            - Path=/api/product/** # Si la URL empieza con esto...
          filters:
            - StripPrefix=1 # Elimina la primera parte de la ruta si el microservicio no la usa

        # Ruta para el servicio de Órdenes
        - id: order-service
          uri: lb://order-service
          predicates:
            - Path=/api/order/**

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
````

### Conceptos Clave
Para entender cómo funciona, piensa en estos tres pilares:

1. **Rutas (Route):** La unión de un ID, un destino (URI), un predicado y filtros.

2. **Predicados (Predicate):** Es una condición "Si...". Ejemplo: "Si el path es /api/order" o "Si la petición llega después de las 12 PM". Si se cumple, se sigue la ruta.

3. **Filtros (Filter):** Permiten modificar la petición o la respuesta. Puedes añadir cabeceras, quitar prefijos de la URL o incluso autenticar al usuario.

### ¿Cómo fluye la petición?
- El Cliente hace un GET a `http://gateway:8080/api/product/1`.

- El Gateway revisa sus Predicados: "Ah, el path coincide con `/api/product/**`".

- El Gateway consulta a Eureka: "¿Dónde está `product-service`?". Eureka le da la IP.

- El Gateway aplica Filtros: (Ej: Quita el `/api` de la URL).

- El Gateway redirige la petición al microservicio final.
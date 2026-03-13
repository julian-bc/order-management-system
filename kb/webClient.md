# WebClient
WebClient es una interfaz moderna de Spring (introducida en Spring Framework 5) que funciona como un cliente HTTP. Su trabajo es permitir que tu microservicio se comunique con otros. Es la pieza central de la librería Spring WebFlux.

## ¿Qué problema soluciona?
WebClient es de naturaleza no bloqueante (asincrónico), WebClient envía la petición y, en lugar de esperar de brazos cruzados, libera el hilo para que siga atendiendo a otros usuarios. Cuando la respuesta llega, WebClient "avisa" y entrega el resultado. Es como pedir una pizza y que te den un avisador: puedes seguir haciendo cosas hasta que el avisador vibre.

## ¿Cómo funciona? (Conceptos Mono y Flux)
WebClient no devuelve objetos directamente (como un `String` o un `User`), sino que devuelve contenedores reactivos:

- Mono: Promete devolver un solo objeto (o ninguno).
- Flux: Promete devolver una lista o flujo de objetos.

## Implementación
1. Instalación de dependencia.
````xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
````

2. Implementación en un servicio.
````java
@Slf4j
@Service
public class ExternalApiService {

    private final WebClient webClient;

    public ExternalApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.ejemplo.com").build();
    }

    public void getProductInfo(String id) {
        webClient.get()
            .uri("/products/{id}", id)
            .retrieve()
            .bodyToMono(ProductDTO.class) // Esperamos un solo producto
            .subscribe(product -> {
                log.info("Datos recibidos: {}", product.getName());
            });
    }
}
````

## Usos y Ventajas
- **Escalabilidad:** Al no bloquear hilos, puedes manejar miles de peticiones simultáneas con muy poca memoria. Es ideal para microservicios con mucho tráfico.
- **Soporte Sincrónico:** Aunque es reactivo, también puedes obligarlo a esperar (bloquear) usando `.block()` si tu proyecto aún no es 100% reactivo.
- **Streaming:** Puede recibir datos en tiempo real (como un flujo de precios de acciones) sin cerrar la conexión.
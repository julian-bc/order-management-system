# CommandLineRunner
Interfaz sencilla de Spring Boot que se utiliza para ejecutar un bloque de código específicamente después de que la aplicación haya arrancado por completo, pero antes de que se considere que está lista para recibir peticiones de usuarios.

## ¿Cómo se implementa?
Hay dos formas comunes de hacerlo, pero la más moderna y limpia es usando un `@Bean` en una clase de configuración (o en tu clase principal).

````java
@Slf4j
@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {
        return args -> {
            log.info("Iniciando carga de datos de prueba...");
            repository.save(new Product("Laptop", 1200.0));
            repository.save(new Product("Mouse", 25.0));
            log.info("¡Base de datos inicializada con {} productos!", repository.count());
        };
    }
}
````

## Usos comunes
- Carga de datos inicial (Seed Data)
- Verificación de Conectividad
- Lectura de Variables
- Tareas de Limpieza
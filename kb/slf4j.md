# Slf4j
SLF4J (Simple Logging Facade for Java) no es un sistema de log en sí mismo, sino una fachada o interfaz.

SLF4J es el control remoto universal y los motores de log (Logback, Log4j2, Java Util Logging) son los diferentes televisores. Tú programas usando los botones del control remoto (SLF4J), y no te importa qué televisor esté conectado detrás; el comando funcionará igual.

## Por qué se usa? (El Infierno de Dependencias)
Históricamente, cuando se usaban simultáneamente librerías basadas en distintos motores como Log4j y Java Util Logging, la consola se convertía en un caos de formatos inconsistentes. SLF4J resuelve este conflicto actuando como una capa de abstracción que unifica todos esos mensajes, permitiendo que Spring Boot los procese bajo un mismo estándar (Logback) y logrando así un desacoplamiento total entre el código y el motor de log.

## Ventajas
1. **Abstracción Total:** Puedes cambiar de motor de log (ej. de Logback a Log4j2) solo cambiando una dependencia en el `pom.xml`, sin tocar ni una sola línea de código Java.
2. **Optimización con Placeholders ({}):** Esta es su mejor característica técnica.
3. **Manejo de Excepciones:** Si pasas una excepción como último argumento, SLF4J imprime el stack trace completo automáticamente.

## Niveles de Log
1. `log.trace()`: Detalles muy finos (casi nunca se usa en prod).
2. `log.debug()`: Información útil para depurar en desarrollo.
3. `log.info()`: Eventos normales del sistema (ej. "Usuario logueado").
4. `log.warn()`: Situaciones inesperadas pero que no rompen la app.
5. `log.error()`: Fallos críticos o excepciones.
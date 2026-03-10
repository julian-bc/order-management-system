# Multi-module Maven
Un proyecto multi-módulo en Maven es una estructura que permite gestionar un grupo de subproyectos (módulos) bajo un único proyecto raíz o "agregador". En lugar de tener múltiples aplicaciones independientes, estas se agrupan para compartir configuraciones y ciclos de vida.

## Cómo funcionan?
La esctructura se basa en dos componentes claves: **Agregación** y **Herencia**:

- **POM Padre (Aggregator):** Es el archivo `pom.xml` en la raíz del proyecto. Su función es listar los módulos que lo componen. Debe tener el tipo de empaquetado definido como `<packaging>pom</packaging>`.
- **Submódulos:** Son proyectos Maven regulares ubicados como subcarpetas. Cada submódulo tiene su propio `pom.xml`, pero apuntan al padre mediante la etiqueta `<parent>`.

## Ventajas
- Reducción de duplicación, al definir dependencias, plugins y propiedades desde el padre todos los hijos lo heredaran automáticamente.
- Gestión centralizada de dependencias, con la sección `<dependencyManagement>`, puedes definir la versión de una librería en un solo lugar. Los hijos solo necesitan declarar el `groupId` y `artifactId`, asegurando que todos usen la misma versión.
- Construcción en un solo paso, podras compilar, probar y empaquetar todo el sistema (backend, frontend, librerías compartidas) con un único comando desde la raíz.
- Separación de responsabilidades, facilita arquitecturas limpias o de microservicios, donde puedes tener un módulo para la lógica de negocio (core), otro para la API (web) y otro para utilidades, manteniendo el código organizado.

## Implementación básica
1. Crear Padre: Generar un proyecto en Maven y cambiar su empaquetado a `pom`.

````xml
<groupId>com.ejemplo</groupId>
<artifactId>proyecto-raiz</artifactId>
<version>1.0-SNAPSHOT</version>
<packaging>pom</packaging>
````

2. Declarar módulos: En el `pom.xml` del padre se añaden los hijos.

````xml
<modules>
    <module>modulo-core</module>
    <module>modulo-web</module>
</modules>
````

3. Configurar Hijos: Cada hijo referencia al padre en su propio `pom.xml`.

````xml
<parent>
    <groupId>com.ejemplo</groupId>
    <artifactId>proyecto-raiz</artifactId>
    <version>1.0-SNAPSHOT</version>
</parent>
````
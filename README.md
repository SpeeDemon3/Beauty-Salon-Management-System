# Beauty Salon Management System

Sistema de Gestión para Salón de Belleza desarrollado en Java y Spring Boot. Proporciona backend RESTful con arquitectura en tres capas, gestión de citas, inventario, ventas (POS), CRM y seguridad con Spring Security. Utiliza JPA/Hibernate para persistencia y pruebas con JUnit/Mockito.

## Características principales
- Gestión de citas (Appointment Management)
- Inventario de productos y servicios
- Punto de venta (POS) y registro de transacciones
- Gestión de clientes (CRM)
- Seguridad y autenticación con Spring Security
- Arquitectura escalable y mantenible

## Arquitectura
- **Backend RESTful**: Java + Spring Boot
- **Persistencia**: JPA/Hibernate
- **Pruebas**: JUnit y Mockito
- **Base de datos**: MySQL
- **Seguridad**: Spring Security

## Estructura del proyecto
```
src/
  main/
    java/com/ruiz/Beauty/Salon/Management/System/
      controller/
      model/
      repository/
      service/
    resources/
      application.properties
      static/
      templates/
  test/
    java/com/ruiz/Beauty/Salon/Management/System/
```

## Instalación y configuración

### Requisitos previos
- Java 17 o superior
- Maven
- MySQL Server

### Configuración de la base de datos
Edita el archivo `src/main/resources/application.properties` con tus credenciales de MySQL:

```
spring.datasource.url=jdbc:mysql://localhost:3306/beauty_salon_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=TU_USUARIO_MYSQL
spring.datasource.password=TU_CONTRASEÑA_MYSQL
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### Instalación
1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu_usuario/Beauty-Salon-Management-System.git
   ```
2. Accede al directorio del proyecto:
   ```bash
   cd Beauty-Salon-Management-System
   ```
3. Compila el proyecto:
   ```bash
   ./mvnw clean install
   ```

### Ejecución
Inicia la aplicación:
```bash
./mvnw spring-boot:run
```
El backend estará disponible en `http://localhost:8080`.

## Pruebas
Ejecuta las pruebas unitarias y de integración:
```bash
./mvnw test
```

## Endpoints principales
- `/api/appointments` — Gestión de citas
- `/api/products` — Inventario de productos
- `/api/services` — Servicios ofrecidos
- `/api/clients` — Gestión de clientes
- `/api/transactions` — Punto de venta y ventas

## Seguridad
- Autenticación y autorización con Spring Security
- Roles: ADMIN, EMPLEADO

## Tecnologías utilizadas
- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Spring Security
- JUnit, Mockito
- Maven

## Contribución
Las contribuciones son bienvenidas. Por favor, abre un issue o envía un pull request.

## Licencia
Este proyecto está bajo la licencia MIT.

## Autor
Antonio Ruiz Benito


# Korvia - Avance 3

Prototipo de tienda de computadoras desarrollado con **Spring Boot 4.1.1**, **Thymeleaf**, **Bootstrap**, **MySQL**, **Spring Data JPA/Hibernate** y **Bean Validation**.

Esta versión incorpora persistencia real para las tablas `roles`, `usuarios`, `categorias`, `productos` y `reservas`, además de CRUD de categorías y productos, consultas multitabla con DTO, selects en cascada y validación de formularios.

## Documentación del avance

- **Informe final APF3 (Word):** `docs/avance3/InformeProyectoAPF3_Grupo8_FINAL.docx`
- **Informe final APF3 (PDF):** `docs/avance3/InformeProyectoAPF3_Grupo8_FINAL.pdf`
- **Capturas utilizadas en el informe:** `docs/avance3/capturas/`
- **Modelo entidad-relación:** `docs/avance3/modelo-er.md`
- **Script de base de datos:** `database/korvia.sql`


## 1. Requisitos

- **JDK 17 o superior.** El proyecto está configurado para Java 21; la compilación y las pruebas también se verificaron con `java.version=17`.
- **MySQL 8 o superior** en `localhost:3306`.
- **MySQL Workbench** (recomendado) o el cliente `mysql` en el PATH.
- No es necesario instalar Maven: el proyecto incluye el Maven Wrapper (`mvnw` / `mvnw.cmd`).

Verificar Java:

```powershell
java -version
```

Verificar MySQL:

```powershell
mysql --version
```

## 2. Si usas otra versión de Java

El proyecto está configurado para Java 21 en `pom.xml`:

```xml
<properties>
    <java.version>21</java.version>
</properties>
```

Si tu JDK es 17, cambia el valor a `17`. Si usas otra versión compatible con Spring Boot 4.1.1, coloca esa versión. Después ejecuta:

```powershell
.\mvnw.cmd clean test
```

Si las pruebas pasan, tu versión de Java es compatible. En un IDE, asegúrate también de seleccionar el mismo JDK para el proyecto.

La compilación con Java 17 se verificó con:

```powershell
.\mvnw.cmd "-Djava.version=17" clean test
```

## 3. Crear la base de datos

### Opción A: MySQL Workbench (recomendada)

1. Abre MySQL Workbench y conéctate a tu servidor local.
2. Abre el archivo `database/korvia.sql`.
3. Ejecuta todo el script.
4. Verifica que se creó la base `korvia` con las tablas `roles`, `usuarios`, `categorias`, `productos` y `reservas`.

### Opción B: consola en Windows

```powershell
cmd /c "mysql --default-character-set=utf8mb4 -u root -p < database\korvia.sql"
```

### Opción C: macOS o Linux

```bash
mysql --default-character-set=utf8mb4 -u root -p < database/korvia.sql
```

El script crea la base desde cero, carga los datos iniciales y es idempotente: si se ejecuta otra vez, no duplica roles, categorías, usuarios ni productos.

> Si ya tienes una base `korvia` con datos que quieres conservar, no ejecutes el script sin revisar antes los cambios. Para reiniciar todo desde cero puedes eliminar la base y volver a ejecutar el script, pero eso borra los datos existentes.

## 4. Configurar la contraseña de MySQL

La contraseña **no está escrita en el proyecto**. Spring Boot la lee de la variable de entorno `DB_PASSWORD`.

### PowerShell

```powershell
$env:DB_PASSWORD = "tu_password_local"
```

### CMD

```cmd
set DB_PASSWORD=tu_password_local
```

### IntelliJ IDEA

1. Abre `Run > Edit Configurations`.
2. Selecciona la configuración de `KorviaApplication`.
3. En `Environment variables`, agrega:

```text
DB_PASSWORD=tu_password_local
```

La variable `DB_PASSWORD` solo existe en la terminal donde la defines. Si abres otra terminal, reinicias VS Code o ejecutas la aplicación desde el botón de “Run” del IDE, debes configurarla también allí.

Si tu usuario de MySQL no es `root`, edita localmente `src/main/resources/application.properties`:

```properties
spring.datasource.username=tu_usuario
```

No subas contraseñas ni configuraciones locales al repositorio.

## 5. Ejecutar las pruebas

Las pruebas usan **H2 en memoria**, por lo que no necesitan MySQL:

```powershell
.\mvnw.cmd clean test
```

Resultado esperado:

```text
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

En macOS o Linux:

```bash
./mvnw clean test
```

Si aparece un error de permisos en macOS/Linux:

```bash
chmod +x mvnw
```

## 6. Ejecutar la aplicación

Con MySQL encendido y `DB_PASSWORD` configurado:

```powershell
.\mvnw.cmd spring-boot:run
```

En macOS o Linux:

```bash
./mvnw spring-boot:run
```

Abre en el navegador:

```text
http://localhost:8080
```

### Usuarios de prueba

| Rol | Correo | Contraseña |
|---|---|---|
| Administrador | `admin@korvia.pe` | `admin123` |
| Cliente | `juan@email.com` | `123456` |

## 7. Funcionalidades principales

- Catálogo, búsqueda y detalle de productos.
- Carrito por sesión HTTP.
- Reservas de productos con control de stock y vencimiento de 24 horas.
- Checkout con generación de boleta en PDF.
- Solicitud de cotización en PDF.
- Panel administrativo.
- CRUD completo de categorías y productos.
- Validación de formularios con mensajes visibles.
- Reportes con consultas multitabla proyectadas a DTO.
- Selects en cascada de categoría → producto en reservas y reportes.
- APIs para cargar productos y filtrar reservas por AJAX.

## 8. Estructura del proyecto

```text
src/main/java/pe/edu/utp/korvia/
  config/       Datos iniciales
  controller/   Controladores MVC y API
  dto/          Formularios y proyecciones de consultas
  model/        Entidades JPA y POJOs de dominio
  repository/   Repositorios Spring Data JPA
  service/      Reglas de negocio y transacciones

src/main/resources/
  templates/    Vistas Thymeleaf
  static/       CSS, JavaScript e imágenes
  application.properties

src/test/       Pruebas con H2
database/       Script SQL de creación y datos iniciales
docs/avance3/   Informe final, modelo ER y capturas
```

## 9. Persistencia y alcance

Se persisten en MySQL:

- `roles`
- `usuarios`
- `categorias`
- `productos`
- `reservas`

Quedan en memoria en esta entrega:

- Boletas.
- Cotizaciones.
- Carrito de compras, que además es independiente por sesión HTTP.

Por eso, al reiniciar la aplicación se conservan los productos, usuarios, categorías y reservas, pero se pierden las boletas, cotizaciones y carritos. Esta decisión está documentada en el informe como trabajo futuro.

## 10. Problemas comunes

| Problema | Causa | Solución |
|---|---|---|
| `Access denied for user 'root'` | Contraseña incorrecta | Configurar bien `DB_PASSWORD` |
| `Access denied ... (using password: NO)` | No se definió `DB_PASSWORD` en esa terminal | Ejecutar `$env:DB_PASSWORD = "..."` en la misma terminal antes de arrancar |
| `Unknown database 'korvia'` | No se ejecutó el script SQL | Ejecutar `database/korvia.sql` |
| `Communications link failure` | MySQL no está encendido | Iniciar el servicio de MySQL |
| `Port 8080 was already in use` | Otro proceso usa el puerto | Cerrar la aplicación anterior o cambiar `server.port` |
| `release version 21 not supported` | El JDK es menor que la versión configurada | Cambiar `<java.version>` en `pom.xml` o instalar un JDK compatible |
| `Table 'korvia.productos' doesn't exist` | La base está incompleta | Ejecutar el script SQL |
| Acentos o eñes dañados | Codificación incorrecta | Usar `--default-character-set=utf8mb4` o Workbench |
| `mvnw: Permission denied` en macOS/Linux | Falta permiso de ejecución | Ejecutar `chmod +x mvnw` |

## 11. Documentación

- `docs/avance3/InformeProyectoAPF3_Grupo8_FINAL.docx`: informe final del avance en Word.
- `docs/avance3/InformeProyectoAPF3_Grupo8_FINAL.pdf`: versión PDF del informe.
- `docs/avance3/modelo-er.md`: diagrama entidad-relación.
- `docs/avance3/capturas/`: capturas de evidencia utilizadas en el informe.
- `database/korvia.sql`: script de base de datos.

## 12. Nota académica

Este proyecto es un prototipo académico. Las contraseñas de los usuarios se almacenan en texto plano y las rutas administrativas no tienen un filtro de autorización del lado del servidor. Para un entorno real se debe incorporar Spring Security, cifrado de contraseñas, validación adicional y control de acceso.

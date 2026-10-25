# Proyecto Marcos Web - Korvia

Sistema web de e-commerce de productos tecnológicos para la empresa **Korvia**, desarrollado para el curso **Marcos de Desarrollo Web** (6.º ciclo, Ingeniería de Sistemas).

## Integrantes

- Liendo Teran, David Josue (U24219926)
- Duffoó Anamaria, Renzo Paolo (U24217453)
- Huayta Huiman, Marco Orlando (U24221284)
- Vilca Valle, Cristofer Edwar (U24221943)

## Contenido del repositorio

- [`korvia/`](./korvia): código fuente Spring Boot + Thymeleaf, pruebas y documentación del tercer avance.
- [`korvia/database/korvia.sql`](./korvia/database/korvia.sql): script DDL + datos iniciales para MySQL.
- [`korvia/docs/avance3/InformeProyectoAPF3_Grupo8_FINAL.docx`](./korvia/docs/avance3/InformeProyectoAPF3_Grupo8_FINAL.docx): informe final del APF3 en Word.
- [`korvia/docs/avance3/InformeProyectoAPF3_Grupo8_FINAL.pdf`](./korvia/docs/avance3/InformeProyectoAPF3_Grupo8_FINAL.pdf): versión PDF del informe.
- [`korvia/docs/avance3/capturas/`](./korvia/docs/avance3/capturas): capturas utilizadas como evidencia.

## Inicio rápido

```powershell
cd korvia
$env:DB_PASSWORD='tu_password_local'
.\mvnw.cmd spring-boot:run
```

La aplicación queda disponible en `http://localhost:8080`. Las instrucciones completas, requisitos y credenciales de prueba están en [`korvia/README.md`](./korvia/README.md).

## Nota académica

La contraseña de MySQL no se almacena en el repositorio: se lee de la variable de entorno `DB_PASSWORD`. Las credenciales de los usuarios de prueba (`admin@korvia.pe` / `admin123` y `juan@email.com` / `123456`) son datos académicos y no representan un esquema de seguridad de producción.

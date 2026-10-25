-- ============================================================
-- Korvia - Script de base de datos (Avance 3)
-- Motor: MySQL 8.x
-- Ejecutar en MySQL Workbench o consola (UTF-8):
--   mysql --default-character-set=utf8mb4 -u root -p < database/korvia.sql
--
-- Nota: la aplicación usa spring.jpa.hibernate.ddl-auto=update, por lo
-- que Hibernate también puede crear/actualizar las tablas. Este script
-- queda como entregable y permite crear la base desde cero.
-- ============================================================

CREATE DATABASE IF NOT EXISTS korvia
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE korvia;

-- Fuerza UTF-8 en la conexión del cliente para conservar tildes y eñes.
SET NAMES utf8mb4;

-- ============================================================
-- 1. TABLA roles
-- ============================================================
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_roles_nombre (nombre)
) ENGINE=InnoDB;

-- ============================================================
-- 2. TABLA categorias
-- ============================================================
CREATE TABLE IF NOT EXISTS categorias (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    codigo VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_categorias_nombre (nombre),
    UNIQUE KEY uk_categorias_codigo (codigo)
) ENGINE=InnoDB;

-- ============================================================
-- 3. TABLA usuarios
-- ============================================================
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    password VARCHAR(255) NOT NULL,
    rol_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_usuarios_email (email),
    KEY idx_usuarios_rol (rol_id),
    CONSTRAINT fk_usuarios_rol FOREIGN KEY (rol_id) REFERENCES roles (id)
) ENGINE=InnoDB;

-- ============================================================
-- 4. TABLA productos
-- ============================================================
CREATE TABLE IF NOT EXISTS productos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(200) NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    descripcion_larga TEXT,
    precio DOUBLE NOT NULL,
    stock INT NOT NULL,
    reservado INT NOT NULL DEFAULT 0,
    imagen VARCHAR(255),
    categoria_id BIGINT NOT NULL,
    cpu VARCHAR(100),
    ram VARCHAR(100),
    almacenamiento VARCHAR(100),
    gpu VARCHAR(100),
    PRIMARY KEY (id),
    KEY idx_productos_categoria (categoria_id),
    KEY idx_productos_nombre (nombre),
    CONSTRAINT fk_productos_categoria FOREIGN KEY (categoria_id) REFERENCES categorias (id)
) ENGINE=InnoDB;

-- ============================================================
-- 5. TABLA reservas
-- ============================================================
CREATE TABLE IF NOT EXISTS reservas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    producto_id BIGINT NOT NULL,
    usuario_id BIGINT NULL,
    cliente_nombre VARCHAR(150) NOT NULL,
    cliente_email VARCHAR(150) NOT NULL,
    cantidad INT NOT NULL,
    fecha_reserva DATETIME(6) NOT NULL,
    fecha_expiracion DATETIME(6) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    KEY idx_reservas_producto (producto_id),
    KEY idx_reservas_usuario (usuario_id),
    KEY idx_reservas_estado (estado),
    CONSTRAINT fk_reservas_producto FOREIGN KEY (producto_id) REFERENCES productos (id),
    CONSTRAINT fk_reservas_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- ============================================================
-- DATOS INICIALES
-- ============================================================

-- Roles
INSERT INTO roles (nombre)
SELECT 'ADMIN' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE nombre = 'ADMIN');
INSERT INTO roles (nombre)
SELECT 'CLIENTE' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE nombre = 'CLIENTE');

-- Categorías
INSERT INTO categorias (nombre, codigo)
SELECT 'PC de Escritorio', 'desktop'
WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE codigo = 'desktop');
INSERT INTO categorias (nombre, codigo)
SELECT 'Laptop', 'laptop'
WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE codigo = 'laptop');
INSERT INTO categorias (nombre, codigo)
SELECT 'All-in-One', 'all-in-one'
WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE codigo = 'all-in-one');
INSERT INTO categorias (nombre, codigo)
SELECT 'Componente', 'component'
WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE codigo = 'component');

-- Usuarios de prueba
INSERT INTO usuarios (nombre, apellido, email, password, rol_id)
SELECT 'Admin', 'Korvia', 'admin@korvia.pe', 'admin123', r.id
FROM roles r
WHERE r.nombre = 'ADMIN'
  AND NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'admin@korvia.pe');

INSERT INTO usuarios (nombre, apellido, email, password, rol_id)
SELECT 'Juan', 'Pérez', 'juan@email.com', '123456', r.id
FROM roles r
WHERE r.nombre = 'CLIENTE'
  AND NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'juan@email.com');

-- Productos de ejemplo (los mismos nombres que usa DataInitializer)
INSERT INTO productos
    (nombre, descripcion, descripcion_larga, precio, stock, reservado, imagen, categoria_id, cpu, ram, almacenamiento, gpu)
SELECT 'PC Básico Oficina',
       'Ideal para tareas de oficina, navegación web y trabajo con documentos.',
       'Equipo para tareas administrativas y ofimática, con Intel Core i3, 8GB de RAM y SSD de 256GB.',
       1200.0, 15, 0, '1-pc-basico-oficina.jpg', c.id,
       'Intel Core i3 12va Gen', '8GB DDR4', '256GB SSD', 'Intel UHD Graphics'
FROM categorias c
WHERE c.codigo = 'desktop'
  AND NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'PC Básico Oficina');

INSERT INTO productos
    (nombre, descripcion, descripcion_larga, precio, stock, reservado, imagen, categoria_id, cpu, ram, almacenamiento, gpu)
SELECT 'PC Gaming Starter',
       'Tu primer PC gamer. Juega tus títulos favoritos en calidad media-alta.',
       'PC gamer de entrada con AMD Ryzen 5 5600, 16GB de RAM y NVIDIA GTX 1650 de 4GB.',
       2800.0, 8, 0, '2-pc-gaming-starter.jpg', c.id,
       'AMD Ryzen 5 5600', '16GB DDR4', '512GB SSD', 'NVIDIA GTX 1650 4GB'
FROM categorias c
WHERE c.codigo = 'desktop'
  AND NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'PC Gaming Starter');

INSERT INTO productos
    (nombre, descripcion, descripcion_larga, precio, stock, reservado, imagen, categoria_id, cpu, ram, almacenamiento, gpu)
SELECT 'PC Gaming Pro',
       'Potencia extrema para jugar en alta calidad y hacer streaming sin lag.',
       'PC gamer de alto rendimiento con AMD Ryzen 7 5800X, 32GB de RAM y RTX 3060 de 12GB.',
       4200.0, 5, 0, '3-pc-gaming-pro.jpg', c.id,
       'AMD Ryzen 7 5800X', '32GB DDR4', '1TB NVMe', 'NVIDIA RTX 3060 12GB'
FROM categorias c
WHERE c.codigo = 'desktop'
  AND NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'PC Gaming Pro');

INSERT INTO productos
    (nombre, descripcion, descripcion_larga, precio, stock, reservado, imagen, categoria_id, cpu, ram, almacenamiento, gpu)
SELECT 'PC Rendimiento Extremo',
       'Lo mejor de lo mejor. Perfecto para gaming 4K, edición y tareas pesadas.',
       'Máximo rendimiento con Intel Core i9 13900K, 64GB DDR5, SSD de 2TB y RTX 4070 Ti.',
       6500.0, 3, 0, '4-pc-rendimiento-extremo.jpg', c.id,
       'Intel Core i9 13900K', '64GB DDR5', '2TB NVMe', 'NVIDIA RTX 4070 Ti'
FROM categorias c
WHERE c.codigo = 'desktop'
  AND NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'PC Rendimiento Extremo');

INSERT INTO productos
    (nombre, descripcion, descripcion_larga, precio, stock, reservado, imagen, categoria_id, cpu, ram, almacenamiento, gpu)
SELECT 'Workstation Data Pro',
       'Para profesionales que trabajan con programas pesados.',
       'Workstation para ciencia de datos y renderizado con Intel Core i9, 64GB DDR5 y RTX 4080.',
       7200.0, 2, 0, '5-workstation-data-pro.jpg', c.id,
       'Intel Core i9 13900K', '64GB DDR5', '2TB NVMe + 4TB HDD', 'NVIDIA RTX 4080'
FROM categorias c
WHERE c.codigo = 'desktop'
  AND NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Workstation Data Pro');

INSERT INTO productos
    (nombre, descripcion, descripcion_larga, precio, stock, reservado, imagen, categoria_id, cpu, ram, almacenamiento, gpu)
SELECT 'PC Compacto Mini',
       'Pequeño pero poderoso. Ahorra espacio sin sacrificar rendimiento.',
       'Formato mini-ITX con Intel Core i5 12400, 16GB de RAM y SSD de 512GB.',
       1800.0, 10, 0, '6-pc-compacto-mini.jpg', c.id,
       'Intel Core i5 12400', '16GB DDR4', '512GB SSD', 'Intel UHD 730'
FROM categorias c
WHERE c.codigo = 'desktop'
  AND NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'PC Compacto Mini');

INSERT INTO productos
    (nombre, descripcion, descripcion_larga, precio, stock, reservado, imagen, categoria_id, cpu, ram, almacenamiento, gpu)
SELECT 'Laptop Estudiante Basic',
       'Portátil económico y confiable para estudios.',
       'Laptop de 14 pulgadas Full HD con Intel Celeron N4500, 8GB de RAM y SSD de 256GB.',
       1600.0, 20, 0, '7-laptop-estudiante-basic.jpg', c.id,
       'Intel Celeron N4500', '8GB DDR4', '256GB SSD', 'Intel UHD'
FROM categorias c
WHERE c.codigo = 'laptop'
  AND NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Laptop Estudiante Basic');

INSERT INTO productos
    (nombre, descripcion, descripcion_larga, precio, stock, reservado, imagen, categoria_id, cpu, ram, almacenamiento, gpu)
SELECT 'Laptop Office Light',
       'Ultraligera para oficina con batería de larga duración.',
       'Laptop de 15.6 pulgadas con Intel Core i5 1235U, 8GB de RAM y SSD de 512GB.',
       2300.0, 12, 0, '8-laptop-office-light.jpg', c.id,
       'Intel Core i5 1235U', '8GB DDR4', '512GB SSD', 'Intel Iris Xe'
FROM categorias c
WHERE c.codigo = 'laptop'
  AND NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Laptop Office Light');

INSERT INTO productos
    (nombre, descripcion, descripcion_larga, precio, stock, reservado, imagen, categoria_id, cpu, ram, almacenamiento, gpu)
SELECT 'Laptop Profesional Plus',
       'Potencia profesional en formato portátil con pantalla 4K.',
       'Laptop profesional con Intel Core i7 1260P, 16GB de RAM, SSD de 1TB y pantalla 4K OLED.',
       3600.0, 7, 0, '9-laptop-profesional-plus.jpg', c.id,
       'Intel Core i7 1260P', '16GB DDR4', '1TB SSD', 'Intel Iris Xe'
FROM categorias c
WHERE c.codigo = 'laptop'
  AND NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Laptop Profesional Plus');

INSERT INTO productos
    (nombre, descripcion, descripcion_larga, precio, stock, reservado, imagen, categoria_id, cpu, ram, almacenamiento, gpu)
SELECT 'Laptop Gaming Portátil',
       'Gaming portátil con pantalla de 144Hz.',
       'Laptop gamer con AMD Ryzen 5 6600H, 16GB DDR5, SSD de 512GB y RTX 3050.',
       3200.0, 6, 0, '10-laptop-gaming-portatil.jpg', c.id,
       'AMD Ryzen 5 6600H', '16GB DDR5', '512GB NVMe', 'NVIDIA RTX 3050'
FROM categorias c
WHERE c.codigo = 'laptop'
  AND NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Laptop Gaming Portátil');

INSERT INTO productos
    (nombre, descripcion, descripcion_larga, precio, stock, reservado, imagen, categoria_id, cpu, ram, almacenamiento, gpu)
SELECT 'Laptop Gaming Elite',
       'Gaming de alto rendimiento con RTX 4070.',
       'Laptop gaming de gama alta con Intel Core i9 13900HX, 32GB DDR5 y RTX 4070.',
       5800.0, 3, 0, '11-laptop-gaming-elite.jpg', c.id,
       'Intel Core i9 13900HX', '32GB DDR5', '1TB NVMe', 'NVIDIA RTX 4070'
FROM categorias c
WHERE c.codigo = 'laptop'
  AND NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Laptop Gaming Elite');

INSERT INTO productos
    (nombre, descripcion, descripcion_larga, precio, stock, reservado, imagen, categoria_id, cpu, ram, almacenamiento, gpu)
SELECT 'Ultrabook Premium 14"',
       'Chasis ultrafino de aluminio, pantalla OLED y batería extendida.',
       'Ultrabook de 14 pulgadas OLED 2.8K con Intel Core i7 1355U, 16GB LPDDR5 y SSD de 1TB.',
       4800.0, 8, 0, '12-laptop-ultrabook-premium.jpg', c.id,
       'Intel Core i7 1355U', '16GB LPDDR5', '1TB SSD', 'Intel Iris Xe'
FROM categorias c
WHERE c.codigo = 'laptop'
  AND NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Ultrabook Premium 14"');

INSERT INTO productos
    (nombre, descripcion, descripcion_larga, precio, stock, reservado, imagen, categoria_id, cpu, ram, almacenamiento, gpu)
SELECT 'Laptop 2 en 1 Convertible',
       'Convertible con pantalla táctil y lápiz óptico.',
       'Laptop 2 en 1 con bisagra de 360 grados, Intel Core i5 1235U y pantalla táctil Full HD.',
       3400.0, 9, 0, '13-laptop-2-en-1-convertible.jpg', c.id,
       'Intel Core i5 1235U', '16GB DDR4', '512GB SSD', 'Intel Iris Xe'
FROM categorias c
WHERE c.codigo = 'laptop'
  AND NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Laptop 2 en 1 Convertible');

INSERT INTO productos
    (nombre, descripcion, descripcion_larga, precio, stock, reservado, imagen, categoria_id, cpu, ram, almacenamiento, gpu)
SELECT 'Laptop Creador Contenido',
       'Diseñada para creadores con pantalla calibrada.',
       'Laptop para creadores con Intel Core i7 13700H, 32GB DDR5 y RTX 4060.',
       4900.0, 4, 0, '14-laptop-cread-or-contedido.jpg', c.id,
       'Intel Core i7 13700H', '32GB DDR5', '1TB NVMe', 'NVIDIA RTX 4060'
FROM categorias c
WHERE c.codigo = 'laptop'
  AND NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Laptop Creador Contenido');

INSERT INTO productos
    (nombre, descripcion, descripcion_larga, precio, stock, reservado, imagen, categoria_id, cpu, ram, almacenamiento, gpu)
SELECT 'All-in-One Familiar',
       'Ideal para el hogar con pantalla 24 pulgadas Full HD.',
       'All-in-One para el hogar con Intel Core i5 1235U, 8GB de RAM y SSD de 512GB.',
       2600.0, 7, 0, '15-all-in-one-familiar.jpg', c.id,
       'Intel Core i5 1235U', '8GB DDR4', '512GB SSD', 'Intel Iris Xe'
FROM categorias c
WHERE c.codigo = 'all-in-one'
  AND NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'All-in-One Familiar');

INSERT INTO productos
    (nombre, descripcion, descripcion_larga, precio, stock, reservado, imagen, categoria_id, cpu, ram, almacenamiento, gpu)
SELECT 'All-in-One Designer',
       'Pantalla 27 pulgadas 4K para diseñadores profesionales.',
       'All-in-One para diseño con pantalla 27 pulgadas 4K, Intel Core i7 1255U y SSD de 1TB.',
       4200.0, 5, 0, '16-all-in-one-designer.jpg', c.id,
       'Intel Core i7 1255U', '16GB DDR4', '1TB SSD', 'Intel Iris Xe'
FROM categorias c
WHERE c.codigo = 'all-in-one'
  AND NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'All-in-One Designer');

INSERT INTO productos
    (nombre, descripcion, descripcion_larga, precio, stock, reservado, imagen, categoria_id, cpu, ram, almacenamiento, gpu)
SELECT 'Memoria RAM 16GB DDR4',
       'Módulo DDR4 3200MHz de alto rendimiento.',
       'Memoria DDR4 de 16GB a 3200MHz compatible con placas Intel y AMD.',
       280.0, 30, 0, '17-memoria-ram-16gb-ddr4.jpg', c.id,
       'DDR4', '16GB', '3200MHz', '-'
FROM categorias c
WHERE c.codigo = 'component'
  AND NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Memoria RAM 16GB DDR4');

INSERT INTO productos
    (nombre, descripcion, descripcion_larga, precio, stock, reservado, imagen, categoria_id, cpu, ram, almacenamiento, gpu)
SELECT 'Disco SSD 1TB NVMe',
       'Unidad SSD NVMe Gen4 hasta 7000 MB/s.',
       'SSD NVMe Gen4 de 1TB con velocidades de hasta 7000 MB/s y formato M.2 2280.',
       450.0, 25, 0, '18-disco-ssd-1tb-nvme.jpg', c.id,
       'NVMe Gen4', '1TB', '7000 MB/s', '-'
FROM categorias c
WHERE c.codigo = 'component'
  AND NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Disco SSD 1TB NVMe');

INSERT INTO productos
    (nombre, descripcion, descripcion_larga, precio, stock, reservado, imagen, categoria_id, cpu, ram, almacenamiento, gpu)
SELECT 'Gabinete Gaming RGB',
       'Gabinete ATX con panel lateral de vidrio templado.',
       'Gabinete ATX mid-tower con vidrio templado, iluminación RGB y soporte para refrigeración líquida.',
       320.0, 18, 0, '19-gabinete-gaming-rgb.jpg', c.id,
       'ATX', 'Vidrio templado', 'RGB', '-'
FROM categorias c
WHERE c.codigo = 'component'
  AND NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Gabinete Gaming RGB');

INSERT INTO productos
    (nombre, descripcion, descripcion_larga, precio, stock, reservado, imagen, categoria_id, cpu, ram, almacenamiento, gpu)
SELECT 'Kit Actualización Intel i5',
       'Kit completo: procesador, motherboard y RAM.',
       'Kit de actualización con Intel Core i5 12400, motherboard B660 y 16GB de RAM DDR4.',
       1200.0, 12, 0, '20-kit-actualizacion-intel-i5.jpg', c.id,
       'Intel Core i5 12400', '16GB DDR4', 'Motherboard B660', '-'
FROM categorias c
WHERE c.codigo = 'component'
  AND NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Kit Actualización Intel i5');

-- ============================================================
-- Verificación rápida
-- ============================================================
SELECT 'roles' AS tabla, COUNT(*) AS registros FROM roles
UNION ALL SELECT 'categorias', COUNT(*) FROM categorias
UNION ALL SELECT 'usuarios', COUNT(*) FROM usuarios
UNION ALL SELECT 'productos', COUNT(*) FROM productos
UNION ALL SELECT 'reservas', COUNT(*) FROM reservas;

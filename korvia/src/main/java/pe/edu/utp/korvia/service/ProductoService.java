package pe.edu.utp.korvia.service;

import pe.edu.utp.korvia.model.Categoria;
import pe.edu.utp.korvia.model.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductoService {

        private final List<Producto> productos = new ArrayList<>();
        private final AtomicLong contadorId = new AtomicLong(1);

        public ProductoService() {
                Categoria desktop = new Categoria(1L, "PC de Escritorio", "desktop");
                Categoria laptop = new Categoria(2L, "Laptop", "laptop");
                Categoria allInOne = new Categoria(3L, "All-in-One", "all-in-one");
                Categoria componente = new Categoria(4L, "Componente", "component");

                agregarProducto(new Producto(null, "PC Básico Oficina",
                                "Ideal para tareas de oficina, navegación web y trabajo con documentos.",
                                1200.0, 15, "1-pc-basico-oficina.jpg", desktop))
                                .setEspecificaciones("Intel Core i3 12va Gen", "8GB DDR4", "256GB SSD",
                                                "Intel UHD Graphics")
                                .setDescripcionLarga(
                                                "Equipo diseñado para <strong>tareas administrativas</strong>, navegación web y ofimática. "
                                                                +
                                                                "Cuenta con un procesador <strong>Intel Core i3 de 12va generación</strong>, "
                                                                +
                                                                "<strong>8GB de RAM DDR4</strong> y un <strong>SSD de 256GB</strong> que garantiza arranques rápidos. "
                                                                +
                                                                "Ideal para oficinas, estudios y trabajo remoto. Incluye <strong>Windows 11 Pro</strong> preinstalado, "
                                                                +
                                                                "teclado y mouse alámbricos. Garantía de <strong>1 año</strong>.");

                agregarProducto(new Producto(null, "PC Gaming Starter",
                                "Tu primer PC gamer. Juega tus títulos favoritos en calidad media-alta sin problemas.",
                                2800.0, 8, "2-pc-gaming-starter.jpg", desktop))
                                .setEspecificaciones("AMD Ryzen 5 5600", "16GB DDR4", "512GB SSD",
                                                "NVIDIA GTX 1650 4GB")
                                .setDescripcionLarga(
                                                "Perfecto para <strong>iniciarse en el gaming a 1080p</strong>. " +
                                                                "Equipado con un <strong>AMD Ryzen 5 5600</strong> de 6 núcleos, "
                                                                +
                                                                "<strong>16GB de RAM DDR4</strong> y una <strong>NVIDIA GTX 1650 con 4GB</strong>. "
                                                                +
                                                                "Corre juegos como <em>Fortnite, Valorant, CS2 y GTA V</em> con excelente rendimiento. "
                                                                +
                                                                "Incluye <strong>gabinete con iluminación RGB</strong>, fuente certificada 550W y "
                                                                +
                                                                "refrigeración por aire. Garantía de <strong>2 años</strong>.");

                agregarProducto(new Producto(null, "PC Gaming Pro",
                                "Potencia extrema para jugar en alta calidad y hacer streaming sin lag.",
                                4200.0, 5, "3-pc-gaming-pro.jpg", desktop))
                                .setEspecificaciones("AMD Ryzen 7 5800X", "32GB DDR4", "1TB NVMe",
                                                "NVIDIA RTX 3060 12GB")
                                .setDescripcionLarga(
                                                "Para gamers que exigen <strong>rendimiento competitivo a 1440p</strong>. "
                                                                +
                                                                "Procesador <strong>AMD Ryzen 7 5800X</strong> de 8 núcleos, "
                                                                +
                                                                "<strong>32GB de RAM DDR4</strong> y <strong>SSD NVMe de 1TB</strong>. "
                                                                +
                                                                "La <strong>RTX 3060 de 12GB</strong> con DLSS te permite jugar y hacer "
                                                                +
                                                                "<em>streaming simultáneo</em> sin perder frames. " +
                                                                "Refrigeración líquida AIO de 240mm. Garantía de <strong>2 años</strong>.");

                agregarProducto(new Producto(null, "PC Rendimiento Extremo",
                                "Lo mejor de lo mejor. Perfecto para gaming 4K, edición y tareas pesadas.",
                                6500.0, 3, "4-pc-rendimiento-extremo.jpg", desktop))
                                .setEspecificaciones("Intel Core i9 13900K", "64GB DDR5", "2TB NVMe",
                                                "NVIDIA RTX 4070 Ti")
                                .setDescripcionLarga(
                                                "Máximo rendimiento para <strong>gaming 4K</strong>, edición de video profesional y "
                                                                +
                                                                "renderizado 3D. Procesador <strong>Intel Core i9 13900K</strong> de 24 núcleos, "
                                                                +
                                                                "<strong>64GB de RAM DDR5</strong> y <strong>SSD NVMe de 2TB</strong>. "
                                                                +
                                                                "La <strong>RTX 4070 Ti</strong> con DLSS 3 y trazado de rayos ofrece "
                                                                +
                                                                "<em>frames ultra altos en 4K</em>. Garantía de <strong>3 años</strong>.");

                agregarProducto(new Producto(null, "Workstation Data Pro",
                                "Para profesionales que trabajan con programas pesados.",
                                7200.0, 2, "5-workstation-data-pro.jpg", desktop))
                                .setEspecificaciones("Intel Core i9 13900K", "64GB DDR5", "2TB NVMe + 4TB HDD",
                                                "NVIDIA RTX 4080")
                                .setDescripcionLarga(
                                                "Workstation profesional para <strong>científicos de datos, editores y arquitectos</strong>. "
                                                                +
                                                                "Procesador <strong>Intel Core i9 13900K</strong>, " +
                                                                "<strong>64GB de RAM DDR5</strong> para multitarea pesada, "
                                                                +
                                                                "<strong>SSD NVMe de 2TB + HDD de 4TB</strong>. " +
                                                                "La <strong>RTX 4080</strong> con 16GB de VRAM acelera <em>IA, renderizado y simulaciones</em>. "
                                                                +
                                                                "Garantía de <strong>3 años</strong>.");

                agregarProducto(new Producto(null, "PC Compacto Mini",
                                "Pequeño pero poderoso. Ahorra espacio sin sacrificar rendimiento.",
                                1800.0, 10, "6-pc-compacto-mini.jpg", desktop))
                                .setEspecificaciones("Intel Core i5 12400", "16GB DDR4", "512GB SSD", "Intel UHD 730")
                                .setDescripcionLarga(
                                                "Formato <strong>mini-ITX compacto</strong> ideal para escritorios pequeños. "
                                                                +
                                                                "Procesador <strong>Intel Core i5 12400</strong>, <strong>16GB de RAM</strong> y "
                                                                +
                                                                "<strong>SSD de 512GB</strong>. Perfecto para <em>ofimática y multimedia</em>. "
                                                                +
                                                                "Consumo energético reducido (<strong>solo 65W</strong>). Garantía de <strong>2 años</strong>.");

                agregarProducto(new Producto(null, "Laptop Estudiante Basic",
                                "Portátil económico y confiable para estudios.",
                                1600.0, 20, "7-laptop-estudiante-basic.jpg", laptop))
                                .setEspecificaciones("Intel Celeron N4500", "8GB DDR4", "256GB SSD", "Intel UHD")
                                .setDescripcionLarga(
                                                "Portátil económico ideal para <strong>estudiantes y tareas básicas</strong>. "
                                                                +
                                                                "Procesador <strong>Intel Celeron N4500</strong>, <strong>8GB de RAM</strong> y "
                                                                +
                                                                "<strong>SSD de 256GB</strong>. Pantalla de <strong>14\" Full HD</strong>. "
                                                                +
                                                                "Batería de larga duración <em>(hasta 8 horas)</em>. Garantía de <strong>1 año</strong>.");

                agregarProducto(new Producto(null, "Laptop Office Light",
                                "Ultraligera para oficina con batería de larga duración.",
                                2300.0, 12, "8-laptop-office-light.jpg", laptop))
                                .setEspecificaciones("Intel Core i5 1235U", "8GB DDR4", "512GB SSD", "Intel Iris Xe")
                                .setDescripcionLarga(
                                                "Diseñada para <strong>profesionales en movimiento</strong>. " +
                                                                "Procesador <strong>Intel Core i5 1235U</strong>, " +
                                                                "<strong>8GB de RAM</strong> y <strong>SSD de 512GB</strong>. "
                                                                +
                                                                "Pantalla <strong>15.6\" Full HD antirreflejo</strong>. Peso: <strong>solo 1.6 kg</strong>. "
                                                                +
                                                                "Batería de <em>hasta 10 horas</em>. Garantía de <strong>2 años</strong>.");

                agregarProducto(new Producto(null, "Laptop Profesional Plus",
                                "Potencia profesional en formato portátil con pantalla 4K.",
                                3600.0, 7, "9-laptop-profesional-plus.jpg", laptop))
                                .setEspecificaciones("Intel Core i7 1260P", "16GB DDR4", "1TB SSD", "Intel Iris Xe")
                                .setDescripcionLarga(
                                                "Para profesionales que necesitan <strong>potencia real en movilidad</strong>. "
                                                                +
                                                                "<strong>Intel Core i7 1260P</strong>, <strong>16GB de RAM</strong> y "
                                                                +
                                                                "<strong>SSD de 1TB</strong>. Pantalla <strong>4K UHD OLED</strong>. "
                                                                +
                                                                "Chasis de <strong>aluminio cepillado</strong> y <strong>Thunderbolt 4</strong>. "
                                                                +
                                                                "Garantía de <strong>3 años</strong>.");

                agregarProducto(new Producto(null, "Laptop Gaming Portátil",
                                "Gaming portátil con pantalla de 144Hz.",
                                3200.0, 6, "10-laptop-gaming-portatil.jpg", laptop))
                                .setEspecificaciones("AMD Ryzen 5 6600H", "16GB DDR5", "512GB NVMe", "NVIDIA RTX 3050")
                                .setDescripcionLarga(
                                                "Gaming portátil sin compromisos. <strong>AMD Ryzen 5 6600H</strong>, "
                                                                +
                                                                "<strong>16GB DDR5</strong> y <strong>SSD NVMe de 512GB</strong>. "
                                                                +
                                                                "Pantalla <strong>15.6\" Full HD 144Hz</strong>. " +
                                                                "La <strong>RTX 3050</strong> corre juegos modernos a <em>1080p en alto</em>. "
                                                                +
                                                                "Teclado RGB retroiluminado. Garantía de <strong>2 años</strong>.");

                agregarProducto(new Producto(null, "Laptop Gaming Elite",
                                "Gaming de alto rendimiento con RTX 4070.",
                                5800.0, 3, "11-laptop-gaming-elite.jpg", laptop))
                                .setEspecificaciones("Intel Core i9 13900HX", "32GB DDR5", "1TB NVMe",
                                                "NVIDIA RTX 4070")
                                .setDescripcionLarga(
                                                "Laptop gaming <strong>de gama alta</strong>. " +
                                                                "<strong>Intel Core i9 13900HX</strong>, <strong>32GB DDR5</strong> y "
                                                                +
                                                                "<strong>SSD NVMe de 1TB</strong>. Pantalla <strong>17.3\" QHD 240Hz</strong>. "
                                                                +
                                                                "La <strong>RTX 4070</strong> con DLSS 3 ofrece <em>rendimiento de escritorio</em>. "
                                                                +
                                                                "Garantía de <strong>3 años</strong>.");

                agregarProducto(new Producto(null, "Ultrabook Premium 14\"",
                                "Chasis ultrafino de aluminio, pantalla OLED y batería extendida.",
                                4800.0, 8, "12-laptop-ultrabook-premium.jpg", laptop))
                                .setEspecificaciones("Intel Core i7 1355U", "16GB LPDDR5", "1TB SSD", "Intel Iris Xe")
                                .setDescripcionLarga(
                                                "Ultrabook premium para <strong>profesionales exigentes</strong>. " +
                                                                "<strong>Intel Core i7 1355U</strong>, <strong>16GB LPDDR5</strong> y "
                                                                +
                                                                "<strong>SSD de 1TB</strong>. Pantalla <strong>14\" OLED 2.8K</strong> con "
                                                                +
                                                                "<em>HDR y 100% DCI-P3</em>. Chasis de <strong>aluminio unibody</strong>, "
                                                                +
                                                                "peso de <strong>1.2 kg</strong>. Batería de <em>hasta 15 horas</em>. "
                                                                +
                                                                "Garantía de <strong>3 años</strong>.");

                agregarProducto(new Producto(null, "Laptop 2 en 1 Convertible",
                                "Convertible con pantalla táctil y lápiz óptico.",
                                3400.0, 9, "13-laptop-2-en-1-convertible.jpg", laptop))
                                .setEspecificaciones("Intel Core i5 1235U", "16GB DDR4", "512GB SSD", "Intel Iris Xe")
                                .setDescripcionLarga(
                                                "Laptop <strong>2 en 1 convertible</strong> con bisagra 360°. " +
                                                                "<strong>Intel Core i5 1235U</strong>, <strong>16GB de RAM</strong> y "
                                                                +
                                                                "<strong>SSD de 512GB</strong>. Pantalla <strong>14\" táctil Full HD</strong> con "
                                                                +
                                                                "<em>soporte para lápiz óptico</em>. Ideal para <strong>diseño y presentaciones</strong>. "
                                                                +
                                                                "Garantía de <strong>2 años</strong>.");

                agregarProducto(new Producto(null, "Laptop Creador Contenido",
                                "Diseñada para creadores con pantalla calibrada.",
                                4900.0, 4, "14-laptop-cread-or-contedido.jpg", laptop))
                                .setEspecificaciones("Intel Core i7 13700H", "32GB DDR5", "1TB NVMe", "NVIDIA RTX 4060")
                                .setDescripcionLarga(
                                                "Laptop pensada para <strong>creadores de contenido</strong>. " +
                                                                "<strong>Intel Core i7 13700H</strong>, <strong>32GB DDR5</strong> y "
                                                                +
                                                                "<strong>SSD NVMe de 1TB</strong>. Pantalla <strong>16\" 2.5K 165Hz</strong>. "
                                                                +
                                                                "La <strong>RTX 4060</strong> acelera edición en Premiere y DaVinci. "
                                                                +
                                                                "Garantía de <strong>2 años</strong>.");

                agregarProducto(new Producto(null, "All-in-One Familiar",
                                "Ideal para el hogar con pantalla 24\" Full HD.",
                                2600.0, 7, "15-all-in-one-familiar.jpg", allInOne))
                                .setEspecificaciones("Intel Core i5 1235U", "8GB DDR4", "512GB SSD", "Intel Iris Xe")
                                .setDescripcionLarga(
                                                "All-in-One para el <strong>hogar</strong>. Pantalla <strong>24\" Full HD IPS</strong>. "
                                                                +
                                                                "<strong>Intel Core i5 1235U</strong>, <strong>8GB de RAM</strong> y "
                                                                +
                                                                "<strong>SSD de 512GB</strong>. Cámara HD con tapa de privacidad. "
                                                                +
                                                                "Incluye <strong>teclado y mouse inalámbricos</strong>. Garantía de <strong>2 años</strong>.");

                agregarProducto(new Producto(null, "All-in-One Designer",
                                "Pantalla 27\" 4K para diseñadores profesionales.",
                                4200.0, 5, "16-all-in-one-designer.jpg", allInOne))
                                .setEspecificaciones("Intel Core i7 1255U", "16GB DDR4", "1TB SSD", "Intel Iris Xe")
                                .setDescripcionLarga(
                                                "Para <strong>diseñadores y creativos</strong>. Pantalla <strong>27\" 4K UHD</strong> "
                                                                +
                                                                "con <em>99% sRGB</em>. <strong>Intel Core i7 1255U</strong>, "
                                                                +
                                                                "<strong>16GB de RAM</strong> y <strong>SSD de 1TB</strong>. "
                                                                +
                                                                "Compatible con <strong>Pantone Validated</strong>. Garantía de <strong>3 años</strong>.");

                agregarProducto(new Producto(null, "Memoria RAM 16GB DDR4",
                                "Módulo DDR4 3200MHz de alto rendimiento.",
                                280.0, 30, "17-memoria-ram-16gb-ddr4.jpg", componente))
                                .setEspecificaciones("DDR4", "16GB", "3200MHz", "—")
                                .setDescripcionLarga(
                                                "Módulo de memoria <strong>DDR4 de 16GB</strong> a <strong>3200MHz</strong>. "
                                                                +
                                                                "Compatible con placas <em>Intel y AMD</em>. " +
                                                                "Disipador de aluminio. Ideal para <strong>ampliar RAM</strong>. Garantía de por vida.");

                agregarProducto(new Producto(null, "Disco SSD 1TB NVMe",
                                "Unidad SSD NVMe Gen4 hasta 7000 MB/s.",
                                450.0, 25, "18-disco-ssd-1tb-nvme.jpg", componente))
                                .setEspecificaciones("NVMe Gen4", "1TB", "7000 MB/s", "—")
                                .setDescripcionLarga(
                                                "SSD <strong>NVMe Gen4 de 1TB</strong> con velocidades de " +
                                                                "<strong>hasta 7000 MB/s</strong>. " +
                                                                "Factor de forma <strong>M.2 2280</strong>. " +
                                                                "Reduce los <strong>tiempos de arranque y carga</strong>. Garantía de <strong>5 años</strong>.");

                agregarProducto(new Producto(null, "Gabinete Gaming RGB",
                                "Gabinete ATX con panel lateral de vidrio templado.",
                                320.0, 18, "19-gabinete-gaming-rgb.jpg", componente))
                                .setEspecificaciones("ATX", "Vidrio templado", "RGB", "—")
                                .setDescripcionLarga(
                                                "Gabinete <strong>ATX mid-tower</strong> con panel lateral de " +
                                                                "<strong>vidrio templado</strong>. Iluminación <strong>RGB frontal</strong>. "
                                                                +
                                                                "Soporta <em>refrigeración líquida de 360mm</em>. " +
                                                                "Gestión de cables y filtros antipolvo. Garantía de <strong>1 año</strong>.");

                agregarProducto(new Producto(null, "Kit Actualización Intel i5",
                                "Kit completo: procesador, motherboard y RAM.",
                                1200.0, 12, "20-kit-actualizacion-intel-i5.jpg", componente))
                                .setEspecificaciones("Intel Core i5 12400", "16GB DDR4", "Motherboard B660", "—")
                                .setDescripcionLarga(
                                                "Kit completo para <strong>actualizar tu PC</strong>. " +
                                                                "Incluye <strong>Intel Core i5 12400</strong>, " +
                                                                "<strong>motherboard B660</strong> con soporte DDR4 y "
                                                                +
                                                                "<strong>16GB de RAM DDR4</strong> (2x8GB). " +
                                                                "Ideal para dar un <strong>salto generacional</strong>. Garantía de <strong>2 años</strong>.");
        }

        public List<Producto> listarTodos() {
                return productos;
        }

        public Producto buscarPorId(Long id) {
                return productos.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        }

        public List<Producto> listarPorCategoria(String codigoCategoria) {
                if ("all".equals(codigoCategoria))
                        return productos;
                return productos.stream().filter(p -> p.getCategoria().getCodigo().equals(codigoCategoria)).toList();
        }

        public List<Producto> buscarPorNombre(String nombre) {
                if (nombre == null || nombre.isEmpty())
                        return productos;
                return productos.stream().filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                                .toList();
        }

        public Producto agregarProducto(Producto producto) {
                if (producto.getId() == null) {
                        producto.setId(contadorId.getAndIncrement());
                }
                if (producto.getReservado() == null) {
                        producto.setReservado(0);
                }
                productos.add(producto);
                return producto;
        }

        public Producto actualizarProducto(Long id, Producto datos) {
                Producto p = buscarPorId(id);
                if (p != null) {
                        p.setNombre(datos.getNombre());
                        p.setDescripcion(datos.getDescripcion());
                        p.setPrecio(datos.getPrecio());
                        p.setStock(datos.getStock());
                        p.setImagen(datos.getImagen());
                        p.setCategoria(datos.getCategoria());
                        p.setCpu(datos.getCpu());
                        p.setRam(datos.getRam());
                        p.setAlmacenamiento(datos.getAlmacenamiento());
                        p.setGpu(datos.getGpu());
                        p.setDescripcionLarga(datos.getDescripcionLarga());
                }
                return p;
        }

        public boolean eliminarProducto(Long id) {
                return productos.removeIf(p -> p.getId().equals(id));
        }

        public void descontarStock(Long id, Integer cantidad) {
                Producto p = buscarPorId(id);
                if (p != null && p.getStockDisponible() >= cantidad) {
                        p.setStock(p.getStock() - cantidad);
                }
        }

        public void devolverStock(Long id, Integer cantidad) {
                Producto p = buscarPorId(id);
                if (p != null) {
                        p.setStock(p.getStock() + cantidad);
                }
        }

        public List<Categoria> listarCategorias() {
                return List.of(
                                new Categoria(1L, "PC de Escritorio", "desktop"),
                                new Categoria(2L, "Laptop", "laptop"),
                                new Categoria(3L, "All-in-One", "all-in-one"),
                                new Categoria(4L, "Componente", "component"));
        }

        public Optional<Producto> buscarOptional(Long id) {
                return productos.stream().filter(p -> p.getId().equals(id)).findFirst();
        }
}
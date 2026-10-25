package pe.edu.utp.korvia.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.korvia.model.Categoria;
import pe.edu.utp.korvia.model.Producto;
import pe.edu.utp.korvia.model.Rol;
import pe.edu.utp.korvia.model.Usuario;
import pe.edu.utp.korvia.repository.CategoriaRepository;
import pe.edu.utp.korvia.repository.ProductoRepository;
import pe.edu.utp.korvia.repository.RolRepository;
import pe.edu.utp.korvia.repository.UsuarioRepository;

import java.util.List;

/**
 * Carga datos iniciales de forma idempotente.
 *
 * Se ejecuta después de que Spring levanta el contexto y de que Hibernate
 * crea/actualiza las tablas. Si la base ya tiene datos, no duplica registros.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    public DataInitializer(RolRepository rolRepository,
            CategoriaRepository categoriaRepository,
            UsuarioRepository usuarioRepository,
            ProductoRepository productoRepository) {
        this.rolRepository = rolRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Rol admin = rolRepository.findByNombre("ADMIN")
                .orElseGet(() -> rolRepository.save(new Rol(null, "ADMIN")));
        Rol cliente = rolRepository.findByNombre("CLIENTE")
                .orElseGet(() -> rolRepository.save(new Rol(null, "CLIENTE")));

        Categoria desktop = categoria("PC de Escritorio", "desktop");
        Categoria laptop = categoria("Laptop", "laptop");
        Categoria allInOne = categoria("All-in-One", "all-in-one");
        Categoria componente = categoria("Componente", "component");

        if (usuarioRepository.count() == 0) {
            usuarioRepository.saveAll(List.of(
                    new Usuario(null, "Admin", "Korvia", "admin@korvia.pe", "admin123", admin),
                    new Usuario(null, "Juan", "Pérez", "juan@email.com", "123456", cliente)));
        }

        if (productoRepository.count() == 0) {
            productoRepository.saveAll(List.of(
                    producto("PC Básico Oficina",
                            "Ideal para tareas de oficina, navegación web y trabajo con documentos.",
                            1200.0, 15, "1-pc-basico-oficina.jpg", desktop,
                            "Intel Core i3 12va Gen", "8GB DDR4", "256GB SSD", "Intel UHD Graphics",
                            "Equipo diseñado para <strong>tareas administrativas</strong>, navegación web y ofimática. "
                                    + "Cuenta con un procesador <strong>Intel Core i3 de 12va generación</strong>, "
                                    + "<strong>8GB de RAM DDR4</strong> y un <strong>SSD de 256GB</strong> que garantiza arranques rápidos. "
                                    + "Ideal para oficinas, estudios y trabajo remoto. Incluye <strong>Windows 11 Pro</strong> preinstalado, "
                                    + "teclado y mouse alámbricos. Garantía de <strong>1 año</strong>."),
                    producto("PC Gaming Starter",
                            "Tu primer PC gamer. Juega tus títulos favoritos en calidad media-alta sin problemas.",
                            2800.0, 8, "2-pc-gaming-starter.jpg", desktop,
                            "AMD Ryzen 5 5600", "16GB DDR4", "512GB SSD", "NVIDIA GTX 1650 4GB",
                            "Perfecto para <strong>iniciarse en el gaming a 1080p</strong>. Equipado con un "
                                    + "<strong>AMD Ryzen 5 5600</strong> de 6 núcleos, <strong>16GB de RAM DDR4</strong> "
                                    + "y una <strong>NVIDIA GTX 1650 con 4GB</strong>. Corre juegos como "
                                    + "<em>Fortnite, Valorant, CS2 y GTA V</em> con excelente rendimiento. "
                                    + "Incluye <strong>gabinete con iluminación RGB</strong>, fuente certificada 550W y "
                                    + "refrigeración por aire. Garantía de <strong>2 años</strong>."),
                    producto("PC Gaming Pro",
                            "Potencia extrema para jugar en alta calidad y hacer streaming sin lag.",
                            4200.0, 5, "3-pc-gaming-pro.jpg", desktop,
                            "AMD Ryzen 7 5800X", "32GB DDR4", "1TB NVMe", "NVIDIA RTX 3060 12GB",
                            "Para gamers que exigen <strong>rendimiento competitivo a 1440p</strong>. "
                                    + "Procesador <strong>AMD Ryzen 7 5800X</strong> de 8 núcleos, "
                                    + "<strong>32GB de RAM DDR4</strong> y <strong>SSD NVMe de 1TB</strong>. "
                                    + "La <strong>RTX 3060 de 12GB</strong> con DLSS te permite jugar y hacer "
                                    + "<em>streaming simultáneo</em> sin perder frames. "
                                    + "Refrigeración líquida AIO de 240mm. Garantía de <strong>2 años</strong>."),
                    producto("PC Rendimiento Extremo",
                            "Lo mejor de lo mejor. Perfecto para gaming 4K, edición y tareas pesadas.",
                            6500.0, 3, "4-pc-rendimiento-extremo.jpg", desktop,
                            "Intel Core i9 13900K", "64GB DDR5", "2TB NVMe", "NVIDIA RTX 4070 Ti",
                            "Máximo rendimiento para <strong>gaming 4K</strong>, edición de video profesional y "
                                    + "renderizado 3D. Procesador <strong>Intel Core i9 13900K</strong> de 24 núcleos, "
                                    + "<strong>64GB de RAM DDR5</strong> y <strong>SSD NVMe de 2TB</strong>. "
                                    + "La <strong>RTX 4070 Ti</strong> con DLSS 3 y trazado de rayos ofrece "
                                    + "<em>frames ultra altos en 4K</em>. Garantía de <strong>3 años</strong>."),
                    producto("Workstation Data Pro",
                            "Para profesionales que trabajan con programas pesados.",
                            7200.0, 2, "5-workstation-data-pro.jpg", desktop,
                            "Intel Core i9 13900K", "64GB DDR5", "2TB NVMe + 4TB HDD", "NVIDIA RTX 4080",
                            "Workstation profesional para <strong>científicos de datos, editores y arquitectos</strong>. "
                                    + "Procesador <strong>Intel Core i9 13900K</strong>, "
                                    + "<strong>64GB de RAM DDR5</strong> para multitarea pesada, "
                                    + "<strong>SSD NVMe de 2TB + HDD de 4TB</strong>. "
                                    + "La <strong>RTX 4080</strong> con 16GB de VRAM acelera "
                                    + "<em>IA, renderizado y simulaciones</em>. Garantía de <strong>3 años</strong>."),
                    producto("PC Compacto Mini",
                            "Pequeño pero poderoso. Ahorra espacio sin sacrificar rendimiento.",
                            1800.0, 10, "6-pc-compacto-mini.jpg", desktop,
                            "Intel Core i5 12400", "16GB DDR4", "512GB SSD", "Intel UHD 730",
                            "Formato <strong>mini-ITX compacto</strong> ideal para escritorios pequeños. "
                                    + "Procesador <strong>Intel Core i5 12400</strong>, <strong>16GB de RAM</strong> "
                                    + "y <strong>SSD de 512GB</strong>. Perfecto para <em>ofimática y multimedia</em>. "
                                    + "Consumo energético reducido (<strong>solo 65W</strong>). Garantía de <strong>2 años</strong>."),

                    producto("Laptop Estudiante Basic",
                            "Portátil económico y confiable para estudios.",
                            1600.0, 20, "7-laptop-estudiante-basic.jpg", laptop,
                            "Intel Celeron N4500", "8GB DDR4", "256GB SSD", "Intel UHD",
                            "Portátil económico ideal para <strong>estudiantes y tareas básicas</strong>. "
                                    + "Procesador <strong>Intel Celeron N4500</strong>, <strong>8GB de RAM</strong> "
                                    + "y <strong>SSD de 256GB</strong>. Pantalla de <strong>14 pulgadas Full HD</strong>. "
                                    + "Batería de larga duración <em>(hasta 8 horas)</em>. Garantía de <strong>1 año</strong>."),
                    producto("Laptop Office Light",
                            "Ultraligera para oficina con batería de larga duración.",
                            2300.0, 12, "8-laptop-office-light.jpg", laptop,
                            "Intel Core i5 1235U", "8GB DDR4", "512GB SSD", "Intel Iris Xe",
                            "Diseñada para <strong>profesionales en movimiento</strong>. "
                                    + "Procesador <strong>Intel Core i5 1235U</strong>, <strong>8GB de RAM</strong> "
                                    + "y <strong>SSD de 512GB</strong>. Pantalla <strong>15.6 pulgadas Full HD antirreflejo</strong>. "
                                    + "Peso: <strong>solo 1.6 kg</strong>. Batería de <em>hasta 10 horas</em>. "
                                    + "Garantía de <strong>2 años</strong>."),
                    producto("Laptop Profesional Plus",
                            "Potencia profesional en formato portátil con pantalla 4K.",
                            3600.0, 7, "9-laptop-profesional-plus.jpg", laptop,
                            "Intel Core i7 1260P", "16GB DDR4", "1TB SSD", "Intel Iris Xe",
                            "Para profesionales que necesitan <strong>potencia real en movilidad</strong>. "
                                    + "<strong>Intel Core i7 1260P</strong>, <strong>16GB de RAM</strong> y "
                                    + "<strong>SSD de 1TB</strong>. Pantalla <strong>4K UHD OLED</strong>. "
                                    + "Chasis de <strong>aluminio cepillado</strong> y <strong>Thunderbolt 4</strong>. "
                                    + "Garantía de <strong>3 años</strong>."),
                    producto("Laptop Gaming Portátil",
                            "Gaming portátil con pantalla de 144Hz.",
                            3200.0, 6, "10-laptop-gaming-portatil.jpg", laptop,
                            "AMD Ryzen 5 6600H", "16GB DDR5", "512GB NVMe", "NVIDIA RTX 3050",
                            "Gaming portátil sin compromisos. <strong>AMD Ryzen 5 6600H</strong>, "
                                    + "<strong>16GB DDR5</strong> y <strong>SSD NVMe de 512GB</strong>. "
                                    + "Pantalla <strong>15.6 pulgadas Full HD 144Hz</strong>. "
                                    + "La <strong>RTX 3050</strong> corre juegos modernos a <em>1080p en alto</em>. "
                                    + "Teclado RGB retroiluminado. Garantía de <strong>2 años</strong>."),
                    producto("Laptop Gaming Elite",
                            "Gaming de alto rendimiento con RTX 4070.",
                            5800.0, 3, "11-laptop-gaming-elite.jpg", laptop,
                            "Intel Core i9 13900HX", "32GB DDR5", "1TB NVMe", "NVIDIA RTX 4070",
                            "Laptop gaming <strong>de gama alta</strong>. <strong>Intel Core i9 13900HX</strong>, "
                                    + "<strong>32GB DDR5</strong> y <strong>SSD NVMe de 1TB</strong>. "
                                    + "Pantalla <strong>17.3 pulgadas QHD 240Hz</strong>. "
                                    + "La <strong>RTX 4070</strong> con DLSS 3 ofrece "
                                    + "<em>rendimiento de escritorio</em>. Garantía de <strong>3 años</strong>."),
                    producto("Ultrabook Premium 14\"",
                            "Chasis ultrafino de aluminio, pantalla OLED y batería extendida.",
                            4800.0, 8, "12-laptop-ultrabook-premium.jpg", laptop,
                            "Intel Core i7 1355U", "16GB LPDDR5", "1TB SSD", "Intel Iris Xe",
                            "Ultrabook premium para <strong>profesionales exigentes</strong>. "
                                    + "<strong>Intel Core i7 1355U</strong>, <strong>16GB LPDDR5</strong> y "
                                    + "<strong>SSD de 1TB</strong>. Pantalla <strong>14 pulgadas OLED 2.8K</strong> con "
                                    + "<em>HDR y 100% DCI-P3</em>. Chasis de <strong>aluminio unibody</strong>, "
                                    + "peso de <strong>1.2 kg</strong>. Batería de <em>hasta 15 horas</em>. "
                                    + "Garantía de <strong>3 años</strong>."),
                    producto("Laptop 2 en 1 Convertible",
                            "Convertible con pantalla táctil y lápiz óptico.",
                            3400.0, 9, "13-laptop-2-en-1-convertible.jpg", laptop,
                            "Intel Core i5 1235U", "16GB DDR4", "512GB SSD", "Intel Iris Xe",
                            "Laptop <strong>2 en 1 convertible</strong> con bisagra 360 grados. "
                                    + "<strong>Intel Core i5 1235U</strong>, <strong>16GB de RAM</strong> y "
                                    + "<strong>SSD de 512GB</strong>. Pantalla <strong>14 pulgadas táctil Full HD</strong> con "
                                    + "<em>soporte para lápiz óptico</em>. Ideal para <strong>diseño y presentaciones</strong>. "
                                    + "Garantía de <strong>2 años</strong>."),
                    producto("Laptop Creador Contenido",
                            "Diseñada para creadores con pantalla calibrada.",
                            4900.0, 4, "14-laptop-cread-or-contedido.jpg", laptop,
                            "Intel Core i7 13700H", "32GB DDR5", "1TB NVMe", "NVIDIA RTX 4060",
                            "Laptop pensada para <strong>creadores de contenido</strong>. "
                                    + "<strong>Intel Core i7 13700H</strong>, <strong>32GB DDR5</strong> y "
                                    + "<strong>SSD NVMe de 1TB</strong>. Pantalla <strong>16 pulgadas 2.5K 165Hz</strong>. "
                                    + "La <strong>RTX 4060</strong> acelera edición en Premiere y DaVinci. "
                                    + "Garantía de <strong>2 años</strong>."),

                    producto("All-in-One Familiar",
                            "Ideal para el hogar con pantalla 24 pulgadas Full HD.",
                            2600.0, 7, "15-all-in-one-familiar.jpg", allInOne,
                            "Intel Core i5 1235U", "8GB DDR4", "512GB SSD", "Intel Iris Xe",
                            "All-in-One para el <strong>hogar</strong>. Pantalla <strong>24 pulgadas Full HD IPS</strong>. "
                                    + "<strong>Intel Core i5 1235U</strong>, <strong>8GB de RAM</strong> y "
                                    + "<strong>SSD de 512GB</strong>. Cámara HD con tapa de privacidad. "
                                    + "Incluye <strong>teclado y mouse inalámbricos</strong>. Garantía de <strong>2 años</strong>."),
                    producto("All-in-One Designer",
                            "Pantalla 27 pulgadas 4K para diseñadores profesionales.",
                            4200.0, 5, "16-all-in-one-designer.jpg", allInOne,
                            "Intel Core i7 1255U", "16GB DDR4", "1TB SSD", "Intel Iris Xe",
                            "Para <strong>diseñadores y creativos</strong>. Pantalla <strong>27 pulgadas 4K UHD</strong> "
                                    + "con <em>99% sRGB</em>. <strong>Intel Core i7 1255U</strong>, "
                                    + "<strong>16GB de RAM</strong> y <strong>SSD de 1TB</strong>. "
                                    + "Compatible con <strong>Pantone Validated</strong>. Garantía de <strong>3 años</strong>."),

                    producto("Memoria RAM 16GB DDR4",
                            "Módulo DDR4 3200MHz de alto rendimiento.",
                            280.0, 30, "17-memoria-ram-16gb-ddr4.jpg", componente,
                            "DDR4", "16GB", "3200MHz", "—",
                            "Módulo de memoria <strong>DDR4 de 16GB</strong> a <strong>3200MHz</strong>. "
                                    + "Compatible con placas <em>Intel y AMD</em>. Disipador de aluminio. "
                                    + "Ideal para <strong>ampliar RAM</strong>. Garantía de por vida."),
                    producto("Disco SSD 1TB NVMe",
                            "Unidad SSD NVMe Gen4 hasta 7000 MB/s.",
                            450.0, 25, "18-disco-ssd-1tb-nvme.jpg", componente,
                            "NVMe Gen4", "1TB", "7000 MB/s", "—",
                            "SSD <strong>NVMe Gen4 de 1TB</strong> con velocidades de "
                                    + "<strong>hasta 7000 MB/s</strong>. Factor de forma <strong>M.2 2280</strong>. "
                                    + "Reduce los <strong>tiempos de arranque y carga</strong>. Garantía de <strong>5 años</strong>."),
                    producto("Gabinete Gaming RGB",
                            "Gabinete ATX con panel lateral de vidrio templado.",
                            320.0, 18, "19-gabinete-gaming-rgb.jpg", componente,
                            "ATX", "Vidrio templado", "RGB", "—",
                            "Gabinete <strong>ATX mid-tower</strong> con panel lateral de "
                                    + "<strong>vidrio templado</strong>. Iluminación <strong>RGB frontal</strong>. "
                                    + "Soporta <em>refrigeración líquida de 360mm</em>. "
                                    + "Gestión de cables y filtros antipolvo. Garantía de <strong>1 año</strong>."),
                    producto("Kit Actualización Intel i5",
                            "Kit completo: procesador, motherboard y RAM.",
                            1200.0, 12, "20-kit-actualizacion-intel-i5.jpg", componente,
                            "Intel Core i5 12400", "16GB DDR4", "Motherboard B660", "—",
                            "Kit completo para <strong>actualizar tu PC</strong>. Incluye "
                                    + "<strong>Intel Core i5 12400</strong>, <strong>motherboard B660</strong> con soporte DDR4 y "
                                    + "<strong>16GB de RAM DDR4</strong> (2x8GB). Ideal para dar un "
                                    + "<strong>salto generacional</strong>. Garantía de <strong>2 años</strong>.")));
        }
    }

    private Categoria categoria(String nombre, String codigo) {
        return categoriaRepository.findByCodigo(codigo)
                .orElseGet(() -> categoriaRepository.save(new Categoria(null, nombre, codigo)));
    }

    private Producto producto(String nombre, String descripcion, Double precio, Integer stock,
            String imagen, Categoria categoria, String cpu, String ram,
            String almacenamiento, String gpu, String descripcionLarga) {
        Producto producto = new Producto(null, nombre, descripcion, precio, stock, imagen, categoria);
        producto.setEspecificaciones(cpu, ram, almacenamiento, gpu);
        producto.setDescripcionLarga(descripcionLarga);
        return producto;
    }
}

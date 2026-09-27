/**
 * Datos simulados de productos - Korvia E-commerce
 * 20 productos distribuidos en: PCs, Laptops, All-in-One y Componentes
 * Datos estáticos para la Entrega 1
 */

const PRODUCTS = [
  // ========== PCS DE ESCRITORIO (6 productos) ==========
  {
    id: 1,
    name: "PC Básico Oficina",
    category: "desktop",
    subcategory: "office",
    price: 1200,
    stock: 15,
    reserved: 0,
    image: "assets/img/productos/1-pc-basico-oficina.jpg",
    specs: {
      cpu: "Intel Core i3 12va Gen",
      ram: "8GB DDR4",
      storage: "256GB SSD",
      gpu: "Intel UHD Graphics"
    },
    description: "Ideal para tareas de oficina, navegación web y trabajo con documentos. Rápido y confiable para el día a día."
  },
  {
    id: 2,
    name: "PC Gaming Starter",
    category: "desktop",
    subcategory: "gaming",
    price: 2800,
    stock: 8,
    reserved: 0,
    image: "assets/img/productos/2-pc-gaming-starter.jpg",
    specs: {
      cpu: "AMD Ryzen 5 5600",
      ram: "16GB DDR4",
      storage: "512GB NVMe SSD",
      gpu: "NVIDIA GTX 1650 4GB"
    },
    description: "Tu primer PC gamer. Juega tus títulos favoritos en calidad media-alta sin problemas."
  },
  {
    id: 3,
    name: "PC Gaming Pro",
    category: "desktop",
    subcategory: "gaming",
    price: 4200,
    stock: 5,
    reserved: 0,
    image: "assets/img/productos/3-pc-gaming-pro.jpg",
    specs: {
      cpu: "Intel Core i7 13va Gen",
      ram: "32GB DDR5",
      storage: "1TB NVMe Gen4",
      gpu: "NVIDIA RTX 4060 Ti 8GB"
    },
    description: "Potencia extrema para jugar en alta calidad y hacer streaming sin lag."
  },
  {
    id: 4,
    name: "PC Rendimiento Extremo",
    category: "desktop",
    subcategory: "gaming",
    price: 6500,
    stock: 3,
    reserved: 0,
    image: "assets/img/productos/4-pc-rendimiento-extremo.jpg",
    specs: {
      cpu: "Intel Core i9 13va Gen",
      ram: "64GB DDR5",
      storage: "2TB NVMe Gen4",
      gpu: "NVIDIA RTX 4070 12GB"
    },
    description: "Lo mejor de lo mejor. Perfecto para gaming 4K, edición de video profesional y tareas pesadas."
  },
  {
    id: 5,
    name: "Workstation Data Pro",
    category: "desktop",
    subcategory: "workstation",
    price: 7200,
    stock: 2,
    reserved: 0,
    image: "assets/img/productos/5-workstation-data-pro.jpg",
    specs: {
      cpu: "AMD Ryzen 9 7950X",
      ram: "64GB DDR5",
      storage: "2TB NVMe + 4TB HDD",
      gpu: "NVIDIA RTX 4070 Ti 12GB"
    },
    description: "Para profesionales que trabajan con programas pesados. Perfecta para edición, modelado 3D y análisis de datos."
  },
  {
    id: 6,
    name: "PC Compacto Mini",
    category: "desktop",
    subcategory: "mini",
    price: 1800,
    stock: 10,
    reserved: 0,
    image: "assets/img/productos/6-pc-compacto-mini.jpg",
    specs: {
      cpu: "Intel Core i5 12va Gen",
      ram: "16GB DDR4",
      storage: "512GB SSD",
      gpu: "Intel Iris Xe"
    },
    description: "Pequeño pero poderoso. Ahorra espacio sin sacrificar rendimiento para trabajo y multimedia."
  },

  // ========== LAPTOPS (8 productos) ==========
  {
    id: 7,
    name: "Laptop Estudiante Basic",
    category: "laptop",
    subcategory: "student",
    price: 950,
    stock: 20,
    reserved: 0,
    image: "assets/img/productos/7-laptop-estudiante-basic.jpg",
    specs: {
      cpu: "Intel Celeron N4020",
      ram: "4GB DDR4",
      storage: "128GB SSD",
      screen: "14 pulgadas HD",
      battery: "Hasta 6 horas"
    },
    description: "Económica y funcional para tomar notas, navegar y trabajos escolares básicos."
  },
  {
    id: 8,
    name: "Laptop Office Light",
    category: "laptop",
    subcategory: "office",
    price: 1800,
    stock: 15,
    reserved: 0,
    image: "assets/img/productos/8-laptop-office-light.jpg",
    specs: {
      cpu: "Intel Core i3 12va Gen",
      ram: "8GB DDR4",
      storage: "256GB SSD",
      screen: "15.6 pulgadas Full HD",
      battery: "Hasta 8 horas"
    },
    description: "Ligera y portátil para trabajar desde cualquier lugar. Perfecta para oficina móvil."
  },
  {
    id: 9,
    name: "Laptop Profesional Plus",
    category: "laptop",
    subcategory: "professional",
    price: 3200,
    stock: 10,
    reserved: 0,
    image: "assets/img/productos/9-laptop-profesional-plus.jpg",
    specs: {
      cpu: "Intel Core i5 13va Gen",
      ram: "16GB DDR5",
      storage: "512GB NVMe SSD",
      screen: "14 pulgadas Full HD IPS",
      battery: "Hasta 10 horas"
    },
    description: "Para profesionales en movimiento. Rápida, duradera y lista para cualquier tarea."
  },
  {
    id: 10,
    name: "Laptop Gaming Portátil",
    category: "laptop",
    subcategory: "gaming",
    price: 4500,
    stock: 6,
    reserved: 0,
    image: "assets/img/productos/10-laptop-gaming-portatil.jpg",
    specs: {
      cpu: "AMD Ryzen 7 7735HS",
      ram: "16GB DDR5",
      storage: "1TB NVMe SSD",
      gpu: "NVIDIA RTX 4050 6GB",
      screen: "15.6 pulgadas Full HD 144Hz"
    },
    description: "Juega donde quieras. Potencia gamer en formato portátil."
  },
  {
    id: 11,
    name: "Laptop Gaming Elite",
    category: "laptop",
    subcategory: "gaming",
    price: 6800,
    stock: 4,
    reserved: 0,
    image: "assets/img/productos/11-laptop-gaming-elite.jpg",
    specs: {
      cpu: "Intel Core i7 13va Gen HX",
      ram: "32GB DDR5",
      storage: "2TB NVMe SSD",
      gpu: "NVIDIA RTX 4060 8GB",
      screen: "16 pulgadas QHD 165Hz"
    },
    description: "Gaming sin límites. Máximo rendimiento en pantalla de alta calidad."
  },
  {
    id: 12,
    name: "Laptop Ultrabook Premium",
    category: "laptop",
    subcategory: "ultrabook",
    price: 5200,
    stock: 5,
    reserved: 0,
    image: "assets/img/productos/12-laptop-ultrabook-premium.jpg",
    specs: {
      cpu: "Intel Core i7 13va Gen",
      ram: "16GB LPDDR5",
      storage: "1TB NVMe SSD",
      screen: "13.3 pulgadas 2K OLED",
      weight: "1.2kg",
      battery: "Hasta 14 horas"
    },
    description: "Ultra delgada, ultra ligera, ultra elegante. Lujo y rendimiento en una sola laptop."
  },
  {
    id: 13,
    name: "Laptop 2-en-1 Convertible",
    category: "laptop",
    subcategory: "convertible",
    price: 3800,
    stock: 7,
    reserved: 0,
    image: "assets/img/productos/13-laptop-2-en-1-convertible.jpg",
    specs: {
      cpu: "Intel Core i5 12va Gen",
      ram: "16GB DDR4",
      storage: "512GB SSD",
      screen: "14 pulgadas Full HD táctil",
      extras: "Lápiz digital incluido"
    },
    description: "Laptop y tablet en uno. Perfecta para diseñadores, estudiantes creativos y presentaciones."
  },
  {
    id: 14,
    name: "Laptop Creador de Contenido",
    category: "laptop",
    subcategory: "creator",
    price: 7500,
    stock: 3,
    reserved: 0,
    image: "assets/img/productos/14-laptop-creador-contenido.jpg",
    specs: {
      cpu: "Intel Core i9 13va Gen",
      ram: "32GB DDR5",
      storage: "2TB NVMe SSD",
      gpu: "NVIDIA RTX 4070 8GB",
      screen: "16 pulgadas 4K OLED"
    },
    description: "Para creadores profesionales. Edita video 4K, diseña y renderiza sin esperas."
  },

  // ========== ALL-IN-ONE (2 productos) ==========
  {
    id: 15,
    name: "All-in-One Familiar",
    category: "all-in-one",
    subcategory: "home",
    price: 2400,
    stock: 8,
    reserved: 0,
    image: "assets/img/productos/15-all-in-one-familiar.jpg",
    specs: {
      cpu: "Intel Core i5 12va Gen",
      ram: "8GB DDR4",
      storage: "512GB SSD",
      screen: "23.8 pulgadas Full HD",
      extras: "Webcam HD, parlantes integrados"
    },
    description: "Todo en uno, sin cables. Perfecto para la familia, oficina en casa y entretenimiento."
  },
  {
    id: 16,
    name: "All-in-One Pro Designer",
    category: "all-in-one",
    subcategory: "professional",
    price: 5500,
    stock: 3,
    reserved: 0,
    image: "assets/img/productos/16-all-in-one-pro-designer.jpg",
    specs: {
      cpu: "Intel Core i7 13va Gen",
      ram: "32GB DDR5",
      storage: "1TB NVMe SSD",
      gpu: "NVIDIA RTX 3050 4GB",
      screen: "27 pulgadas 4K IPS",
      extras: "Calibración de color profesional"
    },
    description: "Pantalla espectacular para diseñadores y creativos. Espacio limpio, resultados profesionales."
  },

  // ========== COMPONENTES (4 productos) ==========
  {
    id: 17,
    name: "Memoria RAM 16GB DDR4",
    category: "component",
    subcategory: "ram",
    price: 180,
    stock: 50,
    reserved: 0,
    image: "assets/img/productos/17-memoria-ram-16gb-ddr4.jpg",
    specs: {
      type: "DDR4",
      capacity: "16GB (2x8GB)",
      speed: "3200MHz",
      brand: "Kingston Fury"
    },
    description: "Aumenta la velocidad de tu PC. Más memoria para trabajar con muchos programas a la vez."
  },
  {
    id: 18,
    name: "Disco SSD 1TB NVMe",
    category: "component",
    subcategory: "storage",
    price: 280,
    stock: 30,
    reserved: 0,
    image: "assets/img/productos/18-disco-ssd-1tb-nvme.jpg",
    specs: {
      type: "SSD NVMe Gen 3",
      capacity: "1TB",
      speed: "Lectura 3500MB/s",
      brand: "Western Digital Blue"
    },
    description: "Velocidad extrema. Tu PC arrancará en segundos y tus programas abrirán al instante."
  },
  {
    id: 19,
    name: "Gabinete Gaming RGB",
    category: "component",
    subcategory: "case",
    price: 320,
    stock: 12,
    reserved: 0,
    image: "assets/img/productos/19-gabinete-gaming-rgb.jpg",
    specs: {
      type: "Mid Tower ATX",
      fans: "4 ventiladores RGB incluidos",
      material: "Cristal templado",
      brand: "Cooler Master"
    },
    description: "Dale estilo gamer a tu setup. Excelente flujo de aire y espacio para tus componentes."
  },
  {
    id: 20,
    name: "Kit Actualización Intel i5",
    category: "component",
    subcategory: "upgrade-kit",
    price: 1650,
    stock: 8,
    reserved: 0,
    image: "assets/img/productos/20-kit-actualizacion-intel-i5.jpg",
    specs: {
      includes: "CPU Intel i5 13va Gen + Placa B760 + 16GB DDR5",
      socket: "LGA 1700",
      compatibility: "Listo para instalar"
    },
    description: "Actualiza tu PC de golpe. Kit completo con procesador, placa madre y memoria de última generación."
  }
];

// Función helper para obtener categorías únicas
function getCategories() {
  return [...new Set(PRODUCTS.map(p => p.category))];
}

// Función helper para obtener productos por categoría
function getProductsByCategory(category) {
  return PRODUCTS.filter(p => p.category === category);
}

// Función helper para obtener stock disponible
function getAvailableStock(productId) {
  const product = PRODUCTS.find(p => p.id === productId);
  if (!product) return 0;
  return product.stock - product.reserved;
}

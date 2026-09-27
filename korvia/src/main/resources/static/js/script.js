/*
 * JavaScript - Entrega 1
 * Interacciones básicas del frontend sin backend ni persistencia.
 */

document.addEventListener('DOMContentLoaded', () => {
inicializarCatalogo();
    inicializarDetalle();
    inicializarFormularios();
    inicializarAdministracion();
    inicializarBotonesGenerales();
});

function formatoPrecio(precio) {
    return `S/ ${Number(precio).toLocaleString('es-PE', { minimumFractionDigits: 2 })}`;
}

function inicializarCatalogo() {
    const grid = document.getElementById('productGrid');
    if (!grid || typeof PRODUCTS === 'undefined') return;

    const botones = document.querySelectorAll('[data-category]');
    const mostrarProductos = (categoria = 'all') => {
        const productos = categoria === 'all'
            ? PRODUCTS
            : PRODUCTS.filter(producto => producto.category === categoria);

        grid.innerHTML = productos.map(producto => `
            <div class="col-12 col-md-6 col-lg-4">
                <div class="card h-100 shadow-sm">
                    <img src="${producto.image}" class="card-img-top" alt="${producto.name}" style="height: 220px; object-fit: cover;">
                    <div class="card-body d-flex flex-column">
                        <span class="badge bg-light text-primary align-self-start mb-2">${nombreCategoria(producto.category)}</span>
                        <h5 class="card-title">${producto.name}</h5>
                        <p class="card-text text-muted small flex-grow-1">${producto.description}</p>
                        <p class="fw-bold text-primary fs-5 mb-3">${formatoPrecio(producto.price)}</p>
                        <a href="detalle.html?id=${producto.id}" class="btn btn-primary">
                            <i class="bi bi-eye me-1"></i>Ver detalle
                        </a>
                    </div>
                </div>
            </div>
        `).join('');
    };

    mostrarProductos();
    botones.forEach(boton => {
        boton.addEventListener('click', () => {
            botones.forEach(item => item.classList.remove('active'));
            boton.classList.add('active');
            mostrarProductos(boton.dataset.category);
        });
    });
}

function nombreCategoria(categoria) {
    const nombres = {
        desktop: 'PC Escritorio',
        laptop: 'Laptop',
        'all-in-one': 'All-in-One',
        component: 'Componente'
    };
    return nombres[categoria] || 'Producto';
}

function inicializarDetalle() {
    const nombre = document.getElementById('productName');
    if (!nombre || typeof PRODUCTS === 'undefined') return;

    const id = Number(new URLSearchParams(window.location.search).get('id')) || 1;
    const producto = PRODUCTS.find(item => item.id === id) || PRODUCTS[0];

    const elementos = {
        productName: producto.name,
        productCategory: nombreCategoria(producto.category),
        productPrice: formatoPrecio(producto.price),
        productDescription: producto.description,
        maxQuantityText: `Stock disponible: ${producto.stock}`
    };

    Object.entries(elementos).forEach(([idElemento, valor]) => {
        const elemento = document.getElementById(idElemento);
        if (elemento) elemento.textContent = valor;
    });

    const imagen = document.getElementById('productImage');
    if (imagen) {
        imagen.src = producto.image;
        imagen.alt = producto.name;
    }

    const specs = document.getElementById('specsList');
    if (specs && producto.specs) {
        specs.innerHTML = Object.entries(producto.specs)
            .map(([clave, valor]) => `<li class="list-group-item"><strong>${clave.toUpperCase()}:</strong> ${valor}</li>`)
            .join('');
    }

    const quantity = document.getElementById('quantity');
    const decrease = document.getElementById('decreaseQty');
    const increase = document.getElementById('increaseQty');

    if (quantity) {
        quantity.value = 1;
        quantity.max = producto.stock;
    }
    decrease?.addEventListener('click', () => {
        if (quantity) quantity.value = Math.max(1, Number(quantity.value) - 1);
    });
    increase?.addEventListener('click', () => {
        if (quantity) quantity.value = Math.min(producto.stock, Number(quantity.value) + 1);
    });

    document.getElementById('addToCartBtn')?.addEventListener('click', () => {
        alert(`Producto seleccionado: ${producto.name}`);
    });
    document.getElementById('reserveBtn')?.addEventListener('click', () => {
        alert(`Reserva simulada para: ${producto.name}`);
    });
}

function inicializarFormularios() {
    const mensajes = {
        loginForm: 'Inicio de sesión simulado correctamente.',
        registroForm: 'Registro simulado correctamente.',
        contactoForm: 'Mensaje enviado correctamente. Esta acción es solo una simulación.'
    };

    Object.entries(mensajes).forEach(([idFormulario, mensaje]) => {
        const formulario = document.getElementById(idFormulario);
        if (!formulario) return;
        formulario.addEventListener('submit', event => {
            event.preventDefault();
            if (!formulario.checkValidity()) {
                formulario.classList.add('was-validated');
                return;
            }
            alert(mensaje);
            formulario.reset();
            formulario.classList.remove('was-validated');
        });
    });
}

function inicializarAdministracion() {
    const tabla = document.getElementById('inventoryBody');
    if (!tabla || typeof PRODUCTS === 'undefined') return;

    const total = document.getElementById('totalProducts');
    const stockBajo = document.getElementById('lowStockProducts');
    if (total) total.textContent = PRODUCTS.length;
    if (stockBajo) stockBajo.textContent = PRODUCTS.filter(producto => producto.stock < 5).length;

    tabla.innerHTML = PRODUCTS.map(producto => `
        <tr>
            <td>${producto.id}</td>
            <td>${producto.name}</td>
            <td>${nombreCategoria(producto.category)}</td>
            <td>${formatoPrecio(producto.price)}</td>
            <td><span class="badge ${producto.stock < 5 ? 'bg-warning text-dark' : 'bg-success'}">${producto.stock}</span></td>
            <td><button class="btn btn-sm btn-outline-primary" type="button" onclick="verProducto(${producto.id})"><i class="bi bi-eye"></i></button></td>
        </tr>
    `).join('');

    document.getElementById('searchBtn')?.addEventListener('click', filtrarInventario);
    document.getElementById('searchProducts')?.addEventListener('input', filtrarInventario);

    document.getElementById('productForm')?.addEventListener('submit', event => {
        event.preventDefault();
        if (!event.currentTarget.checkValidity()) {
            event.currentTarget.classList.add('was-validated');
            return;
        }
        alert('Producto agregado de forma simulada. No se guarda en una base de datos.');
        bootstrap.Modal.getInstance(document.getElementById('addProductModal'))?.hide();
        event.currentTarget.reset();
    });
}

function filtrarInventario() {
    const texto = (document.getElementById('searchProducts')?.value || '').toLowerCase();
    document.querySelectorAll('#inventoryBody tr').forEach(fila => {
        fila.style.display = fila.textContent.toLowerCase().includes(texto) ? '' : 'none';
    });
}

function verProducto(id) {
    const producto = PRODUCTS.find(item => item.id === id);
    if (producto) alert(`${producto.name}\nPrecio: ${formatoPrecio(producto.price)}\nStock: ${producto.stock}`);
}

function inicializarBotonesGenerales() {
    document.querySelectorAll('[data-bs-toggle="modal"]').forEach(elemento => {
        elemento.addEventListener('click', () => {
            // El comportamiento del modal lo gestiona Bootstrap.
        });
    });
}

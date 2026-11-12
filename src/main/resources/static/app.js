const URL_SESION = "/api/v1/sesion";
const URL_USUARIOS = "/api/v1/usuarios";
const URL_ROLES = "/api/v1/roles";
const URL_ESTADOS = "/api/v1/estados";

const ROL_ADMINISTRADOR = "Administrador";
const ROL_SIN_ASIGNAR = "Sin asignar";
const ESTADO_PENDIENTE = "Pendiente";
const ESTADO_ACTIVO = "Activo";
const ESTADO_INACTIVO = "Inactivo";

const VISTAS = ["vistaLogin", "vistaRegistro", "vistaAdmin", "vistaRepartidor"];

//sin filtros el panel muestra solo los mas recientes; los demas se encuentran buscando
const CANTIDAD_USUARIOS_RECIENTES = 5;

//el color de la pastilla de cada estado (las clases estan en estilos.css)
const CLASE_ESTADO = {
    [ESTADO_PENDIENTE]: "pildora-pendiente",
    [ESTADO_ACTIVO]: "pildora-activo",
    [ESTADO_INACTIVO]: "pildora-inactivo"
};

const cajaMensaje = document.getElementById("mensaje");
const barraSesion = document.getElementById("barraSesion");
const usuarioActual = document.getElementById("usuarioActual");
const rolActual = document.getElementById("rolActual");
const botonCerrarSesion = document.getElementById("botonCerrarSesion");

const formularioLogin = document.getElementById("formularioLogin");
const loginUsuario = document.getElementById("loginUsuario");
const loginContrasena = document.getElementById("loginContrasena");
const botonIrARegistro = document.getElementById("botonIrARegistro");

const formularioRegistro = document.getElementById("formularioRegistro");
const registroNombre = document.getElementById("registroNombre");
const registroCorreo = document.getElementById("registroCorreo");
const registroUsuario = document.getElementById("registroUsuario");
const registroContrasena = document.getElementById("registroContrasena");
const botonIrALogin = document.getElementById("botonIrALogin");

const tipoBusqueda = document.getElementById("tipoBusqueda");
const textoBusqueda = document.getElementById("textoBusqueda");
const filtroRol = document.getElementById("filtroRol");
const filtroEstado = document.getElementById("filtroEstado");
const botonBuscar = document.getElementById("botonBuscar");
const botonVerTodos = document.getElementById("botonVerTodos");
const tablaUsuarios = document.getElementById("tablaUsuarios");
const avisoRecientes = document.getElementById("avisoRecientes");

const dialogoHistorial = document.getElementById("dialogoHistorial");
const tituloHistorial = document.getElementById("tituloHistorial");
const tablaHistorial = document.getElementById("tablaHistorial");
const botonCerrarHistorial = document.getElementById("botonCerrarHistorial");

const saludoRepartidor = document.getElementById("saludoRepartidor");
const textoRepartidor = document.getElementById("textoRepartidor");

//los roles que se le pueden dar a un usuario (todos menos 'Sin asignar')
let rolesAsignables = [];
//para que el mensaje anterior no esconda al nuevo antes de tiempo
let temporizadorMensaje;

// ---------- utilidades (igual que en la libreria) ----------

function concatenar(...partes) {
    return partes.join("");
}

function mostrarMensaje(texto, tipo) {
    clearTimeout(temporizadorMensaje);
    cajaMensaje.textContent = texto;
    cajaMensaje.className = concatenar("mensaje ", tipo);
    temporizadorMensaje = setTimeout(() => cajaMensaje.classList.add("oculto"), 4000);
}

function validarObligatorio(dato, mensaje) {
    if (dato.trim() === "") {
        mostrarMensaje(mensaje, "error");
        return false;
    }
    return true;
}

//muestra una sola pantalla y esconde las demas
function mostrarVista(idVista) {
    VISTAS.forEach(id => document.getElementById(id).classList.toggle("oculto", id !== idVista));
}

async function llamarApi(url, metodo, cuerpo) {
    const opciones = { method: metodo, headers: { "Content-Type": "application/json" } };
    if (cuerpo) {
        opciones.body = JSON.stringify(cuerpo);
    }
    let respuesta;
    try {
        if (metodo !== "GET") {
            const csrf = await (await fetch("/api/v1/csrf")).json();
            opciones.headers[csrf.headerName] = csrf.token;
        }
        respuesta = await fetch(url, opciones);
    } catch (error) {
        throw new Error("Se presentó un problema con el servidor");
    }
    //401 y 403 los responde Spring Security, no nuestros controladores
    if (respuesta.status === 401) {
        throw new Error("Debes iniciar sesión para hacer esto");
    }
    if (respuesta.status === 403) {
        throw new Error("No tienes permiso para esta acción");
    }
    const json = await respuesta.json();
    if (!respuesta.ok) {
        throw new Error(json.mensajes && json.mensajes.length > 0 ? json.mensajes[0] : "Se presentó un problema inesperado");
    }
    return json;
}

function crearElemento(etiqueta, clase, texto) {
    const elemento = document.createElement(etiqueta);
    if (clase) {
        elemento.className = clase;
    }
    if (texto !== undefined) {
        elemento.textContent = texto;
    }
    return elemento;
}

function crearPildora(texto, clase) {
    return crearElemento("span", concatenar("pildora ", clase), texto);
}

function crearFilaAviso(texto, columnas) {
    const fila = document.createElement("tr");
    const celda = crearElemento("td", "aviso", texto);
    celda.colSpan = columnas;
    fila.appendChild(celda);
    return fila;
}

function crearBoton(texto, clase, alHacerClic) {
    const boton = crearElemento("button", clase, texto);
    boton.type = "button";
    boton.onclick = alHacerClic;
    return boton;
}

function crearOpcion(valor, texto) {
    const opcion = crearElemento("option", "", texto);
    opcion.value = valor;
    return opcion;
}

function llenarLista(lista, opciones, textoTodos) {
    lista.innerHTML = "";
    if (textoTodos) {
        lista.appendChild(crearOpcion("", textoTodos));
    }
    opciones.forEach(opcion => lista.appendChild(crearOpcion(opcion.id, opcion.nombre)));
}

//"2026-11-12T08:38:58.46" -> "2026-11-12 08:38"
function formatearFechaHora(texto) {
    return texto.substring(0, 16).replace("T", " ");
}

//"2026-11-12T08:38:58.46" -> "2026-11-12"
function formatearFecha(texto) {
    return texto.substring(0, 10);
}

// ---------- sesion ----------

//al abrir la pagina: si ya hay alguien logueado entra directo, si no muestra el login
async function iniciar() {
    try {
        const respuesta = await llamarApi(URL_SESION, "GET");
        if (respuesta.datos.length > 0) {
            await entrar(respuesta.datos[0]);
        } else {
            mostrarVista("vistaLogin");
        }
    } catch (error) {
        mostrarVista("vistaLogin");
        mostrarMensaje(error.message, "error");
    }
}

//cada rol ve su pantalla (RF10.8)
async function entrar(usuario) {
    usuarioActual.textContent = usuario.nombre;
    rolActual.textContent = usuario.rol.nombre;
    barraSesion.classList.remove("oculto");
    if (usuario.rol.nombre === ROL_ADMINISTRADOR) {
        mostrarVista("vistaAdmin");
        await cargarListas();
        await consultarUsuarios();
    } else {
        saludoRepartidor.textContent = concatenar("Hola, ", usuario.nombre);
        textoRepartidor.textContent = concatenar("Iniciaste sesión como ", usuario.rol.nombre, ".");
        mostrarVista("vistaRepartidor");
    }
}

formularioLogin.addEventListener("submit", async evento => {
    evento.preventDefault();
    if (!validarObligatorio(loginUsuario.value, "Escribe tu usuario o correo")
        || !validarObligatorio(loginContrasena.value, "Escribe tu contraseña")) {
        return;
    }
    try {
        const datos = { usuarioOCorreo: loginUsuario.value, contrasena: loginContrasena.value };
        const respuesta = await llamarApi(URL_SESION, "POST", datos);
        formularioLogin.reset();
        await entrar(respuesta.datos[0]);
    } catch (error) {
        mostrarMensaje(error.message, "error");
    }
});

botonCerrarSesion.addEventListener("click", async () => {
    try {
        const respuesta = await llamarApi(URL_SESION, "DELETE");
        mostrarMensaje(respuesta.mensajes[0], "exito");
    } catch (error) {
        mostrarMensaje(error.message, "error");
    }
    barraSesion.classList.add("oculto");
    mostrarVista("vistaLogin");
});

// ---------- registro ----------

formularioRegistro.addEventListener("submit", async evento => {
    evento.preventDefault();
    const datos = {
        nombre: registroNombre.value,
        correo: registroCorreo.value,
        nombreUsuario: registroUsuario.value,
        contrasena: registroContrasena.value
    };
    if (!validarObligatorio(datos.nombre, "El nombre es obligatorio")
        || !validarObligatorio(datos.correo, "El correo es obligatorio")
        || !validarObligatorio(datos.nombreUsuario, "El nombre de usuario es obligatorio")
        || !validarObligatorio(datos.contrasena, "La contraseña es obligatoria")) {
        return;
    }
    try {
        const respuesta = await llamarApi(URL_USUARIOS, "POST", datos);
        formularioRegistro.reset();
        mostrarVista("vistaLogin");
        mostrarMensaje(respuesta.mensajes[0], "exito");
    } catch (error) {
        mostrarMensaje(error.message, "error");
    }
});

botonIrARegistro.addEventListener("click", () => mostrarVista("vistaRegistro"));
botonIrALogin.addEventListener("click", () => mostrarVista("vistaLogin"));

// ---------- panel del administrador ----------

//llena los desplegables de rol y estado con lo que hay en la base de datos
async function cargarListas() {
    const roles = await llamarApi(URL_ROLES, "GET");
    const estados = await llamarApi(URL_ESTADOS, "GET");
    rolesAsignables = roles.datos.filter(rol => rol.nombre !== ROL_SIN_ASIGNAR);
    llenarLista(filtroRol, roles.datos, "Todos");
    llenarLista(filtroEstado, estados.datos, "Todos");
}

//arma la direccion con los filtros: /api/v1/usuarios?nombre=ana&rol.id=...&estadoUsuario.id=...
async function consultarUsuarios() {
    const parametros = new URLSearchParams();
    if (textoBusqueda.value.trim() !== "") {
        parametros.append(tipoBusqueda.value, textoBusqueda.value.trim());
    }
    if (filtroRol.value) {
        parametros.append("rol.id", filtroRol.value);
    }
    if (filtroEstado.value) {
        parametros.append("estadoUsuario.id", filtroEstado.value);
    }
    const hayFiltros = parametros.toString() !== "";
    try {
        const respuesta = await llamarApi(concatenar(URL_USUARIOS, "?", parametros.toString()), "GET");
        let usuarios = respuesta.datos;
        //sin filtros: solo los 5 registrados mas recientemente (la fecha mas nueva primero)
        if (!hayFiltros) {
            usuarios = [...usuarios]
                .sort((primero, segundo) => segundo.fechaRegistro.localeCompare(primero.fechaRegistro))
                .slice(0, CANTIDAD_USUARIOS_RECIENTES);
        }
        const hayMas = !hayFiltros && respuesta.datos.length > CANTIDAD_USUARIOS_RECIENTES;
        avisoRecientes.textContent = concatenar("Mostrando los ", CANTIDAD_USUARIOS_RECIENTES,
            " usuarios registrados más recientemente. Usa los filtros para encontrar los demás.");
        avisoRecientes.classList.toggle("oculto", !hayMas);
        pintarUsuarios(usuarios);
    } catch (error) {
        mostrarMensaje(error.message, "error");
    }
}

//una fila por usuario; los botones dependen del estado de la cuenta
function pintarUsuarios(usuarios) {
    tablaUsuarios.innerHTML = "";
    if (usuarios.length === 0) {
        tablaUsuarios.appendChild(crearFilaAviso("No se encontraron usuarios", 5));
        return;
    }
    usuarios.forEach(usuario => {
        const fila = document.createElement("tr");
        const estado = usuario.estadoUsuario.nombre;

        //columna usuario: el nombre en negrita y debajo @usuario y el correo
        const celdaUsuario = document.createElement("td");
        celdaUsuario.append(
            crearElemento("div", "nombre-usuario", usuario.nombre),
            crearElemento("div", "detalle-usuario", concatenar("@", usuario.nombreUsuario, " · ", usuario.correo))
        );

        const celdaRol = document.createElement("td");
        celdaRol.appendChild(crearPildora(usuario.rol.nombre, "pildora-rol"));

        const celdaEstado = document.createElement("td");
        celdaEstado.appendChild(crearPildora(estado, CLASE_ESTADO[estado] || "pildora-inactivo"));

        const celdaRegistro = crearElemento("td", "", formatearFecha(usuario.fechaRegistro));

        const celdaAcciones = document.createElement("td");
        const acciones = crearElemento("div", "acciones");

        //pendiente: se aprueba eligiendo el rol / activo: se le puede cambiar el rol
        if (estado === ESTADO_PENDIENTE || estado === ESTADO_ACTIVO) {
            const listaRol = document.createElement("select");
            llenarLista(listaRol, rolesAsignables);
            if (estado === ESTADO_ACTIVO) {
                listaRol.value = usuario.rol.id;
            }
            const accion = estado === ESTADO_PENDIENTE ? "aprobacion" : "rol";
            const texto = estado === ESTADO_PENDIENTE ? "Aprobar" : "Cambiar rol";
            const clase = estado === ESTADO_PENDIENTE ? "" : "secundario";
            acciones.append(listaRol, crearBoton(texto, clase, () => accionUsuario(usuario, accion, { id: listaRol.value })));
        }
        if (estado === ESTADO_ACTIVO) {
            acciones.append(crearBoton("Desactivar", "peligro", () => accionUsuario(usuario, "desactivacion")));
        }
        if (estado === ESTADO_INACTIVO) {
            acciones.append(crearBoton("Reactivar", "", () => accionUsuario(usuario, "reactivacion")));
        }
        acciones.append(crearBoton("Historial", "secundario", () => consultarHistorial(usuario)));
        celdaAcciones.appendChild(acciones);

        fila.append(celdaUsuario, celdaRol, celdaEstado, celdaRegistro, celdaAcciones);
        tablaUsuarios.appendChild(fila);
    });
}

//aprobar, cambiar rol, desactivar o reactivar: PUT /api/v1/usuarios/{id}/{accion}
async function accionUsuario(usuario, accion, cuerpo) {
    if (accion === "desactivacion" && !confirm(concatenar("¿Seguro que deseas desactivar a ", usuario.nombre, "?"))) {
        return;
    }
    try {
        const respuesta = await llamarApi(concatenar(URL_USUARIOS, "/", usuario.id, "/", accion), "PUT", cuerpo);
        mostrarMensaje(respuesta.mensajes[0], "exito");
        await consultarUsuarios();
    } catch (error) {
        mostrarMensaje(error.message, "error");
    }
}

//abre la ventanita con los cambios que ha tenido la cuenta del usuario
async function consultarHistorial(usuario) {
    try {
        const respuesta = await llamarApi(concatenar(URL_USUARIOS, "/", usuario.id, "/historial"), "GET");
        tituloHistorial.textContent = concatenar("Historial de ", usuario.nombre);
        tablaHistorial.innerHTML = "";
        if (respuesta.datos.length === 0) {
            tablaHistorial.appendChild(crearFilaAviso("Este usuario todavía no tiene cambios", 4));
        }
        respuesta.datos.forEach(registro => {
            const fila = document.createElement("tr");
            const celdaRol = document.createElement("td");
            celdaRol.appendChild(crearPildora(registro.rol.nombre, "pildora-rol"));
            const celdaEstado = document.createElement("td");
            const estado = registro.estadoUsuario.nombre;
            celdaEstado.appendChild(crearPildora(estado, CLASE_ESTADO[estado] || "pildora-inactivo"));
            fila.append(
                crearElemento("td", "", formatearFechaHora(registro.fecha)),
                celdaRol,
                celdaEstado,
                crearElemento("td", "", registro.administrador.nombre)
            );
            tablaHistorial.appendChild(fila);
        });
        dialogoHistorial.showModal();
    } catch (error) {
        mostrarMensaje(error.message, "error");
    }
}

botonBuscar.addEventListener("click", consultarUsuarios);

//buscar tambien con la tecla Enter
textoBusqueda.addEventListener("keydown", evento => {
    if (evento.key === "Enter") {
        consultarUsuarios();
    }
});

botonVerTodos.addEventListener("click", async () => {
    textoBusqueda.value = "";
    filtroRol.value = "";
    filtroEstado.value = "";
    await consultarUsuarios();
});

botonCerrarHistorial.addEventListener("click", () => dialogoHistorial.close());

iniciar();

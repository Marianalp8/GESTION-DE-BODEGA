package co.edu.uco.bodegapp.negocio.negocio.impl;

import co.edu.uco.bodegapp.dao.factoria.DAOFactory;
import co.edu.uco.bodegapp.dominio.UsuarioDominio;
import co.edu.uco.bodegapp.entidad.EstadoUsuarioEntidad;
import co.edu.uco.bodegapp.entidad.HistorialUsuarioEntidad;
import co.edu.uco.bodegapp.entidad.RolEntidad;
import co.edu.uco.bodegapp.entidad.UsuarioEntidad;
import co.edu.uco.bodegapp.negocio.negocio.UsuarioNegocio;
import co.edu.uco.bodegapp.negocio.negocio.assembler.impl.UsuarioEntidadAssembler;
import co.edu.uco.bodegapp.negocio.negocio.reglas.impl.usuario.*;
import co.edu.uco.bodegapp.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.bodegapp.transversal.excepciones.BodegappNegocioExcepcion;
import co.edu.uco.bodegapp.transversal.utilitario.UtilContrasena;
import co.edu.uco.bodegapp.transversal.utilitario.UtilFecha;
import co.edu.uco.bodegapp.transversal.utilitario.UtilId;

import java.util.UUID;

public class UsuarioNegocioImpl implements UsuarioNegocio {

    private static final String ESTADO_ACTIVO = "Activo";
    private static final String ESTADO_INACTIVO = "Inactivo";

    private final DAOFactory daoFactory;

    public UsuarioNegocioImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void registrarInformacionNuevoUsuario(UsuarioDominio datos) {
        ValidarDatosRegistrarNuevoUsuarioRule.obtenerInstancia().ejecutar(datos);
        AsegurarCorreoNoRegistradoRule.obtenerInstancia().ejecutar(datos.getCorreo(), daoFactory);
        AsegurarNombreUsuarioNoRegistradoRule.obtenerInstancia().ejecutar(datos.getNombreUsuario(), daoFactory);

        //no se copian el rol ni el estado que mande la pantalla: el Builder deja 'Sin asignar' y 'Pendiente'
        var usuarioNuevo = new UsuarioDominio.Builder()
                .id(UtilId.generarId())
                .nombre(datos.getNombre())
                .correo(datos.getCorreo())
                .nombreUsuario(datos.getNombreUsuario())
                .contrasena(UtilContrasena.encriptar(datos.getContrasena()))
                .fechaRegistro(UtilFecha.obtenerFechaHoraActual())
                .build();
        var usuarioEntidad = UsuarioEntidadAssembler.getInstance().convertirAEntidad(usuarioNuevo);
        daoFactory.obtenerUsuarioDAO().crear(usuarioEntidad);
    }

    @Override
    public UsuarioDominio iniciarSesion(String usuarioOCorreo, String contrasena) {
        ValidarDatosInicioSesionRule.obtenerInstancia().ejecutar(usuarioOCorreo, contrasena);

        var usuarioEncontrado = buscarPorNombreUsuarioOCorreo(usuarioOCorreo);
        AsegurarCredencialesCorrectasRule.obtenerInstancia().ejecutar(usuarioEncontrado, contrasena);
        AsegurarCuentaPuedeIniciarSesionRule.obtenerInstancia().ejecutar(usuarioEncontrado);

        //la contrasena nunca se devuelve, ni encriptada: el Builder la deja vacia
        var usuarioSinContrasena = new UsuarioEntidad.Builder()
                .id(usuarioEncontrado.getId())
                .nombre(usuarioEncontrado.getNombre())
                .correo(usuarioEncontrado.getCorreo())
                .nombreUsuario(usuarioEncontrado.getNombreUsuario())
                .rol(usuarioEncontrado.getRol())
                .estadoUsuario(usuarioEncontrado.getEstadoUsuario())
                .fechaRegistro(usuarioEncontrado.getFechaRegistro())
                .build();
        return UsuarioEntidadAssembler.getInstance().convertirADominio(usuarioSinContrasena);
    }

    @Override
    public void aprobarUsuario(UUID idAdministrador, UUID idUsuario, UUID idRol) {
        AsegurarQuienGestionaEsAdministradorActivoRule.obtenerInstancia().ejecutar(idAdministrador, daoFactory);
        AsegurarUsuarioExisteRule.obtenerInstancia().ejecutar(idUsuario, daoFactory);
        var usuarioActual = daoFactory.obtenerUsuarioDAO().consultarPorId(idUsuario);
        AsegurarUsuarioEstaPendienteRule.obtenerInstancia().ejecutar(usuarioActual);
        AsegurarRolEsAsignableRule.obtenerInstancia().ejecutar(idRol, daoFactory);

        var rolAsignado = daoFactory.obtenerRolDAO().consultarPorId(idRol);
        var estadoActivo = consultarEstadoPorNombre(ESTADO_ACTIVO);
        guardarCambioConHistorial(idAdministrador, usuarioActual, rolAsignado, estadoActivo);
    }

    @Override
    public void cambiarRolUsuario(UUID idAdministrador, UUID idUsuario, UUID idRol) {
        AsegurarQuienGestionaEsAdministradorActivoRule.obtenerInstancia().ejecutar(idAdministrador, daoFactory);
        AsegurarUsuarioExisteRule.obtenerInstancia().ejecutar(idUsuario, daoFactory);
        var usuarioActual = daoFactory.obtenerUsuarioDAO().consultarPorId(idUsuario);
        AsegurarUsuarioEstaActivoRule.obtenerInstancia().ejecutar(usuarioActual);
        AsegurarRolEsAsignableRule.obtenerInstancia().ejecutar(idRol, daoFactory);
        AsegurarRolEsDiferenteAlActualRule.obtenerInstancia().ejecutar(usuarioActual, idRol);
        //si era administrador y deja de serlo, debe quedar al menos otro administrador activo
        AsegurarNoEsUltimoAdministradorActivoRule.obtenerInstancia().ejecutar(usuarioActual, daoFactory);

        var rolNuevo = daoFactory.obtenerRolDAO().consultarPorId(idRol);
        guardarCambioConHistorial(idAdministrador, usuarioActual, rolNuevo, usuarioActual.getEstadoUsuario());
    }

    @Override
    public void desactivarUsuario(UUID idAdministrador, UUID idUsuario) {
        AsegurarQuienGestionaEsAdministradorActivoRule.obtenerInstancia().ejecutar(idAdministrador, daoFactory);
        AsegurarUsuarioExisteRule.obtenerInstancia().ejecutar(idUsuario, daoFactory);
        AsegurarAdministradorNoSeDesactivaASiMismoRule.obtenerInstancia().ejecutar(idAdministrador, idUsuario);
        var usuarioActual = daoFactory.obtenerUsuarioDAO().consultarPorId(idUsuario);
        AsegurarUsuarioEstaActivoRule.obtenerInstancia().ejecutar(usuarioActual);
        AsegurarNoEsUltimoAdministradorActivoRule.obtenerInstancia().ejecutar(usuarioActual, daoFactory);

        var estadoInactivo = consultarEstadoPorNombre(ESTADO_INACTIVO);
        guardarCambioConHistorial(idAdministrador, usuarioActual, usuarioActual.getRol(), estadoInactivo);
    }

    @Override
    public void reactivarUsuario(UUID idAdministrador, UUID idUsuario) {
        AsegurarQuienGestionaEsAdministradorActivoRule.obtenerInstancia().ejecutar(idAdministrador, daoFactory);
        AsegurarUsuarioExisteRule.obtenerInstancia().ejecutar(idUsuario, daoFactory);
        var usuarioActual = daoFactory.obtenerUsuarioDAO().consultarPorId(idUsuario);
        AsegurarUsuarioEstaInactivoRule.obtenerInstancia().ejecutar(usuarioActual);

        //vuelve con el mismo rol que tenia
        var estadoActivo = consultarEstadoPorNombre(ESTADO_ACTIVO);
        guardarCambioConHistorial(idAdministrador, usuarioActual, usuarioActual.getRol(), estadoActivo);
    }

    //se entra con el nombre de usuario O con el correo: primero se busca por uno y, si no aparece, por el otro
    //si no aparece por ninguno se devuelve un usuario vacio y la regla de credenciales lo rechaza
    private UsuarioEntidad buscarPorNombreUsuarioOCorreo(String usuarioOCorreo) {
        var usuarioDao = daoFactory.obtenerUsuarioDAO();

        var filtroPorNombreUsuario = new UsuarioEntidad.Builder().nombreUsuario(usuarioOCorreo).build();
        var usuariosEncontrados = usuarioDao.consultarPorFiltro(filtroPorNombreUsuario);

        if (usuariosEncontrados.isEmpty()) {
            var filtroPorCorreo = new UsuarioEntidad.Builder().correo(usuarioOCorreo).build();
            usuariosEncontrados = usuarioDao.consultarPorFiltro(filtroPorCorreo);
        }

        return usuariosEncontrados.isEmpty() ? new UsuarioEntidad.Builder().build() : usuariosEncontrados.get(0);
    }

    private EstadoUsuarioEntidad consultarEstadoPorNombre(String nombreEstado) {
        var filtro = new EstadoUsuarioEntidad.Builder().nombre(nombreEstado).build();
        var estadosEncontrados = daoFactory.obtenerEstadoUsuarioDAO().consultarPorFiltro(filtro);

        if (estadosEncontrados.isEmpty()) {
            var mensajeUsuario = CatalogoMensajes.UsuarioNegocioImpl.ESTADO_USUARIO_NO_CONFIGURADO;
            throw BodegappNegocioExcepcion.crear(mensajeUsuario);
        }
        return estadosEncontrados.get(0);
    }

    //toda accion del administrador hace DOS cosas: actualiza el usuario y agrega una fila al historial
    //las dos van en la misma transaccion (la abre y la confirma la Fachada): o se guardan las dos o ninguna
    private void guardarCambioConHistorial(UUID idAdministrador, UsuarioEntidad usuarioActual, RolEntidad rol,
                                           EstadoUsuarioEntidad estadoUsuario) {
        var usuarioActualizado = new UsuarioEntidad.Builder()
                .id(usuarioActual.getId())
                .nombre(usuarioActual.getNombre())
                .correo(usuarioActual.getCorreo())
                .nombreUsuario(usuarioActual.getNombreUsuario())
                .contrasena(usuarioActual.getContrasena())
                .rol(rol)
                .estadoUsuario(estadoUsuario)
                .fechaRegistro(usuarioActual.getFechaRegistro())
                .build();
        daoFactory.obtenerUsuarioDAO().actualizar(usuarioActual.getId(), usuarioActualizado);

        var historial = new HistorialUsuarioEntidad.Builder()
                .id(UtilId.generarId())
                .usuario(usuarioActualizado)
                .administrador(new UsuarioEntidad.Builder().id(idAdministrador).build())
                .rol(rol)
                .estadoUsuario(estadoUsuario)
                .fecha(UtilFecha.obtenerFechaHoraActual())
                .build();
        daoFactory.obtenerHistorialUsuarioDAO().crear(historial);
    }
}

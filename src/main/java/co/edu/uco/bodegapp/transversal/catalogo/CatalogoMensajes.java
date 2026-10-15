package co.edu.uco.bodegapp.transversal.catalogo;

//cada clase interna agrupa los mensajes de un tema
//un buen mensaje dice que fallo, por que y que hacer
public class CatalogoMensajes {

    private CatalogoMensajes() {
    }

    public static class UtilSql {

        private UtilSql() {
        }
        public static final String USUARIO_ERROR_PROBLEMA_VALIDANDO_SI_CONEXION_SQL_ESTA_ABIERTA = "Se ha presentado un problema tratando de validar si la conexion contra la fuente de informacion en la cual se iba a tratar de llevar a cabo la operacion deseada estaba o no abierta. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_VALIDANDO_SI_CONEXION_SQL_ESTA_ABIERTA = "Se ha presentado un problema NO CONTROLADO tratando de validar si la conexion contra la fuente de informacion en la cual se iba a tratar de llevar a cabo la operacion deseada estaba o no abierta. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_VALIDANDO_SI_TRANSACCION_SQL_ESTA_INICIADA = "Se ha presentado un problema tratando de validar si la conexion contra la fuente de informacion estaba en un estado consistente al tratar de llevar a cabo la operacion deseada. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_VALIDANDO_SI_TRANSACCION_SQL_ESTA_INICIADA = "Se ha presentado un problema NO CONTROLADO tratando de validar si la conexion contra la fuente de informacion estaba en un estado consistente al tratar de llevar a cabo la operacion deseada. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_INICIANDO_TRANSACCION_SQL = "Se ha presentado un problema tratando de iniciar la transaccion contra la fuente de informacion. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_INICIANDO_TRANSACCION_SQL = "Se ha presentado un problema NO CONTROLADO tratando de iniciar la transaccion contra la fuente de informacion. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_CONFIRMANDO_TRANSACCION_SQL = "Se ha presentado un problema tratando de confirmar la transaccion contra la fuente de informacion. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONFIRMANDO_TRANSACCION_SQL = "Se ha presentado un problema NO CONTROLADO tratando de confirmar la transaccion contra la fuente de informacion. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_CANCELANDO_TRANSACCION_SQL = "Se ha presentado un problema tratando de cancelar la transaccion contra la fuente de informacion. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CANCELANDO_TRANSACCION_SQL = "Se ha presentado un problema NO CONTROLADO tratando de cancelar la transaccion contra la fuente de informacion. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_CERRANDO_CONEXION_SQL = "Se ha presentado un problema tratando de cerrar la conexion contra la fuente de informacion. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CERRANDO_CONEXION_SQL = "Se ha presentado un problema NO CONTROLADO tratando de cerrar la conexion contra la fuente de informacion. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_CONEXION_SQL_NO_ESTA_ABIERTA = "No es posible continuar con la operacion deseada debido a que la conexion contra la fuente de informacion esta vacia o se encuentra cerrada. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_NO_ES_POSIBLE_INICIAR_TRANSACCION_SQL = "No es posible continuar con la operacion deseada debido a que la conexion contra la fuente de informacion se encuentra en un estado inconsistente porque esta cerrada, esta vacia o porque la transaccion ya fue iniciada. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_NO_ES_POSIBLE_CONFIRMAR_TRANSACCION_SQL = "No es posible confirmar los cambios de la operacion deseada debido a que la conexion contra la fuente de informacion esta vacia, esta cerrada o porque la transaccion no fue iniciada. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_NO_ES_POSIBLE_CANCELAR_TRANSACCION_SQL = "No es posible deshacer los cambios de la operacion deseada debido a que la conexion contra la fuente de informacion esta vacia, esta cerrada o porque la transaccion no fue iniciada. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_NO_ES_POSIBLE_CERRAR_CONEXION_SQL = "No es posible cerrar la conexion contra la fuente de informacion debido a que esta vacia o ya se encuentra cerrada. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
    }

    public static class SqlServerDAOFactory {

        private SqlServerDAOFactory() {
        }
        public static final String USUARIO_ERROR_PROBLEMA_ABRIENDO_CONEXION_SQL_SERVER ="Se ha presentado un problema tratando de abrir la conexion con la fuente de informacion. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion";
        public static final String USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_ABRIENDO_CONEXION_SQL_SERVER ="Se ha presentado un problema NO CONTROLADO tratando de abrir la conexion con la fuente de informacion. Por favor intente de  nuevo y si el problema persiste contacte al administrador de la  aplicacion ";
    }

    public static class DAOFactory {

        private DAOFactory() {
        }

        public static final String USUARIO_ERROR_FUENTE_INFORMACION_NO_HABILITADA = "La fuente de informacion que se solicito para llevar a cabo la operacion deseada no esta habilitada. Por favor contacte al administrador de la aplicacion y reporte la novedad";
    }

    public static class RolSqlServerDAO {

        private RolSqlServerDAO() {
        }

        public static final String USUARIO_ERROR_PROBLEMA_CONSULTANDO_ROL_POR_ID = "Se ha presentado un problema tratando de consultar la informacion del rol deseado. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_ROL_POR_ID = "Se ha presentado un problema NO CONTROLADO tratando de consultar la informacion del rol deseado. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_CONSULTANDO_ROL_POR_FILTRO = "Se ha presentado un problema tratando de consultar la informacion de los roles. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_ROL_POR_FILTRO = "Se ha presentado un problema NO CONTROLADO tratando de consultar la informacion de los roles. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_CONSULTANDO_TODOS_LOS_ROLES = "Se ha presentado un problema tratando de consultar todos los roles. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_TODOS_LOS_ROLES = "Se ha presentado un problema NO CONTROLADO tratando de consultar todos los roles. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
    }

    public static class EstadoUsuarioSqlServerDAO {

        private EstadoUsuarioSqlServerDAO() {
        }

        public static final String USUARIO_ERROR_PROBLEMA_CONSULTANDO_ESTADO_USUARIO_POR_ID = "Se ha presentado un problema tratando de consultar la informacion del estado de usuario deseado. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_ESTADO_USUARIO_POR_ID = "Se ha presentado un problema NO CONTROLADO tratando de consultar la informacion del estado de usuario deseado. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_CONSULTANDO_ESTADO_USUARIO_POR_FILTRO = "Se ha presentado un problema tratando de consultar la informacion de los estados de usuario. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_ESTADO_USUARIO_POR_FILTRO = "Se ha presentado un problema NO CONTROLADO tratando de consultar la informacion de los estados de usuario. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_CONSULTANDO_TODOS_LOS_ESTADOS_USUARIO = "Se ha presentado un problema tratando de consultar todos los estados de usuario. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_TODOS_LOS_ESTADOS_USUARIO = "Se ha presentado un problema NO CONTROLADO tratando de consultar todos los estados de usuario. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
    }

    public static class UsuarioSqlServerDAO {

        private UsuarioSqlServerDAO() {
        }

        public static final String USUARIO_ERROR_PROBLEMA_CREANDO_USUARIO = "Se ha presentado un problema tratando de registrar el usuario. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CREANDO_USUARIO = "Se ha presentado un problema NO CONTROLADO tratando de registrar el usuario. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_CONSULTANDO_USUARIO_POR_ID = "Se ha presentado un problema tratando de consultar la informacion del usuario deseado. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_USUARIO_POR_ID = "Se ha presentado un problema NO CONTROLADO tratando de consultar la informacion del usuario deseado. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_CONSULTANDO_USUARIO_POR_FILTRO = "Se ha presentado un problema tratando de consultar la informacion de los usuarios. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_USUARIO_POR_FILTRO = "Se ha presentado un problema NO CONTROLADO tratando de consultar la informacion de los usuarios. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_CONSULTANDO_TODOS_LOS_USUARIOS = "Se ha presentado un problema tratando de consultar todos los usuarios. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_TODOS_LOS_USUARIOS = "Se ha presentado un problema NO CONTROLADO tratando de consultar todos los usuarios. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_ACTUALIZANDO_USUARIO = "Se ha presentado un problema tratando de actualizar la informacion del usuario. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_ACTUALIZANDO_USUARIO = "Se ha presentado un problema NO CONTROLADO tratando de actualizar la informacion del usuario. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
    }

    public static class HistorialUsuarioSqlServerDAO {

        private HistorialUsuarioSqlServerDAO() {
        }

        public static final String USUARIO_ERROR_PROBLEMA_CREANDO_HISTORIAL_USUARIO = "Se ha presentado un problema tratando de guardar el cambio en el historial del usuario. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CREANDO_HISTORIAL_USUARIO = "Se ha presentado un problema NO CONTROLADO tratando de guardar el cambio en el historial del usuario. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_CONSULTANDO_HISTORIAL_USUARIO_POR_ID = "Se ha presentado un problema tratando de consultar la informacion del registro de historial deseado. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_HISTORIAL_USUARIO_POR_ID = "Se ha presentado un problema NO CONTROLADO tratando de consultar la informacion del registro de historial deseado. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_CONSULTANDO_HISTORIAL_USUARIO_POR_FILTRO = "Se ha presentado un problema tratando de consultar el historial de los usuarios. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_HISTORIAL_USUARIO_POR_FILTRO = "Se ha presentado un problema NO CONTROLADO tratando de consultar el historial de los usuarios. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_CONSULTANDO_TODOS_LOS_HISTORIALES_USUARIO = "Se ha presentado un problema tratando de consultar todo el historial de los usuarios. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
        public static final String USUARIO_ERROR_PROBLEMA_NO_CONTROLADO_CONSULTANDO_TODOS_LOS_HISTORIALES_USUARIO = "Se ha presentado un problema NO CONTROLADO tratando de consultar todo el historial de los usuarios. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
    }

    public static class UtilContrasena {

        private UtilContrasena() {
        }

        public static final String USUARIO_ERROR_PROBLEMA_ENCRIPTANDO_CONTRASENA = "Se ha presentado un problema tratando de proteger la contrasena. Por favor intente de nuevo y si el problema persiste contacte al administrador de la aplicacion y reporte la novedad";
    }

    //mensajes de las reglas de negocio del usuario: registro, inicio de sesion y acciones del administrador
    public static class UsuarioNegocioImpl {

        private UsuarioNegocioImpl() {
        }

        public static final String DATOS_USUARIO_OBLIGATORIOS = "No es posible registrar el usuario porque no se recibio su informacion. Por favor diligencie los datos e intente de nuevo";
        public static final String NOMBRE_OBLIGATORIO = "El nombre es obligatorio. Por favor escriba el nombre e intente de nuevo";
        public static final String NOMBRE_LONGITUD_NO_VALIDA = "El nombre debe tener entre 2 y 100 caracteres. Por favor escriba un nombre valido e intente de nuevo";
        public static final String NOMBRE_FORMATO_NO_VALIDO = "El nombre solo puede tener letras y espacios. Por favor escriba un nombre valido e intente de nuevo";
        public static final String CORREO_OBLIGATORIO = "El correo es obligatorio. Por favor escriba el correo e intente de nuevo";
        public static final String CORREO_LONGITUD_NO_VALIDA = "El correo no puede tener mas de 100 caracteres. Por favor escriba un correo valido e intente de nuevo";
        public static final String CORREO_FORMATO_NO_VALIDO = "El correo no tiene un formato valido. Debe verse como nombre@dominio.com. Por favor corrijalo e intente de nuevo";
        public static final String NOMBRE_USUARIO_OBLIGATORIO = "El nombre de usuario es obligatorio. Por favor escriba un nombre de usuario e intente de nuevo";
        public static final String NOMBRE_USUARIO_LONGITUD_NO_VALIDA = "El nombre de usuario debe tener entre 4 y 30 caracteres. Por favor escriba uno valido e intente de nuevo";
        public static final String NOMBRE_USUARIO_FORMATO_NO_VALIDO = "El nombre de usuario solo puede tener letras, numeros, punto o guion bajo, y no puede tener espacios. Por favor escriba uno valido e intente de nuevo";
        public static final String CONTRASENA_OBLIGATORIA = "La contrasena es obligatoria. Por favor escriba una contrasena e intente de nuevo";
        public static final String CONTRASENA_LONGITUD_NO_VALIDA = "La contrasena debe tener minimo 8 caracteres. Por favor escriba una mas larga e intente de nuevo";
        public static final String CONTRASENA_FORMATO_NO_VALIDO = "La contrasena debe tener al menos una letra y un numero. Por favor escriba una contrasena valida e intente de nuevo";

        public static final String CORREO_YA_REGISTRADO = "Ese correo ya esta registrado. Por favor use otro correo o inicie sesion con su cuenta";
        public static final String NOMBRE_USUARIO_YA_REGISTRADO = "Ese nombre de usuario ya existe. Por favor elija otro e intente de nuevo";

        public static final String CREDENCIALES_OBLIGATORIAS = "Para iniciar sesion debe escribir su usuario o correo y su contrasena";
        public static final String CREDENCIALES_INCORRECTAS = "Usuario o contrasena incorrectos";
        public static final String CUENTA_PENDIENTE_DE_APROBACION = "Tu cuenta espera aprobacion del administrador";
        public static final String CUENTA_DESACTIVADA = "Tu cuenta esta desactivada";

        public static final String SIN_PERMISO_PARA_LA_ACCION = "No tienes permiso para esta accion";
        public static final String USUARIO_NO_EXISTE = "No es posible realizar la accion porque el usuario indicado no existe. Por favor verifique el usuario e intente de nuevo";
        public static final String USUARIO_NO_ESTA_PENDIENTE = "Solo se puede aprobar una cuenta que este pendiente, y esta cuenta ya fue aprobada antes";
        public static final String USUARIO_NO_ESTA_ACTIVO = "No es posible realizar la accion porque la cuenta del usuario no esta activa. Si esta pendiente, primero hay que aprobarla; si esta desactivada, primero hay que reactivarla";
        public static final String USUARIO_NO_ESTA_INACTIVO = "Solo se puede reactivar una cuenta que este desactivada";
        public static final String ROL_OBLIGATORIO = "Debe elegir un rol para el usuario. El rol no puede ser 'Sin asignar'";
        public static final String ROL_NO_EXISTE = "El rol elegido no existe. Por favor elija un rol de la lista e intente de nuevo";
        public static final String ROL_IGUAL_AL_ACTUAL = "El usuario ya tiene ese rol. Por favor elija un rol diferente";
        public static final String ADMINISTRADOR_NO_PUEDE_DESACTIVARSE_A_SI_MISMO = "Un administrador no puede desactivar su propia cuenta";
        public static final String ULTIMO_ADMINISTRADOR_ACTIVO = "No es posible realizar la accion porque es el unico administrador activo y el sistema quedaria sin nadie que apruebe cuentas";
        public static final String ESTADO_USUARIO_NO_CONFIGURADO = "No es posible realizar la accion porque falta un estado de usuario en la fuente de informacion. Por favor contacte al administrador de la aplicacion y reporte la novedad";
    }
}

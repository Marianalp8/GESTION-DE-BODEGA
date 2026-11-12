package co.edu.uco.bodegapp.controlador;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//la pantalla pide aqui el token csrf y lo manda en cada POST/PUT/DELETE,
@RestController
public class CsrfControlador {
    //GET http://localhost:987/api/v1/csrf
    @GetMapping("/api/v1/csrf")
    public CsrfToken obtenerTokenCsrf(CsrfToken token) {
        return token;
    }
}

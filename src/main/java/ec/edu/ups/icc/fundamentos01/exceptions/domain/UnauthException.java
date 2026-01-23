
package ec.edu.ups.icc.fundamentos01.exceptions.domain;

import org.springframework.http.HttpStatus;

import ec.edu.ups.icc.fundamentos01.exceptions.base.ApplicationException;

public class UnauthException extends ApplicationException {

    public UnauthException(String messag) {

        super(HttpStatus.UNAUTHORIZED, "No autorizado " +
                "Token de autenticación inválido o no proporcionado. " +
                "Debe incluir un token válido en el header Authorization: Bearer <token>");
    }
}
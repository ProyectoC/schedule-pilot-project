package com.schedulepilot.core.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    // Manage User
    ERROR_MANAGE_USER_CREATE_USER("1001", "User account could not be created."),
    ERROR_MANAGE_USER_AUTH_FAILED("1005", "Username and/or password are not valid."),
    ERROR_MANAGE_USER_NOT_FOUND("1006", "User account not found."),
    ERROR_MANAGE_USER_FORGOT_PASSWORD("1007", "Password from user account could not be restored."),
    ERROR_MANAGE_USER_CHANGE_PASSWORD("1008", "Password from user account could not be changed."),
    ERROR_MANAGE_USER_CHANGE_PASSWORD_MATCH("1009", "Actual password from user account is not valid."),

    // Manage Product
    ERROR_MANAGE_PRODUCT_DISABLE_FAILED("1010", "Product can not be disable."),

    // Manage Rol
    ERROR_MANAGE_ROL_NOT_FOUND("1002", "Rol account not found."),

    // Manage Parameter
    ERROR_MANAGE_PARAMETER_NOT_FOUND("1003", "Parameter not found."),

    // Manage Token
    ERROR_MANAGE_TOKEN_TYPE_NOT_FOUND("1004", "Token type not found."),

    ERROR_CLIENT("99", "Error de cliente."),
    ERROR_UNKNOWN("98", "Error check with your system administrator."),
    ERROR_RECORD_NOT_FOUND("97", "Error, el registro no se ha encontrado."),
    ERROR_INVALID_DATA("96", "Error, datos no validos."),
    ERROR_SEND_EMAIL("95", "Error, no fue posible enviar el email."),
    ERROR_REGISTER_LOG("94", "Error, no fue posible registrar el log de seguridad."),
    ERROR_AUTHENTICATION("93", "Error en la autenticaci\u00f3n."),
    ERROR_TOKEN_COMPANY("92", "Error, Token de seguridad invalido."),
    ERROR_PARAMETER_SECURITY_NOT_FOUND("91", "Error, no se ha encontrado el par\u00e1metro con nombre: "),
    ERROR_CONVERT_DATE("90", "Error, no se ha podido convertir la fecha."),
    ERROR_DATA_ACCESS("89", "Error, ocurrio un problema al comunicarse con la capa de datos."),
    ERROR_DATA_ACCESS_ROLl_BACK("88", "Error, ocurrio un problema al comunicarse con la capa de datos."),
    ERROR_DATA_ACCESS_INTEGRATION_VIOLATION("87", "Error, ocurrio un problema al comunicarse con la capa de datos, violacion de la integridad de datos.");

    private final String code;
    private final String description;

    ExceptionCode(String code, String description) {
        this.code = code;
        this.description = description;
    }
}

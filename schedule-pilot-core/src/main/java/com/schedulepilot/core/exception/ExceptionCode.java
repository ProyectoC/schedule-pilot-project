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
    ERROR_MANAGE_PRODUCT_CREATE_FAILED("1011", "Product can not be created."),
    ERROR_MANAGE_PRODUCT_UPDATE_FAILED("1012", "Product can not be updated."),

    // Loan Process
    ERROR_LOAN_PROCESS_DATE_NOT_VALID("1013", "Loan date is not valid"),
    ERROR_LOAN_PROCESS_PRODUCTS_ARE_EMPTY("1013", "Loan, products can not be empty"),
    ERROR_LOAN_PROCESS_USER_ACCOUNT_GENERATE_TICKET_CHECK_OUT_NOT_VALID("1014", "Loan, rol from user account are not valid to generate check out"),
    ERROR_LOAN_PROCESS_TICKET_CHECK_IN_HAS_EXPIRED("1015", "Ticket check in has expired."),
    ERROR_LOAN_PROCESS_TICKET_CHECK_IN_ALREADY_USED("1016", "Ticket check in already used."),
    ERROR_LOAN_PROCESS_USER_ACCOUNT_GENERATE_TICKET_CHECK_LOG_NOT_VALID("1017", "Loan, rol from user account are not valid to generate check log"),
    ERROR_LOAN_PROCESS_TICKET_CHECK_OUT_ALREADY_USED("1017", "Ticket check out already used."),
    ERROR_LOAN_PROCESS_TICKET_CHECK_OUT_HAS_EXPIRED("1018", "Ticket check out has expired."),

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

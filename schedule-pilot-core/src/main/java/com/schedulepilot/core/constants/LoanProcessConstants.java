package com.schedulepilot.core.constants;

public class LoanProcessConstants {

    // GLOBAL REST
    public static final String REST_PATH_DEFAULT_V1 = ResourceMappingConstants.REST_API_V1 + "/public/ws/loan";

    // REST
    public static final String CREATE_REQUEST_CHECK_IN_REST = "/create/request-check-in";
    public static final String GET_REQUEST_CHECK_IN_REST = "/users/{userAccountId}/request-check-in";
    public static final String GET_REQUEST_TICKET_CHECK_IN_REST = "/users/{userAccountId}/ticket-check-in";

    public static final String CREATE_REQUEST_CHECK_OUT_REST = "/create/request-check-out";
    public static final String CREATE_REQUEST_CHECK_LOG_REST = "/create/request-check-log";

    // STATUS
    public static final String EXPIRED_STATUS = "VENCIDO";
    public static final String USED_STATUS = "REDIMIDO";
    public static final String GENERATED_STATUS = "GENERADO";
    public static final String NOT_FOUND_STATUS = "NO ENCONTRADO";
    public static final String FOUND_STATUS = "ENCONTRADO";

    private LoanProcessConstants() {
    }
}

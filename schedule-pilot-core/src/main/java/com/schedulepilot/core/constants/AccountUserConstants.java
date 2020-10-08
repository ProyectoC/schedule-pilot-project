package com.schedulepilot.core.constants;

public class AccountUserConstants {

    // GLOBAL REST
    public static final String REST_PATH_DEFAULT_V1 = ResourceMappingConstants.REST_API_V1 + "/public/security/users";

    // REST
    public static final String CREATE_USER_ACCOUNT_REST = "/create";
    public static final String ACTIVATE_USER_ACCOUNT_REST = "/activate-account";
    public static final String AUTH_AUTHORIZE_USER_ACCOUNT_REST = "/auth";
    public static final String FORGOT_PASSWORD_USER_ACCOUNT_REST = "/forgot-password";
    public static final String CHANGE_PASSWORD_USER_ACCOUNT_REST = "/change-password";

    private AccountUserConstants() {
    }
}

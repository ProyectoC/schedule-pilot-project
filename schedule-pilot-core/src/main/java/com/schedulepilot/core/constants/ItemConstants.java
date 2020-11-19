package com.schedulepilot.core.constants;

public class ItemConstants {

    // GLOBAL REST
    public static final String REST_PATH_DEFAULT_V1 = ResourceMappingConstants.REST_API_V1 + "/public/ws/items";

    // REST
    public static final String CREATE_ITEM_REST = "/create";
    public static final String UPDATE_ITEM_REST = "/update";
    public static final String DELETE_ITEM_REST = "/delete";
    public static final String STATUS_ITEM_REST = "/status";

    public static final String ENABLE_STATUS = "DISPONIBLE";
    public static final String ON_LOAD_STATUS = "EN PRESTAMO";

    private ItemConstants() {
    }
}

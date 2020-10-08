package com.schedulepilot.core.util.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDto<T> {

    private static final int SUCCESS_CODE = 0;
    private static final int NO_SESSION_ERROR_CODE = -1;
    public static final int ERROR_CODE = -2;

    private int code;
    private String description;
    private Long rows;
    private T result;

    public ResponseDto(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public ResponseDto(int code, String description, T result) {
        this.code = code;
        this.description = description;
        this.result = result;
    }

    public ResponseDto() {
        super();
    }

    public static <T> ResponseDto<T> success() {
        return new ResponseDto<>(SUCCESS_CODE, "Success");
    }

    public static <T> ResponseDto<T> success(String description) {
        return new ResponseDto<>(SUCCESS_CODE, description);
    }

    public static <T> ResponseDto<T> success(T object) {
        return new ResponseDto<>(SUCCESS_CODE, "Success", object);
    }

    public static <T> ResponseDto<T> noSessionError(String description) {
        return new ResponseDto<>(NO_SESSION_ERROR_CODE, description);
    }

    public static <T> ResponseDto<T> error(String description) {
        return new ResponseDto<>(ERROR_CODE, description);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}

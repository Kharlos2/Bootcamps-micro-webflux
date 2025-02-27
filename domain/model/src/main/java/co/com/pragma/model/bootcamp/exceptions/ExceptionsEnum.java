package co.com.pragma.model.bootcamp.exceptions;

public enum ExceptionsEnum {
    MIN_CAPACITY(400, "Min 1 capacity"),
    MAX_CAPABILITIES(400, "Max 4 capabilities"),
    DUPLICATE_CAPACITY(400, "Not valid duplicate capacity"),
    DUPLICATE_BOOTCAMP(409, "Not valid duplicate bootcamp")
    ;

    private final int httpStatus;
    private final String message;

    ExceptionsEnum(int httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}

package dev.volcent.App.enums;

public enum Response {
    NOT_FOUND("not found"),
    BAD_REQUEST("bad request"),
    DONE("done");

    public final String message;

    private Response(String message) {
        this.message = message;
    }
}

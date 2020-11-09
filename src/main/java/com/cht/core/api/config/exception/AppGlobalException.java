package com.cht.core.api.config.exception;

import com.cht.Response;

public class AppGlobalException extends RuntimeException {

    private static final long serialVersionUID = -4901434558868605358L;

    private Response res;

    public AppGlobalException(String msg) {
        this.res = Response.createError(msg);
    }

    public AppGlobalException(String msg, Throwable cause) {
        this.res = Response.createError(msg, 500, cause);
    }

    public Response getRes() {
        return this.res;
    }
}
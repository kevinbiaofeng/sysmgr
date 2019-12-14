package com.snake.mcf.common.web.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Result {

    private boolean success;

    private final List<Error> errors;

    private Object data;

    public Result(boolean success, List<Error> errors) {
        this.success = success;
        if (errors == null) {
            errors = new ArrayList<Error>(2);
        }
        this.errors = errors;
        this.data = null;
    }

    public Result(boolean success, String error) {
        this(success, (List<Error>) null);
        if (error != null) {
            addError(error);
        }
    }

    public Result(boolean success, Object data) {
        this.success = success;
        this.errors = Collections.emptyList();
        this.data = data;
    }

    public static final Result fail(String error) {
        return new Result(false, error);
    }

    public static final Result succ() {
        return succ(null);
    }

    public static final Result succ(String message) {
        return new Result(true, message);
    }

    public static final Result succ(Object data) {
        return new Result(true, data);
    }

    public boolean isSuccess() {
        return success;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data){
        this.data = data;
    }

    public void addError(String error) {
        this.errors.add(new Error(error));
    }

    public void addHint(String message) {
        this.errors.add(new Error(message, 2));
    }

    public String getLastError() {
        if (errors == null || errors.isEmpty()) {
            return null;
        }

        String message = null;
        for (int i = errors.size() - 1; i >= 0; i--) {
            Error error = errors.get(i);
            if (error.getType() == 1) {
                message = error.getError();
                break;
            }
        }
        if (message == null) {
            message = errors.get(errors.size() - 1).getError();
        }
        return message;
    }

    public static final class Error {
        private final String error;
        private final int type; //1 error 2 hint

        public Error(String error) {
            this(error, 1);
        }

        public Error(String error, int type) {
            this.error = error;
            this.type = type;
        }

        public String getError() {
            return error;
        }

        public int getType() {
            return type;
        }
    }

}

package com.snake.mcf.common.web.exception;

import com.snake.mcf.common.exception.FrameworkException;

public class UnauthorizedAccessException extends FrameworkException {

	private static final long serialVersionUID = 1L;

	public UnauthorizedAccessException(String message) {
        super(message);
    }

}

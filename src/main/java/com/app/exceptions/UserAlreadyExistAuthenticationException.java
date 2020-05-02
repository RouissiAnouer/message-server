package com.app.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserAlreadyExistAuthenticationException extends AuthenticationException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2079844583721425302L;

	public UserAlreadyExistAuthenticationException(final String msg) {
        super(msg);
    }

}

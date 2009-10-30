/*
 * Copyright (c) 2009. The Frontside Software, Inc.
 *
 * The contents of this file are subject to the Gnu General Public License version 2
 * or later (the "License"); You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

package xbl.error;

public class SignInException extends RuntimeException {
	public SignInException(String message) {
		super(message);
	}

	public SignInException(String message, Throwable cause) {
		super(message, cause);
	}

	public SignInException(Throwable cause) {
		super(cause);
	}
}

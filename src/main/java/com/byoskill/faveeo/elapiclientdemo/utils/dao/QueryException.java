/*
 * Copyright (C) 2017 Sylvain Leroy - BYOSkill Company All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the MIT license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the MIT license with
 * this file. If not, please write to: sleroy at byoskill.com, or visit : www.byoskill.com
 * 
 */
package com.byoskill.faveeo.elapiclientdemo.utils.dao;

import java.io.IOException;

public class QueryException extends RuntimeException {

    public QueryException(final IOException e) {
	super("Query returns an error", e);
    }

}

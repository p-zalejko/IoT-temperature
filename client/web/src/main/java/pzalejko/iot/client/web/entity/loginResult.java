/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/
package pzalejko.iot.client.web.entity;

public class loginResult {

	private String content;

	public loginResult(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

}
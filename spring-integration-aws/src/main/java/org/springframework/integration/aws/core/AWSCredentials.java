/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.integration.aws.core;

/**
 * The common interfaces for all implementations of Amazon WS Credentials
 *
 * @author Amol Nayak
 *
 * @since 0.5
 *
 */
public interface AWSCredentials {

	/**
	 * Get the Access key to the Amazon WS account
	 */
	String getAccessKey();

	/**
	 * Get the Secret key to the Amazon WS account
	 */
	String getSecretKey();

}

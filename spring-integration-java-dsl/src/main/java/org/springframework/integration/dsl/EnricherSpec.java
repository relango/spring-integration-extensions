/*
 * Copyright 2014 the original author or authors.
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

package org.springframework.integration.dsl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.dsl.core.MessageHandlerSpec;
import org.springframework.integration.expression.ValueExpression;
import org.springframework.integration.transformer.ContentEnricher;
import org.springframework.integration.transformer.support.AbstractHeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.ExpressionEvaluatingHeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.HeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.StaticHeaderValueMessageProcessor;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.Assert;

/**
 * @author Artem Bilan
 */
public class EnricherSpec extends MessageHandlerSpec<EnricherSpec, ContentEnricher> {

	private final static SpelExpressionParser PARSER = new SpelExpressionParser();

	private final ContentEnricher enricher = new ContentEnricher();

	private final Map<String, Expression> propertyExpressions = new HashMap<String, Expression>();

	private final Map<String, HeaderValueMessageProcessor<?>> headerExpressions = new HashMap<String, HeaderValueMessageProcessor<?>>();

	EnricherSpec() {
	}

	public EnricherSpec requestChannel(MessageChannel requestChannel) {
		this.enricher.setRequestChannel(requestChannel);
		return _this();
	}

	public EnricherSpec requestChannel(String requestChannel) {
		this.enricher.setRequestChannelName(requestChannel);
		return _this();
	}

	public EnricherSpec replyChannel(MessageChannel replyChannel) {
		this.enricher.setReplyChannel(replyChannel);
		return _this();
	}

	public EnricherSpec replyChannel(String replyChannel) {
		this.enricher.setReplyChannelName(replyChannel);
		return _this();
	}

	public EnricherSpec requestTimeout(Long requestTimeout) {
		this.enricher.setRequestTimeout(requestTimeout);
		return _this();
	}

	public EnricherSpec replyTimeout(Long replyTimeout) {
		this.enricher.setReplyTimeout(replyTimeout);
		return _this();
	}

	public EnricherSpec requestPayloadExpression(String requestPayloadExpression) {
		this.enricher.setRequestPayloadExpression(PARSER.parseExpression(requestPayloadExpression));
		return _this();
	}

	public EnricherSpec shouldClonePayload(boolean shouldClonePayload) {
		this.enricher.setShouldClonePayload(shouldClonePayload);
		return _this();
	}

	public <V> EnricherSpec property(String key, V value) {
		this.propertyExpressions.put(key, new ValueExpression<V>(value));
		return _this();
	}

	public EnricherSpec propertyExpression(String key, String expression) {
		Assert.notNull(key);
		this.propertyExpressions.put(key, PARSER.parseExpression(expression));
		return _this();
	}

	public <V> EnricherSpec header(String name, V value) {
		return this.header(name, value, null);
	}

	public <V> EnricherSpec header(String name, V value, Boolean overwrite) {
		AbstractHeaderValueMessageProcessor<V> headerValueMessageProcessor =
				new StaticHeaderValueMessageProcessor<V>(value);
		headerValueMessageProcessor.setOverwrite(overwrite);
		return this.header(name, headerValueMessageProcessor);
	}

	public EnricherSpec headerExpression(String name, String expression) {
		return this.headerExpression(name, expression, null);
	}

	public EnricherSpec headerExpression(String name, String expression, Boolean overwrite) {
		AbstractHeaderValueMessageProcessor<?> headerValueMessageProcessor =
				new ExpressionEvaluatingHeaderValueMessageProcessor<Object>(expression, null);
		headerValueMessageProcessor.setOverwrite(overwrite);
		return this.header(name, headerValueMessageProcessor);
	}

	public <V> EnricherSpec header(String name, HeaderValueMessageProcessor<V> headerValueMessageProcessor) {
		Assert.notNull(name);
		this.headerExpressions.put(name, headerValueMessageProcessor);
		return _this();
	}

	@Override
	protected ContentEnricher doGet() {
		this.enricher.setPropertyExpressions(this.propertyExpressions);
		this.enricher.setHeaderExpressions(this.headerExpressions);
		return this.enricher;
	}

}

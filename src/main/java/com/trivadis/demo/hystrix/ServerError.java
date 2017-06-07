package com.trivadis.demo.hystrix;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Typing;

/**
 * Output-DTO
 */
@JsonSerialize(typing = Typing.STATIC)
public class ServerError {

	@JsonProperty(required = true)
	public Integer httpStatusCode;

	@JsonProperty(required = true)
	public String code;

	@JsonProperty(required = true)
	public String logMessage;

}

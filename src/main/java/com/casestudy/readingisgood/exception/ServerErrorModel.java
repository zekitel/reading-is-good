package com.casestudy.readingisgood.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.ConstraintViolation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ServerErrorModel {

	private HttpStatus status;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime timestamp;
	private String message;
	private List<FieldError> fieldErrorList;
	private Set<ConstraintViolation<?>> constraintViolationSet;

	private ServerErrorModel() {
		timestamp = LocalDateTime.now();
	}

	public ServerErrorModel(HttpStatus status) {
		this();
		this.status = status;
	}

	public ServerErrorModel(HttpStatus status, String message) {
		this();
		this.status = status;
		this.message = message;
	}

}
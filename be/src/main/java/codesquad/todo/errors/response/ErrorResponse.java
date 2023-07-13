package codesquad.todo.errors.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import codesquad.todo.errors.errorcode.ErrorCode;

public class ErrorResponse {
	private final String name;
	private final HttpStatus httpStatus;
	private final String errorMessage;

	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private final List<ValidationError> errors;

	public ErrorResponse(ErrorCode errorCode, List<ValidationError> errors) {
		this.name = errorCode.getName();
		this.httpStatus = errorCode.getHttpStatus();
		this.errorMessage = errorCode.getMessage();
		this.errors = errors;
	}

	public String getName() {
		return name;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public List<ValidationError> getErrors() {
		return errors;
	}
}

package codesquad.todo.errors.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import codesquad.todo.errors.errorcode.ErrorCode;

public class ErrorResponse {
	private final boolean success;
	private final ErrorDto errorCode;

	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private final List<ValidationError> errors;

	public ErrorResponse(ErrorCode errorCode, List<ValidationError> errors) {
		this.success = false;
		this.errorCode = new ErrorDto(errorCode.getHttpStatus().value(), errorCode.getHttpStatus().getReasonPhrase(),
			errorCode.getMessage());
		this.errors = errors;
	}

	public boolean isSuccess() {
		return success;
	}

	public ErrorDto getErrorCode() {
		return errorCode;
	}

	public List<ValidationError> getErrors() {
		return errors;
	}
}

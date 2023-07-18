package codesquad.todo.errors.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import codesquad.todo.errors.errorcode.ErrorCode;

public class ErrorResponse {
	private final boolean success;
	@JsonProperty("errorCode")
	private final ErrorDto errorDto;

	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private final List<ValidationError> errors;

	public ErrorResponse(ErrorCode errorDto, List<ValidationError> errors) {
		this.success = false;
		this.errorDto = new ErrorDto(errorDto.getHttpStatus().value(), errorDto.getHttpStatus().getReasonPhrase(),
			errorDto.getMessage());
		this.errors = errors;
	}

	public boolean isSuccess() {
		return success;
	}

	public ErrorDto getErrorDto() {
		return errorDto;
	}

	public List<ValidationError> getErrors() {
		return errors;
	}
}

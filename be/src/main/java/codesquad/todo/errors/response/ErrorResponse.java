package codesquad.todo.errors.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquad.todo.errors.errorcode.ErrorCode;

public class ErrorResponse {
	private final boolean success;
	@JsonProperty("errorCode")
	private final ErrorDto errorDto;

	public ErrorResponse(ErrorCode errorCode) {
		this.success = false;
		this.errorDto = new ErrorDto(errorCode.getHttpStatus().value(), errorCode.getHttpStatus().getReasonPhrase(),
			errorCode.getMessage());
	}

	public ErrorResponse(ErrorCode errorCode, String message) {
		this.success = false;
		this.errorDto = new ErrorDto(errorCode.getHttpStatus().value(), errorCode.getHttpStatus().getReasonPhrase(),
			message);
	}

	public boolean isSuccess() {
		return success;
	}

	public ErrorDto getErrorDto() {
		return errorDto;
	}
}

package codesquad.todo.errors.errorcode;

import org.springframework.http.HttpStatus;

public enum CardErrorCode implements ErrorCode {
	NOT_FOUND_Card(HttpStatus.NOT_FOUND, "존재하지 않는 카드입니다.");

	private final HttpStatus httpStatus;
	private final String message;

	CardErrorCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	@Override
	public String getName() {
		return name();
	}

	@Override
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	@Override
	public String getMessage() {
		return message;
	}
}

package codesquad.todo.errors.errorcode;

import org.springframework.http.HttpStatus;

public enum HistoryErrorCode implements ErrorCode {
	NOT_FOUND_HISTORY(HttpStatus.NOT_FOUND, "존재하지 않는 활동 기록입니다.");

	private final HttpStatus httpStatus;
	private final String message;

	HistoryErrorCode(HttpStatus httpStatus, String message) {
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

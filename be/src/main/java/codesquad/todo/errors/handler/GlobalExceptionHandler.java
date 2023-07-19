package codesquad.todo.errors.handler;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import codesquad.todo.errors.errorcode.CommonErrorCode;
import codesquad.todo.errors.errorcode.ErrorCode;
import codesquad.todo.errors.exception.RestApiException;
import codesquad.todo.errors.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(RestApiException.class)
	public ResponseEntity<Object> handleRestApiException(RestApiException ex) {
		log.info("RestApiException handling : {}", ex.toString());
		return handleExceptionInternal(ex.getErrorCode());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.warn("handleMethodArgumentNotValid", ex);
		CommonErrorCode errorCode = CommonErrorCode.INVALID_INPUT_FORMAT;
		return handleExceptionInternal(ex, errorCode);
	}

	private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(makeErrorResponse(errorCode));
	}

	private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
		return new ErrorResponse(errorCode);
	}

	private ResponseEntity<Object> handleExceptionInternal(BindException ex, ErrorCode errorCode) {
		return ResponseEntity.status(errorCode.getHttpStatus()).body(makeErrorResponse(ex, errorCode));
	}

	private ErrorResponse makeErrorResponse(BindException ex, ErrorCode errorCode) {
		String message = ex.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(fieldError -> Optional.ofNullable(fieldError.getDefaultMessage()).orElse(errorCode.getMessage()))
			.findAny()
			.orElseThrow();
		return new ErrorResponse(errorCode, message);
	}
}

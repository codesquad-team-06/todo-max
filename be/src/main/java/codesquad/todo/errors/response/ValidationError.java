package codesquad.todo.errors.response;

import java.util.Objects;

import org.springframework.validation.FieldError;

public class ValidationError {
	private final String field;
	private final String message;

	public ValidationError(String field, String message) {
		this.field = field;
		this.message = message;
	}

	public ValidationError() {
		this(null, null);
	}

	public static ValidationError of(FieldError fieldError) {
		return new ValidationError(fieldError.getField(), fieldError.getDefaultMessage());
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getField(), getMessage());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ValidationError)) {
			return false;
		}
		ValidationError that = (ValidationError)obj;
		return Objects.equals(getField(), that.getField()) && Objects.equals(getMessage(), that.getMessage());
	}

	@Override
	public String toString() {
		return String.format("ValidationError(%s, %s)", field, message);
	}
}

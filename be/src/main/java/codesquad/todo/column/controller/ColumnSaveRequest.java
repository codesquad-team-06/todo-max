package codesquad.todo.column.controller;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import codesquad.todo.column.entity.Column;

public class ColumnSaveRequest {
	@NotEmpty(message = "컬럼의 제목은 공백이면 안됩니다.")
	@Size(max = 100, message = "컬럼의 제목은 최대 100글자 이내여야 합니다.")
	private String name;

	public ColumnSaveRequest() {
	}

	public ColumnSaveRequest(String name) {
		this.name = name;
	}

	public Column toEntity() {
		return Column.builder()
			.name(name)
			.build();
	}

	public String getName() {
		return name;
	}
}

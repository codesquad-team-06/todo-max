package codesquad.todo.column.controller;

import codesquad.todo.column.entity.Column;

public class ColumnSaveRequest {
	private String name;

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

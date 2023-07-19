package codesquad.todo.column.controller;

import codesquad.todo.column.entity.Column;

public class ColumnSaveDto {
	private Long id;
	private String name;

	public ColumnSaveDto(Column column) {
		this(column.getId(), column.getName());
	}

	public ColumnSaveDto(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}

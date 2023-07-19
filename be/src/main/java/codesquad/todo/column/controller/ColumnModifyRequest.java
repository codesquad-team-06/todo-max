package codesquad.todo.column.controller;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import codesquad.todo.column.entity.Column;

public class ColumnModifyRequest {
	private Long id;

	@NotEmpty(message = "컬럼의 제목은 공백이면 안됩니다.")
	@Length(max = 100, message = "컬럼의 제목은 최대 100글자 이내여야 합니다.")
	private String name;

	public ColumnModifyRequest() {
	}

	public ColumnModifyRequest(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Column toEntity() {
		return Column.builder()
			.id(id)
			.name(name)
			.build();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}

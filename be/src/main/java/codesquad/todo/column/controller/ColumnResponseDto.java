package codesquad.todo.column.controller;

public class ColumnResponseDto {
	private ColumnSaveDto column;
	private boolean success;

	public ColumnResponseDto() {
	}

	public ColumnResponseDto(ColumnSaveDto column, boolean success) {
		this.column = column;
		this.success = success;
	}

	public ColumnSaveDto getColumn() {
		return column;
	}

	public boolean isSuccess() {
		return success;
	}
}

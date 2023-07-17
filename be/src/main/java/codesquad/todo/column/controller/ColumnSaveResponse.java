package codesquad.todo.column.controller;

public class ColumnSaveResponse {
	private ColumnSaveDto column;
	private boolean success;

	public ColumnSaveResponse() {
	}

	public ColumnSaveResponse(ColumnSaveDto column, boolean success) {
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

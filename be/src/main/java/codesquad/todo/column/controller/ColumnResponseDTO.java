package codesquad.todo.column.controller;

public class ColumnResponseDTO {
	private ColumnSaveDTO column;
	private boolean success;

	public ColumnResponseDTO() {
	}

	public ColumnResponseDTO(ColumnSaveDTO column, boolean success) {
		this.column = column;
		this.success = success;
	}

	public ColumnSaveDTO getColumn() {
		return column;
	}

	public boolean isSuccess() {
		return success;
	}
}

package codesquad.todo.history.entity;

public enum Action {
	REGISTERED("등록"),
	DELETED("삭제"),
	MOVED("이동"),
	MODIFIED("수정");

	private final String name;

	Action(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}

package codesquad.todo.history.entity;

import java.util.Arrays;

public enum Actions {
	SAVE("등록"),
	DELETE("삭제"),
	MOVE("이동"),
	MODIFY("수정");

	private final String name;

	Actions(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static Actions fromMethodName(String methodName) {
		return Arrays.stream(Actions.values())
			.filter(actions -> actions.name().equalsIgnoreCase(methodName.substring(0, methodName.length() - 4)))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Invalid method name: " + methodName));
	}
}

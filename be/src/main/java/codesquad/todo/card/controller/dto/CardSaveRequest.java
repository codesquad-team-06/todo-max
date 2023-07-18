package codesquad.todo.card.controller.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import codesquad.todo.card.entity.Card;

public class CardSaveRequest {
	@NotEmpty(message = "카드 제목을 입력해주세요.")
	@Size(max = 50, message = "글자 수를 초과했습니다. 카드 내용을 50자 이하로 입력해주세요.")
	String title;
	@NotEmpty(message = "카드 내용을 입력해주세요.")
	@Size(max = 500, message = "글자 수를 초과했습니다. 카드 내용을 500자 이하로 입력해주세요.")
	String content;
	Long columnId;

	public CardSaveRequest() {
	}

	public CardSaveRequest(String title, String content, Long columnId) {
		this.title = title;
		this.content = content;
		this.columnId = columnId;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public Long getColumnId() {
		return columnId;
	}

	public Card toEntity() {
		return Card.builder()
			.title(title)
			.content(content)
			.columnId(columnId)
			.build();
	}
}

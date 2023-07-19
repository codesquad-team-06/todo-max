package codesquad.todo.card.controller.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import codesquad.todo.card.entity.Card;

public class CardModifyRequest {
	private Long id;
	@NotEmpty(message = "카드 제목을 입력해주세요.")
	@Size(max = 50, message = "글자 수를 초과했습니다. 카드 내용을 50자 이하로 입력해주세요.")
	private String title;
	@NotEmpty(message = "카드 내용을 입력해주세요.")
	@Size(max = 500, message = "글자 수를 초과했습니다. 카드 내용을 500자 이하로 입력해주세요.")
	private String content;

	public CardModifyRequest() {
	}

	public CardModifyRequest(Long id, String title, String content) {
		this.id = id;
		this.title = title;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public Card toEntity() {
		return Card.builder()
			.id(id)
			.title(title)
			.content(content)
			.build();
	}
}

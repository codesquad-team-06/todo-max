package codesquad.todo.card.controller.dto;
import codesquad.todo.card.entity.Card;

public class CardModifyDTO {
	private Long id;
	private String title;
	private String content;

	public CardModifyDTO(Long id, String title, String content) {
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

	public static CardModifyDTO from(Card card) {
		return new CardModifyDTO(card.getId(), card.getTitle(), card.getContent());
	}
}

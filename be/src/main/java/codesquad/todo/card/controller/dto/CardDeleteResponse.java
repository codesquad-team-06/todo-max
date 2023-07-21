package codesquad.todo.card.controller.dto;

import codesquad.todo.card.entity.Card;

public class CardDeleteResponse {
	private CardResponseDto card;
	private boolean success;

	public CardDeleteResponse(CardResponseDto card, boolean success) {
		this.card = card;
		this.success = success;
	}

	public static CardDeleteResponse from(Card card) {
		return new CardDeleteResponse(new CardResponseDto(card.getId(), card.getTitle(), card.getContent(),
			card.getPosition(), card.getColumnId()), true);
	}

	public CardResponseDto getCard() {
		return card;
	}

	public boolean isSuccess() {
		return success;
	}
}

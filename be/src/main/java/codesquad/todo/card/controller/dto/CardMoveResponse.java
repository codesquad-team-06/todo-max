package codesquad.todo.card.controller.dto;

import codesquad.todo.card.entity.Card;

public class CardMoveResponse {
	private CardResponseDto card;
	private boolean success;

	public CardMoveResponse(CardResponseDto card, boolean success) {
		this.card = card;
		this.success = success;
	}

	public CardResponseDto getCard() {
		return card;
	}

	public boolean isSuccess() {
		return success;
	}

	public static CardMoveResponse from(Card card) {
		return new CardMoveResponse(CardResponseDto.from(card), true);
	}
}

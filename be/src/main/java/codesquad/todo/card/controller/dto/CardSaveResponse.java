package codesquad.todo.card.controller.dto;

import codesquad.todo.card.entity.Card;

public class CardSaveResponse {
	private CardResponseDto card;
	private boolean success;

	public CardSaveResponse(CardResponseDto card, boolean success) {
		this.card = card;
		this.success = success;
	}

	public static CardSaveResponse from(Card card) {
		return new CardSaveResponse(CardResponseDto.from(card), true);
	}

	public CardResponseDto getCard() {
		return card;
	}

	public boolean isSuccess() {
		return success;
	}

}

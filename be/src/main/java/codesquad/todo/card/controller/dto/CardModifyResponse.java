package codesquad.todo.card.controller.dto;

import codesquad.todo.card.entity.Card;

public class CardModifyResponse {
	private CardResponseDto card;
	private boolean success;

	public CardModifyResponse(CardResponseDto cardResponseDtO, boolean success) {
		this.card = cardResponseDtO;
		this.success = success;
	}

	public static CardModifyResponse from(Card card) {
		return new CardModifyResponse(CardResponseDto.from(card), true);
	}

	public CardResponseDto getCard() {
		return card;
	}

	public boolean isSuccess() {
		return success;
	}
}

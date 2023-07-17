package codesquad.todo.card.controller.dto;

import codesquad.todo.card.entity.Card;

public class CardMoveResponse {
	private CardResponseDTO card;
	private boolean success;

	public CardMoveResponse(CardResponseDTO card, boolean success) {
		this.card = card;
		this.success = success;
	}

	public CardResponseDTO getCard() {
		return card;
	}

	public boolean isSuccess() {
		return success;
	}

	public static CardMoveResponse from(Card card){
		return new CardMoveResponse(CardResponseDTO.from(card), true);
	}
}

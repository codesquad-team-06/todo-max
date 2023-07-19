package codesquad.todo.card.controller.dto;

import codesquad.todo.card.entity.Card;

public class CardModifyResponse {
	private CardResponseDTO card;
	private boolean success;

	public CardModifyResponse(CardResponseDTO cardResponseDTO, boolean success) {
		this.card = cardResponseDTO;
		this.success = success;
	}

	public CardResponseDTO getCard() {
		return card;
	}

	public boolean isSuccess() {
		return success;
	}

	public static CardModifyResponse from(Card card) {
		return new CardModifyResponse(CardResponseDTO.from(card), true);
	}
}

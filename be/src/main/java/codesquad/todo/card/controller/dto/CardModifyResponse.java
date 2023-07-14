package codesquad.todo.card.controller.dto;

import codesquad.todo.card.entity.Card;

public class CardModifyResponse {
	private CardModifyDTO card;
	private boolean success;

	public CardModifyResponse(CardModifyDTO cardModifyDTO, boolean success) {
		this.card = cardModifyDTO;
		this.success = success;
	}

	public CardModifyDTO getCard() {
		return card;
	}

	public boolean isSuccess() {
		return success;
	}

	public static CardModifyResponse from(Card card) {
		return new CardModifyResponse(CardModifyDTO.from(card), true);
	}
}

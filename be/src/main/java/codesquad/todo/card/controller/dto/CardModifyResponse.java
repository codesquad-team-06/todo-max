package codesquad.todo.card.controller.dto;

import codesquad.todo.card.entity.Card;

public class CardModifyResponse {
	private CardModifyDTO cardModifyDTO;
	private boolean success;

	public CardModifyResponse(CardModifyDTO cardModifyDTO, boolean success) {
		this.cardModifyDTO = cardModifyDTO;
		this.success = success;
	}

	public CardModifyDTO getCardModifyDTO() {
		return cardModifyDTO;
	}

	public boolean isSuccess() {
		return success;
	}

	public static CardModifyResponse from(Card card) {
		return new CardModifyResponse(CardModifyDTO.from(card), true);
	}
}

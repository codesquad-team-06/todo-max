package codesquad.todo.card.controller.dto;

import codesquad.todo.card.entity.Card;

public class CardDeleteResponse {
	private CardDeleteDTO cardDeleteDTO;
	private boolean success;

	public CardDeleteResponse(CardDeleteDTO cardDeleteDTO, boolean success) {
		this.cardDeleteDTO = cardDeleteDTO;
		this.success = success;
	}

	public CardDeleteDTO getCardDeleteDTO() {
		return cardDeleteDTO;
	}

	public boolean isSuccess() {
		return success;
	}

	public static CardDeleteResponse from(Card card) {
		return new CardDeleteResponse(CardDeleteDTO.from(card), true);
	}
}

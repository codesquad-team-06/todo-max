package codesquad.todo.card.controller.dto;

public class CardSaveResponse {
	private CardSaveDTO card;
	private boolean success;

	public CardSaveResponse(CardSaveDTO card, boolean success){
		this.card = card;
		this.success = success;
	}

	public CardSaveDTO getCard() {
		return card;
	}

	public boolean isSuccess() {
		return success;
	}
}

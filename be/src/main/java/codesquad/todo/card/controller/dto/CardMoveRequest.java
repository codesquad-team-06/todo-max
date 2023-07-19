package codesquad.todo.card.controller.dto;

public class CardMoveRequest {
	private Long id;
	private Long prevCardId;
	private Long nextCardId;
	private Long nextColumnId;

	public CardMoveRequest() {
	}

	public CardMoveRequest(Long id, Long prevCardId, Long nextCardId, Long nextColumnId) {
		this.id = id;
		this.prevCardId = prevCardId;
		this.nextCardId = nextCardId;
		this.nextColumnId = nextColumnId;
	}

	public Long getId() {
		return id;
	}

	public Long getPrevCardId() {
		return prevCardId;
	}

	public Long getNextCardId() {
		return nextCardId;
	}

	public Long getNextColumnId() {
		return nextColumnId;
	}
}

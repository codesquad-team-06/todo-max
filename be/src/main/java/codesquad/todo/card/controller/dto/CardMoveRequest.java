package codesquad.todo.card.controller.dto;

public class CardMoveRequest {
	private Long id;
	private Long prevCardId;
	private Long nextCardId;
	private Long columnId;

	public CardMoveRequest() {
	}

	public CardMoveRequest(Long id, Long prevCardId, Long nextCardId, Long columnId) {
		this.id = id;
		this.prevCardId = prevCardId;
		this.nextCardId = nextCardId;
		this.columnId = columnId;
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

	public Long getColumnId() {
		return columnId;
	}
}

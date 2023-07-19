package codesquad.todo.history.controller.dto;

import codesquad.todo.history.entity.History;

public class HistorySaveDto {
	String cardTitle;
	String prevColumn;
	String nextColumn;
	String actionName;
	Long cardId;

	public HistorySaveDto() {
	}

	public HistorySaveDto(String cardTitle, String prevColumn, String nextColumn, String actionName, Long cardId) {
		this.cardTitle = cardTitle;
		this.prevColumn = prevColumn;
		this.nextColumn = nextColumn;
		this.actionName = actionName;
		this.cardId = cardId;
	}

	public History toEntity() {
		return History.builder()
			.cardTitle(cardTitle)
			.prevColumn(prevColumn)
			.nextColumn(nextColumn)
			.cardId(cardId)
			.actionName(actionName)
			.isDeleted(false)
			.build();
	}

	public String getCardTitle() {
		return cardTitle;
	}

	public String getPrevColumn() {
		return prevColumn;
	}

	public String getNextColumn() {
		return nextColumn;
	}

	public Long getCardId() {
		return cardId;
	}
}

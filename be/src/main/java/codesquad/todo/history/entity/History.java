package codesquad.todo.history.entity;

import java.time.LocalDateTime;

public class History {
	private Long id;
	private String cardTitle;
	private String prevColumn;
	private String nextColumn;
	private LocalDateTime createdAt;
	private boolean isDeleted;
	private String actionName;
	private Long cardId;

	public History() {
	}

	public History(Long id, String cardTitle, String prevColumn, String nextColumn, LocalDateTime createdAt,
		boolean isDeleted, String actionName, Long cardId) {
		this.id = id;
		this.cardTitle = cardTitle;
		this.prevColumn = prevColumn;
		this.nextColumn = nextColumn;
		this.createdAt = createdAt;
		this.isDeleted = isDeleted;
		this.actionName = actionName;
		this.cardId = cardId;
	}

	public static Builder builder() {
		return new Builder();
	}

	public Long getId() {
		return id;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public String getActionName() {
		return actionName;
	}

	public Long getCardId() {
		return cardId;
	}

	@Override
	public String toString() {
		return "History{"
			+ "id=" + id
			+ ", cardTitle='" + cardTitle + '\''
			+ ", prevColumn='" + prevColumn + '\''
			+ ", nextColumn='" + nextColumn + '\''
			+ ", createdAt=" + createdAt
			+ ", isDeleted=" + isDeleted
			+ ", actionName=" + actionName
			+ ", cardId=" + cardId
			+ '}';
	}

	public static class Builder {
		private Long id;
		private String cardTitle;
		private String prevColumn;
		private String nextColumn;
		private LocalDateTime createdAt;
		private boolean isDeleted;
		private String actionName;
		private Long cardId;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder cardTitle(String cardTitle) {
			this.cardTitle = cardTitle;
			return this;
		}

		public Builder prevColumn(String prevColumn) {
			this.prevColumn = prevColumn;
			return this;
		}

		public Builder nextColumn(String nextColumn) {
			this.nextColumn = nextColumn;
			return this;
		}

		public Builder createAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public Builder isDeleted(boolean isDeleted) {
			this.isDeleted = isDeleted;
			return this;
		}

		public Builder actionName(String actionName) {
			this.actionName = actionName;
			return this;
		}

		public Builder cardId(Long cardId) {
			this.cardId = cardId;
			return this;
		}

		public History build() {
			return new History(id, cardTitle, prevColumn, nextColumn, createdAt, isDeleted, actionName, cardId);
		}
	}
}

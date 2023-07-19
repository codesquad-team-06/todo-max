package codesquad.todo.card.entity;

public class Card {
	private Long id; // 카드 아이디
	private String title; // 제목
	private String content; // 내용
	private int position; // 위치값
	private boolean isDeleted; // 삭제여부
	private Long columnId; // 컬럼 아이디

	public Card() {
	}

	public Card(Long id, String title, String content, int position, boolean isDeleted, Long columnId) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.position = position;
		this.isDeleted = isDeleted;
		this.columnId = columnId;
	}

	public static Builder builder() {
		return new Builder();
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public int getPosition() {
		return position;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public Long getColumnId() {
		return columnId;
	}

	@Override
	public String toString() {
		return "Card{"
			+ "id=" + id
			+ ", title='" + title + '\''
			+ ", content='" + content + '\''
			+ ", position=" + position
			+ ", isDeleted=" + isDeleted
			+ ", columnId=" + columnId
			+ '}';
	}

	public static class Builder {
		private Long id; // 카드 아이디
		private String title; // 제목
		private String content; // 내용
		private int position; // 위치값
		private boolean isDeleted; // 삭제여부
		private Long columnId; // 컬럼 아이디

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder content(String content) {
			this.content = content;
			return this;
		}

		public Builder position(int position) {
			this.position = position;
			return this;
		}

		public Builder isDeleted(boolean isDeleted) {
			this.isDeleted = isDeleted;
			return this;
		}

		public Builder columnId(Long columnId) {
			this.columnId = columnId;
			return this;
		}

		public Card build() {
			return new Card(id, title, content, position, isDeleted, columnId);
		}
	}
}

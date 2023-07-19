package codesquad.todo.column.entity;

public class Column {
	private Long id;
	private String name;
	private boolean isDeleted;

	public Column() {
	}

	public Column(Long id, String name, boolean isDeleted) {
		this.id = id;
		this.name = name;
		this.isDeleted = isDeleted;
	}

	public static Builder builder() {
		return new Builder();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	@Override
	public String toString() {
		return "Column{" +
			"id=" + id +
			", name='" + name + '\'' +
			", isDeleted=" + isDeleted +
			'}';
	}

	public static class Builder {
		private Long id;
		private String name;
		private boolean isDeleted;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder isDeleted(boolean isDeleted) {
			this.isDeleted = isDeleted;
			return this;
		}

		public Column build() {
			return new Column(id, name, isDeleted);
		}
	}
}

package codesquad.todo.column.entity;

public class Column {
	private Long id;
	private String name;

	public Column() {
	}

	public Column(Long id, String name) {
		this.id = id;
		this.name = name;
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

	@Override
	public String toString() {
		return "Column{" +
			"id=" + id +
			", name='" + name + '\'' +
			'}';
	}

	public static class Builder {
		private Long id;
		private String name;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Column build() {
			return new Column(id, name);
		}
	}
}

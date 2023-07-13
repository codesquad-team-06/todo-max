package codesquad.todo.column.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import codesquad.todo.column.entity.Column;

@Repository
public class JdbcColumnRepository implements ColumnRepository {

	private final NamedParameterJdbcTemplate template;

	public JdbcColumnRepository(NamedParameterJdbcTemplate template) {
		this.template = template;
	}

	@Override
	public List<Column> findAll() {
		return template.query("SELECT c.id, c.name FROM columns c", getColumnRowMapper());
	}

	@Override
	public Column save(Column column) {
		return null;
	}

	@Override
	public Column modify(Column column) {
		return null;
	}

	@Override
	public Column deleteById(Long id) {
		return null;
	}

	@Override
	public Optional<Column> findById(Long id) {
		return Optional.empty();
	}

	private RowMapper<Column> getColumnRowMapper() {
		return (rs, rowNum) -> Column.builder()
			.id(rs.getLong("id"))
			.name(rs.getString("name"))
			.build();
	}
}

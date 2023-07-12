package codesquad.todo.column.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import codesquad.todo.column.entity.Column;

@Repository
public class JdbcColumnRepository implements ColumnRepository {

	private NamedParameterJdbcTemplate template;

	@Override
	public List<Column> findAll() {
		return null;
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
}

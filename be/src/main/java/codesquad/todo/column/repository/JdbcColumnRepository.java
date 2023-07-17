package codesquad.todo.column.repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
		String sql = "INSERT INTO columns (name) VALUES(:name)";
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(column);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(sql, paramSource, keyHolder);
		return findById(Objects.requireNonNull(keyHolder.getKey()).longValue()).orElseThrow();
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
		String sql = "SELECT id, name FROM columns WHERE id = :id";
		return template.query(sql, new MapSqlParameterSource("id", id), getColumnRowMapper()).stream().findAny();
	}

	private RowMapper<Column> getColumnRowMapper() {
		return (rs, rowNum) -> Column.builder()
			.id(rs.getLong("id"))
			.name(rs.getString("name"))
			.build();
	}
}

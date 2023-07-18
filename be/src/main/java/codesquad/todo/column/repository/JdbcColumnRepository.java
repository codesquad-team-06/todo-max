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
		return template.query("SELECT c.id, c.name FROM columns c WHERE c.is_deleted = false", getColumnRowMapper());
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
		String sql = "UPDATE columns SET is_deleted = true WHERE id = :id";
		SqlParameterSource paramSource = new MapSqlParameterSource("id", id);
		Column delColumn = findById(id).orElseThrow();
		template.update(sql, paramSource);
		return delColumn;
	}

	@Override
	public Optional<Column> findById(Long id) {
		String sql = "SELECT id, name FROM columns WHERE id = :id AND is_deleted = false";
		return template.query(sql, new MapSqlParameterSource("id", id), getColumnRowMapper()).stream().findAny();
	}

	@Override
	public List<String> findAllNameById(List<Long> ids) {
		String sql = "SELECT name FROM columns WHERE id IN (:id)";
		return template.query(sql, new MapSqlParameterSource("id", ids), getColumnNameRowMapper());
	}

	private RowMapper<Column> getColumnRowMapper() {
		return (rs, rowNum) -> Column.builder()
			.id(rs.getLong("id"))
			.name(rs.getString("name"))
			.build();
	}

	private RowMapper<String> getColumnNameRowMapper() {
		return (rs, rowNum) -> rs.getString("name");
	}
}

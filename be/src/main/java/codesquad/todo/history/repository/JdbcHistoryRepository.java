package codesquad.todo.history.repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import codesquad.todo.history.entity.Action;
import codesquad.todo.history.entity.History;

@Repository
public class JdbcHistoryRepository implements HistoryRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final RowMapper<History> historyRowMapper = ((rs, rowNum) -> History.builder()
		.cardTitle(rs.getString("card_title"))
		.prevColumn(rs.getString("prev_column"))
		.nextColumn(rs.getString("next_column"))
		.createAt(rs.getTimestamp("created_at").toLocalDateTime())
		.action(Action.builder().id(rs.getLong("action_id")).name(rs.getString("action_name")).build())
		.build()
	);

	public JdbcHistoryRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<History> findAll() {
		String sql =
			"SELECT h.card_title, h.prev_column, h.next_column, h.created_at, a.id AS action_id, a.name AS action_name "
				+ "FROM history AS h "
				+ "JOIN action AS a ON h.action_id = a.id "
				+ "WHERE h.is_deleted = false "
				+ "ORDER BY h.id DESC;";
		return Collections.unmodifiableList(jdbcTemplate.query(sql, historyRowMapper));
	}

	@Override
	public int deleteByIds(List<Long> ids) {
		String sql = "UPDATE history SET is_deleted = true WHERE id IN (:ids)";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);
		return jdbcTemplate.update(sql, parameters); //삭제된 행 개수 반환
	}

	@Override
	public int countIds(List<Long> ids) {
		String sql = "SELECT COUNT(*) FROM history WHERE id IN (:ids) AND is_deleted = false";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);
		Integer count = jdbcTemplate.queryForObject(sql, parameters, Integer.class);
		return Optional.ofNullable(count).orElse(0);
	}

	@Override
	public History save(History history) {
		return null;
	}

	@Override
	public History modify(History history) {
		return null;
	}

	@Override
	public Optional<History> findById(Long id) {
		return Optional.empty();
	}
}

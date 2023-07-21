package codesquad.todo.history.repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import codesquad.todo.errors.errorcode.HistoryErrorCode;
import codesquad.todo.errors.exception.RestApiException;
import codesquad.todo.history.entity.History;

@Repository
public class JdbcHistoryRepository implements HistoryRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final RowMapper<History> historyRowMapper = ((rs, rowNum) -> History.builder()
		.id(rs.getLong("id"))
		.cardTitle(rs.getString("card_title"))
		.prevColumn(rs.getString("prev_column"))
		.nextColumn(rs.getString("next_column"))
		.createAt(rs.getTimestamp("created_at").toLocalDateTime())
		.actionName(rs.getString("action_name"))
		.isDeleted(rs.getBoolean("is_deleted"))
		.cardId(rs.getLong("card_id"))
		.build()
	);

	public JdbcHistoryRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<History> findAll() {
		String sql =
			"SELECT id, card_title, prev_column, next_column, created_at, action_name, is_deleted, card_id "
				+ "FROM history "
				+ "WHERE is_deleted = false "
				+ "ORDER BY id DESC;";
		return Collections.unmodifiableList(jdbcTemplate.query(sql, historyRowMapper));
	}

	// todo : 예외 처리 수정
	@Override
	public History save(History history) {
		String sql =
			"INSERT INTO history(card_title, prev_column, next_column, created_at, is_deleted, action_name, card_id) "
				+ "VALUES(:cardTitle, :prevColumn, :nextColumn, now(), :isDeleted, :actionName, :cardId)";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(sql, new MapSqlParameterSource()
			.addValue("cardTitle", history.getCardTitle())
			.addValue("prevColumn", history.getPrevColumn())
			.addValue("nextColumn", history.getNextColumn())
			.addValue("isDeleted", history.isDeleted())
			.addValue("actionName", history.getActionName())
			.addValue("cardId", history.getCardId()), keyHolder);
		long historyId = Objects.requireNonNull(keyHolder.getKey()).longValue();

		return findById(historyId);
	}

	@Override
	public History findById(Long id) {
		String sql = "SELECT id, card_title, prev_column, next_Column, created_at, is_deleted, action_name, card_id "
			+ "FROM history WHERE id = :id";
		return jdbcTemplate.query(sql, Map.of("id", id), historyRowMapper)
			.stream()
			.findAny()
			.orElseThrow(() -> new RestApiException(HistoryErrorCode.NOT_FOUND_HISTORY));
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
}

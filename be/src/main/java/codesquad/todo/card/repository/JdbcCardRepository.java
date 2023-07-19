package codesquad.todo.card.repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import codesquad.todo.card.entity.Card;
import codesquad.todo.errors.errorcode.CardErrorCode;
import codesquad.todo.errors.exception.RestApiException;

@Repository
public class JdbcCardRepository implements CardRepository {

	private static final int POSITION_OFFSET = 1024;

	private final NamedParameterJdbcTemplate template;
	private final RowMapper<Card> cardRowMapper = ((rs, rowNum) -> Card.builder()
		.id(rs.getLong("id"))
		.title(rs.getString("title"))
		.content(rs.getString("content"))
		.position(rs.getInt("position"))
		.isDeleted(rs.getBoolean("is_deleted"))
		.columnId(rs.getLong("column_id"))
		.build());

	public JdbcCardRepository(NamedParameterJdbcTemplate template) {
		this.template = template;
	}

	@Override
	public List<Card> findAll() {
		return template.query(
			"SELECT c.id, c.title, c.content, c.position, c.is_deleted, c.column_id "
				+ "FROM card c WHERE c.is_deleted = FALSE",
			cardRowMapper);
	}

	@Override
	public Card save(Card card) {
		String sql = "INSERT INTO card(title, content, position, column_id) "
			+ "VALUES (:title, :content,"
			+ "(SELECT IFNULL(MAX(position), 0) FROM card a WHERE column_id = :columnId AND is_deleted = FALSE) + "
			+ POSITION_OFFSET + ", :columnId);";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(sql, new MapSqlParameterSource()
			.addValue("title", card.getTitle())
			.addValue("content", card.getContent())
			.addValue("columnId", card.getColumnId()), keyHolder);
		long cardId = Objects.requireNonNull(keyHolder.getKey()).longValue();

		return findById(cardId);
	}

	@Override
	public Card modify(Card card) {
		String sql = "UPDATE card SET title = :title, content = :content WHERE id = :id";
		template.update(sql, new MapSqlParameterSource()
			.addValue("title", card.getTitle())
			.addValue("content", card.getContent())
			.addValue("id", card.getId()));

		return findById(card.getId());
	}

	@Override
	public Card deleteById(Long cardId) {
		String sql = "UPDATE card SET is_deleted = TRUE WHERE id = :id";
		template.update(sql, new MapSqlParameterSource()
			.addValue("id", cardId));

		return findById(cardId);
	}

	@Override
	public Card findById(Long id) {
		String sql = "SELECT id,title,content,position,is_deleted,column_id FROM card WHERE id = :id";
		return template.query(sql, Map.of("id", id), cardRowMapper).stream()
			.findAny()
			.orElseThrow(() -> new RestApiException(CardErrorCode.NOT_FOUND_Card));
	}

	@Override
	public List<Card> findAllByColumnId(Long columnId) {
		SqlParameterSource param = new MapSqlParameterSource("column_id", columnId);

		return template.query(
			"SELECT c.id, c.title, c.content, c.position, c.is_deleted, c.column_id FROM card c"
				+ " WHERE c.column_id = :column_id AND c.is_deleted = FALSE",
			param, cardRowMapper);
	}

	@Override
	public Card move(Long id, int position, Long nextColumnId) {
		String sql = "UPDATE card "
			+ "SET position = :position, column_id = :nextColumnId "
			+ "WHERE id = :id;";

		template.update(sql, new MapSqlParameterSource()
			.addValue("id", id)
			.addValue("position", position)
			.addValue("nextColumnId", nextColumnId));

		return findById(id);
	}

	@Override
	public int calculateNextPosition(Long prevCardId, Long nextCardId) {
		/**
		 * 이동할 위치의 칼럼에 카드가 없는 경우(prevCardId = 0 이고 nextCardId = 0인 경우)
		 * return : POSITION_OFFSET
		 * 이동할 위치가 가장 상단인 경우 (prevCardId만 0인 경우)
		 * return : nextCardId인 카드의 position값 + POSITION_OFFSET 반환
		 * 기본적으로 이동할 위치의 position 값 계산
		 * return : (이전 카드 position + 다음 카드 position) / 2
		 * 이동할 position값과 prevCardId의 position값의 차가 1인 경우
		 * return : 0
		 * 이동할 위치가 가장 하단인 경우 (nextCardId만 0인 경우)
		 * return : (이전 카드 position + 0) / 2
		 */
		String sql = "SELECT "
			+ "		CASE "
			+ "       WHEN :prevCardId = 0 AND :nextCardId = 0 THEN " + POSITION_OFFSET
			+ "       WHEN :prevCardId = 0 THEN (SELECT c3.position + " + POSITION_OFFSET
			+ " 								 	FROM card c3 WHERE c3.id = :nextCardId) "
			+ "       WHEN (SELECT IFNULL(SUM(c2.position), 0) FROM card c2 WHERE c2.id = :prevCardId) "
			+ "					 - IFNULL((ROUND(SUM(c1.position) / 2)), " + POSITION_OFFSET + ") = 1 "
			+ " 	  THEN 0 "
			+ "		  ELSE IFNULL((ROUND(SUM(c1.position) / 2)), " + POSITION_OFFSET + ") END AS move_position "
			+ "FROM card c1 "
			+ "WHERE c1.id IN (:prevCardId, :nextCardId);";

		return Optional.ofNullable(template.queryForObject(sql, new MapSqlParameterSource()
				.addValue("prevCardId", prevCardId)
				.addValue("nextCardId", nextCardId), Integer.class))
			.orElseThrow(() -> new RestApiException(CardErrorCode.NOT_FOUND_Card));
	}

	@Override
	public void reallocationPosition(Long columnId) {
		String sql = "UPDATE card c1, "
			+ "(SELECT id, (ROW_NUMBER() OVER (ORDER BY position)) * " + POSITION_OFFSET + " AS move_position "
			+ "FROM card "
			+ "WHERE column_id = :columnId AND is_deleted = FALSE) AS c2 "
			+ "SET c1.position = c2.move_position "
			+ "WHERE c1.id = c2.id;";

		template.update(sql, new MapSqlParameterSource()
			.addValue("columnId", columnId));
	}
}

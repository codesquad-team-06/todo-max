package codesquad.todo.card.repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import codesquad.todo.card.entity.Card;

@Repository
public class JdbcCardRepository implements CardRepository {

	private static final int POSITION_OFFSET = 1024;

	private final NamedParameterJdbcTemplate template;

	public JdbcCardRepository(NamedParameterJdbcTemplate template) {
		this.template = template;
	}

	@Override
	public List<Card> findAll() {
		return template.query(
			"SELECT c.id, c.title, c.content, c.position, c.is_deleted, c.column_id FROM card c WHERE c.is_deleted = FALSE",
			getCardRowMapper());
	}

	@Override
	public Card save(Card card) {
		String sql = "INSERT INTO card(title, content, position, column_id) "
			+ "VALUES (:title, :content, (SELECT IFNULL(MAX(position), 0) FROM card a WHERE column_id = :columnId AND is_deleted = FALSE) + "
			+ POSITION_OFFSET + ", :columnId);";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(sql, new MapSqlParameterSource()
			.addValue("title", card.getTitle())
			.addValue("content", card.getContent())
			.addValue("columnId", card.getColumnId()), keyHolder);
		long cardId = Objects.requireNonNull(keyHolder.getKey()).longValue();

		return findById(cardId).orElseThrow();
	}

	@Override
	public Card modify(Card card) {
		String sql = "UPDATE card SET title = :title, content = :content WHERE id = :id";
		template.update(sql, new MapSqlParameterSource()
			.addValue("title", card.getTitle())
			.addValue("content", card.getContent())
			.addValue("id", card.getId()));

		return findById(card.getId()).orElseThrow();
	}

	@Override
	public Card deleteById(Long cardId) {
		String sql = "UPDATE card SET is_deleted = TRUE WHERE id = :id";
		template.update(sql, new MapSqlParameterSource()
			.addValue("id", cardId));

		return findById(cardId).orElseThrow();
	}

	@Override
	public Optional<Card> findById(Long id) {
		String sql = "SELECT id,title,content,position,is_deleted,column_id FROM card WHERE id = :id";
		return Optional.ofNullable(template.queryForObject(sql, Map.of("id", id), cardRowMapper));
	}


	private final RowMapper<Card> cardRowMapper = ((rs, rowNum) -> Card.builder()
		.id(rs.getLong("id"))
		.title(rs.getString("title"))
		.content(rs.getString("content"))
		.position(rs.getInt("position"))
		.isDeleted(rs.getBoolean("is_deleted"))
		.columnId(rs.getLong("column_id"))
		.build());

	@Override
	public List<Card> findAllByColumnId(Long columnId) {
		SqlParameterSource param = new MapSqlParameterSource("column_id", columnId);

		return template.query(
			"SELECT c.id, c.title, c.content, c.position, c.is_deleted, c.column_id FROM card c WHERE c.column_id = :column_id AND c.is_deleted = FALSE",
			param, getCardRowMapper());
	}

	private RowMapper<Card> getCardRowMapper() {
		return (rs, rowNum) -> Card.builder()
			.id(rs.getLong("id"))
			.title(rs.getString("title"))
			.content(rs.getString("content"))
			.position(rs.getInt("position"))
			.isDeleted(rs.getBoolean("is_deleted"))
			.columnId(rs.getLong("column_id"))
			.build();
	}

}

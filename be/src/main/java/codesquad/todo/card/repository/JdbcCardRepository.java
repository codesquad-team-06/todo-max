package codesquad.todo.card.repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
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
		return null;
	}

	@Override
	public Card save(Card card) {
		String sql = "INSERT INTO card(title, content, position, column_id) "
			+ "VALUES (:title, :content, (SELECT MAX(position) FROM card WHERE column_id = :columnId) + " + POSITION_OFFSET + ", :columnId);";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(sql, new MapSqlParameterSource()
			.addValue("title", card.getTitle())
			.addValue("content", card.getContent())
			.addValue("columnId", card.getColumnId()));
		long cardId = Objects.requireNonNull(keyHolder.getKey()).longValue();

		return findById(cardId).orElseThrow();
	}

	@Override
	public Card modify(Card card) {
		String sql = "UPDATE card SET title = :title, content = :content WHERE id = :id";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(sql, new MapSqlParameterSource()
			.addValue("title", card.getTitle())
			.addValue("content", card.getContent())
			.addValue("id", card.getId()));
		long cardId = Objects.requireNonNull(keyHolder.getKey()).longValue();

		return findById(cardId).orElseThrow();
	}

	@Override
	public Card deleteById(Long id) {
		String sql = "UPDATE card SET is_deleted = TRUE WHERE id = :id";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(sql, new MapSqlParameterSource()
			.addValue("id", id));
		long cardId = Objects.requireNonNull(keyHolder.getKey()).longValue();

		return findById(cardId).orElseThrow();
	}

	@Override
	public Optional<Card> findById(Long id) {
		String sql = "SELECT id,title,content,position,is_deleted,column_id FROM card WHERE id = :id";
		return Optional.ofNullable(template.queryForObject(sql,Map.of("id",id),cardRowMapper));
	}

	private final RowMapper<Card> cardRowMapper = ((rs, rowNum) -> Card.builder()
		.id(rs.getLong("id"))
		.title(rs.getString("title"))
		.content(rs.getString("content"))
		.position(rs.getInt("position"))
		.isDeleted(rs.getBoolean("is_deleted"))
		.columnId(rs.getLong("column_id"))
		.build());
}

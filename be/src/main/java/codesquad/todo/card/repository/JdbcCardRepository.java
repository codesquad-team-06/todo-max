package codesquad.todo.card.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import codesquad.todo.card.entity.Card;

@Repository
public class JdbcCardRepository implements CardRepository {

	private final NamedParameterJdbcTemplate template;

	public JdbcCardRepository(NamedParameterJdbcTemplate template) {
		this.template = template;
	}

	@Override
	public List<Card> findAll() {
		return template.query("SELECT c.id, c.title, c.content, c.position, c.is_deleted, c.column_id FROM card c",
			getCardRowMapper());
	}

	@Override
	public Card save(Card card) {
		return null;
	}

	@Override
	public Card modify(Card card) {
		return null;
	}

	@Override
	public Card deleteById(Long id) {
		return null;
	}

	@Override
	public Optional<Card> findById(Long id) {
		return Optional.empty();
	}

	@Override
	public List<Card> findAllByColumnId(Long columnId) {
		SqlParameterSource param = new MapSqlParameterSource("column_id", columnId);

		return template.query(
			"SELECT c.id, c.title, c.content, c.position, c.is_deleted, c.column_id FROM card c WHERE c.column_id = :column_id",
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

package codesquad.todo.card.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
		return null;
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
}

package codesquad.todo.card.repository;

import java.util.List;
import java.util.Optional;

import codesquad.todo.card.entity.Card;

public interface CardRepository {
	List<Card> findAll();

	Card save(Card card);

	Card modify(Card card);

	Card deleteById(Long id);

	Optional<Card> findById(Long id);
}

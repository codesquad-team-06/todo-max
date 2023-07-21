package codesquad.todo.card.repository;

import java.util.List;

import codesquad.todo.card.entity.Card;

public interface CardRepository {
	List<Card> findAll();

	Card save(Card card);

	Card modify(Card card);

	Card deleteById(Long id);

	Card findById(Long id);

	List<Card> findAllByColumnId(Long columnId);

	Card move(Long id, int position, Long nextColumnId);

	int calculateNextPosition(Long prevCardId, Long nextCardId);

	void reallocationPosition(Long columnId);

	List<Card> deleteAllByColumnId(Long columnId);
}

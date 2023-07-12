package codesquad.todo.card.service;

import org.springframework.stereotype.Service;

import codesquad.todo.card.repository.CardRepository;

@Service
public class CardService {

	private final CardRepository cardRepository;

	public CardService(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}
}

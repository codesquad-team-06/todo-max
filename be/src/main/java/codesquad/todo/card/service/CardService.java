package codesquad.todo.card.service;

import org.springframework.stereotype.Service;

import codesquad.todo.card.controller.dto.CardDeleteResponse;
import codesquad.todo.card.controller.dto.CardModifyRequest;
import codesquad.todo.card.controller.dto.CardModifyResponse;
import codesquad.todo.card.controller.dto.CardSaveRequest;
import codesquad.todo.card.controller.dto.CardSaveResponse;
import codesquad.todo.card.repository.CardRepository;

@Service
public class CardService {

	private final CardRepository cardRepository;

	public CardService(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}

	public CardSaveResponse saveCard(CardSaveRequest cardSaveRequest) {
		return CardSaveResponse.from(cardRepository.save(cardSaveRequest.toEntity()));
	}

	public CardModifyResponse modifyCard(CardModifyRequest cardModifyRequest) {
		return CardModifyResponse.from(cardRepository.modify(cardModifyRequest.toEntity()));
	}

	public CardDeleteResponse deleteCard(Long cardId) {
		return CardDeleteResponse.from(cardRepository.deleteById(cardId));
	}

}

package codesquad.todo.card.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.todo.card.controller.CardListResponse;
import codesquad.todo.card.controller.CardSearchResponse;
import codesquad.todo.card.controller.dto.CardDeleteResponse;
import codesquad.todo.card.controller.dto.CardModifyRequest;
import codesquad.todo.card.controller.dto.CardModifyResponse;
import codesquad.todo.card.controller.dto.CardMoveRequest;
import codesquad.todo.card.controller.dto.CardMoveResponse;
import codesquad.todo.card.controller.dto.CardSaveRequest;
import codesquad.todo.card.controller.dto.CardSaveResponse;
import codesquad.todo.card.entity.Card;
import codesquad.todo.card.repository.CardRepository;
import codesquad.todo.column.entity.Column;
import codesquad.todo.column.repository.ColumnRepository;
import codesquad.todo.history.controller.dto.HistorySaveDto;
import codesquad.todo.history.entity.Actions;
import codesquad.todo.history.service.HistoryService;

@Service
public class CardService {

	private final CardRepository cardRepository;
	private final ColumnRepository columnRepository;
	private final HistoryService historyService;

	public CardService(CardRepository cardRepository, ColumnRepository columnRepository,
		HistoryService historyService) {
		this.cardRepository = cardRepository;
		this.columnRepository = columnRepository;
		this.historyService = historyService;
	}

	@Transactional(readOnly = true)
	public List<CardListResponse> getAllCard() {
		List<CardListResponse> cardListResponses = new ArrayList<>();
		// 1. 모든 카드 조회
		List<Column> columns = columnRepository.findAll();

		for (Column column : columns) {
			// 2. 컬럼 아이디에 따른 카드 조회
			List<CardSearchResponse> cards = cardRepository.findAllByColumnId(column.getId())
				.stream()
				.sorted(Comparator.comparingInt(Card::getPosition).reversed())
				.map(CardSearchResponse::from)
				.collect(Collectors.toUnmodifiableList());

			// 3. DTO 객체로 변환하여 결과 리스트에 저장
			cardListResponses.add(new CardListResponse(column.getId(), column.getName(), cards));
		}

		return cardListResponses;
	}

	@Transactional
	public CardSaveResponse saveCard(CardSaveRequest cardSaveRequest) {
		Card card = cardRepository.save(cardSaveRequest.toEntity());
		generateHistory(card, Actions.REGISTERED, List.of(card.getColumnId()));
		return CardSaveResponse.from(card);
	}

	@Transactional
	public CardModifyResponse modifyCard(CardModifyRequest cardModifyRequest) {
		Card card = cardRepository.modify(cardModifyRequest.toEntity());
		generateHistory(card, Actions.MODIFIED, List.of(card.getColumnId()));
		return CardModifyResponse.from(card);
	}

	@Transactional
	public CardDeleteResponse deleteCard(Long cardId) {
		Card card = cardRepository.deleteById(cardId);
		generateHistory(card, Actions.DELETED, List.of(card.getColumnId()));
		return CardDeleteResponse.from(card);
	}

	@Transactional
	public CardMoveResponse moveCard(CardMoveRequest cardMoveRequest) {
		int calculatePosition = cardRepository.calculateNextPosition(cardMoveRequest.getPrevCardId(),
			cardMoveRequest.getNextCardId());

		if (calculatePosition == 0) {
			// 컬럼 아이디에 해당하는 카드들의 position 재할당
			cardRepository.reallocationPosition(cardMoveRequest.getNextColumnId());
			return moveCard(cardMoveRequest);
		}

		Card moveCard = cardRepository.move(cardMoveRequest.getId(), calculatePosition,
			cardMoveRequest.getNextColumnId());

		generateHistory(moveCard, Actions.MOVED,
			List.of(cardMoveRequest.getPrevColumnId(), cardMoveRequest.getNextColumnId()));
		return CardMoveResponse.from(moveCard);
	}

	public void generateHistory(Card card, Actions action, List<Long> columnIds) {
		List<String> columnNames = columnRepository.findAllNameById(columnIds);
		String prevColumnName = columnNames.get(0);
		String nextColumnName = columnNames.stream().skip(1).findFirst().orElse(prevColumnName);
		historyService.save(
			new HistorySaveDto(card.getTitle(), prevColumnName, nextColumnName, action.getName(), card.getId())
		);
	}
}

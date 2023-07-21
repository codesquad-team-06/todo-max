package codesquad.todo.card.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import codesquad.todo.errors.errorcode.ColumnErrorCode;
import codesquad.todo.errors.exception.RestApiException;

@Service
public class CardService {

	private final CardRepository cardRepository;
	private final ColumnRepository columnRepository;

	public CardService(CardRepository cardRepository, ColumnRepository columnRepository) {
		this.cardRepository = cardRepository;
		this.columnRepository = columnRepository;
	}

	@Transactional(readOnly = true)
	public List<CardListResponse> getAllCard() {
		List<CardListResponse> cardListResponses = new ArrayList<>();
		Map<Long, List<Card>> cardsByColumnIdMap = new HashMap<>();
		// 1. 모든 컬럼 조회
		List<Column> columns = columnRepository.findAll();
		for (Column column : columns) {
			cardsByColumnIdMap.put(column.getId(), new ArrayList<>());
		}

		// 2. 모든 카드 조회
		List<Card> allCards = cardRepository.findAll();

		// 3. 컬럼 아이디별 리스트에 카드 추가
		allCards.forEach(card -> cardsByColumnIdMap.get(card.getColumnId()).add(card));

		// 4. 컬럼 아이디별 카드 리스트를 DTO 객체로 변환
		for (Long columnId : cardsByColumnIdMap.keySet()) {
			String columnName = getColumnName(columns, columnId);
			List<Card> cardsByColumnId = cardsByColumnIdMap.get(columnId);
			List<CardSearchResponse> cardSearchResponses = cardsByColumnId.stream()
				.sorted(Comparator.comparingInt(Card::getPosition).reversed())
				.map(CardSearchResponse::from)
				.collect(Collectors.toUnmodifiableList());
			cardListResponses.add(new CardListResponse(columnId, columnName, cardSearchResponses));
		}
		return cardListResponses;
	}

	private String getColumnName(List<Column> columns, Long columnId) {
		return columns.stream()
			.filter(column -> column.getId().equals(columnId))
			.map(Column::getName)
			.findAny()
			.orElseThrow(() -> new RestApiException(ColumnErrorCode.NOT_FOUND_COLUMN));
	}

	@Transactional
	public CardSaveResponse saveCard(CardSaveRequest cardSaveRequest) {
		Card card = cardRepository.save(cardSaveRequest.toEntity());
		return CardSaveResponse.from(card);
	}

	@Transactional
	public CardModifyResponse modifyCard(CardModifyRequest cardModifyRequest) {
		Card card = cardRepository.modify(cardModifyRequest.toEntity());
		return CardModifyResponse.from(card);
	}

	@Transactional
	public CardDeleteResponse deleteCard(Long cardId) {
		Card card = cardRepository.deleteById(cardId);
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

		return CardMoveResponse.from(moveCard);
	}
}

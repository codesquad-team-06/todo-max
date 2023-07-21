package codesquad.todo.aspect;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import codesquad.todo.card.controller.dto.CardDeleteResponse;
import codesquad.todo.card.controller.dto.CardModifyResponse;
import codesquad.todo.card.controller.dto.CardMoveRequest;
import codesquad.todo.card.controller.dto.CardMoveResponse;
import codesquad.todo.card.controller.dto.CardResponseDto;
import codesquad.todo.card.controller.dto.CardSaveResponse;
import codesquad.todo.column.repository.ColumnRepository;
import codesquad.todo.history.controller.dto.HistorySaveDto;
import codesquad.todo.history.entity.Actions;
import codesquad.todo.history.service.HistoryService;

@Aspect
@Component
public class CardHistoryLogAspect {
	private final HistoryService historyService;
	private final ColumnRepository columnRepository;

	public CardHistoryLogAspect(HistoryService historyService, ColumnRepository columnRepository) {
		this.historyService = historyService;
		this.columnRepository = columnRepository;
	}

	@AfterReturning(value = "PointCuts.saveAction()", returning = "cardSaveResponse")
	public CardSaveResponse logForSave(CardSaveResponse cardSaveResponse) {
		generateHistory(cardSaveResponse.getCard(), Actions.REGISTERED,
			List.of(cardSaveResponse.getCard().getColumnId()));
		return cardSaveResponse;
	}

	@AfterReturning(value = "PointCuts.modifyAction()", returning = "cardModifyResponse")
	public CardModifyResponse logForModify(CardModifyResponse cardModifyResponse) {
		generateHistory(cardModifyResponse.getCard(), Actions.MODIFIED,
			List.of(cardModifyResponse.getCard().getColumnId()));
		return cardModifyResponse;
	}

	@AfterReturning(value = "PointCuts.deleteAction()", returning = "cardDeleteResponse")
	public CardDeleteResponse logForDelete(CardDeleteResponse cardDeleteResponse) {
		generateHistory(cardDeleteResponse.getCard(), Actions.DELETED,
			List.of(cardDeleteResponse.getCard().getColumnId()));
		return cardDeleteResponse;
	}

	@Around("PointCuts.moveAction()")
	public CardMoveResponse logForMove(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		CardMoveRequest cardMoveRequest = (CardMoveRequest)proceedingJoinPoint.getArgs()[0];
		CardMoveResponse cardMoveResponse = (CardMoveResponse)proceedingJoinPoint.proceed();
		generateHistory(cardMoveResponse.getCard(), Actions.MOVED,
			List.of(cardMoveRequest.getPrevColumnId(), cardMoveRequest.getNextColumnId()));
		return cardMoveResponse;
	}

	public void generateHistory(CardResponseDto card, Actions action, List<Long> columnIds) {
		List<String> columnNames = columnRepository.findAllNameById(columnIds);
		String prevColumnName = columnNames.get(0);
		String nextColumnName = columnNames.stream().skip(1).findFirst().orElse(prevColumnName);
		historyService.save(
			new HistorySaveDto(card.getTitle(), prevColumnName, nextColumnName, action.getName(), card.getId()));
	}
}

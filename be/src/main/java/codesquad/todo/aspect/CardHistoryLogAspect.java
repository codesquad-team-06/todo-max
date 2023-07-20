package codesquad.todo.aspect;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import codesquad.todo.card.controller.dto.CardMoveRequest;
import codesquad.todo.card.controller.dto.CardMoveResponse;
import codesquad.todo.card.entity.Card;
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

	@Around("PointCuts.moveAction()")
	public CardMoveResponse logForMove(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		// moveCard 메서드 실행 전 매개변수를 오브젝트 배열로 가져온 후 첫번째 인자(cardMoveRequest)를 추출
		Object[] args = proceedingJoinPoint.getArgs();
		CardMoveRequest cardMoveRequest = (CardMoveRequest)args[0];

		// moveCard 메서드 실행 후 결과를 오브젝트 타입으로 가져온다
		Object result = proceedingJoinPoint.proceed();
		Card card = (Card)result;

		// moveCard 메서드 이름을 이용해서 Action 객체를 가져온다
		Actions action = getAction(proceedingJoinPoint.getSignature().getName());

		// 로그 생성
		generateHistory(card, action, List.of(cardMoveRequest.getPrevCardId(), cardMoveRequest.getNextCardId()));

		return CardMoveResponse.from(card);
	}

	@AfterReturning(value = "PointCuts.otherActions()", returning = "result")
	public void logForOthers(JoinPoint joinPoint, Object result) {
		Card card = (Card)result;
		Actions action = getAction(joinPoint.getSignature().getName());

		generateHistory(card, action, List.of(card.getColumnId()));
	}

	private Actions getAction(String methodName) {
		return Actions.fromMethodName(methodName);
	}

	public void generateHistory(Card card, Actions action, List<Long> columnIds) {
		List<String> columnNames = columnRepository.findAllNameById(columnIds);
		String prevColumnName = columnNames.get(0);
		String nextColumnName = columnNames.stream().skip(1).findFirst().orElse(prevColumnName);
		historyService.save(
			new HistorySaveDto(card.getTitle(), prevColumnName, nextColumnName, action.getName(), card.getId()));
	}
}

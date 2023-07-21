package codesquad.todo.aspect;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import codesquad.todo.card.controller.dto.CardDeleteResponse;
import codesquad.todo.card.controller.dto.CardModifyResponse;
import codesquad.todo.card.controller.dto.CardMoveRequest;
import codesquad.todo.card.controller.dto.CardMoveResponse;
import codesquad.todo.card.controller.dto.CardSaveResponse;
import codesquad.todo.card.entity.Card;
import codesquad.todo.column.repository.ColumnRepository;
import codesquad.todo.history.controller.dto.HistorySaveDto;
import codesquad.todo.history.service.HistoryService;

@ExtendWith(MockitoExtension.class)
public class CardHistoryLogAspectTest {

	@Mock
	private HistoryService historyService;

	@Mock
	private ColumnRepository columnRepository;

	@Mock
	private Card card;

	@Mock
	private ProceedingJoinPoint proceedingJoinPoint;

	@InjectMocks
	private CardHistoryLogAspect cardHistoryLogAspect;

	@Test
	@DisplayName("logForSave()를 실행하면 historyService.save() 메서드가 1번 호출된다.")
	public void testLogForSave() {
		//given
		CardSaveResponse cardSaveResponse = CardSaveResponse.from(card);
		List<String> columnNames = Arrays.asList("Column1");
		when(columnRepository.findAllNameById(anyList())).thenReturn(columnNames);

		//when
		cardHistoryLogAspect.logForSave(cardSaveResponse);

		//then
		verify(historyService, times(1)).save(any(HistorySaveDto.class));
	}

	@Test
	@DisplayName("logForModify()를 실행하면 historyService.save() 메서드가 1번 호출된다.")
	public void testLogForModify() {
		//given
		CardModifyResponse cardModifyResponse = CardModifyResponse.from(card);
		List<String> columnNames = Arrays.asList("Column1");
		when(columnRepository.findAllNameById(anyList())).thenReturn(columnNames);

		//when
		cardHistoryLogAspect.logForModify(cardModifyResponse);

		//then
		verify(historyService, times(1)).save(any(HistorySaveDto.class));
	}

	@Test
	@DisplayName("logForDelete()를 실행하면 historyService.save() 메서드가 1번 호출된다.")
	public void testLogForDelete() {
		//given
		CardDeleteResponse cardDeleteResponse = CardDeleteResponse.from(card);
		List<String> columnNames = Arrays.asList("Column1");
		when(columnRepository.findAllNameById(anyList())).thenReturn(columnNames);

		//when
		cardHistoryLogAspect.logForDelete(cardDeleteResponse);

		//then
		verify(historyService, times(1)).save(any(HistorySaveDto.class));
	}

	@Test
	@DisplayName("logForMove()를 실행하면 historyService.save() 메서드가 1번 호출된다.")
	public void testLogForMove() throws Throwable {
		//given
		CardMoveRequest request = mock(CardMoveRequest.class);
		when(proceedingJoinPoint.getArgs()).thenReturn(new Object[] {request});
		CardMoveResponse cardMoveResponse = CardMoveResponse.from(card);
		when(proceedingJoinPoint.proceed()).thenReturn(cardMoveResponse);
		List<String> columnNames = Arrays.asList("Column1", "Column2");
		when(columnRepository.findAllNameById(anyList())).thenReturn(columnNames);

		//when
		cardHistoryLogAspect.logForMove(proceedingJoinPoint);

		//then
		verify(historyService, times(1)).save(any(HistorySaveDto.class));
	}
}

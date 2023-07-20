package codesquad.todo.aspect;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import codesquad.todo.card.controller.dto.CardMoveRequest;
import codesquad.todo.card.entity.Card;
import codesquad.todo.column.repository.ColumnRepository;
import codesquad.todo.history.controller.dto.HistorySaveDto;
import codesquad.todo.history.service.HistoryService;

@ExtendWith(MockitoExtension.class)
public class CardHistoryLogAspectTest {

	@Mock
	ProceedingJoinPoint proceedingJoinPoint;
	@Mock
	JoinPoint joinPoint;
	@Mock
	MethodSignature methodSignature;
	@Mock
	Card card;
	@InjectMocks
	private CardHistoryLogAspect aspect;
	@Mock
	private HistoryService historyService;
	@Mock
	private ColumnRepository columnRepository;

	@Test
	@DisplayName("logForMove를 실행하면 historyService.save()가 1번 호출된다.")
	public void logForMoveTest() throws Throwable {
		//given
		CardMoveRequest request = mock(CardMoveRequest.class);
		when(proceedingJoinPoint.getArgs()).thenReturn(new Object[] {request});
		when(proceedingJoinPoint.getSignature()).thenReturn(methodSignature);
		when(methodSignature.getName()).thenReturn("moveCard");
		when(proceedingJoinPoint.proceed()).thenReturn(card);
		List<String> columnNames = Arrays.asList("Column1", "Column2");
		when(columnRepository.findAllNameById(anyList())).thenReturn(columnNames);

		//when
		aspect.logForMove(proceedingJoinPoint);

		//then
		verify(historyService, times(1)).save(any(HistorySaveDto.class));
	}

	@Test
	@DisplayName("logForOthers를 실행하면 historyService.save()가 1번 호출된다.")
	public void logForSaveTest() {
		//given
		when(joinPoint.getSignature()).thenReturn(methodSignature);
		when(methodSignature.getName()).thenReturn("saveCard");
		List<String> columnNames = Arrays.asList("Column1");
		when(columnRepository.findAllNameById(anyList())).thenReturn(columnNames);

		//when
		aspect.logForOthers(joinPoint, card);

		//then
		verify(historyService, times(1)).save(any(HistorySaveDto.class));
	}

	@Test
	@DisplayName("logForMove 실행 시 매치하는 Action 값이 없으면 예외를 발생하고 historyService.save 메서드를 호출하지 않는다.")
	public void logForMove_ThrowExceptionTest() throws Throwable {
		//given
		CardMoveRequest request = mock(CardMoveRequest.class);
		when(proceedingJoinPoint.getArgs()).thenReturn(new Object[] {request});
		when(proceedingJoinPoint.getSignature()).thenReturn(methodSignature);
		when(methodSignature.getName()).thenReturn("movCard");
		when(proceedingJoinPoint.proceed()).thenReturn(card);

		//when
		Exception exception = assertThrows(IllegalArgumentException.class,
			() -> aspect.logForMove(proceedingJoinPoint));

		//then
		assertEquals(exception.getMessage(), "Invalid method name: movCard");
		verify(historyService, times(0)).save(any(HistorySaveDto.class));
	}

	@Test
	@DisplayName("logForOthers 실행 시 매치하는 Action 값이 없으면 예외를 발생하고 historyService.save 메서드를 호출하지 않는다.")
	public void logForDelete_ThrowExceptionTest() {
		//given
		when(joinPoint.getSignature()).thenReturn(methodSignature);
		when(methodSignature.getName()).thenReturn("delete");

		//when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> aspect.logForOthers(joinPoint, card));

		//then
		assertEquals(exception.getMessage(), "Invalid method name: delete");
		verify(historyService, times(0)).save(any(HistorySaveDto.class));
	}
}

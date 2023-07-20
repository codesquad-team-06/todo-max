package codesquad.todo.card.service;

import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import codesquad.todo.card.controller.CardListResponse;
import codesquad.todo.card.controller.dto.CardModifyRequest;
import codesquad.todo.card.controller.dto.CardMoveRequest;
import codesquad.todo.card.controller.dto.CardSaveRequest;
import codesquad.todo.card.entity.Card;
import codesquad.todo.card.repository.CardRepository;
import codesquad.todo.column.entity.Column;
import codesquad.todo.column.repository.ColumnRepository;
import codesquad.todo.history.service.HistoryService;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

	@InjectMocks
	private CardService cardService;

	@Mock
	private CardRepository cardRepository;

	@Mock
	private HistoryService historyService;

	@Mock
	private ColumnRepository columnRepository;

	@Test
	@DisplayName("모든 카드를 요청할때 컬럼명 카드를 응답합니다.")
	public void testGetAllCard() {
		// given
		Long[] ids = {1L, 2L, 3L};
		List<Column> mockColumns = new ArrayList<>();
		mockColumns.add(Column.builder().id(ids[0]).name("해야할 일").build());
		mockColumns.add(Column.builder().id(ids[1]).name("하고 있는 일").build());
		mockColumns.add(Column.builder().id(ids[2]).name("완료한 일").build());

		List<Card> mockCards = new ArrayList<>();
		mockCards.add(Card.builder().id(1L).title("제목1").content("내용1").position(1024).columnId(ids[0]).build());
		mockCards.add(Card.builder().id(2L).title("제목2").content("내용2").position(2048).columnId(ids[0]).build());
		mockCards.add(Card.builder().id(3L).title("제목3").content("내용3").position(3072).columnId(ids[0]).build());
		mockCards.add(Card.builder().id(4L).title("제목4").content("내용4").position(1024).columnId(ids[1]).build());
		mockCards.add(Card.builder().id(5L).title("제목5").content("내용5").position(2048).columnId(ids[1]).build());
		mockCards.add(Card.builder().id(6L).title("제목6").content("내용6").position(3072).columnId(ids[1]).build());
		mockCards.add(Card.builder().id(7L).title("제목7").content("내용7").position(1024).columnId(ids[2]).build());
		mockCards.add(Card.builder().id(8L).title("제목8").content("내용8").position(2048).columnId(ids[2]).build());
		mockCards.add(Card.builder().id(9L).title("제목9").content("내용9").position(3072).columnId(ids[2]).build());

		// mocking
		Mockito.when(columnRepository.findAll()).thenReturn(mockColumns);
		Mockito.when(cardRepository.findAll()).thenReturn(mockCards);
		// when
		List<CardListResponse> responses = cardService.getAllCard();
		// then
		CardListResponse response = responses.get(0);
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(response.getColumnId()).isEqualTo(1L);
			softAssertions.assertThat(response.getName()).isEqualTo("해야할 일");
			softAssertions.assertThat(response.getCards().get(0).getId()).isEqualTo(3L);
			softAssertions.assertThat(response.getCards().get(1).getId()).isEqualTo(2L);
			softAssertions.assertThat(response.getCards().get(2).getId()).isEqualTo(1L);
			softAssertions.assertAll();
		});
	}

	@DisplayName("카드 생성 시 생성 정보를 히스토리에 저장하는 HistoryService.save()가 실행된다.")
	public void testSaveCard() {
		//given
		CardSaveRequest saveRequest = new CardSaveRequest("텟트", "테스트", 1L);

		Card card = Card.builder()
			.id(10L)
			.title("카드10")
			.content("내용10")
			.columnId(1L)
			.position(4096)
			.build();
		given(cardRepository.save(any())).willReturn(card);
		given(columnRepository.findAllNameById(any())).willReturn(List.of("해야할 일", "해야할 일"));
		//when
		cardService.saveCard(saveRequest);

		//then
		verify(historyService, times(1)).save(any());
	}

	@Test
	@DisplayName("카드 수정 시 수정 정보를 히스토리에 저장하는 HistoryService.save()가 실행된다.")
	public void testModifyCard() {
		//given
		CardModifyRequest cardModifyRequest = new CardModifyRequest(1L, "테스트", "테스트");

		Card card = Card.builder()
			.id(1L)
			.title("테스트")
			.content("테스트")
			.columnId(1L)
			.position(1024)
			.build();
		given(cardRepository.modify(any())).willReturn(card);
		given(columnRepository.findAllNameById(any())).willReturn(List.of("해야할 일", "해야할 일"));
		//when
		cardService.modifyCard(cardModifyRequest);

		//then
		verify(historyService, times(1)).save(any());
	}

	@Test
	@DisplayName("카드 삭제 시 삭제 정보를 히스토리에 저장하는 HistoryService.save()가 실행된다.")
	public void testDeleteCard() {
		//given
		Long cardId = 1L;

		Card card = Card.builder()
			.id(1L)
			.title("제목1")
			.content("내용1")
			.columnId(1L)
			.position(1024)
			.build();
		given(cardRepository.deleteById(cardId)).willReturn(card);
		given(columnRepository.findAllNameById(any())).willReturn(List.of("해야할 일", "해야할 일"));
		//when
		cardService.deleteCard(cardId);

		//then
		verify(historyService, times(1)).save(any());
	}

	@Test
	@DisplayName("카드 이동 시 이동 정보를 히스토리에 저장하는 HistoryService.save()가 실행된다.")
	public void testMoveCard() {
		//given
		CardMoveRequest moveRequest = new CardMoveRequest(1L, 4L, 5L, 1L, 2L);

		Card card = Card.builder()
			.id(1L)
			.title("테스트")
			.content("테스트")
			.columnId(2L)
			.position(2560)
			.build();
		given(cardRepository.calculateNextPosition(any(), any())).willReturn(2560);
		given(cardRepository.move(1L, 2560, 2L)).willReturn(card);
		given(columnRepository.findAllNameById(any())).willReturn(List.of("해야할 일", "하고 있는 일"));
		//when
		cardService.moveCard(moveRequest);

		//then
		verify(historyService, times(1)).save(any());
	}
}

package codesquad.todo.card.service;

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
import codesquad.todo.card.entity.Card;
import codesquad.todo.card.repository.CardRepository;
import codesquad.todo.column.entity.Column;
import codesquad.todo.column.repository.ColumnRepository;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

	@InjectMocks
	private CardService cardService;

	@Mock
	private CardRepository cardRepository;

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

	@Test
	@DisplayName("해야할 일 컬럼의 카드가 비어있는 경우에도 빈 해야할일 컬럼이 응답한다")
	public void testGetAllCard2() {
		// given
		Long[] ids = {1L, 2L, 3L};
		List<Column> mockColumns = new ArrayList<>();
		mockColumns.add(Column.builder().id(ids[0]).name("해야할 일").build());
		mockColumns.add(Column.builder().id(ids[1]).name("하고 있는 일").build());
		mockColumns.add(Column.builder().id(ids[2]).name("완료한 일").build());

		List<Card> mockCards = new ArrayList<>();
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
			softAssertions.assertThat(response.getCards().size()).isEqualTo(0);
			softAssertions.assertAll();
		});
	}
}

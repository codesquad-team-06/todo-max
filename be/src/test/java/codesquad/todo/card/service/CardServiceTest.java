package codesquad.todo.card.service;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import codesquad.todo.card.controller.CardListResponse;
import codesquad.todo.card.entity.Card;
import codesquad.todo.card.repository.CardRepository;
import codesquad.todo.column.entity.Column;
import codesquad.todo.column.repository.ColumnRepository;
import codesquad.todo.history.repository.HistoryRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CardServiceTest {
	@Autowired
	private CardService cardService;

	@Autowired
	private HistoryRepository historyRepository;

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

		List<Card> mockCardsByColumnId1 = new ArrayList<>();
		mockCardsByColumnId1.add(Card.builder().id(1L).title("제목1").content("내용1").position(1024).build());
		mockCardsByColumnId1.add(Card.builder().id(2L).title("제목2").content("내용2").position(2048).build());
		mockCardsByColumnId1.add(Card.builder().id(3L).title("제목3").content("내용3").position(3072).build());

		List<Card> mockCardsByColumnId2 = new ArrayList<>();
		mockCardsByColumnId1.add(Card.builder().id(4L).title("제목4").content("내용4").position(1024).build());
		mockCardsByColumnId1.add(Card.builder().id(5L).title("제목5").content("내용5").position(2048).build());
		mockCardsByColumnId1.add(Card.builder().id(6L).title("제목6").content("내용6").position(3072).build());

		List<Card> mockCardsByColumnId3 = new ArrayList<>();
		mockCardsByColumnId1.add(Card.builder().id(7L).title("제목7").content("내용7").position(1024).build());
		mockCardsByColumnId1.add(Card.builder().id(8L).title("제목8").content("내용8").position(2048).build());
		mockCardsByColumnId1.add(Card.builder().id(9L).title("제목9").content("내용3").position(3072).build());

		// mocking
		Mockito.when(columnRepository.findAll()).thenReturn(mockColumns);
		Mockito.when(cardRepository.findAllByColumnId(ids[0])).thenReturn(mockCardsByColumnId1);
		Mockito.when(cardRepository.findAllByColumnId(ids[1])).thenReturn(mockCardsByColumnId2);
		Mockito.when(cardRepository.findAllByColumnId(ids[2])).thenReturn(mockCardsByColumnId3);
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
		responses.forEach(System.out::println);
	}
}

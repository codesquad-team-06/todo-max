package codesquad.todo.card.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import codesquad.todo.card.controller.dto.CardModifyRequest;
import codesquad.todo.card.controller.dto.CardMoveRequest;
import codesquad.todo.card.controller.dto.CardSaveRequest;
import codesquad.todo.history.entity.History;
import codesquad.todo.history.repository.HistoryRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CardServiceTest {

	@Autowired
	private CardService cardService;

	@Autowired
	private HistoryRepository historyRepository;

	@Test
	@Order(1)
	@DisplayName("카드 생성 요청이 성공적으로 발생할 때마다 히스토리를 생성한다.")
	public void saveCardHistory() {
		//given
		CardSaveRequest cardSaveRequest = new CardSaveRequest("제목", "내용", 1L);

		//when
		cardService.saveCard(cardSaveRequest);
		//then
		History history = historyRepository.findById(10L).get();
		assertAll(
			() -> assertThat(history.getCardTitle()).isEqualTo("제목"),
			() -> assertThat(history.getNextColumn()).isEqualTo("해야할 일"),
			() -> assertThat(history.getPrevColumn()).isEqualTo("해야할 일"),
			() -> assertThat(history.getActionName()).isEqualTo("등록")
		);
	}

	@Test
	@Order(2)
	@DisplayName("카드 수정 실행 시 수정 정보에 대한 히스토리를 생성한다.")
	public void modifyCardTest() {
		//given
		CardModifyRequest cardModifyRequest = new CardModifyRequest(1L, "제목 수정", "내용 수정");

		//when
		cardService.modifyCard(cardModifyRequest);

		//then
		History history = historyRepository.findById(11L).get();
		assertAll(
			() -> assertThat(history.getCardTitle()).isEqualTo("제목 수정"),
			() -> assertThat(history.getPrevColumn()).isEqualTo("해야할 일"),
			() -> assertThat(history.getNextColumn()).isEqualTo("해야할 일"),
			() -> assertThat(history.getActionName()).isEqualTo("수정")
		);
	}

	@Test
	@Order(3)
	@DisplayName("카드 삭제 실행 시 삭제 정보에 대한 히스토리를 생성한다.")
	public void deleteCardTest() {
		//given
		Long cardId = 2L;
		//when
		cardService.deleteCard(cardId);

		//then
		History history = historyRepository.findById(12L).get();
		assertAll(
			() -> assertThat(history.getCardTitle()).isEqualTo("제목2"),
			() -> assertThat(history.getPrevColumn()).isEqualTo("해야할 일"),
			() -> assertThat(history.getNextColumn()).isEqualTo("해야할 일"),
			() -> assertThat(history.getActionName()).isEqualTo("삭제")
		);
	}

	@Test
	@Order(4)
	@DisplayName("카드 이동 실행 시 이동 정보에 대한 히스토리를 생성한다.")
	public void moveCardTest() {
		//given
		CardMoveRequest cardMoveRequest = new CardMoveRequest(3L, 4L, 5L, 1L, 2L);

		//when
		cardService.moveCard(cardMoveRequest);

		//then
		History history = historyRepository.findById(13L).get();
		assertAll(
			() -> assertThat(history.getCardTitle()).isEqualTo("제목3"),
			() -> assertThat(history.getPrevColumn()).isEqualTo("해야할 일"),
			() -> assertThat(history.getNextColumn()).isEqualTo("하고 있는 일"),
			() -> assertThat(history.getActionName()).isEqualTo("이동")
		);
	}
}

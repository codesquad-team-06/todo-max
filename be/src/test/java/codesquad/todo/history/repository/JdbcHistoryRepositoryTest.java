package codesquad.todo.history.repository;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

import codesquad.todo.card.entity.Card;
import codesquad.todo.card.repository.CardRepository;
import codesquad.todo.history.entity.Actions;
import codesquad.todo.history.entity.History;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
	classes = Repository.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcHistoryRepositoryTest {

	@Autowired
	private CardRepository cardRepository;
	@Autowired
	private HistoryRepository historyRepository;

	@Test
	@DisplayName("is_deleted = false인 모든 활동 기록을 불러온다.")
	public void testFindAll() {
		//given

		//when
		List<History> historyList = historyRepository.findAll();

		//then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(historyList.size()).isEqualTo(9);
		});
	}

	@Test
	@DisplayName("전달 받은 활동 기록 아이디가 모두 유효한 지 확인한다.")
	public void testCountIds() {
		//given
		List<Long> ids = new ArrayList<>(Arrays.asList(4L, 5L, 6L));

		//when
		int validIds = historyRepository.countIds(ids);

		//then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(validIds).isEqualTo(3);
		});
	}

	@Test
	@DisplayName("활동 기록 아이디에 따라 해당 기록의 is_deleted 값을 false로 변경하고 변경된 레코드 수를 반환한다.")
	public void testDeleteByIds() {
		//given
		List<Long> ids = new ArrayList<>(Arrays.asList(4L, 5L, 6L));

		//when
		int deletedHistories = historyRepository.deleteByIds(ids);

		//then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(deletedHistories).isEqualTo(3);
		});
	}

	@Test
	@DisplayName("카드를 등록하면 해당 활동 기록을 히스토리에 저장한다")
	public void testSave() {
		//given
		Card card = Card.builder()
			.title("카드제목")
			.content("카드내용")
			.columnId(1L)
			.build();

		Card savedCard = cardRepository.save(card);

		History history = History.builder()
			.cardTitle(savedCard.getTitle())
			.prevColumn("해야할 일")
			.nextColumn("해야할 일")
			.cardId(savedCard.getId())
			.actionName(Actions.REGISTERED.getName())
			.isDeleted(false)
			.build();

		//when
		History savedHistory = historyRepository.save(history);

		//then
		assertAll(
			() -> assertThat(savedHistory.getCardTitle()).isEqualTo(history.getCardTitle()),
			() -> assertThat(savedHistory.getPrevColumn()).isEqualTo(history.getPrevColumn()),
			() -> assertThat(savedHistory.getNextColumn()).isEqualTo(history.getNextColumn()),
			() -> assertThat(savedHistory.getCardId()).isEqualTo(history.getCardId()),
			() -> assertThat(savedHistory.getCardTitle()).isEqualTo(history.getCardTitle()),
			() -> assertThat(savedHistory.getActionName()).isEqualTo(history.getActionName()),
			() -> assertThat(savedHistory.isDeleted()).isEqualTo(history.isDeleted())
		);
	}
}

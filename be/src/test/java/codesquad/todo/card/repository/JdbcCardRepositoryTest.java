package codesquad.todo.card.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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

// Repository 애노테이션이 붙은 클래스만 빈으로 등록
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
// Replace.NONE으로 설정하면 @ActiveProfiles에 설정한 프로파일 환경값에 따라 데이터소스가 적용된다.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcCardRepositoryTest {

	@Autowired
	private CardRepository cardRepository;

	@Test
	@DisplayName("새로운 카드를 저장하고 저장한 카드를 반환한다.")
	public void saveTest() {
		//given
		Card card = Card.builder()
			.title("카드1")
			.content("내용내용")
			.columnId(1L)
			.build();

		//when
		Card saveCard = cardRepository.save(card);

		//then
		assertAll(
			() -> assertThat(saveCard.getTitle()).isEqualTo(card.getTitle()),
			() -> assertThat(saveCard.getContent()).isEqualTo(card.getContent()),
			() -> assertThat(saveCard.getColumnId()).isEqualTo(card.getColumnId()),
			() -> assertThat(saveCard.getPosition()).isEqualTo(4096),
			() -> assertThat(saveCard.isDeleted()).isFalse()
		);
	}

	@Test
	@DisplayName("카드의 제목, 내용을 수정하고 수정한 카드를 반환한다.")
	public void modifyTest() {
		//given
		Card card = Card.builder()
			.id(1L)
			.title("수정제목")
			.content("수정내용")
			.build();

		//when
		Card modifyCard = cardRepository.modify(card);

		//then
		assertAll(
			() -> assertThat(modifyCard.getId()).isEqualTo(card.getId()),
			() -> assertThat(modifyCard.getTitle()).isEqualTo(card.getTitle()),
			() -> assertThat(modifyCard.getContent()).isEqualTo(card.getContent()),
			() -> assertThat(modifyCard.isDeleted()).isFalse()
		);
	}

	@Test
	@DisplayName("카드의 id를 입력받으면 DB에서 해당하는 카드의 is_deleted 값을 true로 변경하고 반환한다.")
	public void deleteTest() {
		//given
		Long cardId = 1L;

		//when
		Card deletedCard = cardRepository.deleteById(cardId);

		//then
		assertAll(
			() -> assertThat(deletedCard.isDeleted()).isTrue(),
			() -> assertThat(deletedCard.getId()).isEqualTo(cardId)
		);
	}

	@Test
	@DisplayName("카드 id로 카드를 조회하여 반환한다.")
	public void findByIdTest() {
		//given
		Long cardId = 2L;

		//when
		Card card = cardRepository.findById(cardId);

		//then
		assertAll(
			() -> assertThat(card.getId()).isEqualTo(cardId),
			() -> assertThat(card.getTitle()).isEqualTo("제목2"),
			() -> assertThat(card.getContent()).isEqualTo("내용2"),
			() -> assertThat(card.getColumnId()).isEqualTo(1),
			() -> assertThat(card.getPosition()).isEqualTo(2048),
			() -> assertThat(card.isDeleted()).isFalse()
		);
	}

	@Test
	@DisplayName("모든 카드 데이터를 요청합니다.")
	public void testFindAll() {
		// given

		// when
		List<Card> cards = cardRepository.findAll();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(cards.size()).isEqualTo(9);
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("컬럼 아이디에 따른 카드 데이터를 요청합니다.")
	public void testFindAllByColumnId() {
		// given

		// when
		List<Card> cards = cardRepository.findAllByColumnId(1L);
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(cards.size()).isEqualTo(3);
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("이동할 위치의 position과 column_id를 입력 받아 카드의 position과 column_id를 수정한다.")
	void testMove() {
		//given
		Long cardId = 3L;
		int position = 1536;
		Long columnId = 2L;
		//when
		cardRepository.move(cardId, position, columnId);
		Card card = cardRepository.findById(3L);
		//then
		assertAll(
			() -> assertThat(card.getId()).isEqualTo(3L),
			() -> assertThat(card.getPosition()).isEqualTo(1536),
			() -> assertThat(card.getColumnId()).isEqualTo(2L)
		);
	}

	@Test
	@DisplayName("이동할 위치의 상단 카드와 하단 카드의 id를 받아 position 값을 계산한다.")
	void testCalculateNextPosition() {
		//given
		// 1. 이동하는 컬럼에 카드가 없는 경우
		Long prevCardId1 = 0L;
		Long nextCardId1 = 0L;
		// 2. 이동할 위치가 최상단인 경우
		Long prevCardId2 = 0L;
		Long nextCardId2 = 6L;
		// 3. 이동할 위치가 최하단인 경우
		Long prevCardId3 = 4L;
		Long nextCardId3 = 0L;
		// 4. 이동할 위치의 상단 하단 카드가 있는 경우
		Long prevCardId4 = 1L;
		Long nextCardId4 = 2L;
		// 5. 이동할 위치의 position과 상단 카드 position의 차이가 1인 경우(재할당이 필요한 경우)
		cardRepository.move(8L, 1026, 3L);
		Long prevCardId5 = 8L;
		Long nextCardId5 = 7L;

		//when
		int calculateNextPosition1 = cardRepository.calculateNextPosition(prevCardId1, nextCardId1);
		int calculateNextPosition2 = cardRepository.calculateNextPosition(prevCardId2, nextCardId2);
		int calculateNextPosition3 = cardRepository.calculateNextPosition(prevCardId3, nextCardId3);
		int calculateNextPosition4 = cardRepository.calculateNextPosition(prevCardId4, nextCardId4);
		int calculateNextPosition5 = cardRepository.calculateNextPosition(prevCardId5, nextCardId5);

		//then
		assertThat(calculateNextPosition1).isEqualTo(1024);
		assertThat(calculateNextPosition2).isEqualTo(4096);
		assertThat(calculateNextPosition3).isEqualTo(512);
		assertThat(calculateNextPosition4).isEqualTo(1536);
		assertThat(calculateNextPosition5).isEqualTo(0);
	}

	@Test
	@DisplayName("요청받은 columnId에 해당하는 카드들의 position 값 오름차순을 기준으로 POSITION_OFFSET 상수 간격으로 재정렬한다.")
	void testReallocationPosition() {
		//given
		Long columnId = 1L;
		cardRepository.move(1L, 2, 1L);
		cardRepository.move(2L, 1, 1L);
		cardRepository.move(3L, 3, 1L);
		//when
		cardRepository.reallocationPosition(1L);
		List<Card> cardList = cardRepository.findAllByColumnId(columnId);
		int card1Position = cardList.get(0).getPosition();
		int card2Position = cardList.get(1).getPosition();
		int card3Position = cardList.get(2).getPosition();
		//then
		assertAll(
			() -> assertThat(card1Position).isEqualTo(2048),
			() -> assertThat(card2Position).isEqualTo(1024),
			() -> assertThat(card3Position).isEqualTo(3072)
		);
	}
}

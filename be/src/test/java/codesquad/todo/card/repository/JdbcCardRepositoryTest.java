package codesquad.todo.card.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import codesquad.todo.card.entity.Card;

// Repository 애노테이션이 붙은 클래스만 빈으로 등록
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
// Replace.NONE으로 설정하면 @ActiveProfiles에 설정한 프로파일 환경값에 따라 데이터소스가 적용된다.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
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
		Card card = cardRepository.findById(cardId).get();

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
}

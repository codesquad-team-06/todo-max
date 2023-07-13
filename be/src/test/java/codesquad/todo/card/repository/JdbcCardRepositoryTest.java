package codesquad.todo.card.repository;

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
}

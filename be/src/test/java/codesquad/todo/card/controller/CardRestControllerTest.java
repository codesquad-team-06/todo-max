package codesquad.todo.card.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquad.todo.card.controller.dto.CardDeleteResponse;
import codesquad.todo.card.controller.dto.CardModifyRequest;
import codesquad.todo.card.controller.dto.CardModifyResponse;
import codesquad.todo.card.controller.dto.CardMoveRequest;
import codesquad.todo.card.controller.dto.CardMoveResponse;
import codesquad.todo.card.controller.dto.CardResponseDTO;
import codesquad.todo.card.controller.dto.CardSaveRequest;
import codesquad.todo.card.controller.dto.CardSaveResponse;
import codesquad.todo.card.entity.Card;
import codesquad.todo.card.service.CardService;

@WebMvcTest(CardRestController.class)
class CardRestControllerTest {

	@Autowired
	ObjectMapper objectMapper;

	private MockMvc mockMvc;

	@MockBean
	private CardService cardService;

	@Test
	@DisplayName("새로운 카드를 등록하면 등록한 카드 데이터를 json으로 반환한다.")
	void saveCardTest() throws Exception {
		//given
		CardSaveRequest cardSaveRequest = new CardSaveRequest("new제목", "new내용", 1);
		CardSaveResponse cardSaveResponse = new CardSaveResponse(
			new CardResponseDTO(1L, "new제목", "new내용", 1024, 1L), true);
		String body = objectMapper.writeValueAsString(cardSaveRequest);
		//when
		given(cardService.saveCard(any())).willReturn(cardSaveResponse);

		//then
		mockMvc.perform(post("/cards")
				.content(body)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("card.id").value(1L))
			.andExpect(jsonPath("card.title").value("new제목"))
			.andExpect(jsonPath("card.content").value("new내용"))
			.andExpect(jsonPath("card.position").value(1024))
			.andExpect(jsonPath("card.columnId").value(1L))
			.andExpect(jsonPath("success").value(true))
			.andDo(print());
	}

	@Test
	@DisplayName("카드의 제목과 내용을 수정하고 수정된 카드의 데이터를 json으로 반환한다.")
	void modifyCard() throws Exception {
		//given
		CardModifyRequest cardModifyRequest = new CardModifyRequest(1L, "제목수정", "내용수정");
		CardModifyResponse cardModifyResponse = new CardModifyResponse(
			new CardResponseDTO(1L, "제목수정", "내용수정", 1024, 1L), true);

		// when
		given(cardService.modifyCard(any())).willReturn(cardModifyResponse);

		//then
		mockMvc.perform(put("/cards")
				.content(objectMapper.writeValueAsString(cardModifyRequest))
				.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("card.id").value(1L))
			.andExpect(jsonPath("card.title").value("제목수정"))
			.andExpect(jsonPath("card.content").value("내용수정"))
			.andExpect(jsonPath("success").value(true))
			.andDo(print());
	}

	@Test
	@DisplayName("요청받은 카드 번호의 카드의 isDeleted 값을 false로 변환하고 해당 카드의 데이터를 통해 DeletedResponse를 반환한다")
	void deleteCard() throws Exception {
		//given
		Long cardId = 3L;
		CardDeleteResponse deletedResponse = new CardDeleteResponse(3L, true);
		given(cardService.deleteCard(cardId)).willReturn(deletedResponse);

		//when then
		mockMvc.perform(delete("/cards/" + cardId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("cardId").value(3L))
			.andExpect(jsonPath("success").value(true))
			.andDo(print());
	}

	@BeforeEach
	public void beforeEach() {
		mockMvc = MockMvcBuilders.standaloneSetup(new CardRestController(cardService))
			.addFilter(new CharacterEncodingFilter("UTF-8", true))
			.alwaysExpect(status().isOk())
			.alwaysDo(print())
			.build();
		// mocking
		List<CardListResponse> cards = new ArrayList<>();
		List<CardSearchResponse> cardSearches = new ArrayList<>();
		cardSearches.add(CardSearchResponse.from(new Card(1L, "제목1", "내용1", 0, false, 1L)));
		cardSearches.add(CardSearchResponse.from(new Card(2L, "제목2", "내용2", 0, false, 1L)));
		cardSearches.add(CardSearchResponse.from(new Card(3L, "제목3", "내용3", 0, false, 1L)));
		cards.add(new CardListResponse(1L, "해야할 일", cardSearches));
		Mockito.when(cardService.getAllCard()).thenReturn(cards);
	}

	@Test
	@DisplayName("/cards를 요청할때 컬럼별 카드들을 조회한다")
	public void testCardList() throws Exception {
		// given
		String expectedColumnName = "$[%s].name";
		String expectedColumnId = "$[%s].columnId";
		String expectedCards = "$[%s].cards";
		// when
		mockMvc.perform(get("/cards"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath(expectedColumnName, 0).value(equalTo("해야할 일")))
			.andExpect(jsonPath(expectedColumnId, 0).value(equalTo(1)))
			.andExpect(jsonPath(expectedCards, 0).isArray());
	}

	@Test
	@DisplayName("카드이동 요청을 받아 입력받은 위치로 카드를 이동시킨 후 이동 시킨 카드의 데이터를 반환한다.")
	public void testMoveCard() throws Exception {
		// given
		CardMoveRequest cardMoveRequest = new CardMoveRequest(7L, 5L, 4L, 2L);
		CardMoveResponse cardMoveResponse = new CardMoveResponse(new CardResponseDTO(7L, "제목7", "내용7", 1536, 2L), true);
		given(cardService.moveCard(any())).willReturn(cardMoveResponse);
		// when then
		mockMvc.perform(put("/cards/move/7")
				.content(objectMapper.writeValueAsString(cardMoveRequest))
				.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].id").value(7))
			.andExpect(jsonPath("$[0].title").value("제목7"))
			.andExpect(jsonPath("$[0].content").value("내용7"))
			.andExpect(jsonPath("$[0].position").value(1536))
			.andExpect(jsonPath("$[0].columnId").value(2))
			.andExpect(jsonPath("success").value(true))
			.andDo(print());
	}
}

package codesquad.todo.card.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquad.todo.card.controller.dto.CardDeleteResponse;
import codesquad.todo.card.controller.dto.CardModifyDTO;
import codesquad.todo.card.controller.dto.CardModifyRequest;
import codesquad.todo.card.controller.dto.CardModifyResponse;
import codesquad.todo.card.controller.dto.CardSaveDTO;
import codesquad.todo.card.controller.dto.CardSaveRequest;
import codesquad.todo.card.controller.dto.CardSaveResponse;
import codesquad.todo.card.service.CardService;

@WebMvcTest(CardRestController.class)
class CardRestControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	private CardService cardService;

	@Test
	@DisplayName("새로운 카드를 등록하면 등록한 카드 데이터를 json으로 반환한다.")
	void saveCardTest() throws Exception {
		//given
		CardSaveRequest cardSaveRequest = new CardSaveRequest("new제목", "new내용", 1);
		CardSaveResponse cardSaveResponse = new CardSaveResponse(
			new CardSaveDTO(1L, "new제목", "new내용", 1024, 1L), true);
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
		CardModifyResponse cardModifyResponse = new CardModifyResponse(new CardModifyDTO(1L, "제목수정", "내용수정"), true);

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
}

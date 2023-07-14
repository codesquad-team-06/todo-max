package codesquad.todo.card.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import codesquad.todo.card.entity.Card;
import codesquad.todo.card.service.CardService;

@WebMvcTest(CardRestController.class)
class CardRestControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private CardService cardService;

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
}

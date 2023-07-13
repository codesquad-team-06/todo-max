package codesquad.todo.history.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import codesquad.todo.history.controller.dto.HistoryFindAllResponse;
import codesquad.todo.history.service.HistoryService;

@WebMvcTest(HistoryRestController.class)
class HistoryRestControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private HistoryService historyService;

	@Test
	@DisplayName("활동 기록 목록을 불러오고 Json 형태로 데이터를 반환한다.")
	public void shouldReturnAllHistories() throws Exception {
		//given
		HistoryFindAllResponse history1 = new HistoryFindAllResponse("Card Title 5", "3", "3", "5일 전", "수정");
		HistoryFindAllResponse history2 = new HistoryFindAllResponse("Card Title 4", "1", "2", "1주 전", "이동");
		List<HistoryFindAllResponse> histories = Arrays.asList(history1, history2);
		given(historyService.getAll()).willReturn(histories);

		//when & then
		mockMvc.perform(get("/histories"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()").value(histories.size()))
			.andExpect(jsonPath("$[*].cardTitle").value(
				hasItems(histories.stream().map(HistoryFindAllResponse::getCardTitle).toArray())))
			.andExpect(jsonPath("$[*].prevColumn").value(
				hasItems(histories.stream().map(HistoryFindAllResponse::getPrevColumn).toArray())))
			.andExpect(jsonPath("$[*].nextColumn").value(
				hasItems(histories.stream().map(HistoryFindAllResponse::getNextColumn).toArray())))
			.andExpect(jsonPath("$[*].elapsedTime").value(
				hasItems(histories.stream().map(HistoryFindAllResponse::getElapsedTime).toArray())))
			.andExpect(jsonPath("$[*].actionName").value(
				hasItems(histories.stream().map(HistoryFindAllResponse::getActionName).toArray())));
	}
}

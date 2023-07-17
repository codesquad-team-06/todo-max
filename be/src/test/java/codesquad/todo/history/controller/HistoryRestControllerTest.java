package codesquad.todo.history.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquad.todo.errors.errorcode.HistoryErrorCode;
import codesquad.todo.errors.exception.RestApiException;
import codesquad.todo.history.controller.dto.HistoryDeleteRequest;
import codesquad.todo.history.controller.dto.HistoryFindAllResponse;
import codesquad.todo.history.service.HistoryService;

@WebMvcTest(HistoryRestController.class)
class HistoryRestControllerTest {
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private HistoryService historyService;

	@Test
	@DisplayName("활동 기록 목록을 불러오고 Json 타입으로 데이터를 반환한다.")
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

	@Test
	@DisplayName("유효한 활동 기록 id를 전달 받으면 해당 기록의 is_deleted 값을 바꾸고 성공 메세지를 Json 타입으로 반환한다.")
	public void shouldDeleteHistories() throws Exception {
		//given
		List<Long> ids = new ArrayList<>(Arrays.asList(4L, 5L, 6L));
		HistoryDeleteRequest request = new HistoryDeleteRequest(ids);
		when(historyService.deleteByIds(ArgumentMatchers.any(HistoryDeleteRequest.class))).thenReturn(true);

		//when & then
		mockMvc.perform(delete("/histories")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success", is(true)));
	}

	// todo: 추후 예외 발생 테스트 코드 작성
	@Test
	@DisplayName("유효하지 않은 id를 전달 받으면 예외를 발생시키고 실패 메세지를 Json 타입으로 반환한다.")
	public void deleteHistoriesAndThrowException() throws Exception {
		//given
		List<Long> ids = new ArrayList<>(Arrays.asList(100L, 5L, 6L));
		HistoryDeleteRequest request = new HistoryDeleteRequest(ids);
		given(historyService.deleteByIds(ArgumentMatchers.any(HistoryDeleteRequest.class))).willThrow(
			new RestApiException(HistoryErrorCode.NOT_FOUND_HISTORY));

		//when & then
		mockMvc.perform(delete("/histories")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.success", is(false)))
			.andExpect(jsonPath("$.errorCode.status", is(404)))
			.andExpect(jsonPath("$.errorCode.code", is("Not Found")))
			.andExpect(jsonPath("$.errorCode.message", is("존재하지 않는 활동 기록입니다.")));
	}
}

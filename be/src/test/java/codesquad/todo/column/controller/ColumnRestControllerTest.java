package codesquad.todo.column.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquad.todo.column.service.ColumnService;

class ColumnRestControllerTest {

	@Nested
	@DisplayName("컬럼 추가")
	@WebMvcTest(ColumnRestController.class)
	class SaveColumnTest {

		private MockMvc mockMvc;

		@Autowired
		ObjectMapper objectMapper;

		@Autowired
		private ColumnRestController columnRestController;

		@MockBean
		private ColumnService columnService;

		@BeforeEach
		public void beforeEach() {
			mockMvc = MockMvcBuilders.standaloneSetup(columnRestController)
				.addFilter(new CharacterEncodingFilter("UTF-8", true))
				.alwaysDo(print())
				.build();
		}

		@Test
		@DisplayName("컬럼 저장 데이터가 주어지고 서비스에 저장을 요청할때 저장된 컬럼을 응답한다")
		public void saveColumn() throws Exception {
			// given
			ColumnSaveRequest columnSaveRequest = new ColumnSaveRequest("보류한 일");
			ColumnSaveDto columnSaveDto = new ColumnSaveDto(1L, "보류한 일");
			String body = objectMapper.writeValueAsString(columnSaveRequest);
			// mocking
			when(columnService.saveColumn(Mockito.any(ColumnSaveRequest.class))).thenReturn(columnSaveDto);
			// when
			mockMvc.perform(post("/column")
					.contentType(APPLICATION_JSON)
					.content(body)
					.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("column.id").value(equalTo(1)))
				.andExpect(jsonPath("column.name").value(equalTo("보류한 일")))
				.andExpect(jsonPath("success").value(equalTo(true)));
		}

		@Test
		@DisplayName("빈 제목으로 컬럼 추가를 요청할때 에러 코드를 응답합니다")
		public void saveColumn_whenSave_thenInvalidEmpty() throws Exception {
			// given
			ColumnSaveRequest columnSaveRequest = new ColumnSaveRequest("");
			ColumnSaveDto columnSaveDto = new ColumnSaveDto(1L, "");
			String body = objectMapper.writeValueAsString(columnSaveRequest);
			// mocking
			when(columnService.saveColumn(Mockito.any(ColumnSaveRequest.class))).thenReturn(columnSaveDto);
			// when
			mockMvc.perform(post("/column")
					.contentType(APPLICATION_JSON)
					.content(body)
					.accept(APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("errorCode.status").value(equalTo(400)))
				.andExpect(jsonPath("errorCode.code").value(equalTo("Bad Request")))
				.andExpect(jsonPath("errorCode.message").value(equalTo("컬럼의 제목은 공백이면 안됩니다.")))
				.andExpect(jsonPath("success").value(equalTo(false)));
		}

		@Test
		@DisplayName("100글자가 넘는 제목이 주어지고 컬럼 저장을 요청할때 에러 코드를 응답합니다")
		public void saveColumn_whenSave_thenInvalidLength() throws Exception {
			// given
			String[] name = new String[101];
			Arrays.fill(name, "a");
			String joinName = String.join("", name);
			ColumnSaveRequest columnSaveRequest = new ColumnSaveRequest(joinName);
			ColumnSaveDto columnSaveDto = new ColumnSaveDto(1L, "");
			String body = objectMapper.writeValueAsString(columnSaveRequest);
			// mocking
			when(columnService.saveColumn(Mockito.any(ColumnSaveRequest.class))).thenReturn(columnSaveDto);
			// when
			mockMvc.perform(post("/column")
					.contentType(APPLICATION_JSON)
					.content(body)
					.accept(APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("errorCode.status").value(equalTo(400)))
				.andExpect(jsonPath("errorCode.code").value(equalTo("Bad Request")))
				.andExpect(jsonPath("errorCode.message").value(equalTo("컬럼의 제목은 최대 100글자 이내여야 합니다.")))
				.andExpect(jsonPath("success").value(equalTo(false)));
		}
	}

	@Nested
	@DisplayName("컬럼 추가")
	@WebMvcTest(ColumnRestController.class)
	class DeleteColumnTest {
		private MockMvc mockMvc;

		@Autowired
		private ColumnRestController columnRestController;

		@MockBean
		private ColumnService columnService;

		@BeforeEach
		public void beforeEach() {
			mockMvc = MockMvcBuilders.standaloneSetup(columnRestController)
				.addFilter(new CharacterEncodingFilter("UTF-8", true))
				.alwaysDo(print())
				.build();
		}

		@Test
		@DisplayName("컬럼 아이디번호가 주어지고 컬럼 삭제를 요청할때 컬럼을 삭제하고 응답합니다")
		public void testDeleteColumn_givenColumnId_whenRequestDeleteColumn_thenDeleteColumn() throws Exception {
			// given
			String columnId = "1";
			ColumnSaveDto columnSaveDto = new ColumnSaveDto(1L, "해야할 일");
			// mocking
			when(columnService.deleteColumn(any())).thenReturn(columnSaveDto);
			// when
			mockMvc.perform(delete("/column/" + columnId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("column.id").value(equalTo(1)))
				.andExpect(jsonPath("column.name").value(equalTo("해야할 일")))
				.andExpect(jsonPath("success").value(equalTo(true)));
			// then
		}
	}

}

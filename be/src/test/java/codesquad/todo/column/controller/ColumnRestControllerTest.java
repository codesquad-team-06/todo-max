package codesquad.todo.column.controller;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquad.todo.column.service.ColumnService;
import codesquad.todo.errors.errorcode.ColumnErrorCode;
import codesquad.todo.errors.exception.RestApiException;
import codesquad.todo.errors.handler.GlobalExceptionHandler;

@WebMvcTest(controllers = {ColumnRestController.class})
@Import(GlobalExceptionHandler.class)
class ColumnRestControllerTest {

	@Autowired
	ObjectMapper objectMapper;

	private MockMvc mockMvc;

	@Autowired
	private ColumnRestController columnRestController;

	@MockBean
	private ColumnService columnService;

	@BeforeEach
	public void beforeEach() {
		mockMvc = MockMvcBuilders.standaloneSetup(columnRestController)
			.setControllerAdvice(GlobalExceptionHandler.class)
			.addFilter(new CharacterEncodingFilter("UTF-8", true))
			.alwaysDo(print())
			.build();
	}

	@Nested
	@DisplayName("컬럼 추가")
	class SaveColumnTest {

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
				.andExpect(jsonPath("column.id").value(Matchers.equalTo(1)))
				.andExpect(jsonPath("column.name").value(Matchers.equalTo("보류한 일")))
				.andExpect(jsonPath("success").value(Matchers.equalTo(true)));
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
				.andExpect(jsonPath("errorCode.status").value(Matchers.equalTo(400)))
				.andExpect(jsonPath("errorCode.code").value(Matchers.equalTo("Bad Request")))
				.andExpect(jsonPath("errorCode.message").value(Matchers.equalTo("컬럼의 제목은 공백이면 안됩니다.")))
				.andExpect(jsonPath("success").value(Matchers.equalTo(false)));
		}

		@Test
		@DisplayName("제목이 null로 주어지고 컬럼 추가를 요청할때 에러 코드를 응답합니다")
		public void saveColumn_givenNameIsNull_whenSave_thenInvalidEmpty() throws Exception {
			// given
			ColumnSaveRequest columnSaveRequest = new ColumnSaveRequest(null);
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
				.andExpect(jsonPath("errorCode.status").value(Matchers.equalTo(400)))
				.andExpect(jsonPath("errorCode.code").value(Matchers.equalTo("Bad Request")))
				.andExpect(jsonPath("errorCode.message").value(Matchers.equalTo("컬럼의 제목은 공백이면 안됩니다.")))
				.andExpect(jsonPath("success").value(Matchers.equalTo(false)));
		}

		@Test
		@DisplayName("100글자가 넘는 제목이 주어지고 컬럼 저장을 요청할때 에러 코드를 응답합니다")
		public void saveColumn_whenSave_thenInvalidLength() throws Exception {
			// given
			String[] name = new String[101];
			Arrays.fill(name, "a");
			String joinName = String.join("", name);
			ColumnSaveRequest columnSaveRequest = new ColumnSaveRequest(joinName);
			ColumnSaveDto columnSaveDto = new ColumnSaveDto(1L, joinName);
			String body = objectMapper.writeValueAsString(columnSaveRequest);
			// mocking
			when(columnService.saveColumn(Mockito.any(ColumnSaveRequest.class))).thenReturn(columnSaveDto);
			// when
			mockMvc.perform(post("/column")
					.contentType(APPLICATION_JSON)
					.content(body)
					.accept(APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(print())
				.andExpect(jsonPath("errorCode.status").value(Matchers.equalTo(400)))
				.andExpect(jsonPath("errorCode.code").value(Matchers.equalTo("Bad Request")))
				.andExpect(jsonPath("errorCode.message").value(Matchers.equalTo("컬럼의 제목은 최대 100글자 이내여야 합니다.")))
				.andExpect(jsonPath("success").value(Matchers.equalTo(false)));
		}
	}

	@Nested
	@DisplayName("컬럼 삭제")
	class DeleteColumnTest {

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
				.andExpect(jsonPath("column.id").value(Matchers.equalTo(1)))
				.andExpect(jsonPath("column.name").value(Matchers.equalTo("해야할 일")))
				.andExpect(jsonPath("success").value(Matchers.equalTo(true)));
		}

		@Test
		@DisplayName("유효하지 않은 컬럼아이디가 주어지고 컬럼 삭제요청을 할때 에러 코드를 응답받는다")
		public void testDeleteColumn_givenInvalidColumnId_whenRequestDeleteColumn_thenResponseErrorCode() throws
			Exception {
			// given
			String columnId = "9999";
			// mocking
			when(columnService.deleteColumn(Long.valueOf(columnId))).thenThrow(
				new RestApiException(ColumnErrorCode.NOT_FOUND_COLUMN));
			// when
			mockMvc.perform(delete("/column/" + columnId))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("success").value(Matchers.equalTo(false)))
				.andExpect(jsonPath("errorCode.status").value(Matchers.equalTo(404)))
				.andExpect(jsonPath("errorCode.code").value(Matchers.equalTo("Not Found")))
				.andExpect(jsonPath("errorCode.message").value(Matchers.equalTo("존재하지 않는 컬럼입니다.")));
		}
	}

	@Nested
	@DisplayName("컬럼 수정")
	class ModifyColumnTest {

		@Test
		@DisplayName("수정할 컬럼이 주어지고 컬럼 수정을 요청할때 수정된 컬럼을 응답합니다")
		public void testModifyColumn() throws Exception {
			// given
			Long columnId = 1L;
			ColumnModifyRequest modifyRequest = new ColumnModifyRequest(columnId, "수정된 제목");
			ColumnSaveDto columnSaveDto = new ColumnSaveDto(columnId, "수정된 제목");
			String body = objectMapper.writeValueAsString(modifyRequest);
			// mocking
			when(columnService.modifyColumn(any())).thenReturn(columnSaveDto);
			// when
			mockMvc.perform(put("/column/" + columnId)
					.content(body)
					.contentType(APPLICATION_JSON)
					.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("success").value(Matchers.equalTo(true)))
				.andExpect(jsonPath("column.id").value(Matchers.equalTo(1)))
				.andExpect(jsonPath("column.name").value(Matchers.equalTo("수정된 제목")));
		}

		@Test
		@DisplayName("빈 이름이 주어지고 컬럼 수정 요청시 에러 코드를 응답합니다.")
		public void testModifyColumn_givenEmptyName_whenModifyColumn_thenResponseErrorCode() throws
			Exception {
			// given
			Long columnId = 1L;
			ColumnModifyRequest modifyRequest = new ColumnModifyRequest(columnId, "");
			ColumnSaveDto columnSaveDto = new ColumnSaveDto(columnId, "수정된 제목");
			String body = objectMapper.writeValueAsString(modifyRequest);
			// mocking
			when(columnService.modifyColumn(any())).thenReturn(columnSaveDto);
			// when
			mockMvc.perform(put("/column/" + columnId)
					.content(body)
					.contentType(APPLICATION_JSON)
					.accept(APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("success").value(Matchers.equalTo(false)))
				.andExpect(jsonPath("errorCode.status").value(Matchers.equalTo(400)))
				.andExpect(jsonPath("errorCode.code").value(Matchers.equalTo("Bad Request")))
				.andExpect(jsonPath("errorCode.message").value(Matchers.equalTo("컬럼의 제목은 공백이면 안됩니다.")));
		}

		@Test
		@DisplayName("null 이름이 주어지고 컬럼 수정 요청시 에러 코드를 응답합니다.")
		public void testModifyColumn_givenNullName_whenModifyColumn_thenResponseErrorCode() throws
			Exception {
			// given
			Long columnId = 1L;
			ColumnModifyRequest modifyRequest = new ColumnModifyRequest(columnId, null);
			ColumnSaveDto columnSaveDto = new ColumnSaveDto(columnId, "수정된 제목");
			String body = objectMapper.writeValueAsString(modifyRequest);
			// mocking
			when(columnService.modifyColumn(any())).thenReturn(columnSaveDto);
			// when
			mockMvc.perform(put("/column/" + columnId)
					.content(body)
					.contentType(APPLICATION_JSON)
					.accept(APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("success").value(Matchers.equalTo(false)))
				.andExpect(jsonPath("errorCode.status").value(Matchers.equalTo(400)))
				.andExpect(jsonPath("errorCode.code").value(Matchers.equalTo("Bad Request")))
				.andExpect(jsonPath("errorCode.message").value(Matchers.equalTo("컬럼의 제목은 공백이면 안됩니다.")));
		}

		@Test
		@DisplayName("100글자 초과 이름이 주어지고 컬럼 수정 요청시 에러 코드를 응답합니다.")
		public void testModifyColumn_givenLongName_whenModifyColumn_thenResponseErrorCode() throws
			Exception {
			// given
			Long columnId = 1L;
			String[] name = new String[101];
			Arrays.fill(name, "a");
			String joinName = String.join("", name);
			ColumnModifyRequest modifyRequest = new ColumnModifyRequest(columnId, joinName);
			ColumnSaveDto columnSaveDto = new ColumnSaveDto(columnId, "수정된 제목");
			String body = objectMapper.writeValueAsString(modifyRequest);
			// mocking
			when(columnService.modifyColumn(any())).thenReturn(columnSaveDto);
			// when
			mockMvc.perform(put("/column/" + columnId)
					.content(body)
					.contentType(APPLICATION_JSON)
					.accept(APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("success").value(Matchers.equalTo(false)))
				.andExpect(jsonPath("errorCode.status").value(Matchers.equalTo(400)))
				.andExpect(jsonPath("errorCode.code").value(Matchers.equalTo("Bad Request")))
				.andExpect(jsonPath("errorCode.message").value(Matchers.equalTo("컬럼의 제목은 최대 100글자 이내여야 합니다.")));
		}

		@Test
		@DisplayName("존재하지 않는 컬럼 아이디가 주어지고 컬럼 수정 요청시 에러 코드를 응답합니다.")
		public void testModifyColumn_givenNotExistColumnId_whenModifyColumn_thenResponseErrorCode() throws
			Exception {
			// given
			Long columnId = 9999L;
			ColumnModifyRequest modifyRequest = new ColumnModifyRequest(columnId, "수정된 제목");
			String body = objectMapper.writeValueAsString(modifyRequest);
			// mocking
			when(columnService.modifyColumn(any())).thenThrow(new RestApiException(ColumnErrorCode.NOT_FOUND_COLUMN));
			// when
			mockMvc.perform(put("/column/" + columnId)
					.content(body)
					.contentType(APPLICATION_JSON)
					.accept(APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("success").value(Matchers.equalTo(false)))
				.andExpect(jsonPath("errorCode.status").value(Matchers.equalTo(404)))
				.andExpect(jsonPath("errorCode.code").value(Matchers.equalTo("Not Found")))
				.andExpect(jsonPath("errorCode.message").value(Matchers.equalTo("존재하지 않는 컬럼입니다.")));
		}
	}

}

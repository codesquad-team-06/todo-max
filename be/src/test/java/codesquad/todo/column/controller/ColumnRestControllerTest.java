package codesquad.todo.column.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

import codesquad.todo.column.service.ColumnService;

@WebMvcTest(ColumnRestController.class)
class ColumnRestControllerTest {

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
			.alwaysExpect(status().isOk())
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
				.contentType(MediaType.APPLICATION_JSON)
				.content(body)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("column.id").value(equalTo(1)))
			.andExpect(jsonPath("column.name").value(equalTo("보류한 일")))
			.andExpect(jsonPath("success").value(equalTo(true)));
	}

}

package codesquad.todo.column.service;

import static org.mockito.ArgumentMatchers.*;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import codesquad.todo.column.controller.ColumnSaveDto;
import codesquad.todo.column.controller.ColumnSaveRequest;
import codesquad.todo.column.entity.Column;
import codesquad.todo.column.repository.ColumnRepository;

@ExtendWith(MockitoExtension.class)
class ColumnServiceTest {

	@InjectMocks
	private ColumnService columnService;

	@Mock
	private ColumnRepository columnRepository;

	@Test
	@DisplayName("컬럼 이름이 주어지고 저장요청시 컬럼이 저장되고 저장된 컬럼 정보를 반환한다")
	public void testSaveColumn_givenName_thenSaveTheColumn() {
		// given
		ColumnSaveRequest columnSaveRequest = new ColumnSaveRequest("보류한 일");
		// mocking
		Mockito.when(columnRepository.save(any(Column.class))).thenReturn(new Column(1L, "보류한 일"));
		// when
		ColumnSaveDto response = columnService.saveColumn(columnSaveRequest);
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(response.getId()).isGreaterThan(0L);
			softAssertions.assertThat(response.getName()).isEqualTo("보류한 일");
			softAssertions.assertAll();
		});
	}
}

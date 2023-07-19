package codesquad.todo.column.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

import codesquad.todo.column.entity.Column;

// Repository 애노테이션이 붙은 클래스만 빈으로 등록
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
// Replace.NONE으로 설정하면 @ActiveProfiles에 설정한 프로파일 환경값에 따라 데이터소스가 적용된다.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcColumnRepositoryTest {
	@Autowired
	private ColumnRepository columnRepository;

	@Test
	@DisplayName("모든 컬럼 데이터를 요청한다")
	public void testFindAll() {
		// given

		// when
		List<Column> columns = columnRepository.findAll();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(columns.size()).isEqualTo(3);
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("특정 컬럼 아이디에 따른 컬럼 이름들을 요청합니다.")
	public void testFindAllNameById() {
		// given
		Column saveColumn1 = columnRepository.save(Column.builder().name("보류한 일").build());
		Column saveColumn2 = columnRepository.save(Column.builder().name("보류한 일2").build());
		List<Long> ids = new ArrayList<>(Arrays.asList(saveColumn1.getId(), saveColumn2.getId()));
		// when
		List<String> names = columnRepository.findAllNameById(ids);
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(names).contains("보류한 일", "보류한 일2");
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("제목 데이터가 주어지고 컬럼 저장을 요청할때 저장된다")
	public void testSave() {
		// given
		String name = "보류중인 일";
		Column column = Column.builder()
			.name(name)
			.build();
		// when
		Column saveColumn = columnRepository.save(column);
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(saveColumn.getId()).isGreaterThan(0L);
			softAssertions.assertThat(saveColumn.getName()).isEqualTo(name);
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("컬럼 아이디번호가 주어지고 컬럼 삭제 요청시 컬럼이 삭제됩니다.")
	public void testDeleteById() {
		// given
		Column saveColumn = columnRepository.save(Column.builder().name("보류한 일").isDeleted(false).build());
		// when
		Column delColumn = columnRepository.deleteById(saveColumn.getId());
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(saveColumn.getId()).isEqualTo(delColumn.getId());
			softAssertions.assertThat(saveColumn.getName()).isEqualTo(delColumn.getName());
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("수정된 컬럼이 주어지고 컬럼 수정 요청시 컬럼이 수정됩니다.")
	public void testModifyColumn() {
		// given
		Column saveColumn = columnRepository.save(Column.builder().name("기존 제목").isDeleted(false).build());
		Column column = Column.builder().id(saveColumn.getId()).name("수정된 제목").build();
		// when
		Column modifiedColumn = columnRepository.modify(column);
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(modifiedColumn.getId()).isEqualTo(saveColumn.getId());
			softAssertions.assertThat(modifiedColumn.getName()).isEqualTo("수정된 제목");
			softAssertions.assertAll();
		});
	}
}

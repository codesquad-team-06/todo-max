package codesquad.todo.history.repository;

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

import codesquad.todo.history.entity.History;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
	classes = Repository.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcHistoryRepositoryTest {
	@Autowired
	private HistoryRepository historyRepository;

	@Test
	@DisplayName("is_deleted = false인 모든 활동 기록을 불러온다.")
	public void testFindAll() {
		//given

		//when
		List<History> historyList = historyRepository.findAll();

		//then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(historyList.size()).isEqualTo(7);
		});
	}
}

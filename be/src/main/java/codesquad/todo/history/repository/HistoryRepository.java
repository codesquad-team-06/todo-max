package codesquad.todo.history.repository;

import java.util.List;

import codesquad.todo.history.entity.History;

public interface HistoryRepository {

	List<History> findAll();

	History save(History history);

	int deleteByIds(List<Long> ids);

	int countIds(List<Long> ids);

	History findById(Long id);
}

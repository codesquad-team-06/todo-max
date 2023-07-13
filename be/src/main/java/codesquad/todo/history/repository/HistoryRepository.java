package codesquad.todo.history.repository;

import java.util.List;
import java.util.Optional;

import codesquad.todo.history.entity.History;

public interface HistoryRepository {

	List<History> findAll();

	History save(History history);

	History modify(History history);

	int deleteByIds(List<Long> ids);

	int countByIds(List<Long> ids);

	Optional<History> findById(Long id);
}

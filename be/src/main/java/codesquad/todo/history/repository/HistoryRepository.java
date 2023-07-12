package codesquad.todo.history.repository;

import java.util.List;
import java.util.Optional;

import codesquad.todo.history.entity.History;

public interface HistoryRepository {

	List<History> findAll();

	History save(History history);

	History modify(History history);

	History deleteById(Long id);

	Optional<History> findById(Long id);
}

package codesquad.todo.history.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import codesquad.todo.history.entity.History;

@Repository
public class JdbcHistoryRepository implements HistoryRepository {

	@Override
	public List<History> findAll() {
		return null;
	}

	@Override
	public History save(History history) {
		return null;
	}

	@Override
	public History modify(History history) {
		return null;
	}

	@Override
	public History deleteById(Long id) {
		return null;
	}

	@Override
	public Optional<History> findById(Long id) {
		return Optional.empty();
	}
}

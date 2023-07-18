package codesquad.todo.column.repository;

import java.util.List;
import java.util.Optional;

import codesquad.todo.column.entity.Column;

public interface ColumnRepository {
	List<Column> findAll();

	Column save(Column column);

	Column modify(Column column);

	Column deleteById(Long id);

	Optional<Column> findById(Long id);

	List<String> findAllNameById(List<Long> ids);
}

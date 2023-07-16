package codesquad.todo.column.service;

import org.springframework.stereotype.Service;

import codesquad.todo.column.repository.ColumnRepository;

@Service
public class ColumnService {

	private ColumnRepository columnRepository;

	public ColumnService(ColumnRepository columnRepository) {
		this.columnRepository = columnRepository;
	}

}

package codesquad.todo.column.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.todo.column.controller.ColumnSaveDto;
import codesquad.todo.column.controller.ColumnSaveRequest;
import codesquad.todo.column.entity.Column;
import codesquad.todo.column.repository.ColumnRepository;

@Service
public class ColumnService {

	private final ColumnRepository columnRepository;

	public ColumnService(ColumnRepository columnRepository) {
		this.columnRepository = columnRepository;
	}

	@Transactional
	public ColumnSaveDto saveColumn(ColumnSaveRequest columnSaveRequest) {
		Column saveColumn = columnRepository.save(columnSaveRequest.toEntity());
		return new ColumnSaveDto(saveColumn);
	}

	@Transactional
	public ColumnSaveDto deleteColumn(Long columnId) {
		Column delColumn = columnRepository.deleteById(columnId);
		return new ColumnSaveDto(delColumn);
	}
}

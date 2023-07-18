package codesquad.todo.column.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.todo.column.controller.ColumnModifyRequest;
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

	public List<String> findColumnNames(List<Long> ids) {
		return columnRepository.findAllNameById(ids);
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

	@Transactional
	public ColumnSaveDto modifyColumn(ColumnModifyRequest request) {
		Column modifiedColumn = columnRepository.modify(request.toEntity());
		return new ColumnSaveDto(modifiedColumn);
	}

	public boolean existColumnById(Long columnId) {
		return columnRepository.findById(columnId).isPresent();
	}
}

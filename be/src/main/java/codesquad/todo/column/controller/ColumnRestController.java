package codesquad.todo.column.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.todo.column.service.ColumnService;
import codesquad.todo.errors.errorcode.ColumnErrorCode;
import codesquad.todo.errors.exception.RestApiException;

@RestController
@RequestMapping("/column")
public class ColumnRestController {

	private final ColumnService columnService;

	public ColumnRestController(ColumnService columnService) {
		this.columnService = columnService;
	}

	@PostMapping
	public ColumnResponseDTO saveColumn(@Valid @RequestBody final ColumnSaveRequest request) {
		ColumnSaveDTO columnSaveDto = columnService.saveColumn(request);
		return new ColumnResponseDTO(columnSaveDto, true);
	}

	@DeleteMapping(path = "/{columnId}")
	public ColumnResponseDTO deleteColumn(@PathVariable final Long columnId) {
		if (!columnService.existColumnById(columnId)) {
			throw new RestApiException(ColumnErrorCode.NOT_FOUND_COLUMN);
		}
		ColumnSaveDTO columnSaveDto = columnService.deleteColumn(columnId);
		return new ColumnResponseDTO(columnSaveDto, true);
	}

	@PutMapping(path = "/{columnId}")
	public ColumnResponseDTO modifyColumn(@PathVariable final Long columnId,
		@Valid @RequestBody final ColumnModifyRequest request) {
		if (!columnService.existColumnById(columnId)) {
			throw new RestApiException(ColumnErrorCode.NOT_FOUND_COLUMN);
		}
		ColumnSaveDTO columnSaveDto = columnService.modifyColumn(request);
		return new ColumnResponseDTO(columnSaveDto, true);
	}
}

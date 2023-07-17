package codesquad.todo.column.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.todo.column.service.ColumnService;

@RestController
@RequestMapping("/column")
public class ColumnRestController {

	private final ColumnService columnService;

	public ColumnRestController(ColumnService columnService) {
		this.columnService = columnService;
	}

	@PostMapping
	public ColumnSaveResponse saveColumn(@Valid @RequestBody ColumnSaveRequest request) {
		ColumnSaveDto columnSaveDto = columnService.saveColumn(request);
		return new ColumnSaveResponse(columnSaveDto, true);
	}
}

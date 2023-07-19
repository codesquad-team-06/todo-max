package codesquad.todo.column.controller;

import org.springframework.web.bind.annotation.RestController;

import codesquad.todo.column.service.ColumnService;

@RestController
public class ColumnRestController {

	private final ColumnService columnService;

	public ColumnRestController(ColumnService columnService) {
		this.columnService = columnService;
	}
}

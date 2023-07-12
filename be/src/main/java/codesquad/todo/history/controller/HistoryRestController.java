package codesquad.todo.history.controller;

import org.springframework.web.bind.annotation.RestController;

import codesquad.todo.history.service.HistoryService;

@RestController
public class HistoryRestController {

	private final HistoryService historyService;

	public HistoryRestController(HistoryService historyService) {
		this.historyService = historyService;
	}
}

package codesquad.todo.history.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.todo.history.controller.dto.HistoryDeleteRequest;
import codesquad.todo.history.controller.dto.HistoryFindAllResponse;
import codesquad.todo.history.service.HistoryService;

@RestController
@RequestMapping("/histories")
public class HistoryRestController {
	private final HistoryService historyService;

	public HistoryRestController(HistoryService historyService) {
		this.historyService = historyService;
	}

	@GetMapping
	public List<HistoryFindAllResponse> getList() {
		return historyService.getAll();
	}

	@DeleteMapping
	public ResponseEntity<?> deleteList(@RequestBody HistoryDeleteRequest request) {
		historyService.deleteByIds(request);
		return ResponseEntity.ok(Collections.singletonMap("success", true));
	}
}

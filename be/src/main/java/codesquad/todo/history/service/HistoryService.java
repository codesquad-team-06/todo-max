package codesquad.todo.history.service;

import org.springframework.stereotype.Service;

import codesquad.todo.history.repository.HistoryRepository;

@Service
public class HistoryService {

	private final HistoryRepository historyRepository;

	public HistoryService(HistoryRepository historyRepository) {
		this.historyRepository = historyRepository;
	}
}

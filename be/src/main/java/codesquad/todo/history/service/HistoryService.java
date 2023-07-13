package codesquad.todo.history.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.todo.history.controller.dto.HistoryDeleteRequest;
import codesquad.todo.history.controller.dto.HistoryFindAllResponse;
import codesquad.todo.history.repository.HistoryRepository;


@Service
public class HistoryService {

	private final HistoryRepository historyRepository;

	public HistoryService(HistoryRepository historyRepository) {
		this.historyRepository = historyRepository;
	}

	@Transactional(readOnly = true)
	public List<HistoryFindAllResponse> getAll() {
		return historyRepository.findAll()
			.stream()
			.map(HistoryFindAllResponse::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public boolean deleteByIds(HistoryDeleteRequest request) {
		validate(request);
		int deletedRows = historyRepository.deleteByIds(request.getHistoryId());
		return deletedRows > 0;
	}

	// todo: 추후 Custom Exception으로 변경
	public void validate(HistoryDeleteRequest request) {
		int count = historyRepository.countIds(request.getHistoryId());
		if (count != request.getHistoryId().size()) {
			throw new RuntimeException("유효하지 않은 히스토리입니다.");
		}
	}
}

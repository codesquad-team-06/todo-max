package codesquad.todo.history.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public boolean deleteAndFetch(List<Long> ids) {
		validate(ids);
		int deletedRows = historyRepository.deleteByIds(ids);
		return deletedRows > 0;
	}

	//todo : 예외처리 ENUM
	public void validate(List<Long> ids) {
		int count = historyRepository.countByIds(ids);
		if (count != ids.size()) {
			throw new RuntimeException("유효하지 않은 히스토리입니다.");
		}
	}
}

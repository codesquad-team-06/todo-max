package codesquad.todo.history.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.todo.errors.errorcode.HistoryErrorCode;
import codesquad.todo.errors.exception.RestApiException;
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
		if (validate(request)) {
			throw new RestApiException(HistoryErrorCode.NOT_FOUND_HISTORY);
		}
		int deletedRows = historyRepository.deleteByIds(request.getHistoryId());
		return deletedRows > 0;
	}

	@Transactional(readOnly = true)
	public boolean validate(HistoryDeleteRequest request) {
		int count = historyRepository.countIds(request.getHistoryId());
		return count == request.getHistoryId().size();
	}
}

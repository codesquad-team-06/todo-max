package codesquad.todo.history.controller.dto;

import java.util.List;

public class HistoryDeleteRequest {
	private List<Long> historyId;
	
	public HistoryDeleteRequest() {
	}

	public HistoryDeleteRequest(List<Long> historyId) {
		this.historyId = historyId;
	}

	public List<Long> getHistoryId() {
		return historyId;
	}
}

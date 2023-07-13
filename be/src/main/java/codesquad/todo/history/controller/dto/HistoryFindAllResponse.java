package codesquad.todo.history.controller.dto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import codesquad.todo.history.entity.History;

public class HistoryFindAllResponse {
	private final String cardTitle;
	private final String prevColumn;
	private final String nextColumn;
	private final String elapsedTime;
	private final String actionName;

	public HistoryFindAllResponse(String cardTitle, String prevColumn, String nextColumn, String elapsedTime,
		String actionName) {
		this.cardTitle = cardTitle;
		this.prevColumn = prevColumn;
		this.nextColumn = nextColumn;
		this.elapsedTime = elapsedTime;
		this.actionName = actionName;
	}

	public static HistoryFindAllResponse from(History history) {
		return new HistoryFindAllResponse(
			history.getCardTitle(),
			history.getPrevColumn(),
			history.getNextColumn(),
			calculateElapsedTime(history.getCreatedAt()),
			history.getAction().getName());
	}

	public static String calculateElapsedTime(LocalDateTime start) {
		LocalDateTime now = LocalDateTime.now();

		long minDiff = ChronoUnit.MINUTES.between(start, now);
		if (minDiff < 60) {
			return minDiff + "분 전";
		}

		long hoursDiff = ChronoUnit.HOURS.between(start, now);
		if (hoursDiff < 24) {
			return hoursDiff + "시간 전";
		}

		long daysDiff = ChronoUnit.DAYS.between(start, now);
		if (daysDiff < 7) {
			return daysDiff + "일 전";
		}

		long weeksDiff = daysDiff / 7;
		return weeksDiff + "주 전";
	}

	public String getCardTitle() {
		return cardTitle;
	}

	public String getPrevColumn() {
		return prevColumn;
	}

	public String getNextColumn() {
		return nextColumn;
	}

	public String getElapsedTime() {
		return elapsedTime;
	}

	public String getActionName() {
		return actionName;
	}
}

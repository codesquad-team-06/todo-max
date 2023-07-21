package codesquad.todo.card.controller;

import java.util.List;

public class CardListResponse {

	private List<CardSearchResponse> cards;
	private Long columnId; // 컬럼의 아이디 번호
	private String name; // 컬럼의 제목

	public CardListResponse(Long columnId, String name, List<CardSearchResponse> cards) {
		this.columnId = columnId;
		this.name = name;
		this.cards = cards;
	}

	public Long getColumnId() {
		return columnId;
	}

	public String getName() {
		return name;
	}

	public List<CardSearchResponse> getCards() {
		return cards;
	}
}

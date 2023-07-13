package codesquad.todo.card.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.todo.card.service.CardService;

@RestController
public class CardRestController {
	private final CardService cardService;

	public CardRestController(CardService cardService) {
		this.cardService = cardService;
	}

	@GetMapping("/cards")
	public List<CardListResponse> list() {
		return cardService.getAllCard();
	}
}

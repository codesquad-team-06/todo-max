package codesquad.todo.card.controller;

import org.springframework.web.bind.annotation.RestController;

import codesquad.todo.card.service.CardService;

@RestController
public class CardRestController {
	private final CardService cardService;

	public CardRestController(CardService cardService) {
		this.cardService = cardService;
	}
}

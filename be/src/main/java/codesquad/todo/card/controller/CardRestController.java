package codesquad.todo.card.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.todo.card.controller.dto.CardDeleteResponse;
import codesquad.todo.card.controller.dto.CardModifyRequest;
import codesquad.todo.card.controller.dto.CardModifyResponse;
import codesquad.todo.card.controller.dto.CardMoveRequest;
import codesquad.todo.card.controller.dto.CardMoveResponse;
import codesquad.todo.card.controller.dto.CardSaveRequest;
import codesquad.todo.card.controller.dto.CardSaveResponse;
import codesquad.todo.card.service.CardService;

@RestController
@RequestMapping("/cards")
public class CardRestController {
	private final CardService cardService;

	public CardRestController(CardService cardService) {
		this.cardService = cardService;
	}

	@PostMapping
	public CardSaveResponse saveCard(@RequestBody CardSaveRequest cardSaveRequest) {
		return cardService.saveCard(cardSaveRequest);
	}

	@PutMapping
	public CardModifyResponse modifyCard(@RequestBody CardModifyRequest cardModifyRequest) {
		return cardService.modifyCard(cardModifyRequest);
	}

	@DeleteMapping("/{cardId}")
	public CardDeleteResponse deleteCard(@PathVariable Long cardId) {
		return cardService.deleteCard(cardId);
	}

	@GetMapping
	public List<CardListResponse> list() {
		return cardService.getAllCard();
	}

	@PutMapping("/move/{cardId}")
	public CardMoveResponse moveCard(@RequestBody CardMoveRequest cardMoveRequest){
		return cardService.moveCard(cardMoveRequest);
	}
}

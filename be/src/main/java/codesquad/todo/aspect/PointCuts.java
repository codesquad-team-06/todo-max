package codesquad.todo.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {
	@Pointcut("execution(public * codesquad.todo.card.service.CardService.moveCard(..))")
	public void moveAction() {

	}

	@Pointcut("execution(public * codesquad.todo.card.service.CardService.saveCard(..)) || "
		+ "execution(public * codesquad.todo.card.service.CardService.deleteCard(..)) || "
		+ "execution(public * codesquad.todo.card.service.CardService.modifyCard(..))")
	public void otherActions() {

	}
}

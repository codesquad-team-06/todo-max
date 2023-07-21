package codesquad.todo.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {

	@Pointcut("execution(public * codesquad.todo.card.service.CardService.saveCard(..))")
	public void saveAction() {

	}

	@Pointcut("execution(public * codesquad.todo.card.service.CardService.deleteCard(..))")
	public void deleteAction() {

	}

	@Pointcut("execution(public * codesquad.todo.card.service.CardService.modifyCard(..))")
	public void modifyAction() {

	}

	@Pointcut("execution(public * codesquad.todo.card.service.CardService.moveCard(..))")
	public void moveAction() {

	}
}

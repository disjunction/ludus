package com.pluseq.quizzer;

public interface QuizInterface {
	public ValueInterface getDescription();
	public ValueInterface[] getVariants();
	public void answer(ValueInterface answer);
}

package com.pluseq.quizzer;

public class EvaluatorEmbedded implements Evaluator {

	@Override
	public boolean isCorrect(QuizInterface quiz, ValueInterface answer) {
		return ((QuizEmbeddedAnswer)quiz).getCorrectAnswer().equals(answer.toString());
	}

}

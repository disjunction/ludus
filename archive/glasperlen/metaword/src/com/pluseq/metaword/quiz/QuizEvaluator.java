package com.pluseq.metaword.quiz;
import com.pluseq.metaword.core.Word;

public interface QuizEvaluator {
	boolean isCorrect(QuizInterface quiz, Word answer);
}

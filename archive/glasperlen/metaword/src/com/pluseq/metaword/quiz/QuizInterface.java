package com.pluseq.metaword.quiz;
import com.pluseq.metaword.core.Word;

public interface QuizInterface {
	Word getAnswerWord();
	String getId();
	Word[] getWordVariants();
	Object getDescription();
}

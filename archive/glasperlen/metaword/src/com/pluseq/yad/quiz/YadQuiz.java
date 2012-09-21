package com.pluseq.yad.quiz;

import java.util.ArrayList;
import java.util.Arrays;

import com.pluseq.metaword.core.LangDirection;
import com.pluseq.metaword.core.Word;
import com.pluseq.metaword.quiz.QuizInterface;

public class YadQuiz implements QuizInterface {
	public String answerSign;
	public ArrayList<Word> variants;
	public LangDirection direction;
	public Word subject;
	
	@Override
	public Word getAnswerWord() {
		for (Word w : variants) {
			if (w.sign.equals(answerSign)) {
				return w;
			}
		}
		return null;
	}

	@Override
	public Object getDescription() {
		return "translate: " + subject.spelling;
	}

	@Override
	public String getId() {
		return getAnswerWord().sign;
	}

	@Override
	public Word[] getWordVariants() {
		return Arrays.copyOf(variants.toArray(), variants.size(), Word[].class);
	}

}

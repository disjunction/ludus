package com.pluseq.yad.quiz;

import java.util.ArrayList;
import java.util.Arrays;

import com.pluseq.metaword.core.Word;
import com.pluseq.metaword.quiz.QuizQueue;
import com.pluseq.metaword.runtime.WordManager;
import com.pluseq.metaword.storage.WordSourceInterface;

public class YadQuizFront {
	private YadQuizzer yq = new YadQuizzer("deen");
	private WordManager wm = new WordManager();
	private WordSourceInterface source;
	
	public YadQuizFront(WordSourceInterface source) {
		this.source = source;
	}
	
	public QuizQueue makeQueue(int size) throws Exception {
		ArrayList<Word> w = source.getWords();
		yq.setPossibleAnswers((Word[]) Arrays.copyOf(w.toArray(), w.size(), Word[].class));
		
		return yq.makeQueue(size);
	}
	
	public YadQuizzer getQuizzer() {
		return yq;
	}
}

package com.pluseq.metaword.quiz;
import java.util.ArrayList;

public class QuizQueue extends ArrayList<QuizInterface>{
	private static final long serialVersionUID = 1L;
	private int position = 0;
	private int correctCount = 0;
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public int getPosition() {
		return position;
	}
	
	public QuizInterface current() {
		if (position < size()) {
			return get(position);
		} else {
			// end of queue reached
			return null;
		}
	}
	
	public QuizInterface next() {
		if (position < size()) {
			position++;
		}
		return current();
	}
	
	public QuizInterface next(boolean isCorrect) {
		if (isCorrect) {
			correctCount++;
		}
		return next();
	}
	
	public int getCorrectCount() {
		return correctCount;
	}
}

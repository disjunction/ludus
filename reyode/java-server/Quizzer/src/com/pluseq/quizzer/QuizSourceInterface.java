package com.pluseq.quizzer;

public interface QuizSourceInterface {
	public QuizInterface getNext();
	public void reset();
	public int size();
	
	public static final int UNKNOWN = -1;
}

package com.pluseq.quizzer;

import java.util.ArrayList;

public class QuizSpinner implements QuizEmbeddedAnswer {
	private String description;
	private String[] variants;
	private String correctAnswer;
	
	int answerIndex = 0;
	
	public QuizSpinner(String description, String[] variants) {
		this.description = description;
		this.variants = variants;
	}
	
	public void setCorrectAnswer(String correct) {
		this.correctAnswer = correct;
	}
	
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	
	@Override
	public ValueString getDescription() {
		return new ValueString(description);
	}

	@Override
	public ValueString[] getVariants() {
		ArrayList<ValueString> a = new ArrayList<ValueString>();
		for (String val : variants) {
			a.add(new ValueString(val));
		}
		return (ValueString[]) a.toArray(new ValueString[a.size()]);
	}

	@Override
	public void answer(ValueInterface answer) {
		description.replace("[...]", answer.toString());
	}
	
	public String toString() {
		String out = description + "\n";
		for (String v : variants) {
			out += " * " + v + "\n";
		}
		return out;
	}
	
	public String getId() {
		return toString().hashCode() + ";" + correctAnswer.hashCode();
	}
	
	public Object[] toArray() {
		return new Object[] {getId(), description, variants, correctAnswer};
	}
}

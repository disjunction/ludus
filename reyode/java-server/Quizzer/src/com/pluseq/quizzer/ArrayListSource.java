package com.pluseq.quizzer;
import java.util.*;

public class ArrayListSource extends ArrayList<QuizInterface> implements QuizSourceInterface {

	protected Iterator<QuizInterface> iterator;
	private static final long serialVersionUID = 915774582142906759L;

	@Override
	public QuizInterface getNext() {
		if (null == iterator) {
		    iterator = this.iterator();
		}
		if (iterator.hasNext()) {
			return iterator.next();
		} else {
			return null;
		}
	}

	@Override
	public void reset() {
		iterator = this.iterator();
	}
}

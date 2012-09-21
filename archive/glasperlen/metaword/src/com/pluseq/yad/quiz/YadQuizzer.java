package com.pluseq.yad.quiz;

import java.util.*;
import com.pluseq.metaword.core.LangDirection;
import com.pluseq.metaword.core.Word;
import com.pluseq.metaword.core.util.MetawordException;
import com.pluseq.metaword.quiz.*;
import com.pluseq.yad.SimpleTranslator;
import com.pluseq.yad.TranslatorInterface;


public class YadQuizzer implements QuizEvaluator, QuizFactory {
	private TranslatorInterface translator;
	private ArrayList<Word> remainingAnswers;
	private List<Word> possibleAsList;
	private LangDirection direction;
	private int variantNumber = 3;
	private Random randy = new Random();
	
	public YadQuizzer(String direction) {
		this.direction = new LangDirection(direction);
		translator = new SimpleTranslator(this.direction.reversed());
	}
	
	public void setPossibleAnswers(Word[] possibleAnswers) {
		possibleAsList = Arrays.asList(possibleAnswers);
	}
	
	@Override
	public boolean isCorrect(QuizInterface quiz, Word answer) {
		return quiz.getAnswerWord().spelling.equals(answer.spelling);
	}

	private void checkRemaining() {
		if (null == remainingAnswers) {
			Collections.shuffle(possibleAsList);
			remainingAnswers = new ArrayList<Word>(possibleAsList);
		}
		if (remainingAnswers.size() <= 0) {
			throw new MetawordException("YadQuizzer has not enough answers to generate QuizQueue");
		}
	}
	
	/**
	 * finds n-1 random words from answers
	 * and adds the given answer to random position
	 * @param answer
	 * @return
	 */
	private ArrayList<Word> makeVariants(Word answer) {
		Collections.shuffle(possibleAsList);
		Iterator<Word> variantIterator = possibleAsList.iterator();
		int desiredPosition = randy.nextInt(variantNumber);
		ArrayList<Word> result = new ArrayList<Word>();
		for (int i = 0; i < variantNumber; i++) {
			if (i == desiredPosition) {
				result.add(answer);
			} else {
				Word candid;
				do {
					candid = variantIterator.next();
				} while (candid.spelling.equals(answer.spelling));
				result.add(candid);
			}
		}
		return result;
	}
	
	private QuizInterface makeOne() {
		YadQuiz q = new YadQuiz();
		checkRemaining();
		int lastIndex = remainingAnswers.size()-1;
		Word answer = remainingAnswers.get(lastIndex);
		remainingAnswers.remove(lastIndex);
		
		Word[] translated = translator.findForSpelling(answer.spelling);
		if (translated == null) {
			throw new MetawordException("cannot translate " + answer.spelling);
		}
		
		q.variants = makeVariants(answer);
		q.subject = translated[0];
		q.answerSign = answer.sign;
		q.direction = direction;
		return q;
	}

	@Override
	public QuizQueue makeQueue(int size) {
		remainingAnswers = null;
		QuizQueue q = new QuizQueue();
		while (q.size() < size) {
			q.add(makeOne());
		}
		return q;
	}
}

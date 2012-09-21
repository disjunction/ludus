package com.pluseq.quizzer.reyad;
import java.util.*;

import com.pluseq.quizzer.*;
import com.pluseq.reyad.*;

public class WordMixerSource implements QuizSourceInterface {
	private ArrayListSource arraySource = null;
	public int maxPackage = 20;
	public int variantNumber = 4;
	
	private Direction direction;
	
	public WordMixerSource(Direction direction) {
		this.direction = direction;
	}
	
	@Override
	public QuizInterface getNext() {
		return arraySource.getNext();
		// autoreset if we ran out of words...
		/*
		if (null == quiz) {
			this.reset();
			quiz = arraySource.getNext();
		
		return quiz;
		*/
	}
	
	/**
	 * Mixes a prepared list of translations into of set of quizes
	 */
	class WordMixer
	{
		public ArrayList<SimpleTrans> questions;
		private List<Word> possibleAsList;
		private Random randy = new Random();
		private Iterator<SimpleTrans > qit;
		
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
					} while (candid.getSpelling().equals(answer.getSpelling()));
					result.add(candid);
				}
			}
			return result;
		}
		
		public void init() {
			possibleAsList = new ArrayList<Word>();
			
			Collections.shuffle(questions, randy);
			for (SimpleTrans q : questions) {
				possibleAsList.add(q.makeSubjWord());
			}
			qit = questions.iterator();
		}
		
		public QuizSpinner pop()
		{
			if (qit.hasNext()) {
				SimpleTrans question = qit.next();
				ArrayList<Word> variants = makeVariants(question.makeSubjWord());
				
				ArrayList<String> strVariants = new ArrayList<String>();
				for (Word w : variants) {
					strVariants.add(w.getSpelling());
				}
				
				QuizSpinner quiz = new QuizSpinner(question.objSpelling, (String[]) strVariants.toArray(new String[strVariants.size()]));
				quiz.setCorrectAnswer(question.subjSpelling);
				return quiz;
			} else {
				return null;
			}
		}
	}
	
	@Override 
	public void reset() {
		try {
			SimpleTransTableInterface st = Storage._().getSimpleTrans();
			SimpleTrans[] sts = st.findByTags(null, direction, maxPackage);
			sts = SimpleTrans.invertArray(sts);
			
			WordMixer wordMixer = new WordMixer();
			wordMixer.questions = new ArrayList<SimpleTrans>(Arrays.asList(sts));
			wordMixer.init();
			
			arraySource = new ArrayListSource();
			QuizSpinner q;
			while (null != (q = wordMixer.pop())) {
				arraySource.add(q);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int size() {
		return arraySource == null? QuizSourceInterface.UNKNOWN : arraySource.size();
	}
}

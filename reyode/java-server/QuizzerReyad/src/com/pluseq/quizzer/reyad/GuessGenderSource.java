package com.pluseq.quizzer.reyad;
import com.pluseq.quizzer.*;
import com.pluseq.reyad.*;

public class GuessGenderSource implements QuizSourceInterface {
	private ArrayListSource arraySource = null;
	private String[] strVariants = new String[]{"Maskulinum", "Femininum", "Neutrum"};
	private Direction direction;
	
	public int maxPackage = 20;
	
	public GuessGenderSource(Direction direction) {
		this.direction = direction;
	}
	
	@Override
	public QuizInterface getNext() {
		return arraySource.getNext();
	}
	
	@Override 
	public void reset() {
		try {
			SimpleTransTableInterface st = Storage._().getSimpleTrans();
			RelTableInterface rt = Storage._().getRel();
			SimpleTrans[] sts = st.findObjByHomoRels(new String[] {"l.m", "l.f", "l.n"}, "l.gender", direction, maxPackage);

			arraySource = new ArrayListSource();
			QuizSpinner q;
			
			for(int i = 0; i < sts.length; i++) {
				q = new QuizSpinner(sts[i].subjSpelling + " - " + sts[i].objSpelling , strVariants);
				Rel r = rt.getBySubjectType(sts[i].objSign, "l.gender");
				if (null == r) {
					continue;
				}
				
				if (r.objSign.equals("l.m")) {
					q.setCorrectAnswer("Maskulinum");
				} else if (r.objSign.equals("l.f")) {
					q.setCorrectAnswer("Femininum");
				} else {
					q.setCorrectAnswer("Neutrum");
				}
					
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
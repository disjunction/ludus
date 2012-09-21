package com.pluseq.quizzer.reyad;
import java.util.Hashtable;

import com.pluseq.quizzer.*;
import com.pluseq.reyad.Direction;
import com.pluseq.reyad.cli.Bootstrap;

public class QuizzerRpc {
	boolean iwork = true;
	
	Hashtable<String, QuizSourceInterface> sources = new Hashtable<String, QuizSourceInterface>();
	
	private QuizSourceInterface getSourceByDirectionString(String direction) {
		if (!sources.containsKey(direction)) {
			Direction d = new Direction(direction);
			MergedProxySource s = new MergedProxySource();
			s.add(new WordMixerSource(d));
			if (d.target.equals("de")) {
				s.add(new GuessGenderSource(d));
			}
			sources.put(direction, s);
		}
		return  sources.get(direction);
	}
	
	public QuizzerRpc() {
		try {
			(new Bootstrap()).init();
		} catch (Exception e) {
			throw new RuntimeException("problem during the bootstrap");
		}
	}
	
	public Object[] getOne(String direction) {
		QuizSourceInterface s = getSourceByDirectionString(direction);
		if (s == null) {
			throw new RuntimeException("there are no sources for direction '" + direction + "'");
		}
		s.reset();
		return ((QuizSpinner)s.getNext()).toArray();
	}
}

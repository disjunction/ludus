package com.pluseq.quizzer;

import java.util.*;

public class MergedProxySource extends ArrayList<QuizSourceInterface> implements QuizSourceInterface {
	
	private static final long serialVersionUID = 38868320329634457L;
	private ArrayList<Integer> weights = new ArrayList<Integer>();
	private int totalWeight = 0;

	public boolean addWeighet(QuizSourceInterface o, Integer weight) {
		totalWeight += weight;
		weights.add(weight);
		return super.add(o);
	}
	
	public boolean add(QuizSourceInterface o) {
		return addWeighet(o, 1);
	}
	
	@Override
	public QuizInterface getNext() {
		int rand = (int)(Math.random() * totalWeight);
		int collected = 0;
		for (int i = 0; i < size(); i++) {
			if (collected >= rand) {
				return get(i).getNext();
			}
			collected +=  weights.get(i);
		}
		throw new RuntimeException("no source found in merge proxy");
	}

	@Override
	public void reset() {
		for (QuizSourceInterface source : this) {
			source.reset();
		}
	}

	@Override
	public int size() {
		return super.size();
	}
}

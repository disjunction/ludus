package com.pluseq.quizzer.reyad.cli;
import java.util.Scanner;

import com.pluseq.quizzer.*;
import com.pluseq.quizzer.reyad.*;
import com.pluseq.reyad.*;
import com.pluseq.reyad.cli.Bootstrap;
import com.pluseq.reyad.jdbc.StorageJdbc;
import gnu.getopt.Getopt;

public class ReyadQuizzer {
	protected static StorageJdbc s;
	
	protected static int number = 5;
	protected static String[] tags = null;
	
	protected static void showHelp() {
		System.out.println("Usage: reyad_quizzer [options]\n" +
				" -n : count of quizes\n" +
				" -v : count possible answers\n" +
				" -t : coma-separated tags\n");			
	}
	
	public static void main(String[] args) throws Exception {
		(new Bootstrap()).init();

		WordMixerSource s = new WordMixerSource(new Direction("ende"));
		
		Getopt g = new Getopt("reyad", args, "-hn:v:t:");
		int c;
		while ((c = g.getopt()) != -1) {
			switch (c) {
				case 'h':
					showHelp();
					return;
				case 'n':
					s.maxPackage = Integer.parseInt(g.getOptarg());
					break;
				case 'v':
					s.variantNumber = Integer.parseInt(g.getOptarg());
					break;
				case 't':
					tags = g.getOptarg().split(",");
					break;
			}
		}
		
		s.reset();

		System.out.println("Prepared " + s.size() + " questions");
		
		EvaluatorEmbedded eval = new EvaluatorEmbedded();
		Scanner sc = new Scanner(System.in);
		
		int correct = 0;
		QuizSpinner q;
		while (null != (q = (QuizSpinner)s.getNext())) {
			System.out.println(q.toString());
			ValueString[] variants = q.getVariants();
			
			Integer selected = null;
			do {
				String input = sc.nextLine();
				if (input.equals("q")) return;
				try {
					selected = Integer.parseInt(input);
					if (selected < 1 || selected>variants.length) {
						System.out.println("eneter number between 1 and " + variants.length);
						selected = null;
					}
				} catch (NumberFormatException e){
					System.out.println("wrong number enetered, try again");
				}
			} while (selected == null);
			
			if (eval.isCorrect(q, variants[selected - 1])) {
				System.out.println("ok");
				correct++;
			} else {
				System.out.println("Wrong... correct was " + q.getCorrectAnswer());
			}
		}
		
		System.out.println("done. Correct: " + Math.round(correct*100/s.size()) + "%\n");
	}
}

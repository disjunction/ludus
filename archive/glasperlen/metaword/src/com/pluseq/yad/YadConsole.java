package com.pluseq.yad;

import java.io.*;

import com.pluseq.metaword.core.*;
import com.pluseq.metaword.core.util.*;
import com.pluseq.metaword.mwq.MWQResultSet;
import com.pluseq.metaword.mwq.MWQStatement;
import com.pluseq.metaword.quiz.QuizInterface;
import com.pluseq.metaword.quiz.QuizQueue;
import com.pluseq.metaword.runtime.WordManager;
import com.pluseq.metaword.storage.*;
import com.pluseq.metaword.storage.serializer.XMLWordSource;
import com.pluseq.util.StringUtil;
import com.pluseq.vivid.*;
import com.pluseq.yad.parser.*;
import com.pluseq.yad.quiz.YadQuizFront;
import com.pluseq.yad.quiz.YadQuizzer;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

public class YadConsole {

	private enum Keyword {
		lang, parse, read, simpleTrans, resimpleTrans, truncate, trans, t, word, sign, list, q, quit, help, h, nul, get,
		
		// MWQ
		mwq, set, countWords, show,
		
		// List
		add, remove, load, save, clear,
		
		// Quiz
		quiz, variants, start;
		
		public static Keyword toKeyword(String str) {
			if (str.equals("?")) return help;
	        try { return valueOf(str); } 
	        catch (Exception ex) { return nul; }
	    }   
	}
	
	protected void out(String s) {
		System.out.println(s);
	}
	
	protected void out(Word w) {
		out(w.getLangId() + ": " + w.spelling + " (" + w.sign + ")");
	}
	
	private YadReferenceList list = new YadReferenceList();
	private String mwQuery;
	private MWQStatement mwqs;
	private MWQResultSet rs;
	private QuizQueue currentQQ;
	private YadQuizzer quizzer;
	private WordManager wm = new WordManager();
	private String langId = "en";
	private Hashtable variables = new Hashtable();
	
	public YadConsole()
	{
		variables.put("langFrom", Lang.Any);
		variables.put("langTo", Lang.Any);
	}
	
	/**
	 * process a string starting with "mwq" keyword
	 * @param params
	 * @throws Exception 
	 */
	private void processLineMWQ(String[] params) throws Exception {
		if (params.length == 0) {
			out(mwQuery);
			return;
		}
		
		switch (Keyword.toKeyword(params[0])) {
		    case set:
		    	mwQuery = StringUtil.implode(" ", params, 1);
		    	break;
		    
		    case countWords:
		    	mwqs = new MWQStatement(mwQuery);
		    	mwqs.setOperation(MWQStatement.Operation.countWords);
		    	rs = wm.executeMWQ(mwqs);
		    	if (null == rs) {
		    		out("cannot execute: " + mwQuery);
		    	} else {
		    		out("countWords: " + rs.getCountWords());
		    	}
		    	
		    	break;
		    
		    case show:
		    	mwqs = new MWQStatement(mwQuery);
		    	rs = wm.executeMWQ(mwqs);
		    	if (null == rs) {
		    		out("cannot execute: " + mwQuery);
		    	} else {
			    	for (Word w : rs.getWords()) {
			    		out(w);
			    	}
			    	out("countWords: " + rs.getCountWords());
		    	}
		    	break;
		    	
		    default:
		    	out("wrong operator: " + params[0]);
		}
	}
	
	private void processLineQuiz(String[] params) throws Exception {
		if (params.length == 0) {
			if (null == currentQQ) {
				out("queue is empty");
				return;
			}
			out("Current quiz queue size: " + currentQQ.size());
			out("Correct count: " + currentQQ.getCorrectCount());
			if (currentQQ.current() == null) {
				out("Completed.");
			} else {
				out(currentQQ.current());
			}
			return;
		}
		
		switch (Keyword.toKeyword(params[0])) {
			case start:
				if (null == list || list.getCountWords() == 0) {
					out("Current list is empty, cannot generate quiz");
					return;
				}
				YadQuizFront qf = new YadQuizFront(list);
				quizzer = qf.getQuizzer();
				currentQQ = qf.makeQueue(list.getCountWords() - 2);
				out(currentQQ.current());
				break;
		}
	}
	
	private void processLineList(String[] params) {
		if (params.length == 0) {
			out("List contains: " + list.getCountWords());
			return;
		}
		
		switch (Keyword.toKeyword(params[0])) {
			case show:
				for (Word w : list.getWords()) {
		    		out(w);
		    	}
				out("countWords: " + list.getCountWords());
				break;
				
	    	case add:
	    		mwqs = new MWQStatement(mwQuery);
		    	rs = wm.executeMWQ(mwqs);
		    	if (null == rs) {
		    		out("cannot execute: " + mwQuery);
		    	} else {
			    	for (Word w : rs.getWords()) {
			    		list.getWords().add(w);
			    	}
			    	out("countWords: " + list.getCountWords());
		    	}
		    	break;
		    
	    	case clear:
	    		list = new YadReferenceList();
	    		break;
	    		
	    	case load:
	    		list = new YadReferenceList();
	    		try {
	    			list.readFile(new File(params[1]));
	    			out("countWords: " + list.getCountWords());
	    		} catch (IOException e) {
	    			out("cannot read file: " + params[1] + ", because " + e.getMessage());
	    		}
	    		break;
	    	
	    	case save:
	    		try {
	    			list.writeFile(new File(params[1]));
	    			out("countWords: " + list.getCountWords());
	    		} catch (IOException e) {
	    			out("cannot write file: " + params[1] + ", because " + e.getMessage());
	    		}
	    		break;
	    		
	    	default:
		    	out("wrong operator: " + params[0]);
		}
	}
	
	private void out(int intParam) {
		out(String.valueOf(intParam));
	}
	private void out(QuizInterface quiz) {
		out("Quiz: " + (currentQQ.getPosition() + 1));
		out(quiz.getDescription().toString());
		int i = 0;
		for (Word quizWord : quiz.getWordVariants()) {
			System.out.println("  " + (++i) + ". " + quizWord.spelling);
		}
	}

	public void processLine(String line) throws Exception
	{
		line = line.trim();
		if (line.length() > 0 && line.substring(0, 1).equals("#")) {
			return;
		}
		String[] chunks = line.split(" ");
		String command = chunks[0];
		YadDataGenerator dg;
		Word w;
		
		String[] params = new String[chunks.length -1];
		for (int i = 1; i< chunks.length; i++) {
			params[i - 1] = chunks[i];
		}
		
		// if the input text was a number, then process it as a quiz answer
		try {
			int answerPos;
			if ((answerPos = Integer.valueOf(command) - 1) >= 0) {
				if (currentQQ == null || currentQQ.current() == null) {
					out("no question for this answer");
				} else {
					QuizInterface q = currentQQ.current();
					if (answerPos < q.getWordVariants().length) {
						Word answerWord = q.getWordVariants()[answerPos];
						boolean result = quizzer.isCorrect(currentQQ.current(), answerWord);
						if (result) {
							out("Correct!");
						} else {
							out("Wrong. Correct: " + currentQQ.current().getAnswerWord().getSpelling());
						}
						QuizInterface next = currentQQ.next(result);
						if (null == next) {
							out("Complete. Correct Count: " + currentQQ.getCorrectCount() + "/" + currentQQ.size());
						} else {
							out(next);
						}
					} else {
						out("max answer number is " + q.getWordVariants().length);
					}
				}
				return;
			}
		}
		catch (NumberFormatException e) {}
		
		switch (Keyword.toKeyword(command)) {
			case q:
			case quit:
				throw new RuntimeException("quit!");
			case trans:
			case t:
				translateSpelling(params[0]);
				break;
			case parse:
				dg = new YadDataGenerator();
				YadParserInterface parser;
				if (params.length < 3 || params[2].equals("ksocrat")) {
					parser = new YadParserKSocrat();
				} else {
					parser = new YadParserBeolingus();
				}
				dg.parseAndSerialize(new File(params[0]), new File(params[1]), parser);
				out("done parsing.");
				break;
			
			case read:
				XMLWordSource xws = new XMLWordSource();
				xws.readFile(new File(params[0]));
				dg = new YadDataGenerator();
				dg.fill(xws);
				out("done reading.");
				break;
			
			case simpleTrans:
				dg = new YadDataGenerator();
				dg.generateSimpleTrans(params[0], params[1], false);
				out("done making simpleTrans.");
				break;
				
			case resimpleTrans:
				dg = new YadDataGenerator();
				dg.generateSimpleTrans(params[0], params[1], true);
				out("done making reversed simpleTrans.");
				break;
				
			case truncate:
				MetawordStorage._().getRelTable().truncate();
				MetawordStorage._().getWordTable().truncate();
				YadStorage._().getSimpleTransTable().truncate();
				out("word and rel truncated.");
				break;
			
			case h:
			case help:
				InputStream stream = YadConsole.class.getResourceAsStream("YadConsoleHelp.txt");
				BufferedReader in = new BufferedReader(new InputStreamReader(stream));
				String tmpLine = null;
				while((tmpLine = in.readLine()) != null) out(tmpLine);
				break;
			case lang:
				if (params.length == 0) {
					out("Current lang: " + langId);
				} else {
					langId = params[0];
					out("Lang changed to: " + langId);
				}
				break;
			case sign:
				w = wm.getBySign(params[0]);
				if (null == w) {
					out("Sign '" + params[0] + "' not found");
				} else {
					mwQuery = "{\"sign\": \"" + w.sign + "\"}"; 
					out(w);
				}
				break;
			case word:
				w = wm.getBySpelling(params[0], langId);
				if (null == w) {
					out("Word '" + params[0] + "' not found in lang:" + langId);
				} else {
					mwQuery = "{\"sign\": \"" + w.sign + "\"}";
					out(w);
				}
				break;
	    	
			case set:
	    		variables.put(params[0], params[1]);
	    		out(params[0] + " = " + params[1]);
	    		break;
	    		
			case get:
				if (!variables.containsKey(params[0])) {
					out("not set");
				} else {
					out(params[0] + " = " + variables.get(params[0]));
				}
	    		break;

			case mwq:
				processLineMWQ(params);
				break;
			
			case list:
				processLineList(params);
				break;
			
			case quiz:
				processLineQuiz(params);
				break;
				
			case nul:
				translateSpelling(command);
		}
	}

	protected void translateSpelling(String spelling) throws Exception {
		Word[] words = YadStorage._().getSimpleTransTable().findTranslationWords(spelling,
				(String)variables.get("langFrom") + variables.get("langTo"));
		if (null == words) {
			System.out.println("Sorry, '" + spelling + "' not found");
		} else {
			for (Word w : words) {
				out(w);
			}
		}
	}
	
	public void readInput() throws Exception {
		InputStreamReader converter = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(converter);
		YadConsole console = new YadConsole();
		
		while (true) {
			System.out.print("\nyad> ");				
			String input = in.readLine();
			try {
				console.processLine(input);
			} catch (Exception e) {
				if (e.getMessage() != null  && e.getMessage().equals("quit!")) {
					System.out.println("Bye.");
					return;
				} else {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * this is used for the auto-complete functions
	 */
	public String[] getCommands() {
		return new String[]{"parse", "read", "simpleTrans", "resimpleTrans", "trans", "help", "truncate", "sign", "mwq", "list", "quit"};
	}
	
	public static void main(String[] args) throws Exception {
		YadBootstrap._().init();
		YadConsole console = new YadConsole();
		console.readInput();
	}
}

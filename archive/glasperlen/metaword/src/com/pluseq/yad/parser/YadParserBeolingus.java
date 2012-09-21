package com.pluseq.yad.parser;

import java.util.ArrayList;
import java.util.StringTokenizer;

import com.pluseq.metaword.core.*;
import com.pluseq.metaword.core.util.Transliterator;
import com.pluseq.metaword.storage.WordSourceHeader;

public class YadParserBeolingus extends YadParserAbstract {
	private LangDirection direction = new LangDirection("deen");
	
	private class WordPack {
		ArrayList<Word> semicoloned = new ArrayList<Word>();
		ArrayList<Rel> complement = new ArrayList<Rel>();
	}
	
	private enum Gender { masculine, feminine, neuter, nul};
	private enum Number { singular, plural, nul};
	private enum PartOfSpeech { verb, adjective, noun, nul};
	
	private ArrayList<WordPack> makePacks(String str, String langId) {
		ArrayList<WordPack> wordPacks = new ArrayList<WordPack>();

		for (String chunk : str.split("\\|")) {
			WordPack pack = new WordPack();
			
			// replace to go {went; gone} with just "to go" - makes further ";" split easier
			chunk = chunk.replaceAll("\\{\\w+\\;\\s\\w+\\}", "");
			
			for (String subchunk : chunk.split("\\;")) {
				
				boolean collect = true;
				Gender gender = Gender.nul;
				Number number = Number.nul;
				PartOfSpeech partOfSpeech = PartOfSpeech.nul;
				
				String spelling = "";
				ArrayList<String> pieceRels = new ArrayList<String>();
			
				for (String piece : subchunk.split(" ")) {

					piece = piece.trim();
					if (piece.length() > 1 && (piece.substring(0, 1).equals("{") || piece.substring(0, 1).equals("["))) {
						collect = false;
						if (piece.equals("{f}")) {
							gender = Gender.feminine;
							if (number == Number.nul) number = Number.singular;
							partOfSpeech = PartOfSpeech.noun;
						} else
						if (piece.equals("{m}")) {
							gender = Gender.masculine;
							if (number == Number.nul) number = Number.singular;
							partOfSpeech = PartOfSpeech.noun;
						} else
						if (piece.equals("{n}")) {
							gender = Gender.neuter;
							if (number == Number.nul) number = Number.singular;
							partOfSpeech = PartOfSpeech.noun;
						} else
						if (piece.equals("{pl}")) {
							number = Number.plural;
						} else
						if (piece.equals("{adj}")) {
							partOfSpeech = PartOfSpeech.adjective;
						} else
						if (piece.equals("{vt}")) {
							partOfSpeech = PartOfSpeech.verb;
						} else
						if (piece.equals("{vi}")) {
							partOfSpeech = PartOfSpeech.verb;
						} else {
							pieceRels.add(piece);
						}
					}
					if (collect) {
						if (spelling.length() > 0) {
							spelling += " ";
						}
						spelling += piece;
					}
				}
				
				if (spelling.length() > 0) {
					
					if (langId.equals("en") && spelling.matches("^to\\s.*")) {
						spelling = spelling.substring(3);
						partOfSpeech = PartOfSpeech.verb;
					}
					
					Word newWord = new Word(transliterator.toSign(spelling, langId), spelling);
					pack.semicoloned.add(newWord);
					if (gender != Gender.nul) {
						pack.complement.add(new Rel(newWord.sign, Lang.Ling+gender.toString(), Lang.Ling+"gender"));
					}
					if (number != Number.nul) {
						pack.complement.add(new Rel(newWord.sign, Lang.Ling+number.toString(), Lang.Ling+"number"));
					}
					if (partOfSpeech != PartOfSpeech.nul) {
						pack.complement.add(new Rel(newWord.sign, Lang.Ling+partOfSpeech.toString(), Lang.Ling+"part_of_speech"));
					}
					for (String pieceRel : pieceRels) {
						pack.complement.add(new Rel(newWord.sign, "--" + pieceRel, Rel.TypeProperty));
					}
				}
			} // end of ;- loop
			
			if (pack.semicoloned.size() == 0) {
				pack = new WordPack();
				pack.semicoloned.add(new Word("..null"));
			}
			wordPacks.add(pack);
		}
		
		return wordPacks;
	}
	
	private void parseLine(String s) {
		if (s.substring(0, 1).equals("#")) return;
		String[] halves = s.split("::");
		if (halves.length != 2) return;
		
		ArrayList<WordPack> sourcePacks = makePacks(halves[0], direction.source);
		ArrayList<WordPack> targetPacks = makePacks(halves[1], direction.target);
		
		int limit = Math.min(sourcePacks.size(), targetPacks.size());
		for (int i = 0; i < limit; i++) {
			for (Word targetWord : targetPacks.get(i).semicoloned) {
				words.add(targetWord);
			}
			for (Word sourceWord : sourcePacks.get(i).semicoloned) {
				words.add(sourceWord);
				for (Word targetWord : targetPacks.get(i).semicoloned) {
					rels.add(new Rel(sourceWord.sign, targetWord.sign, Rel.TypeTrans));
				}
			}
			
			// part of speech should be in sync (i.e. if it was ajective in german, it's also ajective in english)
			
			for (Rel sourceRel : sourcePacks.get(i).complement) {
				rels.add(sourceRel);
				if (sourceRel.typeSign.equals(Lang.Ling + "number")) {
					for (Word targetWord : targetPacks.get(i).semicoloned) {
						rels.add(new Rel(targetWord.sign, sourceRel.objSign, sourceRel.typeSign));
					}
				}
			}
			
			for (Rel targetRel : targetPacks.get(i).complement) {
				rels.add(targetRel);
			}
		}
	}
	
	@Override
	public void feed(String data) {
		langFrom = direction.source;
		langTo = direction.target;
		
		StringTokenizer st = new StringTokenizer(data, "\n\r", false);
		while (st.hasMoreTokens()) {
			parseLine(st.nextToken());
		}
	}

	@Override
	public WordSourceHeader getHeader() {
		WordSourceHeader header = new WordSourceHeader();
		header.domain = "beolingus";
		header.url = "http://dict.tu-chemnitz.de/";
		return header;
	}

}

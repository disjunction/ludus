package com.pluseq.metaword.storage.serializer;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pluseq.metaword.core.Metaword;
import com.pluseq.metaword.core.Rel;
import com.pluseq.metaword.core.Word;
import com.pluseq.metaword.runtime.MetawordRegistry;
import com.pluseq.metaword.storage.FileWordSourceInterface;
import com.pluseq.metaword.storage.Headered;
import com.pluseq.metaword.storage.WordSourceHeader;
import com.pluseq.metaword.storage.WordSourceInterface;
import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XMLWordSource implements FileWordSourceInterface, Headered {
	
	protected static final int objectsPerBatch = 1000;
	
	protected WordSourceHeader header;
	public void setHeader(WordSourceHeader header) {
		this.header = header;
	}
	
	public WordSourceHeader getHeader() {
		if (null == header) {
			header = new WordSourceHeader();
		}
		header.generator = "XMLWordSource, " + Metaword.getSignature();
		return header;
	}
	
	private static Logger logger = MetawordRegistry.getLogger(XMLWordSource.class.getName());
	
	protected static XStream xstream;
	public XMLWordSource() {
		if (null == xstream) {
			xstream = new XStream(new DomDriver());
			xstream.alias("header", WordSourceHeader.class);
			xstream.alias("word", Word.class);
			xstream.alias("rel", Rel.class);
		}
	}
	
	public XMLWordSource(WordSourceInterface source) {
		this();
		setWords(source.getWords());
		setRels(source.getRels());
	}
	
	protected ArrayList<Rel> rels = new ArrayList<Rel>();
	protected ArrayList<Word> words = new ArrayList<Word>();	
	
	@Override
	public ArrayList<Rel> getRels() {
		return rels;
	}

	@Override
	public ArrayList<Word> getWords() {
		return words;
	}
	
	public void setWords(ArrayList<Word> words) {
		this.words = words;
	}
	
	public void setRels(ArrayList<Rel> rels) {
		this.rels = rels;
	}
	
	public void writeFile(String filename) throws IOException {
		writeFile(new File(filename));
	}
	
	protected ArrayList<ArrayList<Word>> splitWords(ArrayList<Word> source, int perBatch) {
		ArrayList<ArrayList<Word>> batches = new ArrayList<ArrayList<Word>>();
		ArrayList<Word> curBatch = null;
		
		int i = 0;
		for (Word obj : source) {
			if (i > perBatch || curBatch == null) {
				if (curBatch != null) {
					batches.add(curBatch);
				}
				curBatch = new ArrayList<Word>();
				i = 0;
			}
			curBatch.add(obj);
			i++;
		}
		
		if (curBatch != null) {
			batches.add(curBatch);
		}
		
		return batches;
	}
	
	protected ArrayList<ArrayList<Rel>> splitRels(ArrayList<Rel> source, int perBatch) {
		ArrayList<ArrayList<Rel>> batches = new ArrayList<ArrayList<Rel>>();
		ArrayList<Rel> curBatch = null;
		
		int i = 0;
		for (Rel obj : source) {
			if (i > perBatch || curBatch == null) {
				if (curBatch != null) {
					batches.add(curBatch);
				}
				curBatch = new ArrayList<Rel>();
				i = 0;
			}
			curBatch.add(obj);
			i++;
		}
		
		if (curBatch != null) {
			batches.add(curBatch);
		}
		
		return batches;
	}
	
	@Override
	public void writeFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream out = xstream.createObjectOutputStream(fos);

		ArrayList<ArrayList<Word>> wordPacks = splitWords(getWords(), objectsPerBatch);
		ArrayList<ArrayList<Rel>> relPacks = splitRels(getRels(), objectsPerBatch);
		
		WordSourceHeader header = getHeader();
		String[] parts = new String[wordPacks.size() + relPacks.size()];
		int i;
		for (i = 0; i < wordPacks.size(); i++) {
			parts[i] = "words";
		}
		for (int j = 0; j < relPacks.size(); j++) {
			parts[i + j] = "rels";
		}
		header.parts = parts;
				
		out.writeObject(header);
		for (ArrayList<Word> wordPack : wordPacks) {
			out.writeObject(wordPack);
		}
		for (ArrayList<Rel> relPack : relPacks) {
			out.writeObject(relPack);
		}

		out.close();
	}

	@Override
	public void readFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream in = xstream.createObjectInputStream(fis);

		logger.info("reading header");
		
		try {
			setHeader((WordSourceHeader)in.readObject());

			setWords(new ArrayList<Word>());
			setRels(new ArrayList<Rel>());
			
			logger.info("reading word/rel chunks");
			
			int chunkCount = header.parts.length;
			int counter = 0;
			for (String part : header.parts) {
				counter++;
				if (part.equals("words")) {
					this.words.addAll((ArrayList<Word>)in.readObject());
					logger.info("word chunk " + counter + "/"  + chunkCount);
				}
				if (part.equals("rels")) {
					this.rels.addAll((ArrayList<Rel>)in.readObject());
					logger.info("rel chunk " + counter + "/"  + chunkCount);
				}
			}
		} catch (ClassNotFoundException e) {
			MetawordRegistry.getLogger(this.getClass().getName()).throwing(this.getClass().getName(), null, e);
		}
		
		in.close();
		fis.close();
	}	
	
	public String stringify() {
		return xstream.toXML(getHeader());
	}
}

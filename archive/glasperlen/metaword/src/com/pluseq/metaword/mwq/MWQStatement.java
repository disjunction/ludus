package com.pluseq.metaword.mwq;
import com.pluseq.metaword.core.*;
import com.pluseq.metaword.core.util.MetawordException;
import com.pluseq.metaword.storage.MWQInterface;
import com.pluseq.metaword.storage.WordSourceInterface;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class MWQStatement implements MWQInterface {
	static XStream jsonReader;
	
	public enum Operation {
		words,
		rels,
		countWords,
		countRels
	}
	
	private MWQFilters filters;
	private Operation operation = Operation.words;
	
	public MWQStatement() {
		if (null == jsonReader) {
			jsonReader = new XStream(new JettisonMappedXmlDriver());
	        jsonReader.setMode(XStream.NO_REFERENCES);
	        jsonReader.alias("filters", MWQFilters.class);
	        jsonReader.alias("rel", MWQRelFilters.class);
		}
	}
	
	public MWQStatement(String query) {
		this();
		
		try {
			parseQuery(query);
		} catch (Exception e) {
			// nullify constructor exception
		}
	}
	 
	
	private void parseQuery(String query) {
		if (query == null || query.length() == 0) {
			throw new MetawordException("empty query");
		}
		
		if (query.substring(0, 1).equals("{")) {
			String json = "{\"filters\": " + query + "}";
			filters = (MWQFilters)jsonReader.fromXML(json);
		} else if (query.substring(0, 4).equals("MWQ.")) {
			throw new MetawordException("MWQ operations are not yet supported");
		}
	}
	
	public MWQFilters getFilters() {
		return filters;
	}
	
	public Operation getOperation() {
		return operation;
	}
	
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
	@Override
	public WordSourceInterface execute(String query) {
		this.parseQuery(query);
		MWQResultSet result = new MWQResultSet();
		result.getWords().add(new Word("++lang:" + this.filters.rel.objSign));
		return result;
	}
}

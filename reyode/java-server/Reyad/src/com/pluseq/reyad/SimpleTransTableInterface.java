package com.pluseq.reyad;

public interface SimpleTransTableInterface {
	/**
	 * wildcarded tanslation
	 * @param spelling
	 * @param direction
	 * @return
 	 * @throws Exception
	 */
	public Word[] findTranslationWords(String spelling, Direction direction) throws Exception;
	public SimpleTrans[] findByTags(String[] tags, Direction direction, int limit) throws Exception;
	public void initialize() throws Exception;
	SimpleTrans[] findObjByHomoRels(String[] objSigns, String type, Direction direction, int limit) throws Exception;
}

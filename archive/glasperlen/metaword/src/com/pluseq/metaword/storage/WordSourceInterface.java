package com.pluseq.metaword.storage;

import java.util.ArrayList;
import com.pluseq.metaword.core.*;

public interface WordSourceInterface {
	public ArrayList<Word> getWords();
	public ArrayList<Rel> getRels();
}

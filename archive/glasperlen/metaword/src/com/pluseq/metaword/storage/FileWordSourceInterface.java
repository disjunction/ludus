package com.pluseq.metaword.storage;

import java.io.File;
import java.io.IOException;

public interface FileWordSourceInterface extends WordSourceInterface {
	void writeFile(File file) throws IOException;
	void readFile(File file) throws IOException;
}

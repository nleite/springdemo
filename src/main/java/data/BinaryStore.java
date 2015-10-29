package data;

import java.io.InputStream;

public interface BinaryStore<T> {
	
	public boolean write(InputStream in, T t);

	public boolean alreadyThere(String filename);
}

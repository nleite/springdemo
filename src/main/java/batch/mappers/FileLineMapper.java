package batch.mappers;

public interface FileLineMapper<T> {
	
	
	public T mapLine(String line, int lineNumber) throws Exception;
}

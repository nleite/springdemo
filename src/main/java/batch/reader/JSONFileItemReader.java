package batch.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ReaderNotOpenException;
import org.springframework.batch.item.file.NonTransientFlatFileException;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import batch.mappers.FileLineMapper;

public class JSONFileItemReader<T> extends
		AbstractItemCountingItemStreamItemReader<T> implements
		ResourceAwareItemReaderItemStream<T>, InitializingBean  {

	private boolean noInput=false;
	private BufferedReader reader;
	private Resource resource;
	private static final Log logger = LogFactory.getLog(JSONFileItemReader.class);
	private int lineCount = 0;
	private FileLineMapper<T> lineMapper;
	// default encoding for input files
	public static final String DEFAULT_CHARSET = Charset.defaultCharset().name();
	
	private String encoding = DEFAULT_CHARSET;
	
	@Override
	protected T doRead() throws Exception {
		
		
		if (this.noInput){
			return null;
		}
		
		String line = readLine();
		if(line == null){
			return null;
		}
		
		return lineMapper.mapLine(line, lineCount);
		
	}

	
	/**
	 * @return next line (skip comments).getCurrentResource
	 */
	private String readLine() {

		if (reader == null) {
			throw new ReaderNotOpenException("Reader must be open before it can be read.");
		}

		String line = null;

		try {
			line = this.reader.readLine();
			if (line == null) {
				return null;
			}
			lineCount++;
		}
		catch (IOException e) {
			// Prevent IOException from recurring indefinitely
			// if client keeps catching and re-calling
			noInput = true;
			throw new NonTransientFlatFileException("Unable to read from resource: [" + resource + "]", e, line,
					lineCount);
		}
		return line;
	}
	
	@Override
	protected void doOpen() throws Exception {
		//check resource (readability ...)
		Assert.notNull(resource, "Input resource must be set");

		noInput = true;
		if (!resource.exists()) {
			logger.warn("Input resource does not exist " + resource.getDescription());
			
			return;
		}

		if (!resource.isReadable()) {
			logger.warn("Input resource is not readable " + resource.getDescription());
			return;
		}
		
		this.reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), this.encoding));
		this.noInput = false;
	}

	@Override
	protected void doClose() throws Exception {
		this.lineCount = 0;
		if(reader != null){
			this.reader.close(); 
		}
		
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(lineMapper, "LineMapper is required");
	}

	public void setResource(Resource resource) {
		this.resource = resource;
		
	}


	public void setLineMapper(FileLineMapper<T> lineMapper) {
		this.lineMapper = lineMapper;
	}


	public void setEncoding(String encoding) {
		this.encoding = encoding;
		
	}
	
	
	

}

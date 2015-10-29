package data.gridfs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import com.mongodb.MongoClient;

@Configuration
public class GridFSConfig extends AbstractMongoConfiguration {

	
	private String dbname = "education_videos";
	private String host = "localhost:27017";
	@Override
	protected String getDatabaseName() {
		return dbname;
	}

	@Override
	public MongoClient mongo() throws Exception {
		return new MongoClient(host);
	}

	
	@Bean
	public GridFsTemplate gridFsTemplate() throws Exception {
		return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
	}
	
}

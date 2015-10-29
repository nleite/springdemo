package batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import springdemo.videos.Video;
import batch.mappers.WrappedVideoJsonLineMapper;
import batch.processor.VideoItemProcessor;
import batch.reader.JSONFileItemReader;

import com.mongodb.MongoClient;

import data.VideoBinaryStoreFactory;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {


	private VideoBinaryStoreFactory storeFactory;

	@Autowired
	private MongoTemplate mongoTemplate;

	private String dbname = "springdemo";
	
	
	private static final Log logger = LogFactory.getLog(BatchConfiguration.class);

	// read the source file
	@Bean
	public ItemReader<Video> readFile() {
		JSONFileItemReader<Video> reader = new JSONFileItemReader<Video>();
		reader.setResource( new ClassPathResource("videostest.json"));
		reader.setName("JsonReader");
		reader.setLineMapper(new WrappedVideoJsonLineMapper());
		return reader;
	}

	// process and store binaries
	@Bean
	public VideoItemProcessor loadBinary() {
		logger.info("LOAD");
		storeFactory = new VideoBinaryStoreFactory();
		VideoItemProcessor processor = new VideoItemProcessor();
		processor.setStoreBin(storeFactory.create());
		return processor;
	}

	// store the documents on MongoDB
	@Bean
	public MongoItemWriter<Video> writeDocuments() {
		logger.info("WRITE");
		MongoItemWriter<Video> writer = new MongoItemWriter<Video>();
		writer.setTemplate(mongoTemplate);
		writer.setCollection("videos");
		
		return writer;
	}
	

	@Bean
	public Job importVideoJob(JobBuilderFactory jobs, Step s1,
			JobExecutionListener listener) {
		return jobs.get("importVideoJob").incrementer(new RunIdIncrementer())
				.listener(listener).flow(s1).end().build();
	}

	@Bean
	public MongoTemplate mongoTemplate() {
		MongoClient mongoClient = new MongoClient("localhost:27017");
		MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient,
				dbname);
		// MappingMongoConverter mongoConverter = new
		// MappingMongoConverter(mongoDbFactory, mappingContext)
		// this.mongoTemplate = new MongoTemplate(mongoDbFactory,
		// mongoConverter)

		this.mongoTemplate = new MongoTemplate(mongoDbFactory);
		return mongoTemplate;
	}

	@Bean
	public Step step1(StepBuilderFactory stepBuilderFactory,
			ItemReader<Video> reader, VideoItemProcessor processor,
			MongoItemWriter<Video> writer) {
		return stepBuilderFactory.get("a").<Video, Video> chunk(1)
				.reader(reader).processor(processor).writer(writer).build();
	}

}

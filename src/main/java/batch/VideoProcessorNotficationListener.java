package batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import springdemo.videos.Video;

@Component
public class VideoProcessorNotficationListener extends
		JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(VideoProcessorNotficationListener.class);
	
	private final MongoTemplate mongoTemplate;
	
	@Autowired
	public VideoProcessorNotficationListener(MongoTemplate mongoTemplate){
		this.mongoTemplate = mongoTemplate;
	}
	
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");

			List<Video> results = mongoTemplate.findAll(Video.class);

			for (Video video : results) {
				log.info("Found <" + video + "> in the database.");
			}

		}
	}

}

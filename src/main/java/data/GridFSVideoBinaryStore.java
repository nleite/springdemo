package data;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

import springdemo.videos.Video;
import data.gridfs.GridFSConfig;

public class GridFSVideoBinaryStore implements BinaryStore<Video> {

	private GridFsOperations gridOps;
	
	private static final Log logger = LogFactory.getLog(GridFSVideoBinaryStore.class);
	
	
	public GridFSVideoBinaryStore(){
		@SuppressWarnings("resource")
		ApplicationContext ctx = 
                new AnnotationConfigApplicationContext(GridFSConfig.class);
		gridOps = (GridFsOperations) ctx.getBean("gridFsTemplate");
	}
	
	
	public boolean write(InputStream in, Video t) {
		Document metadata = new Document("course", t.getName() );
		metadata.append("lesson", t.getLesson());
		gridOps.store(in, t.getName(), metadata);
		boolean ok = true;
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Uppps! : " + e.getMessage());
			ok = false;
		}
		return ok;
		
	}


	public boolean alreadyThere(String filename) {
		Query query = new Query().addCriteria(Criteria.where("filename").is(filename));
		return 0 != gridOps.find(query).size();
	}

}

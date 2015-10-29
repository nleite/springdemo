package batch.processor;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.batch.item.ItemProcessor;

import springdemo.videos.Video;
import data.BinaryStore;
public class VideoItemProcessor implements ItemProcessor<Video, Video>{

	private BinaryStore<Video> storeBin;
	
	
	public Video process(Video item) throws Exception {
		
		boolean ok = true;
		//check if the video is already 
		if(!storeBin.alreadyThere(item.getName())){
			//need to store the video
			InputStream in = new FileInputStream(new File(item.getDiskLocation()));
			//TODO: do the check of input stream
			ok = storeBin.write(in, item);
		}
			
		//if the video exists continue
		item.setStored(ok);
		return item;
		
	}


	public void setStoreBin(BinaryStore<Video> storeBin) {
		this.storeBin = storeBin;
	}
	
	
	

}

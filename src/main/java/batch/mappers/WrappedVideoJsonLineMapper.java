package batch.mappers;

import java.util.Map;

import org.springframework.batch.item.file.mapping.JsonLineMapper;

import springdemo.videos.Video;

public class WrappedVideoJsonLineMapper implements FileLineMapper<Video> {

	private JsonLineMapper jsonMapper; 
	
	
	public WrappedVideoJsonLineMapper(){
		jsonMapper = new JsonLineMapper();
	}
	
	
	public Video mapLine(String line, int lineNumber) throws Exception {
		Map<String, Object> videoMap = jsonMapper.mapLine(line, lineNumber);
		
		String diskLocation, name, lesson, course;
		diskLocation = (String)videoMap.get("location");
		name = (String)videoMap.get("name");
		lesson = (String)videoMap.get("lesson");
		course = (String)videoMap.get("course");
		Video mapped = new Video(diskLocation, name, lesson, course);
		
		return mapped;
	}

}

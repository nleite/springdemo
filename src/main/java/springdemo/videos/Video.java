package springdemo.videos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="videos")
public class Video {

	@Field("location")
	private String diskLocation;
	@Id
	private String name;
	@Indexed
	private String lesson;
	
	private String course;
	@Indexed
	private boolean stored = false;
	
	
	public Video(String diskLocation, String name, String lesson, String course) {
		super();
		this.diskLocation = diskLocation;
		this.name = name;
		this.lesson = lesson;
		this.course = course;
	}
	public String getDiskLocation() {
		return diskLocation;
	}
	public void setDiskLocation(String diskLocation) {
		this.diskLocation = diskLocation;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLesson() {
		return lesson;
	}
	public void setLesson(String lesson) {
		this.lesson = lesson;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public boolean isStored() {
		return stored;
	}
	public void setStored(boolean stored) {
		this.stored = stored;
	}
	
	
	
	
	
	
}

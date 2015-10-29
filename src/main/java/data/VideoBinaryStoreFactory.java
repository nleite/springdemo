package data;

import springdemo.videos.Video;

public class VideoBinaryStoreFactory implements BinaryStoreFactory<Video> {

	public BinaryStore<Video> create() {
		return new GridFSVideoBinaryStore();
	}

}

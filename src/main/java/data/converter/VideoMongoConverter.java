package data.converter;

import org.springframework.core.convert.ConversionService;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.util.TypeInformation;

import com.mongodb.DBObject;
import com.mongodb.DBRef;

public class VideoMongoConverter implements MongoConverter {

	public MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> getMappingContext() {
		// TODO Auto-generated method stub
		return null;
	}

	public ConversionService getConversionService() {
		// TODO Auto-generated method stub
		return null;
	}

	public <R> R read(Class<R> type, DBObject source) {
		// TODO Auto-generated method stub
		return null;
	}

	public void write(Object source, DBObject sink) {
		// TODO Auto-generated method stub

	}

	public Object convertToMongoType(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object convertToMongoType(Object obj,
			TypeInformation<?> typeInformation) {
		// TODO Auto-generated method stub
		return null;
	}

	public DBRef toDBRef(Object object, MongoPersistentProperty referingProperty) {
		// TODO Auto-generated method stub
		return null;
	}

	public MongoTypeMapper getTypeMapper() {
		// TODO Auto-generated method stub
		return null;
	}

}

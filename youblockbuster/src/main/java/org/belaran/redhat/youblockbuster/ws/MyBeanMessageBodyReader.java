package org.belaran.redhat.youblockbuster.ws;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.belaran.redhat.youblockbuster.Movie;

public class MyBeanMessageBodyReader implements MessageBodyReader<Movie> {

	@Override
	public boolean isReadable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return type == Movie.class;
	}

	@Override
	public Movie readFrom(Class<Movie> type,
			Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders,
			InputStream entityStream)
					throws IOException, WebApplicationException {

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Movie.class);
			Movie myBean = (Movie) jaxbContext.createUnmarshaller()
					.unmarshal(entityStream);
			return myBean;
		} catch (JAXBException jaxbException) {
			throw new IllegalArgumentException("Error deserializing a MyBean.",
					jaxbException);
		}
	}
	
}
package com.sg.employer.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomJsonTimestampSeserializerWithoutTimeZone extends JsonSerializer<Date> {

	@Override
	public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(EmployerApiApplicationConstants.TIMESTAMP_FORMAT);
	    String dateString = dateFormat.format(date);
	    jsonGenerator.writeString(dateString);
	}

}

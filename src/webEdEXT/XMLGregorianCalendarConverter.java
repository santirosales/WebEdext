package webEdEXT;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import com.google.common.graph.ElementOrder.Type;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class XMLGregorianCalendarConverter {
    @SuppressWarnings("rawtypes")
	public static class Serializer implements JsonSerializer {
        public Serializer() {
            super();
        }
        public JsonElement serialize(Object t, Type type,
                JsonSerializationContext jsonSerializationContext) {
            XMLGregorianCalendar xgcal = (XMLGregorianCalendar) t;
            return new JsonPrimitive(xgcal.toXMLFormat());
        }
		@Override
		public JsonElement serialize(Object t, java.lang.reflect.Type type, JsonSerializationContext jsonSerializationContext) {
            XMLGregorianCalendar xgcal = (XMLGregorianCalendar) t;
            return new JsonPrimitive(xgcal.toXMLFormat());
		}
    }
    @SuppressWarnings("rawtypes")
	public static class Deserializer implements JsonDeserializer {
        public Object deserialize(JsonElement jsonElement, Type type,
                JsonDeserializationContext jsonDeserializationContext) {
            try {
                return DatatypeFactory.newInstance().newXMLGregorianCalendar(
                        jsonElement.getAsString());
            } catch (Exception e) {
                return null;
            }
        }

		@Override
		public Object deserialize(JsonElement jsonElement, java.lang.reflect.Type type, 
				JsonDeserializationContext jsonDeserializationContext) {
            try {
                return DatatypeFactory.newInstance().newXMLGregorianCalendar(
                        jsonElement.getAsString());
            } catch (Exception e) {
                return null;
            }
		}
    }
}
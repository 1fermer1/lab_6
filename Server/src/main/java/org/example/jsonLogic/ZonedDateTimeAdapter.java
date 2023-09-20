package org.example.jsonLogic;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeAdapter  implements JsonDeserializer<ZonedDateTime>, JsonSerializer<ZonedDateTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;

    @Override
    public ZonedDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        String dateTimeString = jsonElement.getAsJsonPrimitive().getAsString();
        return ZonedDateTime.parse(dateTimeString, formatter);
    }

    @Override
    public JsonElement serialize(ZonedDateTime dateTime, Type type, JsonSerializationContext context) {
        String value = formatter.format(dateTime);
        return context.serialize(value);
    }
}

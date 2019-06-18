package microservices.book.gamification.client;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import microservices.book.gamification.client.dto.MultiplicationResultAttempt;

/**
 * Deserializes an attempt coming from the Multiplication microservice into the
 * Gamification's representation of an attempt.
 * 
 * @author keshav
 *
 */

public class MultiplicationResultAttemptDeserializer extends JsonDeserializer<MultiplicationResultAttempt> {

	@Override
	public MultiplicationResultAttempt deserialize(JsonParser jsonParser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		ObjectCodec objectCodec = jsonParser.getCodec();
		JsonNode node = objectCodec.readTree(jsonParser);
		return new MultiplicationResultAttempt(node.get("user").get("alias").asText(),
				node.get("multiplication").get("factorA").asInt(), node.get("multiplication").get("factorB").asInt(),
				node.get("resultAttempt").asInt(), node.get("correct").asBoolean());
	}

}

package microservices.book.gamification.client;

import microservices.book.gamification.client.dto.MultiplicationResultAttempt;

/**
 * This interface allows us to connect to the Multiplication Microservice. Note
 * that it's agnostic to the way of communication.
 * 
 * @author keshav
 *
 */

public interface MultiplicationResultAttemptClient {

	MultiplicationResultAttempt retrieveMultiplicationResultAttemptById(final Long multiplicationId);

}

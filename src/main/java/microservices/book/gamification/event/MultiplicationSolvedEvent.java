package microservices.book.gamification.event;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Event that models the fact that a multiplication has been solved in the
 * system. Provides some context information about the multiplication.
 * 
 * @author keshav
 *
 */

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class MultiplicationSolvedEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6164910704866973278L;

	private final Long multiplicationResultAttemptId;
	private final Long userId;
	private final boolean correct;

	// Empty constructor for JSON/JPA
	public MultiplicationSolvedEvent() {
		multiplicationResultAttemptId = -1l;
		userId = -1l;
		correct = false;
	}
	
}

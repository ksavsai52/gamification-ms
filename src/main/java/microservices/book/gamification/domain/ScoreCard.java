package microservices.book.gamification.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * This class represents the Score linked to an attempt in the game with an
 * associated user and the timestamp in which the score is registered
 * 
 * @author keshav
 *
 */

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class ScoreCard {

	// default score assigned to this card if not specified
	public static final Integer DEFAULT_SCORE = 10;

	@Id
	@GeneratedValue
	@Column(name = "CARD_ID")
	private final Long cardId;

	@Column(name = "USER_ID")
	private final Long userId;

	@Column(name = "ATTEMPT_ID")
	private final Long attemptId;

	@Column(name = "SCORE_TS")
	private final Long scoreTimestamp;

	@Column(name = "SCORE")
	private final Integer score;

	public ScoreCard() {
		this(null, null, null, 0L, 0);
	}

	public ScoreCard(final Long userId, final Long attemptId) {
		this(null, userId, attemptId, System.currentTimeMillis(), DEFAULT_SCORE);
	}

}

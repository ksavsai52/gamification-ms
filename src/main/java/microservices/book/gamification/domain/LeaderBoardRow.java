package microservices.book.gamification.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Represents a line in our leaderboard, it links a user to a total score.
 * 
 * @author keshav
 *
 */

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class LeaderBoardRow {

	private final Long userId;
	private final Long totalScore;

	public LeaderBoardRow() {
		this(0L, 0L);
	}

}

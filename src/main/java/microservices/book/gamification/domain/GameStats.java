package microservices.book.gamification.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * This object contains the result of one or many iterations of the game. It may
 * contain any combination of {@link ScoreCard} objects and {@link BadgeCard}
 * objects.
 * 
 * It can be used as a delta (as a single game iteration) or to represent the
 * total amount of score/badges.
 * 
 * @author keshav
 *
 */

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class GameStats {

	private final Long userId;
	private final Integer score;
	private final List<Badge> badges;

	public GameStats() {
		this(0L, 0, new ArrayList<>());
	}

	/**
	 * Factory method to build an empty instance (zero score and no badges).
	 * 
	 * @param userId the user's Id
	 * @return a {@link GameStats} object with zero score and no badges
	 */
	public static GameStats emptyStats(final Long userId) {
		return new GameStats(userId, 0, Collections.emptyList());
	}

	/**
	 * 
	 * @return an unmodifiable view of the badge cards list
	 */
	public List<Badge> getBadges() {
		return Collections.unmodifiableList(badges);
	}

}

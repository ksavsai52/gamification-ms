package microservices.book.gamification.domain;

/**
 * Enumeration with different type of badges that user can win
 * 
 * @author keshav
 *
 */

public enum Badge {

	// Badges depending on score
	BRONZE_MULTIPLICATOR, SILVER_MULTIPLICATOR, GOLD_MULTIPLICATOR,
	// other badges won for different conditions
	FIRST_ATTEMPT, FIRST_WON, LUCKY_NUMBER

}

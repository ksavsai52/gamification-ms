package microservices.book.gamification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import microservices.book.gamification.domain.LeaderBoardRow;
import microservices.book.gamification.domain.ScoreCard;

public interface ScoreCardRepository extends CrudRepository<ScoreCard, Long> {

	/**
	 * Gets the total score for given user, being the sum of the scores of all his
	 * {@link ScoreCard}
	 * 
	 * @param userId the id of the user for which the total score should be
	 *               retrieved.
	 * @return the total score for the given user.
	 */
	@Query("SELECT SUM(s.score) FROM microservices.book.gamification.domain.ScoreCard s "
			+ "WHERE s.userId = :userId GROUP BY s.userId")
	Integer getTotalScoreForUser(@Param("userId") final Long userId);

	/**
	 * Retrieves a list of {@link LeaderBoardRow}s representing the Leader Board of
	 * users and their total score.
	 * 
	 * @return the leader board, sorted by highest score first.
	 */
	@Query("SELECT NEW microservices.book.gamification.domain.LeaderBoardRow(s.userId, SUM(s.score)) "
			+ "FROM microservices.book.gamification.domain.ScoreCard s "
			+ "GROUP BY s.userId ORDER BY SUM(s.score) DESC")
	List<LeaderBoardRow> findFirst10();

	/**
	 * Retrieves all the {@link ScoreCard}s for a give user, identified by his user
	 * id.
	 * 
	 * @param userId the id of user.
	 * @return a list containing all the {@link ScoreCard}s for the given user,
	 *         sorted by most recent.
	 */
	List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final Long userId);

}
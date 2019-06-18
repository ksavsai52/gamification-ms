package microservices.book.gamification.service;

import java.util.List;

import microservices.book.gamification.domain.LeaderBoardRow;

/**
 * Provides methods to access the {@link LeaderBoardRow} with users and scores.
 * 
 * @author keshav
 *
 */
public interface LeaderBoardService {

	/**
	 * Retrieves the current {@link LeaderBoardRow} with the top score users.
	 * 
	 * @return the users with highest score.
	 */
	List<LeaderBoardRow> getCurrentLeaderBoard();

}

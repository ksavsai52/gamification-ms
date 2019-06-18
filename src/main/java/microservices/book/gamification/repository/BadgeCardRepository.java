package microservices.book.gamification.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import microservices.book.gamification.domain.BadgeCard;

public interface BadgeCardRepository extends CrudRepository<BadgeCard, Long> {

	/**
	 * Retrieve all the {@link BadgeCard}s for given user.
	 * 
	 * @param userId the id of the user to look for {@link BadgeCard}
	 * @return the list of {@link BadgeCard}s, sorted by most recent.
	 */
	List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(final Long userId);

}

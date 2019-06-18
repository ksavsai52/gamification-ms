package microservices.book.gamification.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import microservices.book.gamification.client.MultiplicationResultAttemptClient;
import microservices.book.gamification.client.dto.MultiplicationResultAttempt;
import microservices.book.gamification.domain.Badge;
import microservices.book.gamification.domain.BadgeCard;
import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.domain.ScoreCard;
import microservices.book.gamification.repository.BadgeCardRepository;
import microservices.book.gamification.repository.ScoreCardRepository;

@Service
@Slf4j
public class GameServiceImpl implements GameService {

	public static final int LUCKY_NUMBER = 42;

	private ScoreCardRepository scoreCardRepository;

	private BadgeCardRepository badgeCardRepository;

	private MultiplicationResultAttemptClient multiplicationResultAttemptClient;

	public GameServiceImpl(ScoreCardRepository scoreCardRepository, BadgeCardRepository badgeCardRepository,
			MultiplicationResultAttemptClient multiplicationResultAttemptClient) {
		this.scoreCardRepository = scoreCardRepository;
		this.badgeCardRepository = badgeCardRepository;
		this.multiplicationResultAttemptClient = multiplicationResultAttemptClient;
	}

	@Override
	public GameStats newAttemptForUser(final Long userId, final Long attemptId, final boolean correct) {
		// for the first version, we'll give points only if it's correct
		if (correct) {
			ScoreCard scoreCard = new ScoreCard(userId, attemptId);
			scoreCardRepository.save(scoreCard);
			log.info("User with id {} scored {} points for attempt id {}", userId, scoreCard.getScore(), attemptId);

			List<BadgeCard> badgeCards = processForBadges(userId, attemptId);
			return new GameStats(userId, scoreCard.getScore(),
					badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
		}
		return GameStats.emptyStats(userId);
	}

	/**
	 * Checks the total score and the different score cards obtained to give new
	 * badges in case their conditions are met.
	 * 
	 * @param userId
	 * @param attemptId
	 * @return list of {@link BadgeCard} for provided user id and attempt id.
	 */
	private List<BadgeCard> processForBadges(final Long userId, final Long attemptId) {
		List<BadgeCard> badgeCards = new ArrayList<>();
		int totalScore = scoreCardRepository.getTotalScoreForUser(userId);
		log.info("New score for user {} is {}", userId, totalScore);

		List<ScoreCard> scoreCardList = scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId);
		List<BadgeCard> badgeCardList = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);

		// badges depending on score
		checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.BRONZE_MULTIPLICATOR, totalScore, 100, userId)
				.ifPresent(badgeCards::add);
		checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.SILVER_MULTIPLICATOR, totalScore, 500, userId)
				.ifPresent(badgeCards::add);
		checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.GOLD_MULTIPLICATOR, totalScore, 999, userId)
				.ifPresent(badgeCards::add);

		// first won badge
		if (scoreCardList.size() == 1 && !containsBadge(badgeCardList, Badge.FIRST_WON)) {
			BadgeCard firstWonBadgeCard = giveBadgeToUser(Badge.FIRST_WON, userId);
			badgeCards.add(firstWonBadgeCard);
		}

		// lucky number badge
		MultiplicationResultAttempt attempt = multiplicationResultAttemptClient
				.retrieveMultiplicationResultAttemptById(attemptId);
		if (!containsBadge(badgeCardList, Badge.LUCKY_NUMBER) && (LUCKY_NUMBER == attempt.getMultiplicationFactorA()
				|| LUCKY_NUMBER == attempt.getMultiplicationFactorB())) {
			BadgeCard luckyNumberBadge = giveBadgeToUser(Badge.LUCKY_NUMBER, userId);
			badgeCards.add(luckyNumberBadge);
		}

		return badgeCards;
	}

	/**
	 * Assigns a new badge to given user.
	 * 
	 * @param badge
	 * @param userId
	 * @return {@link BadgeCard} for user.
	 */
	private BadgeCard giveBadgeToUser(final Badge badge, final Long userId) {
		BadgeCard badgeCard = new BadgeCard(userId, badge);
		badgeCardRepository.save(badgeCard);
		log.info("User with id {} won a new badge: {}", userId, badge);
		return badgeCard;
	}

	/**
	 * Checks if the passed list of badges includes the one being checked.
	 * 
	 * @param badgeCardList
	 * @param badge
	 * @return boolean value whether badge is in badgeCardList or not.
	 */
	private boolean containsBadge(final List<BadgeCard> badgeCardList, final Badge badge) {
		return badgeCardList.stream().anyMatch(b -> b.getBadge().equals(badge));
	}

	/**
	 * Convenience method to check the current score against different threshold to
	 * gain badges. It also assigns badge to user if the conditions are met.
	 * 
	 * @param badgeCardList
	 * @param badge
	 * @param score
	 * @param scoreThreshold
	 * @param userId
	 * @return {@link BadgeCard} according to user score and his/her current badges.
	 */
	private Optional<BadgeCard> checkAndGiveBadgeBasedOnScore(final List<BadgeCard> badgeCardList, final Badge badge,
			final int score, final int scoreThreshold, final Long userId) {
		if (score >= scoreThreshold && !containsBadge(badgeCardList, badge)) {
			return Optional.of(giveBadgeToUser(badge, userId));
		}
		return Optional.empty();
	}

	@Override
	public GameStats retrieveStatsForUser(final Long userId) {
		int score = scoreCardRepository.getTotalScoreForUser(userId);
		List<BadgeCard> badgeCards = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
		return new GameStats(userId, score, badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
	}

}

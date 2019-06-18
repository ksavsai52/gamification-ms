package microservices.book.gamification.service;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import microservices.book.gamification.domain.LeaderBoardRow;
import microservices.book.gamification.repository.ScoreCardRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class LeaderBoardServiceImplTest {

	private LeaderBoardServiceImpl leaderBoardService;

	@Mock
	private ScoreCardRepository scoreCardRepository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		leaderBoardService = new LeaderBoardServiceImpl(scoreCardRepository);
	}

	@Test
	public void testGetCurrentLeaderBoard() {
		// given
		Long userId = 1l;
		LeaderBoardRow leaderBoardRow = new LeaderBoardRow(userId, 300L);
		List<LeaderBoardRow> expectedLeaderBoardRows = Collections.singletonList(leaderBoardRow);
		given(scoreCardRepository.findFirst10()).willReturn(expectedLeaderBoardRows);

		// when
		List<LeaderBoardRow> leaderBoardRows = leaderBoardService.getCurrentLeaderBoard();

		// then
		assertThat(leaderBoardRows).isEqualTo(expectedLeaderBoardRows);
	}

}

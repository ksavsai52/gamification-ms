package microservices.book.gamification.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import microservices.book.gamification.domain.LeaderBoardRow;
import microservices.book.gamification.service.LeaderBoardService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(LeaderBoardController.class)
public class LeaderBoardControllerTest {

	@MockBean
	private LeaderBoardService leaderBoardService;

	@Autowired
	private MockMvc mvc;

	private JacksonTester<List<LeaderBoardRow>> json;

	@Before
	public void setUp() {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	@Test
	public void testGetLeaderBoard() throws Exception {
		// given
		LeaderBoardRow leaderBoardRow1 = new LeaderBoardRow(1l, 500l);
		LeaderBoardRow leaderBoardRow2 = new LeaderBoardRow(2l, 400l);
		List<LeaderBoardRow> leaderBoardRows = new ArrayList<>();
		Collections.addAll(leaderBoardRows, leaderBoardRow1, leaderBoardRow2);
		given(leaderBoardService.getCurrentLeaderBoard()).willReturn(leaderBoardRows);

		// when
		MockHttpServletResponse response = mvc.perform(get("/leaders").accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(json.write(leaderBoardRows).getJson());
	}

}

package com.yly.app.rest;


import com.yly.app.api.Flower;
import com.yly.domain.service.FlowerService;
// import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.misusing.InvalidUseOfMatchersException;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FlowerControllerUnitTest {

	@InjectMocks
	private FlowerController flowerController;

	@Mock
	private FlowerService flowerService;

	@Test
	public void givenPoppyFlower_whenUsingDoReturn_thenCorrect() {
		doReturn("Flower").when(flowerService).analyze("poppy");

		String response = flowerController.isAFlower("poppy");
		assertThat(response).isEqualTo("Flower");
	}

	@Test
	public void givenAnyString_whenUsingArgumentMatcher_thenCorrect() {
		when(flowerService.analyze(anyString())).thenReturn("Flower");

		String response = flowerController.isAFlower("violetta");
		assertThat(response).isEqualTo("Flower");
	}

	@Test(expected = InvalidUseOfMatchersException.class)
	public void whenIncorrectMatchers_thenThrowsError() {
		when(flowerService.isABigFlower("poppy", anyInt())).thenReturn(true);

		Flower flower = new Flower("poppy", 15);

		Boolean response = flowerController.isABigFlower(flower);
		assertThat(response).isTrue();
	}

	@Test
	public void whenCorrectMatchers_thenCorrect() {
		when(flowerService.isABigFlower(eq("poppy"), anyInt())).thenReturn(true);

		Flower flower = new Flower("poppy", 15);

		Boolean response = flowerController.isABigFlower(flower);
		assertThat(response).isTrue();
	}

	@Test(expected = InvalidUseOfMatchersException.class)
	public void whenUsingMatchersAsReturnValue_thenThrowsError() {
		flowerController.isAFlower("poppy");

		String orMatcher = or(eq("poppy"), endsWith("y"));
		verify(flowerService).analyze(orMatcher);
	}

	@Test
	public void whenUsingMatchersAsOngoingStubbing_thenCorrect1() {
		flowerController.isAFlower("poppy");

		verify(flowerService).analyze(or(eq("poppy"), endsWith("y")));
	}

	@Test
	public void whenUsingMatchersAsOngoingStubbing_thenCorrect2() {
		flowerController.isAFlower("lily");

		verify(flowerService).analyze(or(eq("poppy"), endsWith("y")));
	}
}

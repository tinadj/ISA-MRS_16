package org.tim16.booker;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tim16.booker.controller.TestController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookerApplicationTests {

	@Autowired
	private TestController controller;

	@Test
	public void contextLoads() throws Exception{
		assertThat(controller).isNotNull();
	}

}

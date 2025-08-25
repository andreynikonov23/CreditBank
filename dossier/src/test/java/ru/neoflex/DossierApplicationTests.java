package ru.neoflex;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("/application-local.properties")
class DossierApplicationTests {

	@Test
	void contextLoads() {
	}

}

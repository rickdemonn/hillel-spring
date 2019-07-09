package hillel.spring;

import hillel.spring.HomeWork3.Hw3Repo;
import hillel.spring.HomeWork3.Hw3Service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HillelSpringApplicationTests {



	@Test
	public void contextLoads() {
	}

	@Test
	public void getRandomTest(){
		Hw3Repo hw3Repo = new Hw3Repo();
		Hw3Service hw3Service = new Hw3Service(hw3Repo);

		int en = 0;
		int fr = 0;
		int it = 0;
		for (int i = 0; i < 100; i++) {
			String s = hw3Service.getRandomGreeting();
			if(s.equals("Hello")){
				en++;
			} else if (s.equals("Salut")){
				fr++;
			} else
				it++;
		}
		System.out.println(en + " " + fr + " " + it);
		assertThat(en).isGreaterThan(20);
		assertThat(fr).isGreaterThan(20);
		assertThat(it).isGreaterThan(20);
	}


}

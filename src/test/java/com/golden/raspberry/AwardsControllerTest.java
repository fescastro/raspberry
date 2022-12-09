package com.golden.raspberry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.golden.raspberry.dto.IntervalAwardsProducerDto;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AwardsControllerTest {

	@Autowired
    private TestRestTemplate testRestTemplate;
	
    @Test
    public void getAwardsCorrects() {

        ResponseEntity<IntervalAwardsProducerDto> response = this.testRestTemplate
            .exchange("/awards/interval-awards-producers", HttpMethod.GET, null, IntervalAwardsProducerDto.class);
    
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getMin().size(), 1);
        assertEquals(response.getBody().getMax().size(), 1);
    }
    
    @Test
    public void getAwardsMinIncorrect() {

        ResponseEntity<IntervalAwardsProducerDto> response = this.testRestTemplate
            .exchange("/awards/interval-awards-producers", HttpMethod.GET, null, IntervalAwardsProducerDto.class);
    
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotEquals(response.getBody().getMin().size(), 0);
        assertEquals(response.getBody().getMax().size(), 1);
    }
    
    @Test
    public void getAwardsMaxIncorrect() {

        ResponseEntity<IntervalAwardsProducerDto> response = this.testRestTemplate
            .exchange("/awards/interval-awards-producers", HttpMethod.GET, null, IntervalAwardsProducerDto.class);
    
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getMin().size(), 1);
        assertNotEquals(response.getBody().getMax().size(), 3);
    }
    
    @Test
    public void getAwardsMAdressIncorrect() {

        ResponseEntity<IntervalAwardsProducerDto> response = this.testRestTemplate
            .exchange("/interval-awards-producers", HttpMethod.GET, null, IntervalAwardsProducerDto.class);
    
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
	
}

package com.udacity.pricing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.pricing.api.PricingController;
import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.service.PricingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PricingController.class)
public class PricingServiceApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	PricingService pricingService;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetVehiclePrice() throws Exception {
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/services/price")
						.param("vehicleId","1"))
				.andExpect(status().isOk());
		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		Price price = objectMapper.readValue(contentAsString,Price.class);
		Assert.isTrue(price !=null,"pricing is null");
	}

	@Test
	public void testGetVehiclePriceOutOfRange() throws Exception {
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/services/price")
						.param("vehicleId","21"))
				.andExpect(status().is4xxClientError());
	}


}

package com.ojtsin.demo;

import java.net.URI;
import java.util.ArrayList;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ojtsin.demo.dto.SCMinfoDTO;
import com.ojtsin.demo.repository.UserSCMRepository;

@SpringBootTest
class OjtSinApplicationTests {

	private UserSCMRepository repository;
	
	public OjtSinApplicationTests(UserSCMRepository repository) {
		this.repository = repository;
	}
	
//	@Test
//	void contextLoads() {
//	}

	// assert equal
//	@Test
//	public static void getInfo() throws JsonMappingException, JsonProcessingException, ParseException {
//		URI uri = UriComponentsBuilder
//                .fromUriString("http://222.122.235.25:3000")
//                .path("/scm/info/1/123-4567")
//                .encode()
//                .build()
//                .toUri();
//		
//		RestTemplate restTemplate = new RestTemplate();
//		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
//        String body = response.getBody();
//        JSONParser parser = new JSONParser();
//        
//        ArrayList<Object> obj = (ArrayList<Object>) parser.parse(body);
//
//    		ObjectMapper objectMapper = new ObjectMapper();
//    		objectMapper.registerModule(new JavaTimeModule());
//    		Boolean successStatus = true;
//    		for(int i =0;i<obj.size();i++) {
//    			String json = objectMapper.writeValueAsString(obj.get(i)); //json object를 json으로 변환
//
//    			SCMinfoDTO scminfodto = objectMapper.readValue(json,SCMinfoDTO.class);
//    			 
//    			String json2 = objectMapper.writeValueAsString(scminfodto.getSeller_info());
//
//    			SCMinfoDTO.Seller_info sellerinfo = objectMapper.readValue(json2, SCMinfoDTO.Seller_info.class);
//    			System.out.println("Test code");
//    			System.out.println(sellerinfo.getBank_name());
//    			 
//    			//int res = service.saveinfo(scminfodto, sellerinfo);
////    			if(res ==1) {
////    					successStatus = false;
////    			}
//
//    		}
//        
////        ObjectMapper objectMapper = new ObjectMapper();
////        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);   //선언한 필드만 매핑
//
////        String jsonSting = objectMapper.writeValueAsString(body);
////        scminfoDTO dtos[] = objectMapper.readValue(jsonSting,Event[].class);
//	}  
	
}

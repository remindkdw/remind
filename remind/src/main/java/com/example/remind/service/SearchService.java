package com.example.remind.service;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.remind.repository.SearchRepository;
import com.example.remind.value.Search;
import com.example.remind.value.SearchResultInterface;

@Service
public class SearchService {
	
    private final String kakaoUrl = "https://dapi.kakao.com/v2/search/blog";
    private final String kakaoKey = "ee936858b0a9a47440206df7925f8172";
    
    private final String naver_blog_url = "https://openapi.naver.com/v1/search/blog.json";
    private final String naver_clinet_id = "vK6kk2utvM8yr7d5lcuq";
    private final String naver_client_secret = "seJK80pn4k";
	
	@Autowired
	private SearchRepository searchRepository;
	
	public Search saveSearch(Search search) {
		return searchRepository.save(search);	
	}
	
    public Map callKakaoApi(String keyword, int page, int size, String sort) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", "KakaoAK "+kakaoKey);
		
		HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders); //엔티티로 만들기
		URI targetUrl = UriComponentsBuilder
		    .fromUriString(kakaoUrl) 			//url
		    .queryParam("query", keyword) 		//param
		    .queryParam("page", page)
		    .queryParam("size", size)
		    .queryParam("sort", sort)
		    .build()
		    .encode(StandardCharsets.UTF_8) 	//인코딩
		    .toUri();

		ResponseEntity<Map> result = restTemplate.exchange(targetUrl, HttpMethod.GET, httpEntity, Map.class);
		
		Search search = new Search();
		search.setKeyWord(keyword);
		searchRepository.save(search);
		
		return result.getBody(); 				//내용 반환	
    }
    
    public Map callNaverApi(String keyword, int page, int size, String sort) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("X-Naver-Client-Id", naver_clinet_id);
		httpHeaders.set("X-Naver-Client-Secret", naver_client_secret);
		
		HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders); //엔티티로 만들기
		URI targetUrl = UriComponentsBuilder
		    .fromUriString(naver_blog_url) 			//url
		    .queryParam("query", keyword) 			//param
		    .queryParam("display", page)
		    .queryParam("start", size)
		    .queryParam("sort", sort)
		    .build()
		    .encode(StandardCharsets.UTF_8) 		//인코딩
		    .toUri();
		
		Search search = new Search();
		search.setKeyWord(keyword);
		searchRepository.save(search);

		ResponseEntity<Map> result = restTemplate.exchange(targetUrl, HttpMethod.GET, httpEntity, Map.class);
		return result.getBody(); 					//내용 반환	
    }
	
	public List<SearchResultInterface> selectTop10() {
		return searchRepository.findGroupByReportWithNativeQuery();	
	}
}
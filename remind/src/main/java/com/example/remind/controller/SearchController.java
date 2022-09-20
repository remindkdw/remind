package com.example.remind.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.remind.exception.ResourceNotFoundException;
import com.example.remind.service.SearchService;
import com.example.remind.value.SearchResultInterface;

@RestController
@RequestMapping("/api/v1")
public class SearchController {
	
    @Autowired
    private SearchService searchService;
    
    @GetMapping("/blog")
    public Map callApi1(@RequestParam(value = "keyword") String keyword, 
    		            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
    		            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
    		            @RequestParam(value = "sort", required = false, defaultValue = "accuracy") String sort) throws ResourceNotFoundException {
		

		Map result = new HashMap();
    
	    result = searchService.callKakaoApi(keyword, page, size, sort);
	    
	    if (result.isEmpty()) {
	    	result = searchService.callNaverApi(keyword, page, size, "sim");
	    }
	    
	    return result;

    }
    
    @GetMapping("/blog/searchTOP10")
    public List < SearchResultInterface	 > selectTop10() throws ResourceNotFoundException {
    	return searchService.selectTop10();
    }

}
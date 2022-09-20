package com.example.remind.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.remind.value.Search;
import com.example.remind.value.SearchResultInterface;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long>{
	
	@Query(nativeQuery = true, value = "select top 10 a.keyWord, a.searchCount \r\n"
			+ "from ( \r\n"
			+ "        select keyword as keyWord, count(keyword) as searchCount from search group by keyword \r\n"
			+ "       ) a \r\n"
			+ "order by a.searchCount desc ")
	List<SearchResultInterface> findGroupByReportWithNativeQuery();

}
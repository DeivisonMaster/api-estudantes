package br.com.api.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class RestTemplateResponsePageable<T> extends PageImpl<T>{
	private static final long serialVersionUID = 1L;
	private boolean last;
	private boolean first;
	private int totalPages;

	public RestTemplateResponsePageable(@JsonProperty("content") List<T> content, 
			@JsonProperty("number") int page, @JsonProperty("size") int size, 
			@JsonProperty("totalElements") long totalElements,
			@JsonProperty("sort") @JsonDeserialize(using = SortDesserializador.class) Sort sort) {
		
		super(content, new PageRequest(page, size, sort), totalElements);
	}
	
	public RestTemplateResponsePageable() {
		super(new ArrayList<>());
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
	
}

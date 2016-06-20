package com.mime.jba.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mime.jba.entity.Blog;
import com.mime.jba.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {

	List<Item> findByBlog(Blog blog, Pageable pageable);
		
}

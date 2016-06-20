package com.mime.jba.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mime.jba.entity.Blog;
import com.mime.jba.entity.User;

public interface BlogRepository extends JpaRepository<Blog, Integer>{

	List<Blog> findByUser(User user);
	
}

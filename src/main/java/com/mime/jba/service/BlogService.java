package com.mime.jba.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.mime.jba.entity.Blog;
import com.mime.jba.entity.Item;
import com.mime.jba.entity.User;
import com.mime.jba.exception.RssException;
import com.mime.jba.repository.BlogRepository;
import com.mime.jba.repository.ItemRepository;
import com.mime.jba.repository.UserRepository;

@Service
public class BlogService {
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RssService rssService;
	
	@Autowired
	private ItemRepository itemRepository;
	
	public void saveItems(Blog blog){
		try {
			List<Item> items = rssService.getItems(blog.getUrl());
			for (Item item : items){
				Item savedItem = itemRepository.findByBlogAndLink(blog, item.getLink());
				if (savedItem == null){
					item.setBlog(blog);
					itemRepository.save(item);
				}
			}
		} 
		catch (RssException e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(fixedDelay = 3600000)  // 1 hour
	public void reloadBlogs(){
		List<Blog> blogs = blogRepository.findAll();
		for (Blog blog : blogs){
			saveItems(blog);
		}
	}
	
	public void save(Blog blog, String name){
		User user = userRepository.findByName(name);
		blog.setUser(user);
		blogRepository.save(blog);
		saveItems(blog);
	}

	@PreAuthorize("#blog.user.name == authentication.name or hasRole('ROLE_ADMIN')")
	public void delete(@P("blog") Blog blog) {
		blogRepository.delete(blog);
	}

	public Blog findOne(int id) {
		return blogRepository.findOne(id);
	}

}

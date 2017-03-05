package com.oppalove.spring.redis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.oppalove.spring.redis.model.Author;
import com.oppalove.spring.redis.model.Book;

@SpringBootApplication
public class App implements CommandLineRunner {

	@Autowired
	private RedisTemplate<String, Author> template;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {

		System.out.println("start");

		Author author = getAuthor();
		ValueOperations<String, Author> ops = this.template.opsForValue();
		String key = "book:name:" + author.getId();
		System.out.println(key);
		ops.set(key, author);

		Author fromDbAuthor;
		fromDbAuthor = ops.get(key);
		System.out.println(fromDbAuthor);
	}

	private Author getAuthor() {
		Author seung = new Author();
		seung.setId(1);
		seung.setAge(38);
		seung.setName("Han SeungHyeon");

		Book first = new Book();
		first.setBookId(1);
		first.setTitle("Gogo land");
		first.setDescription("i don't know");

		Book second = new Book();
		second.setBookId(2);
		second.setTitle("Java world");
		second.setDescription("Let's study");

		List<Book> books = new ArrayList<Book>();
		books.add(first);
		books.add(second);

		seung.setBooks(books);
		return seung;
	}
}

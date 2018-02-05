package com.kittydaddy.app.controller.search;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kittydaddy.metadata.system.domain.search.SRoleEntity;
import com.kittydaddy.service.search.SRoleEntityService;

@RestController
@RequestMapping(value="/search")
public class SearchTest{
	    @Autowired
	    private SRoleEntityService sRoleEntityService;

	    @Autowired
	    private ElasticsearchTemplate esTemplate;

	    @Before
	    public void before() {
	        esTemplate.deleteIndex(SRoleEntity.class);
	        esTemplate.createIndex(SRoleEntity.class);
	        esTemplate.putMapping(SRoleEntity.class);
	        esTemplate.refresh(SRoleEntity.class);
	    }

	    @RequestMapping(method=RequestMethod.GET,value="/saveRole")
	    public void testSave() {
	    	SRoleEntity role = new SRoleEntity("1b036627-da39-11e7-8df6-d89ef30391b6", "超级管理员","3278ac26-da39-11e7-8df6-d89ef30391b6", "root", "root", new Date(), new Date());
	    	SRoleEntity testRoleEntity = sRoleEntityService.save(role);
	        System.out.println(testRoleEntity.getId());
	    }

	    @Test
	    public void testFindOne() {

	    	SRoleEntity role = new SRoleEntity("1b036627-da39-11e7-8df6-d89ef30391b6", "超级管理员","3278ac26-da39-11e7-8df6-d89ef30391b6", "root", "root", new Date(), new Date());
//	    	bookService.save(book);

//	        Book testBook = bookService.findOne(book.getId());
//
//	        assertNotNull(testBook.getId());
//	        assertEquals(testBook.getTitle(), book.getTitle());
//	        assertEquals(testBook.getAuthor(), book.getAuthor());
//	        assertEquals(testBook.getReleaseDate(), book.getReleaseDate());

	    }

	    @RequestMapping(method=RequestMethod.GET,value="/findByName")
	    public String testFindByTitle() {
	    	List<SRoleEntity> roleEntity = sRoleEntityService.findByRoleName("超级");
	    	return roleEntity.toString();
//	        Book book = new Book("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017");
//	        bookService.save(book);
//
//	        List<Book> byTitle = bookService.findByTitle(book.getTitle());
//	        assertThat(byTitle.size(), is(1));
	    }

	    @Test
	    public void testFindByAuthor() {

//	        List<Book> bookList = new ArrayList<>();
//
//	        bookList.add(new Book("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017"));
//	        bookList.add(new Book("1002", "Apache Lucene Basics", "Rambabu Posa", "13-MAR-2017"));
//	        bookList.add(new Book("1003", "Apache Solr Basics", "Rambabu Posa", "21-MAR-2017"));
//	        bookList.add(new Book("1007", "Spring Data + ElasticSearch", "Rambabu Posa", "01-APR-2017"));
//	        bookList.add(new Book("1008", "Spring Boot + MongoDB", "Mkyong", "25-FEB-2017"));
//
//	        for (Book book : bookList) {
//	            bookService.save(book);
//	        }
//
//	        Page<Book> byAuthor = bookService.findByAuthor("Rambabu Posa", new PageRequest(0, 10));
//	        assertThat(byAuthor.getTotalElements(), is(4L));
//
//	        Page<Book> byAuthor2 = bookService.findByAuthor("Mkyong", new PageRequest(0, 10));
//	        assertThat(byAuthor2.getTotalElements(), is(1L));

	    }

	    @Test
	    public void testDelete() {

//	        Book book = new Book("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017");
//	        bookService.save(book);
//	        bookService.delete(book);
//	        Book testBook = bookService.findOne(book.getId());
//	        assertNull(testBook);
	    }
}

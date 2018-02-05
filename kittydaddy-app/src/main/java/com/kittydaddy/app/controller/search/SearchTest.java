package com.kittydaddy.app.controller.search;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.kittydaddy.search.model.system.SRoleEntity;
import com.kittydaddy.search.service.system.SRoleEntityService;

@RestController
@RequestMapping(value="/search")
public class SearchTest{
	    @Autowired
	    private SRoleEntityService sRoleEntityService;

//	    @Autowired
//	    private ElasticsearchTemplate esTemplate;
//
//	    @Before
//	    public void before() {
//	        esTemplate.deleteIndex(SRoleEntity.class);
//	        esTemplate.createIndex(SRoleEntity.class);
//	        esTemplate.putMapping(SRoleEntity.class);
//	        esTemplate.refresh(SRoleEntity.class);
//	    }

	    @RequestMapping(method=RequestMethod.GET,value="/saveRole")
	    public void testSave() {
	    	SRoleEntity role = new SRoleEntity("1b036627-da39-11e7-8df6-d89ef30391b6", "超级管理员","3278ac26-da39-11e7-8df6-d89ef30391b6", "root", "root", new Date(), new Date());
	    	SRoleEntity testRoleEntity = sRoleEntityService.save(role);
	        System.out.println(testRoleEntity.getId());
	    }

}

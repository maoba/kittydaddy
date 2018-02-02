package com.kittydaddy.app.controller.search;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.kittydaddy.app.controller.system.BaseController;
import com.kittydaddy.common.utils.KEsPage;
import com.kittydaddy.search.KElasticsearchService;

@RestController
@RequestMapping(value="/search")
public class SearchController extends BaseController{
	
	@Autowired
	private KElasticsearchService kElasticsearchService;
	
	@RequestMapping(method=RequestMethod.GET,value="saveTest")
	public void createIndexTest() {
		kElasticsearchService.createIndex("ymq_index");
		kElasticsearchService.createIndex("ymq_indexsssss");
	}
	
	@RequestMapping(method=RequestMethod.GET,value="deleteTest")
	public void deleteIndexTest() {
		kElasticsearchService.deleteIndex("ymq_indexsssss");
	}
	
	@RequestMapping(method=RequestMethod.GET,value="indexExistTest")
	public void isIndexExistTest() {
		System.out.println(kElasticsearchService.isIndexExist("ymq_index"));
	}
	
	@RequestMapping(method=RequestMethod.GET,value="addDataTest")
	public void addDataTest() {
	    for (int i = 0; i < 100; i++) {
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("name", "鹏磊" + i);
	        map.put("age", i);
	        map.put("interests", new String[]{"阅读", "学习"});
	        map.put("about", "世界上没有优秀的理念，只有脚踏实地的结果");
	        map.put("processTime", new Date());
	        kElasticsearchService.addData(JSONObject.parseObject(JSONObject.toJSONString(map)), "ymq_index", "about_test", "id=" + i);
	    }
	}
	
	@RequestMapping(method=RequestMethod.GET,value="deleteDataByIdTest")
	public void deleteDataByIdTest() {
	    for (int i = 0; i < 10; i++) {
	    	kElasticsearchService.deleteDataById("ymq_index", "about_test", "id=" + i);
	    }
	}
	
	/**
	 * 通过ID 更新数据
	 * <p>
	 * jsonObject 要增加的数据
	 * index      索引，类似数据库
	 * type       类型，类似表
	 * id         数据ID
	 */
	@RequestMapping(method=RequestMethod.GET,value="updateDataByIdTest")
	public void updateDataByIdTest() {
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("name", "鹏磊");
	    map.put("age", 11);
	    map.put("interests", new String[]{"阅读", "学习"});
	    map.put("about", "这条数据被修改");
	    map.put("processTime", new Date());
	    kElasticsearchService.updateDataById(JSONObject.parseObject(JSONObject.toJSONString(map)), "ymq_index", "about_test", "id=11");
	}
	
	/**
	 * 通过ID获取数据
	 * <p>
	 * index  索引，类似数据库
	 * type   类型，类似表
	 * id     数据ID
	 * fields 需要显示的字段，逗号分隔（缺省为全部字段）
	 */
	@RequestMapping(method=RequestMethod.GET,value="searchDataByIdTest")
	public void searchDataByIdTest() {
	    Map<String, Object> map = kElasticsearchService.searchDataById("ymq_index", "about_test", "id=11", null);
	    System.out.println(JSONObject.toJSONString(map));
	}
	
	
	/**
	 * 使用分词查询
	 * <p>
	 * index          索引名称
	 * type           类型名称,可传入多个type逗号分隔
	 * startTime      开始时间
	 * endTime        结束时间
	 * size           文档大小限制
	 * fields         需要显示的字段，逗号分隔（缺省为全部字段）
	 * sortField      排序字段
	 * matchPhrase    true 使用，短语精准匹配
	 * highlightField 高亮字段
	 * matchStr       过滤条件（xxx=111,aaa=222）
	 */
	@RequestMapping(method=RequestMethod.GET,value="searchListData")
	public void searchListData() {

	    List<Map<String, Object>> list = kElasticsearchService.searchListData("ymq_index", "about_test", 1509959382607l, 1509959383865l, 0, "", "", false, "", "name=鹏磊");

	    for (Map<String, Object> item : list) {

	        System.out.println(JSONObject.toJSONString(item));
	    }
	}
	
	/**
	 * 使用分词查询,并分页
	 * <p>
	 * index          索引名称
	 * type           类型名称,可传入多个type逗号分隔
	 * currentPage    当前页
	 * pageSize       每页显示条数
	 * startTime      开始时间
	 * endTime        结束时间
	 * fields         需要显示的字段，逗号分隔（缺省为全部字段）
	 * sortField      排序字段
	 * matchPhrase    true 使用，短语精准匹配
	 * highlightField 高亮字段
	 * matchStr       过滤条件（xxx=111,aaa=222）
	 */
	@RequestMapping(method=RequestMethod.GET,value="searchDataPage")
	public void searchDataPage() {

	    KEsPage esPage = kElasticsearchService.searchDataPage("ymq_index", "about_test", 10, 5, 1509943495299l, 1509943497954l, "", "processTime", false, "about", "about=鹏磊");

	    for (Map<String, Object> item : esPage.getRecordList()) {

	        System.out.println(JSONObject.toJSONString(item));
	    }

	}
}

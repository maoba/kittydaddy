package com.kittydaddy.search.repository.vcontent;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.kittydaddy.search.model.vcontent.SKVContentSourceEntity;

//搜索引擎内容源查询
public interface SKVContentSourceRepository  extends ElasticsearchRepository<SKVContentSourceEntity, String>{

}

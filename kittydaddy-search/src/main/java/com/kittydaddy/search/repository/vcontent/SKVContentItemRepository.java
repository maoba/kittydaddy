package com.kittydaddy.search.repository.vcontent;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.kittydaddy.search.model.vcontent.SKVContentItemEntity;

public interface SKVContentItemRepository  extends ElasticsearchRepository<SKVContentItemEntity, String>{

}

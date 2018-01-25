package com.kittydaddy.service.vcontent.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kittydaddy.metadata.vcontent.dao.KVContentItemEntityMapper;
import com.kittydaddy.metadata.vcontent.domain.KVContentItemEntity;
import com.kittydaddy.service.vcontent.KVContentItemService;

/**
 * @author kittydaddy
 */
@Service
public class KVContentItemServiceImpl implements KVContentItemService{
    @Autowired
	private KVContentItemEntityMapper kVContentItemEntityMapper;
    
	@Override
	public PageInfo<KVContentItemEntity> queryKvContentItemByPage(String contentId,Integer pageIndex, Integer pageSize) {
		PageHelper.startPage(pageIndex,pageSize, true, null, true);
		List<KVContentItemEntity> entitys = kVContentItemEntityMapper.queryItemByContentId(contentId);
		PageInfo<KVContentItemEntity> page = new PageInfo<KVContentItemEntity>(entitys);
		return page;
	}

}

package com.kittydaddy.service.vcontent.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kittydaddy.metadata.vcontent.dao.KVContentSourceEntityMapper;
import com.kittydaddy.metadata.vcontent.domain.KVContentSourceEntity;
import com.kittydaddy.service.vcontent.KVContentSourceService;

@Service
public class KVContentSourceServiceImpl implements KVContentSourceService{
        @Autowired
        private KVContentSourceEntityMapper kvContentSourceEntityMapper;

		@Override
		public PageInfo<KVContentSourceEntity> queryKvContentItemSourceByPage(String relativeId, String relativeType,
				Integer pageIndex, Integer pageSize) {
			PageHelper.startPage(pageIndex,pageSize, true, null, true);
			List<KVContentSourceEntity> entitys = kvContentSourceEntityMapper.findByRelativeTypeAndRelativeId(relativeType, relativeId);
			PageInfo<KVContentSourceEntity> page = new PageInfo<KVContentSourceEntity>(entitys);
			return page;
		}
        
        
}

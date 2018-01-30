package com.kittydaddy.service.vcontent.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kittydaddy.common.enums.StatusEnum;
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

		@Override
		public void saveUpdateKVContentItemSource(Map<String, Object> params) {
			String relativeType = params.get("relativeType")==null?"":params.get("relativeType").toString();
			String relativeId = params.get("relativeId")==null?"":params.get("relativeId").toString();
			String playUrl = params.get("playUrl")==null?"":params.get("playUrl").toString();
			String duration = params.get("duration")==null?"":params.get("duration").toString();
			String imageUrl = params.get("imageUrl")==null?"":params.get("imageUrl").toString();
			String isFree = params.get("isFree")==null?"":params.get("isFree").toString();
			String source = params.get("source")==null?"":params.get("source").toString();
			
			if(params.get("id") == null){//表示新增
				KVContentSourceEntity kvContentSourceEntity = new KVContentSourceEntity();
				kvContentSourceEntity.setDuration(duration);
				kvContentSourceEntity.setCreateTime(new Date());
				kvContentSourceEntity.setImageUrl(imageUrl);
				kvContentSourceEntity.setIsFree(Integer.parseInt(isFree));
				kvContentSourceEntity.setPlayUrl(playUrl);
				kvContentSourceEntity.setSource(source);
				kvContentSourceEntity.setStatus(StatusEnum.VALID.getValue());
				kvContentSourceEntity.setRelativeId(relativeId);
				kvContentSourceEntity.setRelativeType(relativeType);
				kvContentSourceEntityMapper.insert(kvContentSourceEntity);
				
			}else{//表示更新
				
				KVContentSourceEntity oldKvContentSourceEntity = kvContentSourceEntityMapper.selectByPrimaryKey(params.get("id").toString());
				oldKvContentSourceEntity.setDuration(duration);
				oldKvContentSourceEntity.setUpdateTime(new Date());
				oldKvContentSourceEntity.setImageUrl(imageUrl);
				oldKvContentSourceEntity.setIsFree(Integer.parseInt(isFree));
				oldKvContentSourceEntity.setPlayUrl(playUrl);
				oldKvContentSourceEntity.setSource(source);
				oldKvContentSourceEntity.setStatus(StatusEnum.VALID.getValue());
				kvContentSourceEntityMapper.updateByPrimaryKey(oldKvContentSourceEntity);
			}
		}

		@Transactional
		public void delete(String itemSourceId) {
			kvContentSourceEntityMapper.deleteByPrimaryKey(itemSourceId);
		}

		@Override
		public KVContentSourceEntity queryById(String id) {
			return 	kvContentSourceEntityMapper.selectByPrimaryKey(id);
		}
}

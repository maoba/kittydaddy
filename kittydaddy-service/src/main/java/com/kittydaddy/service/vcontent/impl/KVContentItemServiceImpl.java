package com.kittydaddy.service.vcontent.impl;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kittydaddy.common.constant.Constants;
import com.kittydaddy.common.utils.KCollectionUtils;
import com.kittydaddy.metadata.vcontent.dao.KVContentItemEntityMapper;
import com.kittydaddy.metadata.vcontent.dao.KVContentSourceEntityMapper;
import com.kittydaddy.metadata.vcontent.domain.KVContentItemEntity;
import com.kittydaddy.metadata.vcontent.domain.KVContentSourceEntity;
import com.kittydaddy.service.vcontent.KVContentItemService;

/**
 * @author kittydaddy
 */
@Service
public class KVContentItemServiceImpl implements KVContentItemService{
    @Autowired
	private KVContentItemEntityMapper kVContentItemEntityMapper;
    
    @Autowired
    private KVContentSourceEntityMapper kVContentSourceEntityMapper;
    
	@Override
	public PageInfo<KVContentItemEntity> queryKvContentItemByPage(String contentId,Integer pageIndex, Integer pageSize) {
		PageHelper.startPage(pageIndex,pageSize, true, null, true);
		List<KVContentItemEntity> entitys = kVContentItemEntityMapper.queryItemByContentId(contentId);
		PageInfo<KVContentItemEntity> page = new PageInfo<KVContentItemEntity>(entitys);
		return page;
	}

	@Override
	public void saveUpdateKVContentItem(Map<String, Object> params) {
		String itemChannel = params.get("itemChannel")==null?"":params.get("itemChannel").toString();
		String itemTitle = params.get("itemTitle")==null?"":params.get("itemTitle").toString();
		Integer itemSn = params.get("itemSn")==null?0:Integer.parseInt(params.get("itemSn").toString());
		String itemPeriod = params.get("itemPeriod")==null?"":params.get("itemPeriod").toString();
		String itemSummary = params.get("itemSummary")==null?"":params.get("itemSummary").toString();
		String contentId = params.get("contentId")==null?"":params.get("contentId").toString();
		
        if(params.get("id")==null){//新增
        	KVContentItemEntity itemEntity = new KVContentItemEntity();
        	itemEntity.setContentId(contentId);
        	itemEntity.setCreateTime(new Date());
        	itemEntity.setItemChannel(itemChannel);
        	itemEntity.setItemPeriod(itemPeriod);
        	itemEntity.setItemSn(itemSn);
        	itemEntity.setItemTitle(itemTitle);
        	itemEntity.setItemSummary(itemSummary);
        	kVContentItemEntityMapper.insert(itemEntity);
        	
        }else{//更新
        	KVContentItemEntity kvContentItemEntity = kVContentItemEntityMapper.selectByPrimaryKey(params.get("id").toString());
        	if(kvContentItemEntity !=null){
        		kvContentItemEntity.setUpdateTime(new Date());
        		kvContentItemEntity.setItemChannel(itemChannel);
        		kvContentItemEntity.setItemPeriod(itemPeriod);
        		kvContentItemEntity.setItemSn(itemSn);
        		kvContentItemEntity.setItemTitle(itemTitle);
        		kvContentItemEntity.setItemSummary(itemSummary);
            	kVContentItemEntityMapper.updateByPrimaryKey(kvContentItemEntity);
        	}
        }		
	}

	@Override
	public void delete(String id) {
		kVContentItemEntityMapper.deleteByPrimaryKey(id);
		List<KVContentSourceEntity> sourceEntities = kVContentSourceEntityMapper.findByRelativeTypeAndRelativeId(Constants.TABLE_K_VIDEO_ITEM, id);
		if(KCollectionUtils.isNotEmpty(sourceEntities)){//删除播放源
			for(KVContentSourceEntity sourceEntity : sourceEntities){
				kVContentSourceEntityMapper.deleteByPrimaryKey(sourceEntity.getId());
			}
		}
	}

	@Override
	public KVContentItemEntity queryKvContentItemById(String id) {
		return kVContentItemEntityMapper.selectByPrimaryKey(id);
	}

}

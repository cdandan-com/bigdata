package com.yuan.service.impl;

import com.yuan.dao.BaseDao;
import com.yuan.domain.Item;
import com.yuan.service.ItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 */
@Service("itemService")
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService{

    @Resource(name="itemDao")
    public void setDao(BaseDao<Item> dao) {
        super.setDao(dao);
    }
}
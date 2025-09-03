package com.newzhihao.travelproject.service;

import com.newzhihao.travelproject.mapper.CollectMapper;
import com.newzhihao.travelproject.pojo.Collection;
import com.newzhihao.travelproject.pojo.Contents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionService {
    @Autowired
    private CollectMapper collectMapper;

    public List<Contents> selectCollection(String authorAccount) {
        return collectMapper.selectCollection(authorAccount);
    }

    public void insertCollection(Collection collection) {
        collectMapper.insertCollection(collection);
    }

    public void deleteCollection(Integer contentId) {
        collectMapper.deleteCollection(contentId);
    }

}

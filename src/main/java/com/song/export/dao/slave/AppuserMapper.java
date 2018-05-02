package com.song.export.dao.slave;

import com.song.export.model.bean.slave.Appuser;

public interface AppuserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Appuser record);

    int insertSelective(Appuser record);

    Appuser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Appuser record);

    int updateByPrimaryKey(Appuser record);
}
package com.bzb.atjob.app.auth.core.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.bzb.atjob.app.auth.core.entity.Dept;

@Mapper
public interface DeptMapper {
    long countByExample(DeptExample example);

    int deleteByPrimaryKey(String deptId);

    int insert(Dept record);

    int insertSelective(Dept record);

    List<Dept> selectByExample(DeptExample example);

    Dept selectByPrimaryKey(String deptId);

    int updateByPrimaryKeySelective(Dept record);

    int updateByPrimaryKey(Dept record);
}
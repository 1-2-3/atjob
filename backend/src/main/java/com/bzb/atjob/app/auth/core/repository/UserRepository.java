package com.bzb.atjob.app.auth.core.repository;

import javax.validation.ValidationException;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bzb.atjob.app.auth.core.entity.Dept;
import com.bzb.atjob.app.auth.core.mapper.DeptMapper;
import com.bzb.atjob.app.auth.core.mapper.UserMapper;
import com.bzb.atjob.common.util.MybatisUtil;
import com.bzb.atjob.common.vo.PaggingResult;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRepository {

    @Autowired
    private UserMapper userMapper;
}
package com.bzb.atjob.app.auth.core.repository;

import com.bzb.atjob.app.auth.core.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRepository {

  @Autowired private UserMapper userMapper;
}

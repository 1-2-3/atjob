package com.bzb.atjob.app.auth.adaptor.finder;

import com.bzb.atjob.app.auth.core.role.model.Role;

@FunctionalInterface
public interface FindRoleById {
  Role findRoleById(String roleId);
}

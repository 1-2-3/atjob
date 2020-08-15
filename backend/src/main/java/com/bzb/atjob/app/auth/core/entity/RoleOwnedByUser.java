package com.bzb.atjob.app.auth.core.entity;

import lombok.Value;

/**
 * 被用户拥有的角色
 */
@Value
class RoleOwnedByUser {
    private String roleId;
}
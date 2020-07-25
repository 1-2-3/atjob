package com.bzb.atjob.app.auth.feed.web;

import java.util.List;

import com.bzb.atjob.app.auth.core.application.DeptApplicationService;
import com.bzb.atjob.app.auth.core.entity.Dept;
import com.bzb.atjob.common.basetypes.BaseController;
import com.bzb.atjob.common.vo.ApiResult;
import com.bzb.atjob.common.vo.PaggingResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping({ "/api/v1/dept" })
@Api(value = "/api/v1/dept", tags = "部门")
public class DeptController extends BaseController {

    @Autowired
    DeptApplicationService deptService;

    @ApiOperation(value = "获取部门列表", notes = "")
    @RequestMapping(method = RequestMethod.GET, path = "getDeptList")
    public ApiResult<List<Dept>> getDeptList(
            @ApiParam(value = "页号", required = false) @RequestParam(required = false) Long pageNum,
            @ApiParam(value = "每页条数", required = false) @RequestParam(required = false) Long pageSize,
            @ApiParam(value = "排序", required = false) @RequestParam(required = false) String sort,
            @ApiParam(value = "模糊查询", required = false) @RequestParam(required = false) String query) {

        PaggingResult<Dept> result = deptService.getDeptList(pageNum, pageSize, sort, query);
        return ApiResult.successDataTotal(result.getData(), result.getTotal());
    }

    @ApiOperation(value = "获取匹配主键的部门")
    @RequestMapping(method = RequestMethod.GET, path = "getDeptById")
    public ApiResult<Dept> getDeptById(@RequestParam(required = true) String deptId) {
        return ApiResult.successData(deptService.getDeptById(deptId));
    }

    @ApiOperation(value = "保存或更新部门")
    @RequestMapping(method = RequestMethod.POST, path = "saveDept")
    public ApiResult<Object> saveDept(@RequestBody Dept entity) {
        deptService.saveDept(entity);
        return ApiResult.success();
    }

    @ApiOperation(value = "删除部门")
    @RequestMapping(method = RequestMethod.POST, path = "deleteDept")
    public ApiResult<Object> deleteDept(
            @ApiParam(value = "主键", required = true) @RequestParam(required = true) String deptId) {
        deptService.deleteDept(deptId);
        return ApiResult.success();
    }
}
package com.bzb.atjob.app.auth.feed.web;

import java.util.List;

import com.bzb.atjob.app.auth.core.entity.Dept;
import com.bzb.atjob.app.auth.core.service.DeptService;
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
@Api(value = "/api/v1/dept", tags = "科室")
public class DeptController extends BaseController {

    @Autowired
    DeptService deptService;

    @ApiOperation(value = "获取科室列表", notes = "")
    @RequestMapping(method = RequestMethod.GET, path = "getDeptList")
    public ApiResult<List<Dept>> getDeptList(
            @ApiParam(value = "页号", required = false) @RequestParam(required = false) Integer pageNum,
            @ApiParam(value = "每页条数", required = false) @RequestParam(required = false) Integer pageSize,
            @ApiParam(value = "是否按名称排序,传入 null|asc|desc", required = false) @RequestParam(required = false) String orderByName,
            @ApiParam(value = "是否按编码排序,传入 null|asc|desc", required = false) @RequestParam(required = false) String orderByCode,
            @ApiParam(value = "是否按创建日期排序,传入 null|asc|desc", required = false) @RequestParam(required = false) String orderByCreateTime,
            @ApiParam(value = "模糊查询", required = false) @RequestParam(required = false) String query) {

        PaggingResult<Dept> result = deptService.getDeptList(pageNum, pageSize, orderByName, orderByCode,
                orderByCreateTime, query);
        return ApiResult.successDataTotal(result.getData(), result.getTotal());
    }

    @ApiOperation(value = "判断科室信息是否已存在")
    @RequestMapping(method = RequestMethod.GET, path = "isDeptExists")
    public ApiResult<Object> isDeptExists(
            @ApiParam(value = "主键", required = false) @RequestParam(required = false) String deptId,
            @ApiParam(value = "编码", required = true) @RequestParam(required = true) String code) {
        return ApiResult.successData(deptService.isDeptExists(deptId, code));
    }

    @ApiOperation(value = "获取科室实体")
    @RequestMapping(method = RequestMethod.GET, path = "getDeptById")
    public ApiResult<Dept> getDeptById(@RequestParam(required = true) String deptId) {
        return ApiResult.successData(deptService.getDeptById(deptId));
    }

    @ApiOperation(value = "新增科室")
    @RequestMapping(method = RequestMethod.POST, path = "saveDept")
    public ApiResult<Object> saveDept(@RequestBody Dept entity) {
        deptService.saveDept(entity);
        return ApiResult.success();
    }

    @ApiOperation(value = "修改科室")
    @RequestMapping(method = RequestMethod.POST, path = "updateDept")
    public ApiResult<Object> updateDept(@RequestBody Dept dept) {
        deptService.updateDept(dept);
        return ApiResult.success();
    }

    @ApiOperation(value = "删除科室")
    @RequestMapping(method = RequestMethod.POST, path = "deleteDept")
    public ApiResult<Object> deleteDept(
            @ApiParam(value = "主键", required = true) @RequestParam(required = true) String deptId) {
        deptService.deleteDept(deptId);
        return ApiResult.success();
    }
}
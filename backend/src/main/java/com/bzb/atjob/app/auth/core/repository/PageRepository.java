package com.bzb.atjob.app.auth.core.repository;

import javax.validation.ValidationException;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bzb.atjob.app.auth.core.entity.Page;
import com.bzb.atjob.app.auth.core.mapper.PageMapper;
import com.bzb.atjob.common.util.MybatisUtil;
import com.bzb.atjob.common.vo.PaggingResult;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageRepository {

    @Autowired
    private PageMapper pageMapper;

    public Page byId(String id) {
        return pageMapper.selectById(id);
    }

    public void save(Page entity) {
        // 验证编码不允许重复
        if (this.isPageExists(entity.getPageId(), entity.getCode())) {
            throw new ValidationException("编码不允许重复！");
        }

        if (StringUtils.isBlank(entity.getPageId())) {
            pageMapper.insert(entity);
        } else {
            pageMapper.updateById(entity);
        }
    }

    public void delete(String id) {
        var hasChildPage = hasChildPage(id);
        if (hasChildPage) {
            throw new ValidationException("含有子页面，不允许删除！");
        }

        pageMapper.deleteById(id);
    }

    /**
     * 获取页面列表
     *
     * @param pageNum
     * @param pageSize
     * @param sort
     * @param query
     */
    public PaggingResult<Page> getPageList(Long pageNum, Long pageSize, String sort, String query) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Page> page = MybatisUtil.createPage(pageNum,
                pageSize);

        // 排序
        MybatisUtil.getOrderItemList(sort, Page.class, true).stream().forEach(page::addOrder);

        // 筛选
        QueryWrapper<Page> wrapper = new QueryWrapper<Page>();
        wrapper.lambda()
                .nested(w -> w.like(Page::getName, query).or().like(Page::getCode, StringUtils.defaultString(query)));

        pageMapper.selectPage(page, wrapper);

        return new PaggingResult<Page>(page.getRecords(), page.getSize());
    }

    /**
     * 判断页面信息是否已存在
     *
     * @param pageId
     * @return
     */
    private boolean isPageExists(String pageId, String code) {
        if (StringUtils.isBlank(pageId)) {
            return pageMapper.selectCount(new QueryWrapper<Page>().lambda().eq(Page::getCode, code)) > 0;
        } else {
            return pageMapper.selectCount(
                    new QueryWrapper<Page>().lambda().ne(Page::getPageId, pageId).eq(Page::getCode, code)) > 0;
        }

    }

    /**
     * 判断是否有子页面
     *
     * @param pageId
     */
    private boolean hasChildPage(String pageId) {
        return pageMapper.selectCount(new QueryWrapper<Page>().lambda().eq(Page::getParent, pageId)) > 0;
    }
}
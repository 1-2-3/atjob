import { NzModalService } from 'ng-zorro-antd/modal';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzTreeComponent } from 'ng-zorro-antd/tree';
import { Component, ViewChild, OnInit } from '@angular/core';
import { map } from 'rxjs/operators';
import { STColumn, STRes, STData } from '@delon/abc';
import { ApiResult } from 'src/app/shared/types/api-result';
import { PageService } from './page.service';
import { PageEditComponent } from './edit/page-edit.component';

@Component({
  styleUrls: ['page.component.less'],
  templateUrl: './page.component.html',
})
export class PageComponent implements OnInit {
  constructor(private pageService: PageService, private modalService: NzModalService, private msg: NzMessageService) {}

  pageLoading = false;
  pageTreeData = []; // 页面树数据源
  @ViewChild('pageTree') pageTree: NzTreeComponent;
  pageSelectedKeys = []; // 页面树选中节点

  ngOnInit(): void {
    this.loadPageTree();
  }

  /**
   * 加载页面数据（树型结构）
   */
  loadPageTree(): void {
    this.pageLoading = true;
    this.pageService.getPageTree().subscribe((r: ApiResult) => {
      this.pageLoading = false;

      if (r.success && r.data) {
        this.pageTreeData = r.data;
        this.pageSelectedKeys = [...this.pageSelectedKeys];
      }
    });
  }

  showAddPageWin() {
    const modal = this.modalService.create({
      nzTitle: '新增页面', // 窗体标题
      nzContent: PageEditComponent, // 表单组件
      // 要传递给表单组件的参数
      nzComponentParams: {
        opts: {
          operType: 'add',
          editId: '',
        },
      },
      nzWidth: 900, // 窗体宽度
      nzClosable: false, // 不显示右上角的X按钮
      nzMaskClosable: false, // 不允许点击窗体旁边的空白处关闭
      nzFooter: null, // 不显示自带的底部按钮栏，因为表单组件自己会添加底部按钮栏
    });
    modal.afterClose.subscribe((r) => {
      if (r && r.type === 'ok') {
        // 保存成功后刷新页面树
        this.loadPageTree();
      }
    });
  }

  showModifyPageWin() {
    const selectedTreeNode = this.pageTree.getSelectedNodeList()[0];

    if (!selectedTreeNode) {
      this.msg.error('请选择要修改的项目');
      return;
    }

    const modal = this.modalService.create({
      nzTitle: '修改页面信息', // 窗体标题
      nzContent: PageEditComponent, // 表单组件
      // 要传递给表单组件的参数
      nzComponentParams: {
        opts: {
          operType: 'edit',
          editId: selectedTreeNode.key,
        },
      },
      nzWidth: 900, // 窗体宽度
      nzClosable: false, // 不显示右上角的X按钮
      nzMaskClosable: false, // 不允许点击窗体旁边的空白处关闭
      nzFooter: null, // 不显示自带的底部按钮栏，因为表单组件自己会添加底部按钮栏
    });

    modal.afterClose.subscribe((r) => {
      if (r && r.type === 'ok') {
        // 保存成功后刷新页面树
        this.loadPageTree();
      }
    });
  }

  deletePage() {
    const selectedTreeNode = this.pageTree.getSelectedNodeList()[0];

    if (!selectedTreeNode) {
      this.msg.error('请选择要删除的项目！');
      return;
    }

    this.pageService.deletePage(selectedTreeNode.key).subscribe((r: ApiResult) => {
      if (r.success) {
        this.msg.success('删除成功');
        this.loadPageTree();
      }
    });
  }
}

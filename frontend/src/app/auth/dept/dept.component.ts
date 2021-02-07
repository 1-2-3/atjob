import { NzModalService } from 'ng-zorro-antd/modal';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzTreeComponent } from 'ng-zorro-antd/tree';
import { DeptEditComponent } from './edit/dept-edit.component';
import { Component, ViewChild, OnInit } from '@angular/core';
import { DeptService } from './dept.service';
import { map } from 'rxjs/operators';
import { STColumn, STRes, STData } from '@delon/abc/st';
import { ApiResult } from 'src/app/shared/types/api-result';

@Component({
  styleUrls: ['dept.component.less'],
  templateUrl: './dept.component.html',
})
export class DeptComponent implements OnInit {
  constructor(private deptService: DeptService, private modalService: NzModalService, private msg: NzMessageService) {}

  deptLoading = false; // 科室树是否在加载中
  deptTreeData = []; // 科室树数据源
  @ViewChild('deptTree') deptTree: NzTreeComponent;
  deptSelectedKeys = []; // 科室树选中节点

  ngOnInit() {
    this.loadDeptTree();
  }

  /**
   * 加载科室数据（树型结构）
   */
  loadDeptTree(): void {
    this.deptLoading = true;
    this.deptService.getDeptTree().subscribe((r: ApiResult) => {
      this.deptLoading = false;

      if (r.success && r.data) {
        this.deptTreeData = r.data;
        this.deptSelectedKeys = [...this.deptSelectedKeys];
      }
    });
  }

  /**
   * 显示增加科室窗体
   */
  showAddDeptWin() {
    const modal = this.modalService.create({
      nzTitle: '新增科室', // 窗体标题
      nzContent: DeptEditComponent, // 表单组件
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
        // 保存成功后刷新科室树
        this.loadDeptTree();
      }
    });
  }

  /**
   * 显示修改科室窗体
   */
  showModifyDeptWin() {
    const selectedTreeNode = this.deptTree.getSelectedNodeList()[0];

    if (!selectedTreeNode) {
      this.msg.error('请选择要修改的项目');
      return;
    }

    const modal = this.modalService.create({
      nzTitle: '修改科室信息', // 窗体标题
      nzContent: DeptEditComponent, // 表单组件
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
        // 保存成功后刷新科室树
        this.loadDeptTree();
      }
    });
  }

  /**
   * 删除科室
   */
  deleteDept(): void {
    const selectedTreeNode = this.deptTree.getSelectedNodeList()[0];

    if (!selectedTreeNode) {
      this.msg.error('请选择要删除的项目！');
      return;
    }

    this.deptService.deleteDept(selectedTreeNode.key).subscribe((r: ApiResult) => {
      if (r.success) {
        this.msg.success('删除成功');
        this.loadDeptTree();
      }
    });
  }
}

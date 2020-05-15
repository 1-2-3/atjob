import { NzModalService, NzTreeNode, NzMessageService } from 'ng-zorro-antd';
import { DeptEditComponent } from './edit/dept-edit.component';
import { Component, ViewChild } from '@angular/core';
import { DeptService } from './dept.service';

@Component({
  selector: 'auth-dept',
  styleUrls: ['dept.component.less'],
  templateUrl: './dept.component.html',
})
export class DeptComponent {
  constructor(private deptService: DeptService, private modalService: NzModalService, private msg: NzMessageService) {}

  //科室
  deptLoading = false; //科室树是否在加载中
  deptTreeDs = []; //科室树数据源

  ngOnInit() {
    this.loadDeptTree();
  }

  /**
   * 加载科室数据（树型结构）
   */
  loadDeptTree(): void {
    this.deptLoading = true;

    this.deptService.getDeptTree('h01').subscribe((data) => {
      this.deptTreeDs = data;
      this.deptLoading = false;
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
      if (r && r.type == 'ok') {
        // 保存成功后刷新科室树
        this.loadDeptTree();
      }
    });
  }

  /**
   * 显示修改科室窗体
   */
  showModifyDeptWin() {
    // let seletedNodes: NzTreeNode[] = util.tree.findNodes(this.deptTreeDs, (node) => node.origin.selected == true);
    // if (seletedNodes.length <= 0) {
    //   this.msg.error('请选择要修改的项目');
    //   return;
    // }
    // const modal = this.modalService.create({
    //   nzTitle: '修改科室信息', // 窗体标题
    //   nzContent: DeptEditComponent, // 表单组件
    //   // 要传递给表单组件的参数
    //   nzComponentParams: {
    //     opts: {
    //       operType: 'edit',
    //       editId: seletedNodes[0].key,
    //     },
    //   },
    //   nzWidth: 900, // 窗体宽度
    //   nzClosable: false, // 不显示右上角的X按钮
    //   nzMaskClosable: false, // 不允许点击窗体旁边的空白处关闭
    //   nzFooter: null, // 不显示自带的底部按钮栏，因为表单组件自己会添加底部按钮栏
    // });
    // modal.afterClose.subscribe((r) => {
    //   if (r && r.type == 'ok') {
    //     // 保存成功后刷新科室树
    //     this.loadDeptTree();
    //   }
    // });
  }

  /**
   * 删除科室
   */
  deleteDept(): void {
    // let seletedNodes: NzTreeNode[] = util.tree.findNodes(this.deptTreeDs, (node) => node.origin.selected == true);
    // if (seletedNodes.length <= 0) {
    //   this.msg.error('请选择要删除的项目');
    //   return;
    // }
    // this.deptService.deleteDept(seletedNodes[0].key).subscribe(() => {
    //   this.msg.success('删除成功');
    //   this.loadDeptTree();
    // });
  }
}

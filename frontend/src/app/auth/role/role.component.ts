import { NzModalService, NzMessageService, NzTreeComponent } from 'ng-zorro-antd';
import { Component, ViewChild, OnInit } from '@angular/core';
import { map } from 'rxjs/operators';
import { STColumn, STRes, STData, STColumnBadge } from '@delon/abc';
import { ApiResult } from 'src/app/shared/types/api-result';
import { RoleService } from './role.service';
import { RoleEditComponent } from './edit/role-edit.component';
import { PageService } from '../page/page.service';
import { TreeUtil } from 'src/app/shared/utils/tree-util';
import { promise } from 'protractor';

const IS_STOP_BADGE: STColumnBadge = {
  false: { text: '在用', color: 'success' },
  true: { text: '停用', color: 'error' },
};

@Component({
  styleUrls: ['role.component.less'],
  templateUrl: './role.component.html',
})
export class RoleComponent implements OnInit {
  constructor(
    private roleService: RoleService,
    private modalService: NzModalService,
    private msg: NzMessageService,
    private pageService: PageService,
  ) {}

  pageCardTitle = '页面权限';
  currentRoleId = ''; // 当前选中要编辑页面权限的角色Id

  roleLoading = false;
  @ViewChild('st') st;

  pageLoading = false;
  pageTreeData = []; // 页面树数据源
  pageCheckedKeys = []; // 角色拥有的页面权限
  expandAll = false;

  columns: STColumn[] = [
    { title: '名称', index: 'name', sort: { default: 'ascend' } },
    { title: '状态', index: 'isStop', sort: true, type: 'badge', badge: IS_STOP_BADGE },
    {
      title: '操作',
      width: '100px',
      buttons: [
        {
          icon: 'edit',
          type: 'modal',
          modal: {
            component: RoleEditComponent,
            params: (record: STData) => {
              record.operType = 'edit';
              record.editId = record.roleId;
              return record;
            },
            paramsName: 'opts',
            modalOptions: {
              nzTitle: '编辑角色', // 窗体标题
              nzWidth: 900, // 窗体宽度
              nzClosable: false, // 不显示右上角的X按钮
              nzMaskClosable: false, // 不允许点击窗体旁边的空白处关闭
              nzFooter: null, // 不显示自带的底部按钮栏，因为表单组件自己会添加底部按钮栏
            },
          },
          click: (record, modalResult, st) => {
            if (modalResult && modalResult.type === 'ok') {
              st.load();
            }
          },
        },
        {
          icon: 'delete',
          type: 'del',
          pop: {
            title: '确定要删除吗?',
            okType: 'danger',
          },
          click: (record, modal, st) => {
            this.roleService.deleteRole(record.roleId).subscribe((r: ApiResult) => {
              if (r.success) {
                st.load();
              }
            });
          },
        },
        {
          icon: 'partition',
          tooltip: '页面权限',
          click: (record) => {
            this.pageCardTitle = `${record.name} - 页面权限`;
            this.currentRoleId = record.roleId;
            this.loadPageTree();
          },
        },
      ],
    },
  ];

  ngOnInit(): void {}

  /**
   * 加载角色拥有的页面数据
   */
  loadPageTree(): void {
    this.pageLoading = true;
    this.pageService.getPageTree().subscribe((r: ApiResult) => {
      if (r.success && r.data) {
        this.pageTreeData = r.data;

        this.expandAll = false;
        setTimeout(() => {
          this.expandAll = true;
        });

        // 获取当前角色拥有的页面权限并设置页面权限树选中状态
        if (this.currentRoleId) {
          this.roleService.getRoleById(this.currentRoleId).subscribe((r: ApiResult) => {
            if (r.success) {
              this.pageCheckedKeys = r.data.pageIdListOwned;
              this.pageLoading = false;
            }
          });
        }
      }
    });
  }

  /**
   * 页面权限树节点复选框选中状态改变事件
   */
  pageTreeCheckboxChange(e) {
    // 每次都只获取选中的叶子节点进行持久化
    const checkedLeafKeys = TreeUtil.getCheckedLeafKeys(this.pageTreeData);

    if (this.currentRoleId) {
      this.roleService.getRoleById(this.currentRoleId).subscribe((r: ApiResult) => {
        if (r.success) {
          r.data.pageIdListOwned = checkedLeafKeys;
          this.roleService.saveRole(r.data).subscribe();
        }
      });
    }
  }

  showAddRoleWin() {
    const modal = this.modalService.create({
      nzTitle: '新增角色', // 窗体标题
      nzContent: RoleEditComponent, // 表单组件
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
        // 保存成功后刷新表格
        this.st.load();
      }
    });
  }
}

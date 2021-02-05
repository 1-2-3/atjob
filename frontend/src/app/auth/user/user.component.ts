import { Component, OnInit, ViewChild } from '@angular/core';
import { NzModalService } from 'ng-zorro-antd/modal';
import { NzMessageService } from 'ng-zorro-antd/message';
import { UserService } from './user.service';
import { STColumnBadge, STColumn, STData } from '@delon/abc';
import { UserEditComponent } from './edit/user-edit.component';
import { ApiResult } from 'src/app/shared/types/api-result';

const IS_STOP_BADGE: STColumnBadge = {
  false: { text: '在用', color: 'success' },
  true: { text: '停用', color: 'error' },
};

@Component({
  styleUrls: ['user.component.less'],
  templateUrl: './user.component.html',
})
export class UserComponent implements OnInit {
  constructor(private userService: UserService, private modalService: NzModalService, private msg: NzMessageService) {}

  @ViewChild('st') st;

  columns: STColumn[] = [
    { title: '名称', index: 'name', sort: { default: 'ascend' } },
    { title: '编码', index: 'code', sort: true },
    { title: '登录名', index: 'loginName', sort: true },
    { title: '所属部门', index: 'deptName', sort: true },
    { title: '排序', index: 'indexField', sort: true },
    { title: '状态', index: 'isStop', sort: true, type: 'badge', badge: IS_STOP_BADGE },
    {
      title: '操作',
      width: '100px',
      buttons: [
        {
          icon: 'edit',
          type: 'modal',
          modal: {
            component: UserEditComponent,
            params: (record: STData) => {
              record.operType = 'edit';
              record.editId = record.userId;
              return record;
            },
            paramsName: 'opts',
            modalOptions: {
              nzTitle: '编辑用户', // 窗体标题
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
            this.userService.deleteUser(record.userId).subscribe((r: ApiResult) => {
              if (r.success) {
                st.load();
              }
            });
          },
        },
        {
          icon: 'block',
          type: 'del',
          tooltip: '重置密码',
          pop: {
            title: '确定要重置密码为默认密码吗?',
            okType: 'danger',
          },
          click: (record, modal, st) => {
            this.userService.resetUserDefaultPwd(record.userId).subscribe((r: ApiResult) => {
              if (r.success) {
                this.msg.success('重置密码成功');
              }
            });
          },
        },
      ],
    },
  ];

  ngOnInit(): void {}

  showAddUserWin() {
    const modal = this.modalService.create({
      nzTitle: '新增用户', // 窗体标题
      nzContent: UserEditComponent, // 表单组件
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

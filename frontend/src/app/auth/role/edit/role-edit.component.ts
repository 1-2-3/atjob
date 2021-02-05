import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef } from 'ng-zorro-antd/modal';
import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { map, tap } from 'rxjs/operators';
import { zip, of } from 'rxjs';
import { TreeUtil } from 'src/app/shared/utils/tree-util';
import { CascaderUtil } from 'src/app/shared/utils/cascader-util';
import { ApiResult } from 'src/app/shared/types/api-result';
import { RoleService } from '../role.service';

@Component({
  templateUrl: './role-edit.component.html',
})
export class RoleEditComponent implements OnInit {
  opts: {
    operType: 'add' | 'edit'; // 'add' 'edit'
    editId: string; // 要进行编辑的数据的Id
  };

  formGroup: FormGroup; // 表单对象
  loading = false; // 是否正在加载数据
  submitting = false; // 是否正在提交中
  parentOptions = []; // “父节点”数据源

  constructor(
    private formBuilder: FormBuilder,
    private modal: NzModalRef,
    private msg: NzMessageService,
    private roleService: RoleService,
  ) {}

  /**
   * 加载form
   */
  ngOnInit(): void {
    this.formGroup = this.formBuilder.group({
      roleId: ['', []],
      name: ['', [Validators.required]],
      code: ['', [Validators.required]],
      description: [], // 描述
      isStop: [false], // 是否停用
    });

    this.loadData();
  }

  /**
   * 加载表单数据和控件数据源
   */
  private loadData(): void {
    if (this.opts.operType === 'add') {
    } else if (this.opts.operType === 'edit') {
      // 加载表单数据
      const formDataLoader = this.roleService.getRoleById(this.opts.editId).pipe(
        tap((r: ApiResult) => {
          if (r.success && r.data) {
            this.formGroup.patchValue(r.data);
          }
        }),
      );
      formDataLoader.subscribe();
    } else {
      const neverReachHere: never = this.opts.operType;
    }
  }

  /**
   * 提交
   */
  submit() {
    if (this.formGroup.controls) {
      for (const i of Object.keys(this.formGroup.controls)) {
        this.formGroup.controls[i].markAsDirty();
        this.formGroup.controls[i].updateValueAndValidity();
      }
    }

    if (this.formGroup.valid) {
      this.submitting = true;
      this.roleService.saveRole(this.formGroup.value).subscribe((r: ApiResult) => {
        this.submitting = false;

        // 提交完成关闭子窗口
        if (r.success) {
          this.modal.destroy({
            type: 'ok',
            data: null,
          });
        }
      });
    }
  }

  /**
   * 取消
   */
  cancel() {
    this.modal.destroy({
      type: 'cancel',
      data: null,
    });
  }
}

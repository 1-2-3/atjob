import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef } from 'ng-zorro-antd/modal';
import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { map, tap } from 'rxjs/operators';
import { zip, of } from 'rxjs';
import { TreeUtil } from 'src/app/shared/utils/tree-util';
import { CascaderUtil } from 'src/app/shared/utils/cascader-util';
import { ApiResult } from 'src/app/shared/types/api-result';
import { UserService } from '../user.service';
import { DeptService } from '../../dept/dept.service';
import { RoleService } from '../../role/role.service';

@Component({
  templateUrl: './user-edit.component.html',
})
export class UserEditComponent implements OnInit {
  opts: {
    operType: 'add' | 'edit'; // 'add' 'edit'
    editId: string; // 要进行编辑的数据的Id
  };

  formGroup: FormGroup; // 表单对象
  loading = false; // 是否正在加载数据
  submitting = false; // 是否正在提交中
  deptOptions = []; // 所属部门下拉数据源
  roleOptions = []; // 角色下拉数据源

  constructor(
    private formBuilder: FormBuilder,
    private modal: NzModalRef,
    private msg: NzMessageService,
    private userService: UserService,
    private deptService: DeptService,
    private roleService: RoleService,
  ) {}

  /**
   * 加载form
   */
  ngOnInit(): void {
    this.formGroup = this.formBuilder.group({
      userId: ['', []],
      name: ['', [Validators.required]],
      code: ['', [Validators.required]],
      deptId: [],
      deptCascadeValue: [null, [Validators.required]],
      deptName: ['', []],
      loginName: ['', [Validators.required]],
      inputCode: [],
      phone: [],
      description: [], // 描述
      isStop: [false], // 是否停用
      indexField: [],
      roleIdListOwned: [],
    });

    this.loadData();
  }

  /**
   * 加载表单数据和控件数据源
   */
  private loadData(): void {
    const deptOptionsLoader = this.deptService.getDeptTree().pipe(
      tap((r: ApiResult) => {
        if (r.success) {
          this.deptOptions = r.data;
        }
      }),
    );

    const roleOptionsLoader = this.roleService.getRoleList().pipe(
      tap((r: ApiResult) => {
        if (r.success) {
          this.roleOptions = r.data;
        }
      }),
    );

    if (this.opts.operType === 'add') {
      zip(roleOptionsLoader, deptOptionsLoader).subscribe();
    } else if (this.opts.operType === 'edit') {
      // 加载表单数据
      const formDataLoader = this.userService.getUserById(this.opts.editId).pipe(
        tap((r: ApiResult) => {
          if (r.success && r.data) {
            this.formGroup.patchValue(r.data);
          }
        }),
      );

      zip(roleOptionsLoader, deptOptionsLoader, formDataLoader).subscribe(() => {
        // 生成级联下拉框要求的Id数组
        CascaderUtil.propValueToCascaderValue(this.formGroup, this.deptOptions, 'deptId', 'deptCascadeValue', 'deptId');
      });
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
      CascaderUtil.cascaderValueToPropValue(this.formGroup, 'deptId', 'deptCascadeValue');

      this.submitting = true;
      this.userService.saveUser(this.formGroup.value).subscribe((r: ApiResult) => {
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

  deptSelectionChange(selectedCascadeValues) {
    if (selectedCascadeValues.length) {
      const selectedDept = selectedCascadeValues[selectedCascadeValues.length - 1];
      this.formGroup.patchValue({ deptName: selectedDept.name });
    }
  }
}

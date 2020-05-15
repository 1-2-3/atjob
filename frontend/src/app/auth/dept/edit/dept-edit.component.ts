import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzMessageService, NzModalRef } from 'ng-zorro-antd';
import { Component, OnInit, ViewChild } from '@angular/core';
import { DeptService } from '../dept.service';
import { map } from 'rxjs/operators';
import { zip, of } from 'rxjs';

@Component({
  selector: 'dept-edit',
  templateUrl: './dept-edit.component.html',
})
export class DeptEditComponent implements OnInit {
  opts: {
    operType: 'add' | 'edit'; // 'add' 'edit'
    editId: string; // 要进行编辑的数据的Id
  };

  formGroup: FormGroup; // 表单对象
  loading = false; // 是否正在加载数据
  submitting: boolean = false; // 是否正在提交中
  parentOptions = []; // “父节点”数据源

  constructor(
    private formBuilder: FormBuilder,
    // private modalService: ModalService,
    private modal: NzModalRef,
    private msg: NzMessageService,
    private deptService: DeptService,
  ) {}

  /**
   * 加载form
   */
  ngOnInit(): void {
    // this.formGroup = this.formBuilder.group({
    //   deptId: ['', []], //科室Id
    //   name: ['', [Validators.required]], //部门名称
    //   code: [
    //     '',
    //     [Validators.required],
    //     [util.validators.notExists(this.opts.editId, this.deptService.isDeptCodeExists)],
    //   ], //部门编码
    //   parent: [[]], //父节ID
    //   isLeaf: [false, []], //是否叶
    //   treeIds: [], //树父节点集合
    //   hospitalId: [], //HOSPITAL_ID
    //   nodeLevel: [], //节点深度
    //   inputCode: [], //录入码
    //   description: [], //描述
    //   hospitalCode: [], //医院编码
    //   nameEn: [], //英文名称
    // });
    // this.loadData();
  }

  /**
   * 加载表单数据和控件数据源
   */
  private loadData(): void {
    // if (this.opts.operType === 'add') {
    //   // 新增
    //   this.loading = true;
    //   zip(
    //     // 加载级联下拉框数据源
    //     this.deptService.getDeptCascaderTree().pipe(map(data => (this.parentOptions = data))),
    //   ).subscribe(() => (this.loading = false));
    // } else {
    //   // 编辑
    //   this.loading = true;
    //   zip(
    //     // 加载表单数据
    //     this.deptService
    //       .getDeptById(this.opts.editId)
    //       .pipe(map(dto => this.formGroup.patchValue(dto))),
    //     // 加载级联下拉框数据源
    //     this.deptService.getDeptCascaderTree().pipe(map(data => (this.parentOptions = data))),
    //   ).subscribe(() => {
    //     // 设置级联下拉框初始值
    //     this.formGroup
    //       .get('parent')
    //       .setValue(
    //         util.cascader.getPathValuesByValue(
    //           this.formGroup.get('parent').value,
    //           this.parentOptions,
    //         ),
    //       );
    //     this.loading = false;
    //   });
    // }
  }

  /**
   * 提交
   */
  submit() {
    // this.submitting = true;
    // util.form.submit({
    //   formGroup: this.formGroup,
    //   operType: this.opts.operType,
    //   save: () => this.deptService.saveDept(this.formGroup.value),
    //   update: () => this.deptService.updateDept(this.formGroup.value),
    //   success: () => {
    //     this.msg.success('保存成功');
    //     this.modal.destroy({
    //       type: 'ok',
    //       data: null,
    //     });
    //   },
    //   complete: () => (this.submitting = false),
    // });
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

  /**
   * 将 formGroup 中的控件验证状态转换为字符串。用于触发 nz-form-explain 显示验证错误信息
   */
  get validationState() {
    return null;
    // return util.form.getValidationStat(this.formGroup);
  }
}

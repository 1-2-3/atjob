import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzMessageService, NzModalRef } from 'ng-zorro-antd';
import { Component, OnInit, ViewChild } from '@angular/core';
import { map } from 'rxjs/operators';
import { zip, of } from 'rxjs';
import { TreeUtil } from 'src/app/shared/utils/tree-util';
import { CascaderUtil } from 'src/app/shared/utils/cascader-util';
import { ApiResult } from 'src/app/shared/types/api-result';
import { PageService } from '../page.service';

@Component({
  templateUrl: './page-edit.component.html',
})
export class PageEditComponent implements OnInit {
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
    private pageService: PageService,
  ) {}

  /**
   * 加载form
   */
  ngOnInit(): void {
    this.formGroup = this.formBuilder.group({
      pageId: [''],
      name: ['', [Validators.required]],
      code: ['', [Validators.required]],
      parent: [], // 父节ID
      parentCascadeValue: [[]], // 父节ID 级联下拉框Value
      inputCode: [],
      description: [],
      indexField: [],
      isStop: [],
      path: [],
      icon: [],
      isHide: [false],
      isLeaf: [false],
    });

    this.loadData();
  }

  /**
   * 加载表单数据和控件数据源
   */
  private loadData(): void {
    const parentOptionsLoader = this.pageService.getPageTree().pipe(
      map((r: ApiResult) => {
        if (r.success) {
          this.parentOptions = r.data;
        }
      }),
    );

    if (this.opts.operType === 'add') {
      parentOptionsLoader.subscribe();
    } else if (this.opts.operType === 'edit') {
      // 加载表单数据
      const formDataLoader = this.pageService.getPageById(this.opts.editId).pipe(
        map((r: ApiResult) => {
          if (r.success && r.data) {
            this.formGroup.patchValue(r.data);
          }
        }),
      );

      zip(parentOptionsLoader, formDataLoader).subscribe(() => {
        // 生成级联下拉框要求的Id数组
        CascaderUtil.propValueToCascaderValue(
          this.formGroup,
          this.parentOptions,
          'parent',
          'parentCascadeValue',
          'pageId',
        );
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
      // 级联下拉框的值为数组，提交前取数组最后一个元素
      CascaderUtil.cascaderValueToPropValue(this.formGroup, 'parent', 'parentCascadeValue');

      this.submitting = true;
      this.pageService.savePage(this.formGroup.value).subscribe((r: ApiResult) => {
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

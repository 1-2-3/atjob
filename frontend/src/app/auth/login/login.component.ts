import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { AuthService } from 'src/app/shared/auth/auth.service';
import { ApiResult } from 'src/app/shared/types/api-result';

@Component({
  styleUrls: ['login.component.less'],
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private msg: NzMessageService,
    private router: Router,
  ) {}

  formGroup: FormGroup; // 表单对象
  submitting = false; // 是否正在提交中

  ngOnInit(): void {
    this.formGroup = this.formBuilder.group({
      username: [, [Validators.required]],
      pwd: [, [Validators.required]],
    });
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
      this.authService
        .login(this.formGroup.get('username').value, this.formGroup.get('pwd').value)
        .subscribe((r: ApiResult) => {
          this.submitting = false;
          if (r.success) {
            if (r.data) {
              this.authService.token = r.data;
              this.router.navigateByUrl('/');
            } else {
              this.msg.error('用户名或密码不正确');
            }
          } else {
            this.msg.error('登录失败');
          }
        });
    }
  }
}

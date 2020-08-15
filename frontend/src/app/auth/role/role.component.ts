import { NzModalService, NzMessageService, NzTreeComponent } from 'ng-zorro-antd';
import { Component, ViewChild, OnInit } from '@angular/core';
import { map } from 'rxjs/operators';
import { STColumn, STRes, STData } from '@delon/abc';
import { ApiResult } from 'src/app/shared/types/api-result';
import { RoleService } from './role.service';

@Component({
  styleUrls: ['role.component.less'],
  templateUrl: './role.component.html',
})
export class RoleComponent implements OnInit {
  constructor(private roleService: RoleService, private modalService: NzModalService, private msg: NzMessageService) {}
  ngOnInit(): void {}
}

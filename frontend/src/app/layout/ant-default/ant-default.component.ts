import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/shared/auth/auth.service';
import { ApiResult } from 'src/app/shared/types/api-result';
import { TreeUtil } from 'src/app/shared/utils/tree-util';
import { UserMenuService } from '../user-menu.service';

@Component({
  selector: 'app-ant-default',
  templateUrl: './ant-default.component.html',
  styleUrls: ['./ant-default.component.less'],
})
export class AntDefaultComponent implements OnInit {
  constructor(private userMenuService: UserMenuService, private authService: AuthService) {}

  isCollapsed = false;

  menuTree: any[];

  ngOnInit(): void {
    this.userMenuService.getPagesOwnedByUser(this.authService.userInfo.loginName).subscribe((r: ApiResult) => {
      if (r.success) {
        this.menuTree = TreeUtil.buildTree(r.data, 'pageId', 'parent');
      }
    });
  }
}

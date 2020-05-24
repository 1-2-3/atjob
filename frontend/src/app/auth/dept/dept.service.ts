import { Observable, Subject, zip, of } from 'rxjs';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { TreeUtil } from 'src/app/shared/utils/tree-util';
import { ApiResult } from 'src/app/shared/types/api-result';

@Injectable()
export class DeptService {
  constructor(private http: HttpClient) {}

  /**
   * 获取全部科室列表构建树形结构
   */
  getDeptTree() {
    return this.http
      .get('api/v1/dept/getDeptList', {
        params: {
          sort: 'indexField.ascend-name.ascend',
        },
      })
      .pipe(
        map((r: ApiResult) => {
          if (r.success && r.data) {
            r.data = TreeUtil.buildTree(r.data, 'deptId', 'parent');
          }

          return r;
        }),
      );
  }

  getDeptList() {
    return this.http.get('api/v1/dept/getDeptList');
  }

  /**
   * 获取科室详细信息
   * @param deptId
   */
  getDeptById(deptId: string) {
    return this.http.get(`api/v1/dept/getDeptById`, {
      params: {
        deptId: deptId,
      },
    });
  }

  /**
   * 新增科室
   * @param formData
   */
  saveDept(formData: any) {
    return this.http.post(`api/v1/dept/saveDept`, formData);
  }

  /**
   * 更新科室信息
   * @param formData
   */
  updateDept(formData: any) {
    return this.http.post(`api/v1/dept/updateDept`, formData);
  }

  /**
   * 删除科室
   * @param deptId
   */
  deleteDept(deptId: string) {
    return this.http.post(
      `api/v1/dept/deleteDept`,
      {},
      {
        params: { deptId: deptId },
      },
    );
  }
}

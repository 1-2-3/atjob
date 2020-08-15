import { Observable, Subject, zip, of } from 'rxjs';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { TreeUtil } from 'src/app/shared/utils/tree-util';
import { ApiResult } from 'src/app/shared/types/api-result';

@Injectable()
export class PageService {
  constructor(private http: HttpClient) {}

  /**
   * 获取全部页面列表构建树形结构
   */
  getPageTree() {
    return this.http
      .get('/api/v1/page/getPageList', {
        params: {
          sort: 'indexField.ascend-name.ascend',
        },
      })
      .pipe(
        map((r: ApiResult) => {
          if (r.success && r.data) {
            r.data = TreeUtil.buildTree(r.data, 'pageId', 'parent');
          }

          return r;
        }),
      );
  }

  getPageList() {
    return this.http.get('api/v1/page/getPageList');
  }

  getPageById(pageId: string) {
    return this.http.get(`api/v1/page/getPageById`, {
      params: {
        pageId,
      },
    });
  }

  savePage(formData: any) {
    return this.http.post(`api/v1/page/savePage`, formData);
  }

  deletePage(pageId: string) {
    return this.http.post(
      `api/v1/page/deletePage`,
      {},
      {
        params: { pageId },
      },
    );
  }
}

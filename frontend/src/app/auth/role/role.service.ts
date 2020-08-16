import { Observable, Subject, zip, of } from 'rxjs';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { TreeUtil } from 'src/app/shared/utils/tree-util';
import { ApiResult } from 'src/app/shared/types/api-result';

@Injectable()
export class RoleService {
  constructor(private http: HttpClient) {}

  getRoleById(roleId: string) {
    return this.http.get(`/api/v1/role/getRoleById`, {
      params: {
        roleId,
      },
    });
  }

  saveRole(formData: any) {
    return this.http.post(`/api/v1/role/saveRole`, formData);
  }

  deleteRole(roleId: string) {
    return this.http.post(
      `/api/v1/role/deleteRole`,
      {},
      {
        params: { roleId },
      },
    );
  }
}

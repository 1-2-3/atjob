import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class UserMenuService {
  constructor(private http: HttpClient) {}

  getPagesOwnedByUser(loginName: string) {
    return this.http.get(`/api/v1/user/getPagesOwnedByUser`, {
      params: {
        loginName,
      },
    });
  }
}

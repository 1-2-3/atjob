import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class UserService {
  constructor(private http: HttpClient) {}

  getUserById(userId: string) {
    return this.http.get(`/api/v1/user/getUserById`, {
      params: {
        userId,
      },
    });
  }

  saveUser(formData: any) {
    return this.http.post(`/api/v1/user/saveUser`, formData);
  }

  deleteUser(userId: string) {
    return this.http.post(
      `/api/v1/user/deleteUser`,
      {},
      {
        params: { userId },
      },
    );
  }

  /**
   * 重置密码
   */
  resetUserDefaultPwd(userId: string) {
    return this.http.post(
      `/api/v1/user/resetUserDefaultPwd`,
      {},
      {
        params: { userId },
      },
    );
  }
}

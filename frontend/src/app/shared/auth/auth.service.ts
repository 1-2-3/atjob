import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(private http: HttpClient) {}

  /**
   * 登录
   */
  login(username: string, pwd: string) {
    return this.http.post(`/api/v1/auth/login?loginName=${username}&pwd=${pwd}`, null);
  }

  /**
   * 当前登录用户信息
   */
  get userInfo(): any {
    return JSON.parse(sessionStorage.getItem('app-user-info')) || {};
  }

  /**
   * 当前登录用户信息
   */
  set userInfo(value: any) {
    sessionStorage.setItem('app-user-info', JSON.stringify(value || {}));
  }

  /**
   * 用户登录后取得的 token
   */
  get token(): string {
    return sessionStorage.getItem('app-auth-token');
  }

  /**
   * 用户登录后取得的 token
   */
  set token(value: string) {
    sessionStorage.setItem('app-auth-token', value);
  }
}

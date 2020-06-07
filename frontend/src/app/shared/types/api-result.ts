/**
 * 后端 web api 返回数据的类型
 */
export interface ApiResult {
  success: boolean;
  data: any;
  msg: string;
  errType: number; // 1: 验证错误 2: 权限错误 3：其他
  total: number;
}

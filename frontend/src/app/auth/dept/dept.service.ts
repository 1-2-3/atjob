import { Observable, Subject, zip, of } from 'rxjs';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class DeptService {
  constructor(private http: HttpClient) {}

  /**
   * 获取全部科室列表构建树形结构
   * @param hospitalCode
   */
  getDeptTree(hospitalCode: string): Observable<any> {
    return of(null);
    // let result = new Subject<any>();

    // zip(this.getHospitalByCode(hospitalCode), this.getDeptListByHospitalCode(hospitalCode)).subscribe((allData) => {
    //   let hospital: any = allData[0];
    //   let depts = <any[]>allData[1];
    //   let deptsJoinHospital = depts.map((dept) => {
    //     if (!dept.parent) {
    //       dept.parent = hospital.hospitalId;
    //     }
    //     return dept;
    //   });

    //   let allNodes = [
    //     ...[hospital].map((hospital) => {
    //       return {
    //         title: hospital.name,
    //         key: hospital.hospitalId,
    //         parent: hospital.parent,
    //         isLeaf: hospital.isLeaf,
    //         selectable: false,
    //       };
    //     }),
    //     ...deptsJoinHospital.map((deptsJoinHospital) => {
    //       return {
    //         title: deptsJoinHospital.name,
    //         key: deptsJoinHospital.deptId,
    //         parent: deptsJoinHospital.parent,
    //         isLeaf: deptsJoinHospital.isLeaf,
    //       };
    //     }),
    //   ];

    //   result.next(util.tree.buildTree(allNodes, (data) => data));
    // });

    // return result.asObservable();
  }

  /**
   * 获取CascaderTree
   * @param disableLeaf
   */
  getDeptCascaderTree(disableLeaf = true) {
    return of(null);

    // return this.http
    //   .get(`auth/api/v1/dept/getDeptList`, {
    //     orderByText: 'asc',
    //   })
    //   .pipe(
    //     util.rxjs.handleError(this.errorMessager),
    //     map((data: any) =>
    //       util.cascader.buildCascaderTree(data, (data) => ({
    //         label: data.name,
    //         value: data.deptId,
    //         parent: data.parent,
    //         isLeaf: data.isLeaf,
    //         disabled: disableLeaf == true && data.isLeaf == true,
    //       })),
    //     ),
    //   );
  }

  /**
   * 根据医院编码获取科室列表
   * @param hospitalCode
   */
  getDeptListByHospitalCode(hospitalCode: string) {
    return of(null);

    // return this.http
    //   .get(`auth/api/v1/dept/getDeptListByHospitalCode`, {
    //     hospitalCode: hospitalCode,
    //   })
    //   .pipe(util.rxjs.handleError(this.errorMessager));
  }

  /**
   * 根据医院编码获取医院列表
   * @param hospitalCode
   */
  getHospitalByCode(hospitalCode: string) {
    return of(null);

    // return this.http
    //   .get(`auth/api/v1/hospital/getHospitalByCode`, {
    //     code: hospitalCode,
    //   })
    //   .pipe(util.rxjs.handleError(this.errorMessager));
  }

  /**
   * 获取科室所在医院
   * @param hospitalId
   */
  getDeptHospital(hospitalId: string) {
    return of(null);

    // return this.http
    //   .get(`auth/api/v1/dept/getDeptHospital`, {
    //     hospitalId: hospitalId,
    //   })
    //   .pipe(util.rxjs.handleError(this.errorMessager));
  }

  /**
   * 获取科室详细信息
   * @param deptId
   */
  getDeptById(deptId: string) {
    return of(null);

    // return this.http
    //   .get(`auth/api/v1/dept/getDeptById`, {
    //     deptId: deptId,
    //   })
    //   .pipe(util.rxjs.handleError(this.errorMessager));
  }

  /**
   * 新增科室
   * @param formData
   */
  saveDept(formData: any): Observable<any> {
    return of(null);

    // let dto = util.form.convertFormDataToDto(formData);
    // return this.http.post(`auth/api/v1/dept/saveDept`, dto).pipe(util.rxjs.handleError(this.errorMessager));
  }

  /**
   * 更新科室信息
   * @param formData
   */
  updateDept(formData: any): Observable<any> {
    return of(null);

    // let dto = util.form.convertFormDataToDto(formData);
    // return this.http.post(`auth/api/v1/dept/updateDept`, dto).pipe(util.rxjs.handleError(this.errorMessager));
  }

  /**
   * 删除科室
   * @param deptId
   */
  deleteDept(deptId: string): Observable<any> {
    return of(null);

    // return this.http
    //   .post(
    //     `auth/api/v1/dept/deleteDept`,
    //     {},
    //     {
    //       deptId: deptId,
    //     },
    //   )
    //   .pipe(util.rxjs.handleError(this.errorMessager));
  }

  /**
   * 异步验证科室编码
   * @param code
   * @param id
   */
  isDeptCodeExists = (code: string, deptId: string) => {
    return of(null);

    // return this.http.get(`auth/api/v1/dept/isDeptExists`, {
    //   code: code,
    //   deptId: deptId,
    // });
  };
}

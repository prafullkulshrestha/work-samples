import { Injectable } from '@angular/core';
import {environment} from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employee } from '../../models/Employee';
import { Department } from '../../models/Department';

import { map, publishReplay, catchError, refCount } from 'rxjs/operators';
import {ServiceBase} from '../../core/services/base.service';

@Injectable({
  providedIn: 'root'
})
export class EmployerService extends ServiceBase {

  private baseUrl = environment.defaultEmployerServiceUrl;

  constructor(private http: HttpClient) {
    super();
   }

  createEmployee(employee: Employee): Observable<any> {
    return this.http.post(this.baseUrl+'v1/employees', employee)
    .pipe(
			map((response: Employee) => response),
			publishReplay(1),
			refCount(),
			catchError(this.handleError),
		);
  }

  getEmployeesList(searchCriteria: any): Observable<any> {
    return this.http.post(this.baseUrl+'v1/employees/list', searchCriteria)
    .pipe(
			map((response: Employee[]) => response),
			publishReplay(1),
			refCount(),
			catchError(this.handleError),
		);
  }

  getDepartmentList(): Observable<Department[]> {
    return this.http.get(this.baseUrl+'v1/departments')
    .pipe(
			map((response: Department[]) => response),
			publishReplay(1),
			refCount(),
			catchError(this.handleError),
		);
  }

}

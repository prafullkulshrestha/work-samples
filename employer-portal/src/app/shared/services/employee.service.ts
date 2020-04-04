import { Injectable } from '@angular/core';
import {environment} from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employee } from '../../models/Employee';
import { map, publishReplay, catchError, refCount } from 'rxjs/operators';
import {ServiceBase} from '../../core/services/base.service';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService extends ServiceBase{

  private baseUrl = environment.defaultEmployerServiceUrl;

  constructor(private http: HttpClient) {
    super();
   }

  createEmployee(employee: Employee): Observable<Employee> {
    return this.http.post('${this.baseUrl}', employee)
    .pipe(
			map((response: Employee) => response),
			publishReplay(1),
			refCount(),
			catchError(this.handleError),
		);
  }

  getEmployeesList(): Observable<Employee[]> {
    return this.http.get('${this.baseUrl}')
    .pipe(
			map((response: Employee[]) => response),
			publishReplay(1),
			refCount(),
			catchError(this.handleError),
		);
  }
}

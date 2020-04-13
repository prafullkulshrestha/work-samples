import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import { EmployerService } from './employee.service';
import {environment} from '../../../environments/environment';
import { Department } from '../../models/Department';
import { HttpErrorResponse } from  '@angular/common/http';

describe('EmployerService', () => {

  const employeesListResponseData = {
    totalCount : 3,
    employeeList : [
    {
     employeeId: 100,
     firstName: 'Prafull',
     lastName: 'Kulshrestha',
     gender: 'Male',
     dateOfBirth: '1982-07-07',
     department: {
       departmentId: 107,
       departmentName: 'IT'
     }
    },
    {
      employeeId: 101,
     firstName: 'Pooja',
     lastName: 'Kulshrestha',
     gender: 'Female',
     dateOfBirth: '1987-05-22',
     department: {
      departmentId: 108,
      departmentName: 'Human Resource'
    }
    },
    {
      employeeId: 102,
     firstName: 'Praagy',
     lastName: 'Kulshrestha',
     gender: 'Male',
     dateOfBirth: '2015-11-06',
     department: {
      departmentId: 109,
      departmentName: 'Education'
    }
    }
  ]};

  const newEmployee: any = {
    firstName: 'Jack',
    lastName: 'Ryan',
    gender: 'Male',
    dateOfBirth: '1980-11-07',
    department: '107'
   };

   const newEmployeeResp: any = {
    id: 105,
    firstName: 'Jack',
    lastName: 'Ryan',
    gender: 'Male',
    dateOfBirth: '1980-11-07',
    department: {
      departmentId: 107,
      departmentName: 'IT'
    }
   };
   
  const searchAndSortCriteria: any = {
    pageNo: 0,
    pageSize: 5,
    sortCriteria: {
      firstName: true
    }
  }

  const departments : Department []= [
    {
      departmentId: 120,
      departmentName: 'HR',
      description: 'HR Department'
    },
    {
      departmentId: 121,
      departmentName: 'IT',
      description: 'IT Department'
    },
    {
      departmentId: 122,
      departmentName: 'Finance',
      description: 'Finance Department'
    }
  ]; 
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule ],
      providers: [EmployerService]
    });

  });

  it('should be created', inject([EmployerService], (service: EmployerService) => {
    expect(service).toBeTruthy();
  }));

  it('should have createEmployee function', inject([EmployerService], (service: EmployerService) => {
    expect(service.createEmployee).toBeTruthy();
  }));

  it('should have getEmployeesList function', inject([EmployerService], (service: EmployerService) => {
    expect(service.getEmployeesList).toBeTruthy();
  }));

  it('expects service to get employees list ',
  inject([HttpTestingController, EmployerService],
    (httpMock: HttpTestingController, service: EmployerService) => {
      const testURL = environment.defaultEmployerServiceUrl+'v1/employees/list';
      // We call the service
      service.getEmployeesList(searchAndSortCriteria).subscribe(data => {
        expect(data.employeeList.length).toBe(3);
        expect(data.totalCount).toBe(3);
      });
      // We set the expectations for the HttpClient mock
      const req = httpMock.expectOne(testURL);
      expect(req.request.method).toEqual('POST');
      // Then we set the fake data to be returned by the mock
      req.flush(employeesListResponseData);
      httpMock.verify();
    })
  );

  it('should return an error when the server returns a 404 fetching employees',
  inject([HttpTestingController, EmployerService],
    (httpMock: HttpTestingController, service: EmployerService) => {
      const errorResponse = new HttpErrorResponse({
        error: 'test 404 error',
        status: 404, 
        statusText: 'No employees found'
      });
      const testURL = environment.defaultEmployerServiceUrl+'v1/employees/list';
      // We call the service
      service.getEmployeesList(searchAndSortCriteria).subscribe(
        data => {},
        error  => {
          expect(error.message).toContain('No employees found');
          expect(error.message).toContain('test 404 error');
        }
      );
      // We set the expectations for the HttpClient mock
      const req = httpMock.expectOne(testURL);
      expect(req.request.method).toEqual('POST');
      // Then we set the fake data to be returned by the mock
      req.flush(errorResponse);
      httpMock.verify();
    })
  );

  it('expects service to create new employee ',
  inject([HttpTestingController, EmployerService],
    (httpMock: HttpTestingController, service: EmployerService) => {
      const testURL = environment.defaultEmployerServiceUrl+'v1/employees';
      // We call the service
      service.createEmployee(newEmployee).subscribe(data => {
        expect(data.id).toBe(105);
      });
      // We set the expectations for the HttpClient mock
      const req = httpMock.expectOne(testURL);
      expect(req.request.method).toEqual('POST');
      // Then we set the fake data to be returned by the mock
      req.flush(newEmployeeResp);
      httpMock.verify();
    })
  );

  it('expects service to get all the departments list ',
  inject([HttpTestingController, EmployerService],
    (httpMock: HttpTestingController, service: EmployerService) => {
      const testURL = environment.defaultEmployerServiceUrl+'v1/departments';
      // We call the service
      service.getDepartmentList().subscribe(data => {
        expect(data.length).toBe(3);
      });
      // We set the expectations for the HttpClient mock
      const req = httpMock.expectOne(testURL);
      expect(req.request.method).toEqual('GET');
      // Then we set the fake data to be returned by the mock
      req.flush(departments);
      httpMock.verify();
    })
  );

  it('expects service to not get all the departments list ',
  inject([HttpTestingController, EmployerService],
    (httpMock: HttpTestingController, service: EmployerService) => {
      const testURL = environment.defaultEmployerServiceUrl+'v1/departments';
      // We call the service
      service.getDepartmentList().subscribe(data => {
        expect(data).toBeNull();
      });
      // We set the expectations for the HttpClient mock
      const req = httpMock.expectOne(testURL);
      expect(req.request.method).toEqual('GET');
      // Then we set the fake data to be returned by the mock
      req.flush(null);
      httpMock.verify();
    })
  );

  it('should return an error when the server returns a 404 fetching departments',
  inject([HttpTestingController, EmployerService],
    (httpMock: HttpTestingController, service: EmployerService) => {
      const errorResponse = new HttpErrorResponse({
        error: 'test 404 error',
        status: 404, 
        statusText: 'No departments found'
      });
      const testURL = environment.defaultEmployerServiceUrl+'v1/departments';
      // We call the service
      service.getDepartmentList().subscribe(
        data => {},
        error  => {
          expect(error.message).toContain('No departments found');
          expect(error.message).toContain('test 404 error');
        }
      );
      // We set the expectations for the HttpClient mock
      const req = httpMock.expectOne(testURL);
      expect(req.request.method).toEqual('GET');
      // Then we set the fake data to be returned by the mock
      req.flush(errorResponse);
      httpMock.verify();
    })
  );

});

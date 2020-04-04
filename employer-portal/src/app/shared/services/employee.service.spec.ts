import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { EmployeeService } from './employee.service';

describe('EmployeeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule ],
      providers: [EmployeeService]
    });
   
  });

  it('should be created', inject([EmployeeService], (service: EmployeeService) => {
    expect(service).toBeTruthy();
  }));

  it('should have createEmployee function', inject([EmployeeService], (service: EmployeeService) => {
    expect(service.createEmployee).toBeTruthy();
  }));

  it('should have getEmployeesList function', inject([EmployeeService], (service: EmployeeService) => {
    expect(service.getEmployeesList).toBeTruthy();
  }));

});

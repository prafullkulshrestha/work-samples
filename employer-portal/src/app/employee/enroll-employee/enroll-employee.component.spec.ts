import { async, ComponentFixture, TestBed, fakeAsync } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { EnrollEmployeeComponent } from './enroll-employee.component';
import { Observable, of } from 'rxjs';
import { EmployeeService } from '../../shared/services/employee.service';

describe('EnrollEmployeeComponent', () => {
  let component: EnrollEmployeeComponent;
  let fixture: ComponentFixture<EnrollEmployeeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ FormsModule, HttpClientTestingModule ],
      declarations: [ EnrollEmployeeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EnrollEmployeeComponent);
    component = fixture.debugElement.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have the save method', () => {
    expect(component.save).toBeTruthy();
  });

  it('should fetch data if called asynchronously', fakeAsync(() => {
    let employeeService = fixture.debugElement.injector.get(EmployeeService);
    let data = 
      {
       id: 100,
       firstName: 'Prafull',
       lastName: 'Kulshrestha',
       gender: 'Male',
       dateOfBirth: '1982-07-07',
       departmentName: 'IT'
      };
    let spy = spyOn(employeeService, "createEmployee").and.returnValue(
      of(data)
    );
    fixture.detectChanges();
    tick();
    expect(component.employee).toBe(data);
  }));

});

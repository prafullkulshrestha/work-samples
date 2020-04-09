import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { EnrollEmployeeComponent } from './enroll-employee.component';
import { of } from 'rxjs';
import { EmployerService } from '../../shared/services/employee.service';
import { NO_ERRORS_SCHEMA } from "@angular/core";

describe('EnrollEmployeeComponent', () => {
  let component: EnrollEmployeeComponent;
  let fixture: ComponentFixture<EnrollEmployeeComponent>;
  const data = 
      {
       id: 100,
       firstName: 'Prafull',
       lastName: 'Kulshrestha',
       gender: 'Male',
       dateOfBirth: '1982-07-07',
       departmentName: 'IT'
      };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ FormsModule, HttpClientTestingModule ],
      declarations: [ EnrollEmployeeComponent ],
      providers: [
        EmployerService
			],
			schemas: [NO_ERRORS_SCHEMA]
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

  it('should return saved employee if called asynchronously', fakeAsync(() => {
    let employeeService = fixture.debugElement.injector.get(EmployerService);
     spyOn(employeeService, "createEmployee").and.returnValue(of(data));
     component.save();
    fixture.detectChanges();
    tick();
    expect(component.employee).toEqual(data);
  }));

  it('should submit employee details if called on the enroll employee form', () => {
    component.onSubmit();
    fixture.detectChanges();
    expect(component.submitted).toBe(true);
  });

});

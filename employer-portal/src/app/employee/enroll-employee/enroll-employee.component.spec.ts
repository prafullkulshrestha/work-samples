import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { EnrollEmployeeComponent } from './enroll-employee.component';
import { of } from 'rxjs';
import { EmployerService } from '../../shared/services/employee.service';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { MatSnackBar } from '../../../../node_modules/@angular/material';
import { EpSharedModule } from '../../shared/shared.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import 'hammerjs';
import { Department } from '../../models/Department';
import { throwError } from 'rxjs'; 

describe('EnrollEmployeeComponent', () => {
  let component: EnrollEmployeeComponent;
  let fixture: ComponentFixture<EnrollEmployeeComponent>;
  const data = {
       id: 100,
       firstName: 'Prafull',
       lastName: 'Kulshrestha',
       gender: 'Male',
       dateOfBirth: '1982-07-07',
       department: {
         departmentId: 101,
         departmentName: 'IT'
       }
      };
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

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ FormsModule, HttpClientTestingModule, ReactiveFormsModule, EpSharedModule, BrowserAnimationsModule],
      declarations: [ EnrollEmployeeComponent ],
      providers: [
        EmployerService,
        MatSnackBar
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

  it('should have created the formGroup', () => {
    component.ngOnInit();
    fixture.detectChanges();
    expect(component.employeeFormGroup).toBeTruthy();
  });

  it('should be invalid when form empty', () => {
    component.ngOnInit();
    fixture.detectChanges();
    expect(component.employeeFormGroup.valid).toBeFalsy();
  }); 

  it('should check for fields required validations', () => {
    let errors = {};
    component.ngOnInit();
    fixture.detectChanges();
    let firstName = component.employeeFormGroup.controls['firstName'];
    expect(firstName.valid).toBeFalsy();
    // firstName field is required
    errors = firstName.errors || {};
    expect(errors['required']).toBeTruthy();
  });

  it('should return saved employee if called asynchronously', fakeAsync(() => {
    const employeeService = fixture.debugElement.injector.get(EmployerService);
    component.ngOnInit();
     spyOn(employeeService, 'createEmployee').and.returnValue(of(data));
      component.save();
      fixture.detectChanges();
      tick(10000);
      expect(component.employee).toEqual(data);
  }));

  it('should not return saved employee if failed when called service method asynchronously', fakeAsync(() => {
    const employeeService = fixture.debugElement.injector.get(EmployerService);
    spyOn(employeeService, 'getEmployeesList').and.returnValue(throwError(new Error('Test error')));
    component.ngOnInit();
    fixture.detectChanges();
    tick();
    expect(component.employee)
      .toBeUndefined();
    
  }));

  it('should have called the snackbar after saving if called asynchronously', fakeAsync(() => {
    const employeeService = fixture.debugElement.injector.get(EmployerService);
    component.ngOnInit();
    const snackBar = fixture.debugElement.injector.get(MatSnackBar);
     spyOn(employeeService, 'createEmployee').and.returnValue(of(data));
     spyOn(snackBar, 'open');
      component.save();
      fixture.detectChanges();
      tick(10000);
      expect(snackBar.open).toHaveBeenCalled();
  }));

  it('should have called the reset after saving if called asynchronously', fakeAsync(() => {
    const employeeService = fixture.debugElement.injector.get(EmployerService);
    component.ngOnInit();
    const employeeFormGroup = fixture.debugElement.componentInstance.employeeFormGroup;
    spyOn(employeeService, 'createEmployee').and.returnValue(of(data));
    spyOn(employeeFormGroup, 'reset');
    component.save();
    fixture.detectChanges();
    tick(10000);
    expect(employeeFormGroup.reset).toHaveBeenCalled();
  }));

  it('should return the department list if called asynchronously', fakeAsync(() => {
    const employeeService = fixture.debugElement.injector.get(EmployerService);
     spyOn(employeeService, 'getDepartmentList').and.returnValue(of(departments));
     component.ngOnInit();
      fixture.detectChanges();
      tick(10000);
      expect(component.departments).toEqual(departments);
  }));

  it('should not return departments if failed when called service method asynchronously', fakeAsync(() => {
    const employeeService = fixture.debugElement.injector.get(EmployerService);
     spyOn(employeeService, 'getDepartmentList').and.returnValue(throwError(new Error('Test error')));
     component.ngOnInit();
      fixture.detectChanges();
      tick(10000);
      expect(component.departments).toBeUndefined();
  }));

  it('should submit employee details if called on the enroll employee form', fakeAsync(() => {
   // const employeeService = fixture.debugElement.injector.get(EmployerService);
    component.ngOnInit();
     spyOn(component, 'save');
      component.onSubmit();
      fixture.detectChanges();
      tick(10000);
      expect(component.save).toHaveBeenCalledTimes(1);
  }));

  it('should contain "Enroll New Employee"', () => {
    const compiled: HTMLElement = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('mat-card-title').textContent).toContain('Enroll New Employee');
  });

  it('should contain a create button', () => {
    const compiled: HTMLElement = fixture.debugElement.nativeElement;
    expect(compiled.querySelectorAll('button')[0].textContent).toContain('Create');
  });

  it('should contain a reset button', () => {
    const compiled: HTMLElement = fixture.debugElement.nativeElement;
    expect(compiled.querySelectorAll('button')[1].textContent).toContain('Reset');
  });

  it('should have called the reset after changing the form values and if clicked', fakeAsync(() => {
    const compiled: HTMLElement = fixture.debugElement.nativeElement;
    component.ngOnInit();
    const employeeFormGroup = fixture.debugElement.componentInstance.employeeFormGroup;
    employeeFormGroup.controls['firstName'].setValue("testFirstName");
    spyOn(employeeFormGroup, 'reset');
    compiled.querySelectorAll('button')[1].click();
    fixture.detectChanges();
    tick(1000);
    expect(employeeFormGroup.reset).toHaveBeenCalled();
  }));

});

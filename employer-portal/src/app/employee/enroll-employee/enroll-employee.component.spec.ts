import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule, NgForm, FormGroupDirective } from '@angular/forms';
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
  let originalTimeout;
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
    originalTimeout = jasmine.DEFAULT_TIMEOUT_INTERVAL;
    jasmine.DEFAULT_TIMEOUT_INTERVAL = 1000000;
  });

  afterEach(function() {
    jasmine.DEFAULT_TIMEOUT_INTERVAL = originalTimeout;
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
    component.ngOnInit();
    const employeeFormGroup = component.employeeFormGroup;
    spyOn(component, 'save');
    spyOn(employeeFormGroup, 'reset');
     component.onSubmit();
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

  it('should contain a create button', async(() => {
    fixture.detectChanges();
    const compiled: HTMLElement = fixture.debugElement.nativeElement;
    var buttons = compiled.querySelectorAll('button');
    expect(Array.from(buttons).some((b) => b.firstChild.textContent === 'Create')).toEqual(true);
  }));

  it('should contain a reset button', async(() => {
    fixture.detectChanges();
    const compiled: HTMLElement = fixture.debugElement.nativeElement;
    var buttons = compiled.querySelectorAll('button');
    expect(Array.from(buttons).some((b) => b.firstChild.textContent === 'Reset')).toEqual(true);
  }));

  it('should have called the reset after changing the form values and if clicked', async(() => {
    fixture.detectChanges();
    const compiled: HTMLElement = fixture.debugElement.nativeElement;
    component.ngOnInit();
    const employeeFormGroup = component.employeeFormGroup;
    employeeFormGroup.controls['firstName'].setValue("testFirstName");
    component.reset();
    fixture.detectChanges();
    expect(employeeFormGroup.controls['firstName'].value).toBeNull();
  }));

});

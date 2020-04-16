import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { EmployeeListComponent } from './employee-list.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { EmployerService } from '../../shared/services/employee.service';
import { of } from 'rxjs';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { EpSharedModule } from '../../shared/shared.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { throwError } from 'rxjs'; 
describe('EmployeeListComponent', () => {
  let component: EmployeeListComponent;
  let fixture: ComponentFixture<EmployeeListComponent>;
  let data: any; 
  let employeeData: any; 

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ FormsModule, HttpClientTestingModule, EpSharedModule, BrowserAnimationsModule ],
      declarations: [ EmployeeListComponent],
      providers: [
        EmployerService
			],
			schemas: [NO_ERRORS_SCHEMA]
		})
		.compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmployeeListComponent);
    component = fixture.debugElement.componentInstance;
    data = {
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

    employeeData = [
      {
       id: 100,
       firstName: 'Prafull',
       lastName: 'Kulshrestha',
       gender: 'Male',
       dateOfBirth: '1982-07-07',
       department: 'IT'
      },
      {
       id: 101,
       firstName: 'Pooja',
       lastName: 'Kulshrestha',
       gender: 'Female',
       dateOfBirth: '1987-05-22',
       department: 'Human Resource'
      },
      {
       id: 102,
       firstName: 'Praagy',
       lastName: 'Kulshrestha',
       gender: 'Male',
       dateOfBirth: '2015-11-06',
       department: 'Education'
      }
    ];
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have the loadData method', () => {
    expect(component.loadData).toBeTruthy();
  });

  it('should fetch data if called asynchronously', fakeAsync(() => {
    const employeeService = fixture.debugElement.injector.get(EmployerService);

    spyOn(employeeService, 'getEmployeesList').and.returnValue(of(data));
    component.ngOnInit();
    fixture.detectChanges();
    tick();
    expect(component.employees)
      .toEqual(employeeData);
    
    expect(component.totalCount)
      .toEqual(3);

    expect(component.dataSource.data)
      .toEqual(employeeData);

      expect(component.errorLoadingEmployees)
      .toEqual(false);
  
      expect(component.errorMessageLoadingEmployees)
      .toBe('');

  }));

  it('should not fetch data if failed when called service method asynchronously', fakeAsync(() => {
    const employeeService = fixture.debugElement.injector.get(EmployerService);
    spyOn(employeeService, 'getEmployeesList').and.returnValue(throwError(new Error('Test error')));
    component.ngOnInit();
    fixture.detectChanges();
    tick();
    expect(component.employees.length)
      .toEqual(0);

    expect(component.errorLoadingEmployees)
    .toEqual(true);

    expect(component.errorMessageLoadingEmployees)
    .toEqual('Unknown error occurred');
    
    expect(component.totalCount)
      .toEqual(0);

    expect(component.dataSource.data.length)
      .toEqual(0);

      expect(component.loading)
      .toBe(false);

  }));

  it('should contain 3 child node under mat-card', () => {
    const compiled: HTMLElement = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('mat-card').childNodes.length).toEqual(4);
  });

  it('should contain mat-table child node', () => {
    const compiled: HTMLElement = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('mat-card').querySelector('mat-table')).toBeDefined();
  });

  it('should contain mat-paginator child node under mat-table', () => {
    const compiled: HTMLElement = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('mat-card').querySelector('mat-paginator')).toBeDefined();
  });

  it('without loading employees the mat-table should be null', () => {
    const compiled: HTMLElement = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('mat-table')).toBeNull();
  });

  it('should contain "View All Employees"', () => {
    const compiled: HTMLElement = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('mat-card-title').textContent).toContain('View All Employees');
  });

});

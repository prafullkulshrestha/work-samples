import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { EmployeeListComponent } from './employee-list.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { EmployeeService } from '../../shared/services/employee.service';
import { Observable, of } from 'rxjs';

describe('EmployeeListComponent', () => {
  let component: EmployeeListComponent;
  let fixture: ComponentFixture<EmployeeListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ FormsModule, HttpClientTestingModule ],
      declarations: [ EmployeeListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmployeeListComponent);
    component = fixture.debugElement.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have the loadData method', () => {
    expect(component.loadData).toBeTruthy();
  });

  it('should fetch data if called asynchronously', fakeAsync(() => {
    let employeeService = fixture.debugElement.injector.get(EmployeeService);
    let data = [
      {
       id: 100,
       firstName: 'Prafull',
       lastName: 'Kulshrestha',
       gender: 'Male',
       dateOfBirth: '1982-07-07',
       departmentName: 'IT'
      },
      {
       id: 101,
       firstName: 'Pooja',
       lastName: 'Kulshrestha',
       gender: 'Female',
       dateOfBirth: '1987-05-22',
       departmentName: 'HR'
      },
      {
       id: 102,
       firstName: 'Praagy',
       lastName: 'Kulshrestha',
       gender: 'Male',
       dateOfBirth: '2015-11-06',
       departmentName: 'Education'
      }
    ];
    let spy = spyOn(employeeService, "getEmployeesList").and.returnValue(
      of(data)
    );
    fixture.detectChanges();
    tick();
    expect(component.employees).toBe(data);
  }));

});

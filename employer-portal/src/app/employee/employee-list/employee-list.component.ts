import { Observable, of } from 'rxjs';
import { EmployeeService } from '../../shared/services/employee.service';
import { Employee } from '../../models/Employee';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: "app-employee-list",
  templateUrl: "./employee-list.component.html",
  styleUrls: ["./employee-list.component.scss"]
})
export class EmployeeListComponent implements OnInit {
  employees: Observable<Employee[]>;

  constructor(private employeeService: EmployeeService) {}

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.employees = this.employeeService.getEmployeesList();
  //  this.employees = of(
  //    [
  //      {
  //       id: 100,
  //       firstName: 'Prafull',
  //       lastName: 'Kulshrestha',
  //       gender: 'Male',
  //       dateOfBirth: '1982-07-07',
  //       departmentName: 'IT'
  //      },
  //      {
  //       id: 101,
  //       firstName: 'Pooja',
  //       lastName: 'Kulshrestha',
  //       gender: 'Female',
  //       dateOfBirth: '1987-05-22',
  //       departmentName: 'HR'
  //      },
  //      {
  //       id: 102,
  //       firstName: 'Praagy',
  //       lastName: 'Kulshrestha',
  //       gender: 'Male',
  //       dateOfBirth: '2015-11-06',
  //       departmentName: 'Education'
  //      }
  //    ]
  //  );
  }
 
}
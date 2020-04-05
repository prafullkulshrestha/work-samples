import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../../shared/services/employee.service';
import { Employee } from '../../models/Employee';
@Component({
  selector: 'app-enroll-employee',
  templateUrl: './enroll-employee.component.html',
  styleUrls: ['./enroll-employee.component.scss']
})
export class EnrollEmployeeComponent implements OnInit {

   employee: Employee = new Employee();
   submitted = false;

  constructor(private employeeService: EmployeeService) { }

  ngOnInit() {
  }

  save() {
    this.employeeService.createEmployee(this.employee)
      .subscribe(data => {
        this.employee = data;
      }, error => console.log(error));
  }

  onSubmit() {
    this.save();
    this.submitted = true;
  }

}

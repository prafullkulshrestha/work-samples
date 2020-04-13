import { Component, OnInit } from '@angular/core';
import { EmployerService } from '../../shared/services/employee.service';
import { Employee } from '../../models/Employee';
import {FormControl, Validators, FormGroup} from '@angular/forms';
import { Department } from '../../models/Department';
import * as moment from 'moment';
import {MatSnackBar, MatSnackBarConfig,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition} from '@angular/material';
import { error } from '../../../../node_modules/protractor';

@Component({
  selector: 'app-enroll-employee',
  templateUrl: './enroll-employee.component.html',
  styleUrls: ['./enroll-employee.component.scss']
})
export class EnrollEmployeeComponent implements OnInit {

   employee: any;
   departments: Department [];
   horizontalPosition: MatSnackBarHorizontalPosition = 'center';
   verticalPosition: MatSnackBarVerticalPosition = 'bottom';
   public controls: { [propName: string]: FormControl } = {
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    gender: new FormControl(''),
    dob: new FormControl(''),
    department: new FormControl('')

  };

   public employeeFormGroup: FormGroup;
  constructor(private employerService: EmployerService, private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.employeeFormGroup = new FormGroup (this.controls);
    this.employerService.getDepartmentList().subscribe((departments)=> this.departments = departments,
  (error) => {console.error('Error')})
  }

  save() {
    let dob: string = moment(this.controls.dob.value).format('YYYY-MM-DD');
    let employee: any = {
      firstName: this.controls.firstName.value,
      lastName: this.controls.lastName.value,
      gender: this.controls.gender.value,
      dateOfBirth: dob ,
      department: {
        departmentId: this.controls.department.value
      }
    }
    this.employerService.createEmployee(employee)
      .subscribe(data => {
        this.employee = data;
        let config = new MatSnackBarConfig();
    config.verticalPosition = this.verticalPosition;
    config.horizontalPosition = this.horizontalPosition;
    config.duration = 2000;
      this.snackBar.open('Record saved successfully!', undefined, config);
      this.employeeFormGroup.reset();
      }, error => console.log(error));
  }

  onSubmit() {
    this.save();
  }

}

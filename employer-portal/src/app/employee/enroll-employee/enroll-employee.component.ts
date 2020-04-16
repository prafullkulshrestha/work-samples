import { Component, OnInit } from '@angular/core';
import { EmployerService } from '../../shared/services/employee.service';
import {FormControl, Validators, FormGroup, NgForm} from '@angular/forms';
import { Department } from '../../models/Department';
import * as moment from 'moment';
import {MatSnackBar, MatSnackBarConfig,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition} from '@angular/material';
import { DateAdapter } from '@angular/material';
import { CustomValidationService } from "../../shared/utilities/CustomValidationService";

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
   validDatePattern = /^(0?[1-9]|1[0-2])\/(0?[1-9]|1\d|2\d|3[01])\/(19|20)\d{2}$/ ;
   minDate: any;
   maxDate: any;

   public employeeFormGroup: FormGroup = new FormGroup ({
    firstName: new FormControl('', Validators.compose([Validators.required, Validators.maxLength(60)])),
    lastName: new FormControl('', Validators.compose([Validators.required, Validators.maxLength(60)])),
    gender: new FormControl('', [Validators.required]),
    dateOfBirth: new FormControl('', Validators.compose([Validators.required, CustomValidationService.validateAdult()])),
    department: new FormGroup({
      departmentId: new FormControl('', [Validators.required])
    })

  });
  constructor(private employerService: EmployerService, private snackBar: MatSnackBar, private dateAdapter: DateAdapter<any>) { }

  ngOnInit() {

    this.maxDate = new Date();
    this.minDate = moment(new Date()).subtract(60, 'years');
    this.setDateInput();
    this.employeeFormGroup.get('dateOfBirth').valueChanges.subscribe(() => {
		});


    this.employerService.getDepartmentList().subscribe((departments)=> this.departments = departments,
  (error) => {console.error('Error')})
  }

  save() {
    let dob: string = moment(this.employeeFormGroup.get('dateOfBirth').value).format('YYYY-MM-DD');
    let employee: any = this.employeeFormGroup.value;
    employee.dateOfBirth = dob;
    this.employerService.createEmployee(employee)
      .subscribe(data => {
        this.employee = data;
        let config = new MatSnackBarConfig();
    config.verticalPosition = this.verticalPosition;
    config.horizontalPosition = this.horizontalPosition;
    config.duration = 522000;
    this.snackBar.open('Record saved successfully!', undefined, config);
      }, error => console.log(error));
  }

  reset() {
    this.employeeFormGroup.reset();
  }

  onSubmit() {
    this.save();
    this.employeeFormGroup.reset();
  }

  setDateInput() {
    this.employeeFormGroup.get('dateOfBirth').setValue(moment(this.employeeFormGroup.get('dateOfBirth').value).format('MM/DD/YYYY'));
    this.dateAdapter.setLocale('us');
	}

}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { EmployerService } from '../../shared/services/employee.service';
import { FormControl, Validators, FormGroup, NgForm } from '@angular/forms';
import { Department } from '../../models/Department';
import * as moment from 'moment';
import {
  MatSnackBar, MatSnackBarConfig,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition
} from '@angular/material';
import { DateAdapter } from '@angular/material';
import { CustomValidationService } from "../../shared/utilities/CustomValidationService";
import { takeUntil } from 'rxjs/operators';
import { Subject } from "rxjs";

@Component({
  selector: 'app-enroll-employee',
  templateUrl: './enroll-employee.component.html',
  styleUrls: ['./enroll-employee.component.scss']
})
export class EnrollEmployeeComponent implements OnInit, OnDestroy {

  employee: any;
  departments: Department[];
  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'bottom';
  validDatePattern = /^(0?[1-9]|1[0-2])\/(0?[1-9]|1\d|2\d|3[01])\/(19|20)\d{2}$/;
  minDate: any;
  maxDate: any;
  errorLoadingDepartments: boolean = false;
  errorMessageLoadingDepartments: string;
  errorSavingEmployee: boolean = false;
  errorMessageSavingEmployee: string;
  private destroy$: Subject<boolean> = new Subject<boolean>();

  public employeeFormGroup: FormGroup = new FormGroup({
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
    this.errorLoadingDepartments = false;
    this.errorMessageLoadingDepartments = '';
    this.employerService.getDepartmentList().pipe(takeUntil(this.destroy$)).subscribe((departments) => this.departments = departments,
      (error) => { 
        console.error('Error');
        this.errorLoadingDepartments = true;
        if (error.status == 404){
          this.errorMessageLoadingDepartments = 'No departments found';
        }
        else if (error.status == 500)
                  {
          this.errorMessageLoadingDepartments = 'Error occurred loading the departments. Please try again';
        }
        else {
          this.errorMessageLoadingDepartments = 'Unknown error occurred';
        }
       })
  }

  save() {
    let dob: string = moment(this.employeeFormGroup.get('dateOfBirth').value).format('YYYY-MM-DD');
    let employee: any = this.employeeFormGroup.value;
    employee.dateOfBirth = dob;
    this.errorSavingEmployee = false;
    this.errorMessageSavingEmployee = '';
    this.employerService.createEmployee(employee).pipe(takeUntil(this.destroy$))
      .subscribe(data => {
        this.employee = data;
        let config = new MatSnackBarConfig();
        config.verticalPosition = this.verticalPosition;
        config.horizontalPosition = this.horizontalPosition;
        config.duration = 2000;
        this.snackBar.open('Record saved successfully!', undefined, config);
      }, error => {
        console.log(error);
        this.errorSavingEmployee = true;
       
         if (error.status == 500)
                  {
          this.errorMessageSavingEmployee = 'Error occurred saving the employee. Please try again';
        }
        else {
          this.errorMessageSavingEmployee = 'Unknown error occurred';
        }
      });
  }

  reset() {
    this.employeeFormGroup.reset();
  }

  onSubmit() {
    this.save();
    if(!this.errorSavingEmployee) {
      this.employeeFormGroup.reset();
    }
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.unsubscribe();
  }

}

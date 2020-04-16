import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EnrollEmployeeComponent } from './enroll-employee/enroll-employee.component';
import { EmployeeListComponent } from './employee-list/employee-list.component';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { EmployerService } from '../shared/services/employee.service';
import { EpSharedModule } from '../shared/shared.module';

const routing: ModuleWithProviders = RouterModule.forChild([
	{ path: '', component: EnrollEmployeeComponent },
	{ path: 'employees', component: EmployeeListComponent },
  { path: 'add', component: EnrollEmployeeComponent }


]);

@NgModule({
  imports: [
    CommonModule
    , EpSharedModule
    , routing
    , FormsModule
    , ReactiveFormsModule
  ],
  providers: [EmployerService],
  exports: [
    EpSharedModule
  ],
  bootstrap: [EmployeeListComponent],
  declarations: [EnrollEmployeeComponent, EmployeeListComponent]
})
export class EmployeeModule { }
export default EmployeeModule;

import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EnrollEmployeeComponent } from './enroll-employee/enroll-employee.component';
import { EmployeeListComponent } from './employee-list/employee-list.component';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { EmployeeService } from "../shared/services/employee.service";

const routing: ModuleWithProviders = RouterModule.forChild([
	{ path: '', component: EmployeeListComponent },
	{ path: 'employees', component: EmployeeListComponent },
  { path: 'add', component: EnrollEmployeeComponent }


]);

@NgModule({
  imports: [
    CommonModule
    , routing
    , FormsModule
  ],
  providers: [EmployeeService],
  bootstrap: [EmployeeListComponent],
  declarations: [EnrollEmployeeComponent, EmployeeListComponent]
})
export class EmployeeModule { }
export default EmployeeModule;

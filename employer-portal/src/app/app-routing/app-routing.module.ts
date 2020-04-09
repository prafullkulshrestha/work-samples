import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmployeeModule } from '../employee/employee.module';


const routes: Routes = [
  { path: '', loadChildren: '../employee/employee.module' }
];

@NgModule({
  imports: [EmployeeModule, RouterModule.forRoot(routes)],
  exports: [RouterModule, EmployeeModule]
})
export class AppRoutingModule { }

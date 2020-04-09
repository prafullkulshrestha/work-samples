import { Observable, of } from 'rxjs';
import { EmployerService } from '../../shared/services/employee.service';
import { Employee } from '../../models/Employee';
import { Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource, MatPaginator, MatSort, MatSnackBar, Sort} from '@angular/material';
@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.scss']
})
export class EmployeeListComponent implements OnInit {

  employees: Employee [];
  loading  =  true;
  dataSource = new MatTableDataSource<Employee>(this.employees);
  displayedColumns = ['firstName', 'lastName', 'gender', 'dateOfBirth', 'department'];
  pageSize = 5;
  pageIndex = 0;
  totalCount: number = 0;

  searchAndSortCriteria: any = {
    pageNo: this.pageIndex,
    pageSize: this.pageSize,
    sortCriteria: {
      firstName: true
    }
  }

  constructor(private employeeService: EmployerService, public snackBar: MatSnackBar) {}

  ngOnInit() {
      this.loadData();
  }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;


  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }


  loadData() {
    this.loading = true;
    this.employees = [];
    this.employeeService.getEmployeesList(this.searchAndSortCriteria).subscribe((response: any) => {
       response.employeeList.forEach(e => this.employees.push(
        {
          id: e.employeeId,
          firstName: e.firstName,
          lastName: e.lastName,
          gender: e.gender,
          dateOfBirth: e.dateOfBirth,
          department: e.department.departmentName

        }
      ));
      this.totalCount = response.totalCount;
      this.dataSource = new MatTableDataSource<Employee>(this.employees);
      this.loading = false;
    },
  (error)=> {
    console.error(error);
    this.loading = false;
  });

  }

  public onPageChanged(e: any): void {
    this.searchAndSortCriteria.pageNo = e.pageIndex;
    this.searchAndSortCriteria.pageSize = e.pageSize;
    this.loadData();
	}

  public onSortData(sort: Sort): void {
    this.searchAndSortCriteria.sortCriteria = {};
    let active: string = sort.active;
    let direction: string = sort.direction;
    this.searchAndSortCriteria.sortCriteria[active] = (direction === 'asc')
    this.loadData();
  }

}

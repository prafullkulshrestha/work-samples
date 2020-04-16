import { EmployerService } from '../../shared/services/employee.service';
import { Employee } from '../../models/Employee';
import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatSnackBar, Sort } from '@angular/material';
import { takeUntil } from 'rxjs/operators';
import { Subject } from "rxjs";
@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.scss']
})
export class EmployeeListComponent implements OnInit, OnDestroy {

  employees: Employee[];
  loading = true;
  dataSource = new MatTableDataSource<Employee>(this.employees);
  displayedColumns = ['firstName', 'lastName', 'gender', 'dateOfBirth', 'department'];
  pageSize = 5;
  pageIndex = 0;
  totalCount: number = 0;
  private destroy$: Subject<boolean> = new Subject<boolean>();
  errorLoadingEmployees: boolean = false;
  errorMessageLoadingEmployees: string;
  searchAndSortCriteria: any = {
    pageNo: this.pageIndex,
    pageSize: this.pageSize,
    sortCriteria: {
      firstName: false
    }
  }

  constructor(private employeeService: EmployerService, public snackBar: MatSnackBar) { }

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
    this.errorLoadingEmployees = false;
    this.errorMessageLoadingEmployees = '';
    this.employees = [];
    this.employeeService.getEmployeesList(this.searchAndSortCriteria).pipe(takeUntil(this.destroy$)).subscribe((response: any) => {
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
      (error) => {
        console.error(error);
        this.loading = false;
        this.errorLoadingEmployees = true;
					if (error.status == 404){
						this.errorMessageLoadingEmployees = 'No employees found. Please update criteria and try again.';
					}
					else if (error.status == 500)
                    {
						this.errorMessageLoadingEmployees = 'Error occurred loading the empoyees. Please try again';
          }
          else {
            this.errorMessageLoadingEmployees = 'Unknown error occurred'
          }
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
    this.searchAndSortCriteria.sortCriteria[active] = (direction !== 'asc')
    this.loadData();
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.unsubscribe();
  }

}

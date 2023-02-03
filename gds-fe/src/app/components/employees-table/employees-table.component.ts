import { Component, Input, Output, ViewChild, Inject } from '@angular/core';
import { EmployeeService } from '../../services/employee.service';
import { Employee } from '../../Employee';
import { Salary } from 'src/app/Salary';
import { faImage } from '@fortawesome/free-regular-svg-icons';
import { faPencilAlt } from '@fortawesome/free-solid-svg-icons';
import { faTrash } from '@fortawesome/free-solid-svg-icons';
import { faUser } from '@fortawesome/free-solid-svg-icons';
import { faUserPlus } from '@fortawesome/free-solid-svg-icons';
import { MatPaginator } from '@angular/material/paginator';
import { Observable, Subscription, tap } from 'rxjs';
import { Sort } from '@angular/material/sort';
import { MatSnackBar } from '@angular/material/snack-bar';

import { MatDialog } from '@angular/material/dialog';
import { ModalComponent } from '../modal/modal.component';
import { ResponseData } from '../toolbar/toolbar.component';

declare var window: any;

@Component({
  selector: 'app-employees-table',
  templateUrl: './employees-table.component.html',
  styleUrls: ['./employees-table.component.css']
})
export class EmployeesTableComponent {
  @Input() employee: Employee = {
    id: '',
    name: '',
    login: '',
    salary: ''
  };

  @Input() responseData: ResponseData | undefined;

  @Output() modalTitle = "";

  @ViewChild(MatPaginator)
  paginator: MatPaginator | undefined;

  faImage = faImage;
  faPencil = faPencilAlt;
  faTrash = faTrash;
  faUser = faUser;
  faUserPlus = faUserPlus;
  employees: Employee[] = [];
  sortAsc: boolean = true;
  sortField: string = "id";
  minSal: string = '0';
  maxSal: string = '999999';
  limit: string = '10';
  offset: string = '0';
  sortStr: string = "+id";
  displayedColumns: string[] = ['id', 'name', 'login', 'salary', 'action'];
  sortedData: Employee[] = [];
  errorMsg: string = "";
  countEmployeeResults: number = 0;
  //@ts-ignore
  @Input() events: Observable<ResponseData>;
  //@ts-ignore
  private eventsSubscription: Subscription;

  constructor(private employeeService: EmployeeService, private snackBar: MatSnackBar, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.employeeService.getEmployees('0', '50000', '10', '0', '+id').subscribe(
      (employees) => {
        this.employees = employees.results
        this.sortedData = employees.results;
        this.countEmployeeResults = employees.countEmployees;
      }, (error) => {
        if (error && error.error && error.error.message && error.error.error) {
          this.errorMsg = error.error.message + ": " + error.error.error;
          this.snackBar.open(this.errorMsg, 'Dismiss', {
            duration: 3000
          });
        }
      }
    );
    this.eventsSubscription = this.events.subscribe((responseData) => {
      if (responseData.message) {
        this.snackBar.open(responseData.message, 'Dismiss', {
          duration: 3000
        });
      } else {
        this.snackBar.open(responseData.error, 'Dismiss', {
          duration: 3000
        });
      }
      this.loadEmployeesPage()
    });
    this.sortAsc = true;
    this.sortField = "id";
    this.sortStr = "+id";
    this.offset = '0';
    this.limit = '10';
    this.minSal = '0';
    this.maxSal = '999999';
  }

  ngAfterViewInit() {
    this.paginator?.page.pipe(
      tap(() => this.loadEmployeesPage())
    ).subscribe();
  }

  searchSalaryForm(salary: Salary) {
    this.minSal = salary.minSal;
    this.maxSal = salary.maxSal;
    this.loadEmployeesPage();
    this.paginator!.firstPage();
  }

  loadEmployeesPage() {
    if (this.minSal === null) {
      this.minSal = '0'
    }
    if (this.maxSal === null) {
      this.maxSal = '50000'
    }
    this.employeeService.getEmployees(this.minSal, this.maxSal, this.paginator!.pageSize.toString(), this.paginator!.pageIndex.toString(), this.sortStr).subscribe(
      (employees) => {
        this.employees = employees.results
        this.sortedData = employees.results;
        this.countEmployeeResults = employees.countEmployees;
      }, (error) => {
        if (error && error.error && error.error.message && error.error.error) {
          this.errorMsg = error.error.message + ": " + error.error.error;
          this.snackBar.open(this.errorMsg, 'Dismiss', {
            duration: 3000
          });
        }
      });
  }

  sortData(sort: Sort) {
    let newSortStr = ""
    newSortStr += sort.direction === "asc" ? "+" : "-";
    newSortStr += sort.active;
    this.sortStr = newSortStr;
    this.loadEmployeesPage();
  }

  openDialog(employee: Employee, modalTitle: string): void {
    const dialogRef = this.dialog.open(ModalComponent, {
      data: {
        id: employee.id,
        name: employee.name,
        login: employee.login,
        salary: employee.salary,
        modalTitle: modalTitle,
        isAdd: modalTitle === 'Add Employee',
        isDelete: modalTitle === 'Delete Employee',
        isView: modalTitle === 'View Employee',
        isUpdate: modalTitle === 'Update Employee'
      },
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');

      if (result !== undefined) {
        const saveEmployee: Employee = {
          id: result.id,
          name: result.name,
          login: result.login,
          salary: result.salary
        }

        if (result.isDelete) {
          console.log("deleting employee: ", saveEmployee);
          this.deleteEmployee(saveEmployee)
          return;
        }

        if (result.isAdd) {
          console.log("adding employee: ", saveEmployee);
          this.addEmployee(saveEmployee)
          return;
        }
        console.log("updating employee: ", saveEmployee);
        this.updateEmployee(saveEmployee)
      }
    });
  }

  updateEmployee(employee: Employee) {
    this.employeeService.updateEmployee(employee).subscribe(
      (employees) => {
        this.employees = this.employees.filter(e => e.id != employees[0].id);
        this.employees.push(employees[0]);
        this.sortedData.push(employees[0]);
      }, (error) => {
        if (error && error.error && error.error.message && error.error.error) {
          this.errorMsg = error.error.message + ": " + error.error.error;
          this.snackBar.open(this.errorMsg, 'Dismiss', {
            duration: 3000
          });
        }
      });
    this.loadEmployeesPage();
  }

  deleteEmployee(employee: Employee) {
    this.employeeService.deleteEmployee(employee).subscribe(
      (employees) => {
        this.employees = this.employees.filter(e => e.id != employees[0].id);
        this.sortedData = this.sortedData.filter(e => e.id != employees[0].id);
      }, (error) => {
        if (error && error.error && error.error.message && error.error.error) {
          this.errorMsg = error.error.message + ": " + error.error.error;
          this.snackBar.open(this.errorMsg, 'Dismiss', {
            duration: 3000
          });
        }
      });
    this.loadEmployeesPage();
  }

  addEmployee(employee: Employee) {
    this.employeeService.addEmployee(employee).subscribe(
      (employees) => {
        this.employees.push(employees[0]);
        this.sortedData.push(employees[0]);
        this.countEmployeeResults = this.sortedData.length;
      }, (error) => {
        if (error && error.error && error.error.message && error.error.error) {
          this.errorMsg = error.error.message + ": " + error.error.error;
          this.snackBar.open(this.errorMsg, 'Dismiss', {
            duration: 3000
          });
        }
      });
    this.loadEmployeesPage();
  }
}

function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}

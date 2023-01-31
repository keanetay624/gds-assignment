import { Component, Input, Output, ViewChild } from '@angular/core';
import { EmployeeService } from '../../services/employee.service';
import { Employee } from '../../Employee';
import { Salary } from 'src/app/Salary';
import { faImage } from '@fortawesome/free-regular-svg-icons';
import { faPencilAlt } from '@fortawesome/free-solid-svg-icons';
import { faTrash } from '@fortawesome/free-solid-svg-icons';
import { MatPaginator } from '@angular/material/paginator';
import { tap } from 'rxjs';
import { Sort, MatSort } from '@angular/material/sort';

declare var window:any;

@Component({
  selector: 'app-employees-list',
  templateUrl: './employees-list.component.html',
  styleUrls: ['./employees-list.component.css']
})
export class EmployeesListComponent {
  @Input() employee:Employee = {
    id: '',
    name: '',
    login: '',
    salary: ''
  };

  @Input() employeeToSave:Employee = {
    id: '',
    name: '',
    login: '',
    salary: ''
  }

  @Output() modalTitle = "";

  @ViewChild(MatPaginator)
  paginator: MatPaginator | undefined;

  faImage = faImage;
  faPencil = faPencilAlt;
  faTrash = faTrash;
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

  formModal:any;
  confirmationModal:any;
  employeeToDelete:Employee = {
    id: '',
    name: '',
    login: '',
    salary: ''
  };


  constructor(private employeeService: EmployeeService) { }

  ngOnInit(): void {
    this.employeeService.getEmployeesByParams('0', '999999', '10', '0', "+id").subscribe(
      (employees) => {(this.employees = employees)
        console.log(this.employees)
        this.sortedData = this.employees;
      }
    );
    this.sortedData = this.employees;
    console.log()
    this.sortAsc = true;
    this.sortField = "id";
    this.sortStr = "+id";
    this.offset = '0';
    this.limit = '10';
    this.minSal = '0';
    this.maxSal = '999999';
    this.formModal = new window.bootstrap.Modal(
      document.getElementById("exampleModal")
    );
    this.confirmationModal = new window.bootstrap.Modal(
      document.getElementById("confirmationModal")
    );
  }

  ngAfterViewInit(){
    this.paginator?.page.pipe(
      tap(() => this.loadEmployeesPage())
    ).subscribe();
  }

  searchSalaryForm(salary: Salary) {
    this.minSal = salary.minSal;
    this.maxSal = salary.maxSal;

    this.employeeService.getEmployeesBySalary(this.minSal, this.maxSal, this.limit.toString(), this.offset.toString(), this.sortStr).subscribe(
      (employees) => {
        this.employees = [];
        this.employees = employees
      });
  }

  openModal(employee:Employee, action: string){
    this.modalTitle = action;
    this.formModal.show();
    this.employee = employee;
    this.employeeToSave = {...this.employee}
  }

  closeFormModal() {
    this.formModal.hide();
    this.employee = {
      id: '',
      name: '',
      login: '',
      salary: ''
    };
    this.employeeToDelete = this.employeeToSave;
    this.employeeToSave = {
      id: '',
      name: '',
      login: '',
      salary: ''
    }
  }

  closeConfirmationModal() {
    this.confirmationModal.hide();
    this.employee = {
      id: '',
      name: '',
      login: '',
      salary: ''
    };
    this.employeeToSave = {
      id: '',
      name: '',
      login: '',
      salary: ''
    }
    this.employeeToDelete = {
      id: '',
      name: '',
      login: '',
      salary: ''
    }
  }

  saveModal(modalTitle: string) {
    if (modalTitle === 'Edit Employee') {
      this.employeeService.updateEmployee(this.employeeToSave).subscribe(
        (employees) => {
          this.employees = this.employees.filter(e => e.id != employees[0].id);
          this.employees.push(employees[0]);
          this.sortedData.push(employees[0]);
        });
    } else {
      this.employeeService.addEmployee(this.employeeToSave).subscribe(
        (employees) => {
          this.employees.push(employees[0]);
          this.sortedData.push(employees[0]);
        });
    }
    this.closeFormModal();
  }

  deleteEmployeeModal() {
    this.confirmationModal.show();
    this.closeFormModal();
  }

  confirmDeleteEmployee() {
    this.employeeService.deleteEmployee(this.employeeToDelete).subscribe(
      (employees) => {
        this.employees = this.employees.filter(e => e.id != employees[0].id);
        this.sortedData = this.sortedData.filter(e => e.id != employees[0].id);
      });
    this.closeConfirmationModal();
  }

  addEmployee() {
    const employeeToCreate:Employee = {
      id: '',
      name: '',
      login: '',
      salary: ''
    }
    this.openModal(employeeToCreate, 'Add Employee');
  }

  loadEmployeesPage() {
    this.employeeService.getEmployeesBySalary(this.minSal, this.maxSal, this.paginator!.pageSize.toString(), this.paginator!.pageIndex.toString() , this.sortStr).subscribe(
      (employees) => {
        this.employees = [];
        this.employees = employees
        this.sortedData = [];
        this.sortedData = employees;
      });
  }
 
  sortData(sort: Sort) {
    const data = this.employees.slice();
    if (!sort.active || sort.direction === '') {
      this.sortedData = data;
      return;
    }

    this.sortedData = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      
      switch (sort.active) {
        case 'name':
          return compare(a.name, b.name, isAsc);
        case 'login':
          return compare(a.login, b.login, isAsc);
        case 'salary':
          return compare(a.salary, b.salary, isAsc);
        case 'id':
          return compare(a.id, b.id, isAsc);
        default:
          return 0;
      }
    });
  }
}

function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}

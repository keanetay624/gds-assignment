import { Component, Input, Output } from '@angular/core';
import { EmployeeService } from '../../services/employee.service';
import { Employee } from '../../Employee';
import { Salary } from 'src/app/Salary';
import { faImage } from '@fortawesome/free-regular-svg-icons';
import { faPencilAlt } from '@fortawesome/free-solid-svg-icons';
import { faTrash } from '@fortawesome/free-solid-svg-icons';

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
      (employees) => (this.employees = employees)
    );
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

  onSortFieldClick(sortField: string) {
    console.log("sorting on: " + sortField);
    console.log("current sortField: " + this.sortField);
    let sortStr: string;

    if (sortField === this.sortField) {
      this.sortAsc ? sortStr = "+" + sortField : sortStr = "-" + sortField;
      this.sortAsc = !this.sortAsc;
      this.sortStr = sortStr;
    } else {
      this.sortAsc ? sortStr = "-" + sortField : sortStr = "+" + sortField;
      this.sortStr = sortStr;
    }

    this.sortField = sortField;

    this.employeeService.getEmployeesByParams(this.minSal, this.maxSal, this.limit.toString(), this.offset.toString(), this.sortStr).subscribe(
      (employees) => (this.employees = employees)
    );

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

  getPreviousPage() {
    if (this.limit > this.offset) return;
    const offsetNumber = (Number)(this.offset) - (Number)(this.limit);
    this.offset = offsetNumber.toString();
    this.employeeService.getEmployeesBySalary(this.minSal, this.maxSal, this.limit.toString(), this.offset.toString(), this.sortStr).subscribe(
      (employees) => {
        this.employees = [];
        this.employees = employees
      });
  }

  getNextPage() {
    if (this.employees.length < (Number)(this.limit)) return;
    const offsetNumber = (Number)(this.offset) + (Number)(this.limit);
    this.offset = offsetNumber.toString();
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
    console.log("Saving employee...", this.employeeToSave)

    if (modalTitle === 'Edit Employee') {
      this.employeeService.updateEmployee(this.employeeToSave).subscribe(
        (employees) => {
          this.employees = this.employees.filter(e => e.id != employees[0].id);
          this.employees.push(employees[0]);
        });
    } else {
      this.employeeService.addEmployee(this.employeeToSave).subscribe(
        (employees) => {
          this.employees.push(employees[0]);
        });
    }
    this.closeFormModal();
  }

  deleteEmployeeModal() {
    this.confirmationModal.show();
    this.closeFormModal();
  }

  confirmDeleteEmployee() {
    console.log(this.employeeToDelete)
    this.employeeService.deleteEmployee(this.employeeToDelete).subscribe(
      (employees) => {
        this.employees = this.employees.filter(e => e.id != employees[0].id);
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
}

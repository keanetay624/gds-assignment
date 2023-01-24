import { Component } from '@angular/core';
import { EmployeeService } from '../../services/employee.service';
import { Employee } from '../../Employee';
import { Salary } from 'src/app/Salary';


@Component({
  selector: 'app-employees-list',
  templateUrl: './employees-list.component.html',
  styleUrls: ['./employees-list.component.css']
})
export class EmployeesListComponent {
  employees: Employee[] = [];
  sortAsc: boolean = true;
  sortField: string = "id";
  minSal: string = '0';
  maxSal: string = '999999';
  limit: string = '10';
  offset: string = '0';
  sortStr: string = "+id";

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
}

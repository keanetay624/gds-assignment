import { Component } from '@angular/core';
import { EmployeeService } from '../../services/employee.service';
import { Employee } from '../../Employee';


@Component({
  selector: 'app-employees-list',
  templateUrl: './employees-list.component.html',
  styleUrls: ['./employees-list.component.css']
})
export class EmployeesListComponent {
  employees: Employee[] = [];
  sortField: string = "id";
  sortAsc: boolean = true;

  constructor(private employeeService: EmployeeService) { }

  ngOnInit(): void {
    this.employeeService.getEmployeesByParams('0', '4000', '5', '0', "+id").subscribe(
      (employees) => (this.employees = employees)
    );
    this.sortAsc = true;
    this.sortField = "id";
  }

  onSortFieldClick(sortField: string) {
    console.log("sorting on: " + sortField);
    console.log("current sortField: " + this.sortField);
    let sortStr: string;

    if (sortField === this.sortField) {
      this.sortAsc ? sortStr = "+" + sortField : sortStr = "-" + sortField;
      this.sortAsc = !this.sortAsc;
    } else {
      this.sortAsc ? sortStr = "-" + sortField : sortStr = "+" + sortField;
    }


    this.sortField = sortField;

    this.employeeService.getEmployeesByParams('0', '4000', '5', '0', sortStr).subscribe(
      (employees) => (this.employees = employees)
    );

  }
}

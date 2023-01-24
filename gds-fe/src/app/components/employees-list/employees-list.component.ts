import { Component } from '@angular/core';
import { Employee } from '../../Employee';
import { EMPLOYEES } from '../../mock-employees';

@Component({
  selector: 'app-employees-list',
  templateUrl: './employees-list.component.html',
  styleUrls: ['./employees-list.component.css']
})
export class EmployeesListComponent {
  employees: Employee[] = EMPLOYEES
}

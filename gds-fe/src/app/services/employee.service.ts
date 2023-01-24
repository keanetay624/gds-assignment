import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Employee } from '../Employee';
import { EMPLOYEES } from '../mock-employees';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor() { }

  getEmployeesByParams(): Observable<Employee[]> {
    const employees = of(EMPLOYEES);
    return employees;
  }
}

import { Injectable } from '@angular/core';
import { Employee } from '../Employee';
import { EMPLOYEES } from '../mock-employees';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor() { }

  getEmployeesByParams() {
    return EMPLOYEES;
  }
}

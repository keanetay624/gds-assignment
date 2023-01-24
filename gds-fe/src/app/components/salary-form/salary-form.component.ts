import { Component, EventEmitter, Output } from '@angular/core';
import { EmployeeService } from '../../services/employee.service';
import { Employee } from '../../Employee';
import { Salary } from '../../Salary';

@Component({
  selector: 'app-salary-form',
  templateUrl: './salary-form.component.html',
  styleUrls: ['./salary-form.component.css']
})
export class SalaryFormComponent {
  @Output() onSearchSalaryForm: EventEmitter<Salary> = new EventEmitter;
  minSal: string = '0';
  maxSal: string = '999,999';
  employees: Employee[] = [];

  constructor(private employeeService: EmployeeService) { }

  onSubmit() {
    const newSalary = {
      minSal: this.minSal,
      maxSal: this.maxSal
    }
    this.onSearchSalaryForm.emit(newSalary)
  }

}

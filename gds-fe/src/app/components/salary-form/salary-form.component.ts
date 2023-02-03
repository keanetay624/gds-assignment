import { Component, EventEmitter, Output, ElementRef, ViewChild } from '@angular/core';
import { EmployeeService } from '../../services/employee.service';
import { Employee } from '../../Employee';
import { Salary } from '../../Salary';
import { distinctUntilChanged, merge, tap, fromEvent, debounceTime } from 'rxjs';

@Component({
  selector: 'app-salary-form',
  templateUrl: './salary-form.component.html',
  styleUrls: ['./salary-form.component.css']
})
export class SalaryFormComponent {
  @Output() onSearchSalaryForm: EventEmitter<Salary> = new EventEmitter;
  minSal: string = '0';
  maxSal: string = '50000';
  employees: Employee[] = [];
  hide = true;

  @ViewChild('minSalary') minSalary: ElementRef | undefined;
  @ViewChild('maxSalary') maxSalary: ElementRef | undefined;

  constructor(private employeeService: EmployeeService) { }

  ngAfterViewInit() {
    // @ts-ignore
    merge(fromEvent(this.minSalary.nativeElement, 'keyup'), fromEvent(this.maxSalary.nativeElement, 'keyup'))
      .pipe(
        debounceTime(150), distinctUntilChanged(),
        tap(() => {
          const sal = {
            minSal: this.minSal,
            maxSal: this.maxSal
          }
          this.onSearchSalaryForm.emit(sal)
          console.log('emitting salary')
        })
      ).subscribe()
  }
}

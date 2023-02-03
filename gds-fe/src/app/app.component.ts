import { Component } from '@angular/core';

export interface ResponseData {
  message: string
  error: string
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title: string = $localize`Employee Salary Management`;

  updateEmployeesTable(responseData: ResponseData) {
    console.log('from app component')
    console.log(responseData)
  }
}

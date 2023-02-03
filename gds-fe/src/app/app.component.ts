import { Component } from '@angular/core';
import { Subject } from 'rxjs';

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
  responseData: ResponseData = {
    message: '',
    error: ''
  };

  eventsSubject: Subject<ResponseData> = new Subject<ResponseData>();

  updateEmployeesTable(responseData: ResponseData) {
    this.responseData = responseData;
    this.emitEventToChild();
  }

  emitEventToChild() {
    this.eventsSubject.next(this.responseData);
  }

}

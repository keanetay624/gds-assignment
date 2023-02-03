import { Component, EventEmitter, Output } from '@angular/core';
import { EmployeeService } from '../../services/employee.service';

export interface ResponseData {
  message: string
  error: string
}


@Component({
  selector: 'app-toolbar',
  templateUrl: 'toolbar.component.html',
  styleUrls: ['toolbar.component.css'],
})
export class ToolbarComponent {
  @Output() onUpload: EventEmitter<ResponseData> = new EventEmitter;

  constructor(private employeeService: EmployeeService) { }
  onFileSelected(event: any) {
    console.log('file selected')
    const file = event.target.files[0];
    this.employeeService.doUploadEmployees(file).subscribe(
      (response) => {
        let responseData: ResponseData = {
          message: response.message,
          error: ''
        }
        this.onUpload.emit(responseData)
      }, (error) => {
        if (error && error.error) {
          let responseData: ResponseData = {
            message: '',
            error: error.error.message + ": " + error.error.error
          }
          this.onUpload.emit(responseData)
        }
      }
    );
  }
}

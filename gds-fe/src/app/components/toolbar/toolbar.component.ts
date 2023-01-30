import {Component} from '@angular/core';
import { EmployeeService } from '../../services/employee.service';
/**
 * @title Toolbar overview
 */
@Component({
  selector: 'app-toolbar',
  templateUrl: 'toolbar.component.html',
  styleUrls: ['toolbar.component.css'],
})
export class ToolbarComponent {
  constructor(private employeeService: EmployeeService) { }
  onFileSelected(event:any) {
    const file = event.target.files[0];
    this.employeeService.doUploadEmployees(file).subscribe();
  }
}

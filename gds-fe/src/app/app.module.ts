import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { NavMenuComponent } from './components/nav-menu/nav-menu.component';
import { EmployeesListComponent } from './components/employees-list/employees-list.component';
import { NavBtnComponent } from './components/nav-btn/nav-btn.component';
import { EmployeeComponent } from './components/employee/employee.component';
import { SalaryFormComponent } from './components/salary-form/salary-form.component';

@NgModule({
  declarations: [
    AppComponent,
    NavMenuComponent,
    EmployeesListComponent,
    NavBtnComponent,
    EmployeeComponent,
    SalaryFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FontAwesomeModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

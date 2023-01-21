import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavMenuComponent } from './components/nav-menu/nav-menu.component';
import { EmployeesListComponent } from './components/employees-list/employees-list.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NavBtnComponent } from './components/nav-btn/nav-btn.component';

@NgModule({
  declarations: [
    AppComponent,
    NavMenuComponent,
    EmployeesListComponent,
    NavBtnComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FontAwesomeModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

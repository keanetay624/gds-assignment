import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http'
import { Observable } from 'rxjs';
import { Employee } from '../Employee';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private apiUrl = 'http://localhost:8080/users'

  constructor(private http: HttpClient) {
  }

  getEmployees(minSalary: string, maxSalary: string,
    limit: string, offset: string, sort: string): Observable<any> {
    return this.http.get<any>(this.apiUrl, {
      params: new HttpParams()
        .append('minSalary', minSalary)
        .append('maxSalary', maxSalary)
        .append('limit', limit)
        .append('offset', offset)
        .append('sort', sort)
    })
      .pipe(
        map((response) => {
          // return response.results;
          return response;
        })
      )
  }

  doUploadEmployees(file: File): Observable<any> {
    let formData = new FormData();
    formData.append("reportProgress", 'true');
    formData.append('file', file)

    return this.http.post<any>(`${this.apiUrl}/upload`,
      formData, {
      headers: new HttpHeaders()
        .append('Accept', '*/*')
    })
      .pipe(
        map((response) => {
          return response;
        })
      )
  }

  updateEmployee(employee: Employee): Observable<Employee[]> {
    let params = new HttpParams()
    params = params.append("name", employee.name)
    params = params.append('login', employee.login)
    params = params.append('salary', employee.salary)

    return this.http.patch<any>(`${this.apiUrl}/${employee.id}`, params)
      .pipe(
        map((response) => {
          return response.results;
        })
      )
  }

  deleteEmployee(employee: Employee): Observable<Employee[]> {

    return this.http.delete<any>(`${this.apiUrl}/${employee.id}`)
      .pipe(
        map((response) => {
          return response.results;
        })
      )
  }

  addEmployee(employee: Employee): Observable<Employee[]> {
    let params = new HttpParams()
    params = params.append("name", employee.name)
    params = params.append('login', employee.login)
    params = params.append('salary', employee.salary)

    return this.http.post<any>(`${this.apiUrl}/${employee.id}`, params)
      .pipe(
        map((response) => {
          return response.results;
        })
      )
  }
}

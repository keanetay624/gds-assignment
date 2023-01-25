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

  getEmployeesByParams(minSalary: string, maxSalary: string,
    limit: string, offset: string, sort: string): Observable<Employee[]> {
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
          return response.results;
        })
      )
  }

  getEmployeesBySalary(minSalary: string, maxSalary: string,
    limit: string, offset: string, sort: string): Observable<Employee[]> {
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
          return response.results;
        })
      )
  }

  doUploadEmployees(file:File): Observable<Employee[]>{
    let formData = new FormData();
    formData.append("reportProgress", 'true');
    formData.append('file', file)

    return this.http.post<any>(`${this.apiUrl}/upload`,
      formData, {headers:new HttpHeaders()
      .append('Accept', '*/*')})
      .pipe(
        map((response) => {
          return response.results;
        })
      )
  }
}

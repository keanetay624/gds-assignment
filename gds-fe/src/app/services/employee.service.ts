import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http'
import { Observable, of } from 'rxjs';
import { Employee } from '../Employee';
import { EMPLOYEES } from '../mock-employees';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private apiUrl = 'http://localhost:8080/users'

  constructor(private http: HttpClient) { }

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
}

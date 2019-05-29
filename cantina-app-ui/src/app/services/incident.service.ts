import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import {tap} from 'rxjs/internal/operators';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class IncidentService {

  constructor(private http: HttpClient) { }

  createNewIncident(payload:Object){
    return this.http.post("http://www.localhost:5000/api/incident/create",payload).pipe(
      tap((res => res)),
      catchError(err => {
          return throwError(err.error.message)
      })
    );
  }
}

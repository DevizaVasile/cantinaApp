import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import {tap} from 'rxjs/internal/operators';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StaffService {

  constructor(private http: HttpClient) {

  }

  getFood(){
    return this.http.get('http://www.localhost:5000/api/food/getAll', 
    {}).pipe(
         tap( res => {}),
         catchError(err => {
            return throwError(err.error.message)})   
         );    
  }

  updateFood(payload:Object){
    return this.http.post("http://www.localhost:5000/api/food/set",payload).pipe(
      tap((res => res)),
      catchError(err => {
          return throwError(err.error.message)
      })
    );
  }
}

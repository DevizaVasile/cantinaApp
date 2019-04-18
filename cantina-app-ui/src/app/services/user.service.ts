import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {tap} from 'rxjs/internal/operators';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { 

  }

  getUserProfile(payload:String){
    return this.http.get('http://www.localhost:5000/api/auth/profile/'+payload, 
    {}).pipe(
         tap( res => {}),
         catchError(err => {
            return throwError(err.error.message)})   
         );    
  }

  getMenuForDay(payload:String){
    return this.http.get("http://localhost:5000/api/menu/getAll/"+payload,{}).pipe(
      tap( res => {}),
      catchError(err => {
         return throwError(err.error.message)})   
      ); 
  }

  addNewOrder(payload:Object){
    return this.http.post("http://localhost:5000/api/invoice/newInvoice",payload).pipe(
      tap( res => res)
    );
  }
}

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

  createNewFood(payload:Object){
    return this.http.post("http://www.localhost:5000/api/food/create",payload).pipe(
      tap((res => res)),
      catchError(err => {
          return throwError(err.error.message)
      })
    );
  }

  getFutureWorkigDays(){
    return this.http.get("http://localhost:5000/api/workingDay/getFutureWorkingDays", {}).pipe(
      tap( res => {}),
      catchError(err => {
         return throwError(err.error.message)})   
      ); 
  }

  checkIfDayAlreadyExists(payload:Object){
    return this.http.get("http://localhost:5000/api/workingDay/getExist/"+payload, {}).pipe(
      tap( res => {}),
      catchError(err => {
         return throwError(err.error.message)})   
      ); 
  }

  createNewWorkingDay(payload:Object){
    return this.http.post("http://localhost:5000/api/workingDay/create/"+payload, {}).pipe(
      tap( res => {}),
      catchError(err => {
         return throwError(err.error.message)})   
      ); 
  }

  getFoodNotInMenuForDay(payload:String){
    return this.http.get("http://localhost:5000/api/menu/getFoodNotInMenuForDay/"+payload,{}).pipe(
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

  updateMenuForTheDay(payload:MenuUpdate){
    return this.http.post("http://localhost:5000/api/menu/update/"+payload.day,{"toAdd":payload.toAdd,"toRemove":payload.toRemove}).pipe(
      tap(res => {},
        catchError(err => {
          return throwError(err.error.message)
        }))
    );
  }

  toggleDayActive(payload:String){
    return this.http.post("http://localhost:5000/api/workingDay/toggleActive/"+payload, {}).pipe(
      tap( res => {}),
      catchError(err => {
         return throwError(err.error.message)})   
      );
  }

  closeOrder(payload:Object){
    return this.http.post("http://localhost:5000/api/invoice/closeOrder",payload).pipe(
      tap( res => {}),
      catchError(err => {
         return throwError(err.error.message)}) 
    );
  }

  isOrderClosed(payload:Object){
    return this.http.post("http://localhost:5000/api/invoice/isOrderClosed",payload).pipe(
      tap( res => {}),
      catchError(err => {
         return throwError(err.error.message)}) 
    );
  }

  getAllOpenIncidents(){
    return this.http.get("http://localhost:5000/api/incident/getAllOpen").pipe(
      tap( res => {}),
      catchError(err => {
         return throwError(err.error.message)}) 
    );
  }

  updateIncidentMessage(payload:Object){
    return this.http.post("http://localhost:5000/api/incident/setResponse",payload).pipe(
      tap( res => {}),
      catchError(err => {
         return throwError(err.error.message)}) 
    );
  }

}

 interface MenuUpdate{
  day:String;
  toAdd:Array<Object>;
  toRemove:Array<Object>;
}



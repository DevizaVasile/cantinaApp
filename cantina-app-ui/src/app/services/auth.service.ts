import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import * as moment from "moment";
import {tap} from 'rxjs/internal/operators';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import {Subject,Observable} from 'rxjs'; 
@Injectable({
  providedIn: 'root'
})
export class AuthService {
    public emailValue = new Subject<any>();
    constructor(private http: HttpClient) {

    }

    login(email:string, password:string) {
        return this.http.post('http://www.localhost:5000/api/auth/signin', 
        {email, password}).pipe(
             tap(res => this.setSession(res)),
             catchError(err => {
                return throwError(err.error.message)})   
             );    
    }

    register(email:string, password:string, firstName:string, lastName:string){
        return this.http.post<any>('http://www.localhost:5000/api/auth/signup',{email,password,firstName,lastName})
        .pipe(tap(res => this.registerResponse(res)))
    }

    private registerResponse(registerResponse){
        console.log(registerResponse)
    }
          
    private setSession(authResult) {
        const expiresAt = moment().add(authResult.tokenExpiration,'milliseconds');
        localStorage.setItem('id_token', authResult.tokenExpiration);
        localStorage.setItem("expires_at", JSON.stringify(expiresAt.valueOf()));
        localStorage.setItem("email",authResult.email);
        localStorage.setItem("roles", JSON.stringify(authResult.roles));  
        
        this.setEmailValue(authResult.email)
    }          

    logout() {
        localStorage.removeItem("id_token");
        localStorage.removeItem("expires_at");
        localStorage.removeItem("email");
        localStorage.removeItem("roles");
    }

    public isLoggedIn() {
        return moment().isBefore(this.getExpiration());
    }

    isLoggedOut() {
        return !this.isLoggedIn();
    }

    getExpiration() {
        const expiration = localStorage.getItem("expires_at");
        const expiresAt = JSON.parse(expiration);
        return moment(expiresAt);
    }
    
    getToken(){
        return localStorage.getItem("id_token");
    }

    hasRole(role:String){
        if(localStorage.getItem===undefined){
            return false
        }
        const roles = JSON.parse(localStorage.getItem('roles'));
        const rolesValues = roles.map(element => {
            return element.authority;
        });
        if(rolesValues.indexOf(role) === -1){
            return false;
        }
        return true;
    }

    getsubject():Observable<any>{
        return this.emailValue.asObservable();
      }
    
    setEmailValue(value:string){
        this.emailValue.next(value)
    }
}
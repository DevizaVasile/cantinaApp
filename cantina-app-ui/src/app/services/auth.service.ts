import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as moment from "moment";
import {tap} from 'rxjs/internal/operators';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
    
    constructor(private http: HttpClient) {

    }

    login(email:string, password:string) {
        return this.http.post<any>('http://www.localhost:5000/api/auth/signin', 
        {email, password}).pipe(tap(res => this.setSession(res)))        
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
        localStorage.setItem("roles", JSON.stringify(authResult.roles.map(val => {
          return val.authority
        })));   
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
}
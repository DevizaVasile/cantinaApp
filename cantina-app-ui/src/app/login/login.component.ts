import { Component, OnInit } from '@angular/core';
import {FormGroup, FormBuilder} from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { Validators } from '@angular/forms'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form:FormGroup;
  error:boolean=false;
  errorMessage:String="";

  constructor(private fb:FormBuilder, 
               private authService: AuthService, 
               private router: Router) {

      this.form = this.fb.group({
          email: ['',Validators.required],
          password: ['',Validators.required]
      });
  }

  ngOnInit(){

  }

  login() {
      const val = this.form.value;

      if (val.email && val.password) {
          this.authService.login(val.email, val.password)
              .subscribe(
                (val) => {this.router.navigateByUrl('/');},
                (error) => {this.error=true; this.errorMessage=error}
              );
      }
  }

  logout(){
      this.authService.logout();
  }

}


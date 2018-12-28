import { Component, OnInit } from '@angular/core';
import {FormGroup, FormBuilder} from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { Validators } from '@angular/forms'

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form:FormGroup;

  constructor(private fb:FormBuilder, 
               private authService: AuthService, 
               private router: Router) {

      this.form = this.fb.group({
          email: ['',Validators.required],
          password: ['',Validators.required],
          password2: ['',Validators.required]
      });
  }

  register() {
    const val = this.form.value;

    if (val.email && val.password && val.password2 && val.password===val.password2) {
        this.authService.register(val.email, val.password, "NameA", "NameB")
            .subscribe(
                () => {console.log("subscirebe");}
            );
    }
}

  ngOnInit() {
  }

}

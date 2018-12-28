import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from './services/auth.service'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [AuthService]
})
export class AppComponent {
  title = 'cantinaApp';
  constructor(private authService : AuthService,
              private router: Router){
    
  }

  ngOnInit(){
    if(localStorage.getItem("email") === undefined || localStorage.getItem("email") === null){
        this.router.navigateByUrl("login")
    }
  }
}

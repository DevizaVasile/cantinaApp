import { Component, OnInit } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AuthService } from '../services/auth.service';
import { RoleGuardService } from '../services/role-guard.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css'],
  providers: [RoleGuardService]
})
export class NavigationComponent implements OnInit {
  isCollapsed = true;
  email:String;
  constructor(private authService:AuthService,private roleGuard:RoleGuardService, private router:Router) {
    this.authService.getsubject().subscribe(nextValue => {this.email=nextValue});
  }

  toggleMenu() {
    this.isCollapsed = !this.isCollapsed;
  }

  logout() {
    this.authService.logout();
    this.router.navigateByUrl('/')
  }

  ngOnInit() {
    this.email = localStorage.getItem("email");
  }

  debugg(){
    console.log(this.email)
  }

}

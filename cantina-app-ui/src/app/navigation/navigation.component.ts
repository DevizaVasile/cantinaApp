import { Component, OnInit } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css'],
  providers: [AuthService]
})
export class NavigationComponent implements OnInit {
  isCollapsed = true;
  email:string;
  constructor(private authService:AuthService) {}

  toggleMenu() {
    this.isCollapsed = !this.isCollapsed;
  }

  logout() {
    this.authService.logout();
  }

  ngOnInit() {
    this.email = localStorage.getItem("email");
    console.log(this.email)
  }

}

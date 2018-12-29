import { Injectable } from '@angular/core';
import { 
  Router,
  CanActivate,
  ActivatedRouteSnapshot
} from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuardService implements CanActivate {

  constructor(public auth: AuthService, public router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRole = route.data.expectedRole;
    
    if( !this.auth.isLoggedIn()){
      this.router.navigateByUrl("login")
      return false;
    }
    if( !this.auth.hasRole(expectedRole)){
      this.router.navigateByUrl("unauthorized")
      return false;
    }
    return true;
  }
}

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component'
import { RoleGuardService } from './services/role-guard.service';
import { AdminComponent} from './admin/admin.component';
import { UnauthorizedComponent } from './unauthorized/unauthorized.component';
import { StaffComponent } from './staff/staff.component'
import { UserComponent } from './user/user.component';
import { UserHistoryComponent } from './user-history/user-history.component' 

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'admin', component: AdminComponent, canActivate:[RoleGuardService], data:{expectedRole: "ROLE_ADMIN"}},
  {path: 'staff', component: StaffComponent, canActivate:[RoleGuardService], data:{expectedRole: "ROLE_STAFF"}},
  {path: 'user', component: UserComponent, canActivate:[RoleGuardService], data:{expectedRole: "ROLE_USER"}},
  {path: 'user/history', component: UserHistoryComponent, canActivate:[RoleGuardService], data:{expectedRole: "ROLE_USER"}},
  {path: 'unauthorized', component: UnauthorizedComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

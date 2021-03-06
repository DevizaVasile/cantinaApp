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
import { GenericIncidentComponent } from './generic-incident/generic-incident.component'
import { ServerErrorComponent } from './server-error/server-error.component'
import { StaffClosingComponent } from './staff-closing/staff-closing.component'
import { StaffIncidentsComponent } from './staff-incidents/staff-incidents.component'

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'admin', component: AdminComponent, canActivate:[RoleGuardService], data:{expectedRole: "ROLE_ADMIN"}},
  {path: 'staff', component: StaffComponent, canActivate:[RoleGuardService], data:{expectedRole: "ROLE_STAFF"}},
  {path: 'user', component: UserComponent, canActivate:[RoleGuardService], data:{expectedRole: "ROLE_USER"}},
  {path: 'user/history', component: UserHistoryComponent, canActivate:[RoleGuardService], data:{expectedRole: "ROLE_USER"}},
  {path: 'unauthorized', component: UnauthorizedComponent},
  {path: 'generic-incident/:day', component:GenericIncidentComponent, canActivate:[RoleGuardService], data:{expectedRole: "ROLE_USER"}},
  {path: 'error', component:ServerErrorComponent},
  {path: 'staff-closing', component:StaffClosingComponent, canActivate:[RoleGuardService], data:{expectedRole: "ROLE_STAFF"}},
  {path: 'staff-incidents', component:StaffIncidentsComponent, canActivate:[RoleGuardService], data:{expectedRole: "ROLE_STAFF"}}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

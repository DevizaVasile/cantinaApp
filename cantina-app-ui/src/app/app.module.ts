import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatTabsModule } from '@angular/material/tabs';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatInputModule} from '@angular/material';
import {MatButtonModule} from '@angular/material/button';
import {MatSnackBarModule} from "@angular/material";
import {MatSortModule} from "@angular/material"
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatListModule} from '@angular/material/list';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatDialogModule} from '@angular/material/dialog';
import {MatIconModule} from '@angular/material/icon';
import {TextFieldModule} from '@angular/cdk/text-field';


import { InterceptorService } from './services/interceptor.service';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomepageComponent } from './homepage/homepage.component';
import { NavigationComponent } from './navigation/navigation.component';
import { RegisterComponent } from './register/register.component';
import { AdminComponent } from './admin/admin.component';
import { UnauthorizedComponent } from './unauthorized/unauthorized.component';
import { StaffComponent } from './staff/staff.component';
import { UserComponent } from './user/user.component';
import { AuthService } from './services/auth.service';
import { StaffService } from './services/staff.service';
import { UserService } from './services/user.service';
import { DialogOverviewExampleDialog } from './user/user.component';
import { DialogOverviewExampleDialog2 } from './user-history/user-history.component';
import { from } from 'rxjs';
import { UserHistoryComponent } from './user-history/user-history.component';
import { GenericIncidentComponent } from './generic-incident/generic-incident.component';
import { ServerErrorComponent } from './server-error/server-error.component';
import { StaffClosingComponent } from './staff-closing/staff-closing.component';
import { StaffIncidentsComponent } from './staff-incidents/staff-incidents.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomepageComponent,
    NavigationComponent,
    RegisterComponent,
    AdminComponent,
    UnauthorizedComponent,
    StaffComponent,
    UserComponent,
    DialogOverviewExampleDialog,
    DialogOverviewExampleDialog2,
    UserHistoryComponent,
    GenericIncidentComponent,
    ServerErrorComponent,
    StaffClosingComponent,
    StaffIncidentsComponent
  ],
  imports: [
    FormsModule,
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule.forRoot(),
    MatTabsModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatTableModule,
    MatPaginatorModule,
    MatInputModule,
    MatButtonModule,
    MatSnackBarModule,
    MatSortModule,
    MatDatepickerModule,
    MatNativeDateModule,
    DragDropModule,
    MatCheckboxModule,
    MatListModule,
    MatGridListModule,
    MatDialogModule,
    MatIconModule,
    TextFieldModule
  ],
  providers: [
    {
    provide: HTTP_INTERCEPTORS,
    useClass: InterceptorService,
    multi: true
    },
    AuthService,
    StaffService,
    UserService,
    MatDatepickerModule
  ],
  bootstrap: [AppComponent],
  entryComponents:[DialogOverviewExampleDialog,DialogOverviewExampleDialog2]
})
export class AppModule { }

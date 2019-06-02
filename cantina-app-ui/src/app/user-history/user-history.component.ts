import { Component, OnInit, Inject } from '@angular/core';
import {MatTableDataSource,MatPaginator,MatSort} from '@angular/material';
import { Router } from '@angular/router';
import {MatSnackBar} from '@angular/material';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { UserService } from '../services/user.service'




@Component({
  selector: 'app-user-history',
  templateUrl: './user-history.component.html',
  styleUrls: ['./user-history.component.css'],
  providers: [UserService]
})
export class UserHistoryComponent implements OnInit {
  selectedTabIndex:Number;

  pastInvoices:Array<Object>;
  pastInvoicesDataSource = new MatTableDataSource(this.pastInvoices);
  pastInvoicesColumns = ['day', 'report'];

  viewOrder:Array<any>;
  viewOrderDate:String;
  
  constructor(private userService:UserService, private router:Router, public dialog: MatDialog) {
    this.selectedTabIndex=0;

    this.viewOrder = [];
    this.viewOrderDate = "1969";
   }

  ngOnInit() {
    this.getPastInvoice();
  }

  getPastInvoice(){
    this.pastInvoices = [];
    this.userService.getPastInvoices({"email":localStorage.getItem("email")}).subscribe( (res:Array<PastInvoices> )=>{
      res.forEach( item => {
        this.pastInvoices.push(item);
      })
      this.pastInvoicesDataSource = new MatTableDataSource(this.pastInvoices);
    })
  }

  onDayClick(item){
    this.userService.getOrderForDay(item.day,localStorage.getItem("email")).subscribe( (res:Array<any>) =>{
      this.viewOrder=res;
      this.viewOrderDate=item.day;
      this.selectedTabIndex=2;
    });
  }

  reportIncidentClick(element){
    this.router.navigateByUrl('/generic-incident/'+element.day)
  }

  watchResponse(element): void {
    this.userService.getMessage(element.day, element.email).subscribe( res =>{
      const dialogRef = this.dialog.open(DialogOverviewExampleDialog2, {
        width: '350px',
        data:{message:res}
      });
      let that = this;
      dialogRef.afterClosed().subscribe( result => {
        if(result){
          this.userService.setFeedback(element.day, element.email, 2).subscribe()
        }
        else{
          this.userService.setFeedback(element.day, element.email, 3).subscribe()
        }

        setTimeout(function(){
          that.getPastInvoice()
            }, 1500);
         });
    },
    err =>{
      
    })
    
  }

}

interface PastInvoices{
  day:string;
  email:string;
}

interface DialogData{
  message:string;
}

@Component({
  selector: 'dialog-overview-example-dialog2',
  template: `
              <p>{{data.message.response}}</p>
              <p>Are you satisfied with the response from the staff?</p>
              <button  mat-raised-button class="m-1 p-1" (click)="onYesClick()">Yes</button> 
              <button  mat-raised-button class="m-1 p-1" (click)="onNoClick()">No Thanks</button>
             `
})
export class DialogOverviewExampleDialog2 {

  constructor(
    public dialogRef: MatDialogRef<DialogOverviewExampleDialog2>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {

    }

  onNoClick(): void {
    this.dialogRef.disableClose=true;
    this.dialogRef.close(false);
  }

  onYesClick(): void {
    this.dialogRef.close(true);
  }
}

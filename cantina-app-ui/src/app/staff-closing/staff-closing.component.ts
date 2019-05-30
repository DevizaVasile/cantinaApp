import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Validators } from '@angular/forms'
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material'

import { UserService } from '../services/user.service'
import { StaffService } from '../services/staff.service'

@Component({
  selector: 'app-staff-closing',
  templateUrl: './staff-closing.component.html',
  styleUrls: ['./staff-closing.component.css'],
  providers: [StaffService, UserService]
})
export class StaffClosingComponent implements OnInit {

  searchUserForm:FormGroup;
  now:string;
  selectedTabIndex:number;
  viewOrder:Array<any>;
  orderStatusClosed: boolean;

  constructor(private userService: UserService, private fb:FormBuilder, private router:Router,public snackBar: MatSnackBar, private staffService: StaffService) {
  
    this.selectedTabIndex=0;

    this.searchUserForm = this.fb.group({
      userEmail: ['', [Validators.required, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]],
    });

    setInterval(() => {
      let date = new Date();
      this.now = date.getDate()+"-"+(date.getMonth()+1)+"-"+date.getFullYear() +"    "+date.getHours()+":"+date.getMinutes()
    }, 60);
   }

  ngOnInit() {
    this.orderStatusClosed = true;
    this.isOrderClosed()
  }

  onSearch(){
    let a=this._getNowAsString()
    let b=this.searchUserForm.getRawValue().userEmail
    this.userService.getOrderForDay(this._getNowAsString(), this.searchUserForm.getRawValue().userEmail).subscribe( (res:Array<any>) =>{
      if(res.length>0){
        this.selectedTabIndex=1;
        this.viewOrder=res;
      }
      else{
        //user not found
        this.snackBar.open("User not found","x",{duration:2000})
      }

      (err) => {
        this.snackBar.open("User not found","x",{duration:2000})
      }
      
    });
  }

  onCloseOrder(){
    this.staffService.closeOrder({userEmail: localStorage.getItem("email"), day:this._getNowAsString()}).subscribe( (res:any) =>{
      this.snackBar.open("Order closed","x",{duration:2000})
      this.isOrderClosed()
    })
  }

  isOrderClosed(){
    this.staffService.isOrderClosed({userEmail: localStorage.getItem("email"), day:this._getNowAsString()}).subscribe( (res:any) =>{
      this.orderStatusClosed = res;
    })
  }

  _getNowAsString(){
    let now = new Date();
    let month = now.getMonth()+1;
    let monthStr;
    if(month<10){
      monthStr="0"+month.toString()
    }
    else{
      monthStr=month.toString()
    }
    return now.getFullYear()+"-"+monthStr+"-"+now.getDate()
  }

}

import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Validators } from '@angular/forms'
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material'

import { UserService } from '../services/user.service'

@Component({
  selector: 'app-staff-closing',
  templateUrl: './staff-closing.component.html',
  styleUrls: ['./staff-closing.component.css']
})
export class StaffClosingComponent implements OnInit {

  searchUserForm:FormGroup;
  now:string;
  selectedTabIndex:number;
  viewOrder:Array<any>;

  constructor(private userService: UserService, private fb:FormBuilder, private router:Router,public snackBar: MatSnackBar) {
  
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
        debugger
        this.snackBar.open("User not found","x",{duration:2000})
      }
      
    });
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

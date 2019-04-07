import { Component, OnInit } from '@angular/core';
import { UserService} from "../services/user.service";
import {MatDatepickerInputEvent} from '@angular/material';
import {MatTableDataSource,MatPaginator,MatSort} from '@angular/material';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
  providers: [UserService]
})
export class UserComponent implements OnInit {

  selectedTabIndex:Number;
  balance:Number;
  fName:String;
  lName:String;

  minDate = new Date();
  maxDate = new Date();

  allFood: Array<Object> = [];
  foodList = new MatTableDataSource(this.allFood);
  displayedFoodColumns = ['name', 'weigth','price'];

  constructor(private userService:UserService) { 
    this.selectedTabIndex=0;
    this.maxDate.setMonth(this.maxDate.getMonth()+1);
    this.minDate.setDate(this.minDate.getDate()+1)
  }

  ngOnInit() {
    this.getUserProfile();
  }

  onNewOrderPress(){
    this.selectedTabIndex=1;
  }

  dayFilter = (d: Date): boolean => {
    const day = d.getDay();
    // Prevent Saturday and Sunday from being selected.
    return day !== 0 && day !== 6;
  }

  calendarSelectionChange(type: string, event: MatDatepickerInputEvent<Date>){
    let date  = event.value.getDate().toString();
    let month =  (event.value.getMonth()+1).toString();
    if(date.length === 1 ){
      date = "0"+date;
    }
    if(month.length === 1 ){
      month = "0"+month;
    }
    let query:String = event.value.getFullYear()+"-"+month+"-"+date;
    this.userService.getMenuForDay(query).subscribe( (res:FoodPayload) => {
      this.allFood=[];
      res.food.forEach(obj => {this.allFood.push(obj)})
      this.foodList = new MatTableDataSource(this.allFood);
    });
  }

  getUserProfile(){
    this.userService.getUserProfile(localStorage.getItem("email")).subscribe( (res:UserProfile) => {
      this.balance=res.balance;
      this.fName=res.firstName;
      this.lName=res.lastName;
    });
  }

}

interface UserProfile {
  balance:Number;
  firstName:String;
  lastName:String;
}

interface FoodPayload{
  day:string;
  food:Array<Object>;
}

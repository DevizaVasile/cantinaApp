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

  orderedFood:Array<Food> = [];
  orderedFoodList = new MatTableDataSource(this.orderedFood);
  orderedFoodColumns = ['name', 'price', 'quantity']

  sum:number;

  constructor(private userService:UserService) { 
    this.selectedTabIndex=0;
    this.maxDate.setMonth(this.maxDate.getMonth()+1);
    this.minDate.setDate(this.minDate.getDate()+1)   
    this.sum=0; 

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

      //clear all older selections
      this.sum=0;
      this.orderedFood=[];
      this.orderedFoodList = new MatTableDataSource(this.orderedFood);
      
    });
  }

  getUserProfile(){
    this.userService.getUserProfile(localStorage.getItem("email")).subscribe( (res:UserProfile) => {
      this.balance=res.balance;
      this.fName=res.firstName;
      this.lName=res.lastName;
    });
  }

  getRecord(oEvent){
    let result  = this.orderedFood.find( x=>x.id==oEvent.id);
    if(result == undefined && result == null){
      this.orderedFood.push({id:oEvent.id, name:oEvent.name, price:oEvent.price, quantity:1});
      this.orderedFoodList = new MatTableDataSource(this.orderedFood);
    } else {
      result.quantity=result.quantity+1;
    }
    this.sum=this.sum+oEvent.price;
    
    
  }

  deleteEntry(oEvent){
    let result  = this.orderedFood.find( x=>x.id==oEvent.id);
    if(result.quantity === 1){
      let index = this.orderedFood.indexOf(result);
      this.orderedFood.splice(index,1);
      this.orderedFoodList = new MatTableDataSource(this.orderedFood);
    }
    else{
      result.quantity=result.quantity-1;
    }
    this.sum=this.sum-result.price;
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

interface Food{
  name:String;
  price:number;
  id:number;
  quantity:number;
}

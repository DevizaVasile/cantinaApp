import { Component, OnInit } from '@angular/core';
import {MatTableDataSource,MatPaginator,MatSort} from '@angular/material';
import { Router } from '@angular/router';

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
  
  constructor(private userService:UserService, private router:Router) {
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

}

interface PastInvoices{
  day:string;
  email:string;
}

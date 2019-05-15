import { Component, OnInit } from '@angular/core';
import {MatTableDataSource,MatPaginator,MatSort} from '@angular/material';


import { UserService } from '../services/user.service'
import { debug } from 'util';


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
  pastInvoicesColumns = ['day'];

  constructor(private userService:UserService,) {
    this.selectedTabIndex=0;
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
      debugger;
      this.pastInvoicesDataSource = new MatTableDataSource(this.pastInvoices);
    })
  }

}

interface PastInvoices{
  day:string;
  email:string;
}

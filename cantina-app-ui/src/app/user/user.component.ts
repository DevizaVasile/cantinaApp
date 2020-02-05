import { Component, OnInit, Inject } from '@angular/core';
import { UserService } from "../services/user.service";
import { MatDatepickerInputEvent } from '@angular/material';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { MatSnackBar } from '@angular/material';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
  providers: [UserService]
})
export class UserComponent implements OnInit {

  selectedTabIndex: Number;
  balance: number;
  fName: String;
  lName: String;

  minDate = new Date();
  maxDate = new Date();

  allFood: Array<Object> = [];
  foodList = new MatTableDataSource(this.allFood);
  displayedFoodColumns = ['name', 'weigth', 'price'];

  orderedFood: Array<Food> = [];
  orderedFoodList = new MatTableDataSource(this.orderedFood);
  orderedFoodColumns = ['name', 'price', 'quantity']

  selectedDate: String;
  sum: number;

  futureInvoices: Array<any>;
  viewOrder: Array<any>;
  viewOrderDate: String;

  constructor(private userService: UserService, public snackBar: MatSnackBar, public dialog: MatDialog) {
    this.selectedTabIndex = 0;
    this.maxDate.setMonth(this.maxDate.getMonth() + 1);
    this.minDate.setDate(this.minDate.getDate() + 1)
    this.sum = 0;
    this.viewOrder = [];
    this.viewOrderDate = "1969";

  }

  ngOnInit() {
    this.getUserProfile();
    this.getFutureInvoice();
  }

  onNewOrderPress() {
    this.selectedTabIndex = 1;
  }

  dayFilter = (d: Date): boolean => {
    const day = d.getDay();
    // Prevent Saturday and Sunday from being selected.
    return day !== 0 && day !== 6;
  }

  calendarSelectionChange(type: string, event: MatDatepickerInputEvent<Date>) {
    let date = event.value.getDate().toString();
    let month = (event.value.getMonth() + 1).toString();
    if (date.length === 1) {
      date = "0" + date;
    }
    if (month.length === 1) {
      month = "0" + month;
    }
    let query: String = event.value.getFullYear() + "-" + month + "-" + date;
    this.selectedDate = query;
    this.userService.getMenuForDay(query).subscribe((res: FoodPayload) => {
      this.allFood = [];
      res.food.forEach(obj => { this.allFood.push(obj) })
      this.foodList = new MatTableDataSource(this.allFood);

      //clear all older selections
      this._cleanUp();

    });
  }

  getUserProfile() {
    this.userService.getUserProfile(localStorage.getItem("email")).subscribe((res: UserProfile) => {
      this.balance = res.balance;
      this.fName = res.firstName;
      this.lName = res.lastName;
    });
  }

  getRecord(oEvent) {
    let result = this.orderedFood.find(x => x.id == oEvent.id);
    if (result == undefined && result == null) {
      this.orderedFood.push({ id: oEvent.id, name: oEvent.name, price: oEvent.price, quantity: 1 });
      this.orderedFoodList = new MatTableDataSource(this.orderedFood);
    } else {
      result.quantity = result.quantity + 1;
    }
    this.sum = this.sum + oEvent.price;
  }

  deleteEntry(oEvent) {
    let result = this.orderedFood.find(x => x.id == oEvent.id);
    if (result.quantity === 1) {
      let index = this.orderedFood.indexOf(result);
      this.orderedFood.splice(index, 1);
      this.orderedFoodList = new MatTableDataSource(this.orderedFood);
    }
    else {
      result.quantity = result.quantity - 1;
    }
    this.sum = this.sum - result.price;
  }

  putNewOrder() {
    let payload = {
      "sumRON": this.sum,
      "email": localStorage.getItem("email"),
      "order": this._getFoodIdAndQuantityFromOrderedItems(this.orderedFood),
      "day": this.selectedDate
    };
    this.userService.addNewOrder(payload).subscribe(
      (response: any) => {
        if (response.success) {
          this.snackBar.open(response.message, "x", { duration: 2000 })
        }
        else {
          this.snackBar.open("Error", "x", { duration: 2000 })
        }
      },
      (error: any) => {
        this.snackBar.open(error.error.message, "x", { duration: 2000 })
      }
    )
    this._cleanUp();
  }

  _cleanUp() {
    this.balance = this.balance - this.sum;
    this.sum = 0;
    this.orderedFood = [];
    this.orderedFoodList = new MatTableDataSource(this.orderedFood);
    this.getFutureInvoice();
  }

  _getFoodIdAndQuantityFromOrderedItems(orderedFoodArray: Array<any>) {
    let order = [];
    orderedFoodArray.map(x => {
      order.push({ "foodId": x.id, "quantity": x.quantity })
    });
    return order;
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(DialogOverviewExampleDialog, {
      width: '250px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.putNewOrder();
      }
    })
  }

  getFutureInvoice() {
    this.futureInvoices = [];
    this.userService.getFutureInvoices({ "email": localStorage.getItem("email") }).subscribe((res: Array<FutureInvoices>) => {
      res.forEach(item => {
        this.futureInvoices.push(item.day);
      })
    })
  }

  onFutureDayItemClick(event, item) {
    this.userService.getOrderForDay(item, localStorage.getItem("email")).subscribe((res: Array<any>) => {
      console.log(res)
      this.viewOrder = res;
      this.viewOrderDate = item;
      this.selectedTabIndex = 2;
    });
  }

}

interface UserProfile {
  balance: number;
  firstName: String;
  lastName: String;
}

interface FoodPayload {
  day: string;
  food: Array<Object>;
}

interface FutureInvoices {
  day: string;
  email: string;
}

interface Food {
  name: String;
  price: number;
  id: number;
  quantity: number;
}

@Component({
  selector: 'dialog-overview-example-dialog',
  template: `
              <p>Are you ready to place the order?</p>
              <button  mat-raised-button class="m-1 p-1" (click)="onYesClick()">Yes</button> 
              <button  mat-raised-button class="m-1 p-1" (click)="onNoClick()">No Thanks</button>
             `
})
export class DialogOverviewExampleDialog {

  constructor(
    public dialogRef: MatDialogRef<DialogOverviewExampleDialog>) {

  }

  onNoClick(): void {
    this.dialogRef.disableClose = true;
    this.dialogRef.close(false);
  }

  onYesClick(): void {
    this.dialogRef.close(true);
  }
}
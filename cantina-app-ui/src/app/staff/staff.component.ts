import { Component, OnInit, ViewChild } from '@angular/core';
import { StaffService} from '../services/staff.service'

import {FormGroup, FormBuilder} from '@angular/forms';
import {MatTableDataSource,MatPaginator,MatSort} from '@angular/material';
import { Validators } from '@angular/forms'
import {MatSnackBar} from '@angular/material';

@Component({
  selector: 'app-staff',
  templateUrl: './staff.component.html',
  styleUrls: ['./staff.component.css'],
  providers: [StaffService]
})



export class StaffComponent implements OnInit {

  allFood: Array<Object> = [];
  displayedColumns = ['name', 'weigth','price'];
  dataSource = new MatTableDataSource(this.allFood);


  futureWorkingDays: Array<Object> = [];
  displayedWorkingDaysColumns = ['day', 'visible'];
  workingDayDataSource = new MatTableDataSource(this.futureWorkingDays);

  minDate = new Date();
  maxDate = new Date();

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatSort) sort2: MatSort;
  @ViewChild("paginator") paginator: MatPaginator;
  @ViewChild("paginator2") paginator2: MatPaginator;

  updateForm:FormGroup;
  createForm:FormGroup;

  constructor(private staffService: StaffService, private fb:FormBuilder, public snackBar: MatSnackBar) {
    this.updateForm = this.fb.group({
      name: ['',Validators.required],
      price: ['',[Validators.required,Validators.pattern(/^[.\d]+$/)]],
      id:[ {value: '', disabled:true}],
      weigth:['',[Validators.required,Validators.pattern(/^[1-9][0-9]*$/)]]
    });

    this.createForm = this.fb.group({
      name: ['',Validators.required],
      price: ['',[Validators.required,Validators.pattern(/^[.\d]+$/)]],
      weigth:['',[Validators.required,Validators.pattern(/^[1-9][0-9]*$/)]]
    });
 
    this.maxDate.setFullYear(this.minDate.getFullYear()+1);
  }

  ngOnInit() {
    this.getAllFood();
    this.getFutureWorkingDays();
    this.dataSource.sort=this.sort;
    this.dataSource.paginator=this.paginator
    this.workingDayDataSource.paginator=this.paginator2
    this.workingDayDataSource.sort=this.sort2;
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  // *****
  // TAB #1 logic
  // *****

  getAllFood(){
    this.staffService.getFood().subscribe( (res:Array<Object>) => {
      this.allFood=[];
      res.forEach(obj => {this.allFood.push(obj)})
      this.dataSource = new MatTableDataSource(this.allFood.reverse());
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
      });
  }

  getRecord(oEvent){
    this.updateForm.patchValue(oEvent)
  }

  updateFood(){
    let payload = {
      id:this.updateForm.getRawValue().id,
      visible:true,
      name:this.updateForm.value.name,
      price:this.updateForm.value.price,
      weigth:this.updateForm.value.weigth
    }
    this.staffService.updateFood(payload).subscribe( 
      (response:any) => {
        if(response.success){
          this.snackBar.open(response.message,"x",{duration:2000})
          setTimeout( ()=>{this.getAllFood()},1000)
        }
        else{
          this.snackBar.open("Error","x",{duration:2000})
        }
      },

      (error:any) =>{
        this.snackBar.open(error,"x",{duration:2000})
      }
    )
  }

  createNewFood(){
    let payload = {
      visible:true,
      name:this.createForm.value.name,
      price:this.createForm.value.price,
      weigth:this.createForm.value.weigth
    }
    this.staffService.createNewFood(payload).subscribe(
      (response:any) => {
        if(response.success){
          this.snackBar.open(response.message,"x",{duration:2000})
          this.createForm.reset()
          setTimeout( ()=>{this.getAllFood()},1000)
        }
        else{
          this.snackBar.open("Error","x",{duration:2000})
        }
      },

      (error:any) => {
        this.snackBar.open(error,"x",{duration:2000})
      }
    )
  }

  // *****
  // TAB #2 logic
  // *****

  getFutureWorkingDays(){
    const sorter =function(a,b) {
      if (a.day < b.day)
        return -1;
      if (a.day > b.day)
        return 1;
      return 0;
    }
    this.staffService.getFutureWorkigDays().subscribe( (res:Array<Object>) => {
      res.forEach(obj => {this.futureWorkingDays.push(obj)});
      this.workingDayDataSource = new MatTableDataSource(this.futureWorkingDays.sort(sorter));
      this.workingDayDataSource.sort = this.sort2;
      this.workingDayDataSource.paginator = this.paginator2;    
      });  
  }

  applyFilter2(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.workingDayDataSource.filter = filterValue;
    if (this.workingDayDataSource.paginator) {
      this.workingDayDataSource.paginator.firstPage();
    }
  }

  dayFilter = (d: Date): boolean => {
    const day = d.getDay();
    // Prevent Saturday and Sunday from being selected.
    return day !== 0 && day !== 6;
  }
  
  debugg(){
    // console.log(this.maxDate)
  }


}

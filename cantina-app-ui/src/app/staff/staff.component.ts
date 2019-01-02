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

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  updateForm:FormGroup;

  constructor(private staffService: StaffService, private fb:FormBuilder, public snackBar: MatSnackBar) {
    this.updateForm = this.fb.group({
      name: ['',Validators.required],
      price: ['',[Validators.required,Validators.pattern(/^[.\d]+$/)]],
      id:[ {value: '', disabled:true}],
      weigth:['',[Validators.required,Validators.pattern(/^[.\d]+$/)]]
  });
  }

  ngOnInit() {
    this.getAllFood();
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  getAllFood(){
    this.staffService.getFood().subscribe( (res:Array<Object>) => {
      this.allFood=[];
      res.forEach(obj => {this.allFood.push(obj)})
      this.dataSource = new MatTableDataSource(this.allFood);
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
          let that=this;
          setTimeout( ()=>{this.getAllFood()},1000)
        }
        else{
          this.snackBar.open("Error","x",{duration:2000})
          console.log(response)
        }
      },

      (error:any) =>{
        this.snackBar.open(error,"x",{duration:2000})
        console.log(typeof(error))
      }
    )
  }

}

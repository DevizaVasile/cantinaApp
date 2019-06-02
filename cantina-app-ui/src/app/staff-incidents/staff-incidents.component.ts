import { Component, OnInit } from '@angular/core';
import {MatTableDataSource,MatPaginator,MatSort} from '@angular/material';
import { Router } from '@angular/router';
import {MatSnackBar} from '@angular/material';
import { FormGroup, FormBuilder} from '@angular/forms';

import { StaffService} from '../services/staff.service'

@Component({
  selector: 'app-staff-incidents',
  templateUrl: './staff-incidents.component.html',
  styleUrls: ['./staff-incidents.component.css'],
  providers: [StaffService]
})
export class StaffIncidentsComponent implements OnInit {


  incidents:Array<Object> = [];
  incidentsDataSource = new MatTableDataSource(this.incidents);
  incidentsColumns = ['day', 'createdAt'];

  genericIncidentForm:FormGroup;

  selectedTabIndex:Number;

  currentClickedIncident:Incident

  constructor(private staffService: StaffService, private fb:FormBuilder) { 
    this.genericIncidentForm = this.fb.group({
      day:[ {value: '', disabled:true}],
      text:[ {value: '', disabled:true}],
      message:[ {value:''}]
    });
  }

  ngOnInit() {
    this.getOpenIncidents();
  }

  onDayClick(element){
    this.selectedTabIndex=1;
    this.currentClickedIncident=element;
    this.genericIncidentForm.patchValue({"text":element.text, "day":element.day, "message":""})
  }

  submitIncidentResponse(){
    let payload ={
      userId:this.currentClickedIncident.userId,
      day:this.genericIncidentForm.getRawValue().day,
      response:this.genericIncidentForm.getRawValue().message}
      this.genericIncidentForm.getRawValue().message;
      let that = this;
    this.staffService.updateIncidentMessage(payload).subscribe( resp=>{
      
      setTimeout(function(){
        that.getOpenIncidents()
          }, 1500);
        this.selectedTabIndex=0;
       });
  }

  getOpenIncidents(){
    this.staffService.getAllOpenIncidents().subscribe( (res:Array<Incident> ) =>{
      this.incidents=[];
      res.forEach( item => {
        this.incidents.push(item);
      })
      this.incidentsDataSource = new MatTableDataSource(this.incidents);
    })
  }

}

interface Incident{
  id: number,
  userId: number,
  invoiceId: number,
  foodId: number,
  text: string,
  response: string,
  status: number,
  day: string,
  createdAt: string
  

}

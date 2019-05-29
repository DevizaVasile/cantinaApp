import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormGroup, FormBuilder} from '@angular/forms';
import { Router } from '@angular/router';

import { IncidentService } from "../services/incident.service"

@Component({
  selector: 'app-generic-incident',
  templateUrl: './generic-incident.component.html',
  styleUrls: ['./generic-incident.component.css'],
  providers: [IncidentService]
})
export class GenericIncidentComponent implements OnInit {

  day:String;
  genericIncidentForm:FormGroup;

  constructor(private route: ActivatedRoute, private fb:FormBuilder, private router:Router, private incidentService:IncidentService) { 
    this.genericIncidentForm = this.fb.group({
      day:[ {value: '', disabled:true}],
      message:[ {value:''}]
    });
  }

  ngOnInit() {
    this.route.params.subscribe( params => {
      this.genericIncidentForm.patchValue({"day":params['day']})
   });
  }

  submitIncident(){
    let now = new Date();
    let payload = {
      userEmail:localStorage.getItem("email"),
      isGeneric:true,
      day:this.genericIncidentForm.getRawValue().day,
      text:this.genericIncidentForm.getRawValue().message.value,
      createdAt:now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDay()
    }

    this.incidentService.createNewIncident(payload).subscribe(
      (res:any) => {
        this.router.navigateByUrl("/user")
 
    },
     (error:any) =>{
      this.router.navigateByUrl("/error")
    }
  )}
}

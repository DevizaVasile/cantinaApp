import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-generic-incident',
  templateUrl: './generic-incident.component.html',
  styleUrls: ['./generic-incident.component.css']
})
export class GenericIncidentComponent implements OnInit {

  day:String;

  constructor(private route: ActivatedRoute) { 
    this.day="HEHE"
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.day = params['day'];
      debugger;
   });
  }

}

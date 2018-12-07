import { Component, OnInit, OnDestroy } from '@angular/core';
import { Car } from './car';
import { CarsApiService } from './cars-api.service';
import { Subscription } from 'rxjs/Subscription';
import { Router } from "@angular/router";

@Component({
  selector: 'app-diceroll',
  templateUrl: './diceroll.component.html',
  styleUrls: ['./diceroll.component.css']
})
export class DicerollComponent implements OnInit {
  carsListSubs: Subscription;
  carsList: Car[];
	

  constructor(private carsApi: CarsApiService, private router: Router) { }

  ngOnInit() {
	this.carsListSubs = this.carsApi
        .diceroll()
        .subscribe(res => {
            this.carsList = res;
        },
        console.error
     );
    }

 }
  


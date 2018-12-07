import { Component, OnInit, OnDestroy } from '@angular/core';
import { Car } from './car';
import { CarsApiService } from './cars-api.service';
import { Subscription } from 'rxjs/Subscription';
import { Router } from "@angular/router";

@Component({
  selector: 'app-cars',
  templateUrl: './cars.component.html',
  styleUrls: ['./cars.component.css']
})
export class CarsComponent implements OnInit, OnDestroy {
  carsListSubs: Subscription;
  carsList: Car[]

  constructor(private carsApi: CarsApiService, private router: Router) { }

  ngOnInit() {
     this.carsListSubs = this.carsApi
        .getCars()
        .subscribe(res => {
            this.carsList = res;
        },
        console.error
     );
  }
  ngOnDestroy() {
     this.carsListSubs.unsubscribe();
  }
  


}

import { Component, OnInit } from '@angular/core';
import { Car } from './car';

@Component({
  selector: 'app-cars',
  templateUrl: './cars.component.html',
  styleUrls: ['./cars.component.css']
})
export class CarsComponent implements OnInit {

  carOne: Car = new Car("2008 WRX", "Subaru", "Compact", "4", "23.0", "4")
  carTwo: Car = new Car("2008 WRX", "Subaru", "Compact", "4", "23.0", "4")
  carThree: Car = new Car("2008 WRX", "Subaru", "Compact", "4", "23.0", "4")
  carFour: Car = new Car("2008 WRX", "Subaru", "Compact", "4", "23.0", "4")
  carFive: Car = new Car("2008 WRX", "Subaru", "Compact", "4", "23.0", "4")
  carSix: Car = new Car("2008 WRX", "Subaru", "Compact", "4", "23.0", "4")
  carSeven: Car = new Car("2008 WRX", "Subaru", "Compact", "4", "23.0", "4")

  MockCars: Car[] = [
  this.carOne,
  this.carTwo,
  this.carThree,
  this.carFour,
  this.carFive,
  this.carSix,
  this.carSeven,
  ]

  constructor() { }

  ngOnInit() {
  }

}

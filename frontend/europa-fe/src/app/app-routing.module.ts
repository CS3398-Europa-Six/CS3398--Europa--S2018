import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { DashComponent } from './dash/dash.component';
import { CarsComponent } from './cars/cars.component';

const routes: Routes = [
  { path: '', redirectTo: '/dash', pathMatch: 'full' },
  { path: 'dash', component: DashComponent },
  { path: 'cars', component: CarsComponent },

  ]

@NgModule({
  imports: [
     CommonModule,
     RouterModule.forRoot(routes)
  ],
  exports: [
     RouterModule
  ],
  declarations: []
})
export class AppRoutingModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { CarsComponent } from './dash/cars/cars.component';
import { ChangelogComponent } from './changelog/changelog.component';
import { DashComponent } from './dash/dash.component';
import { OverviewComponent } from './dash/overview/overview.component';
import { LoginComponent } from './login/login.component';
import { SimplesearchComponent } from './simplesearch/simplesearch.component';
import { AdvancedSearchComponent } from './advanced-search/advanced-search.component';
import { AddCarComponent } from './dash/add-car/add-car.component';


const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'changelog', component: ChangelogComponent },
  { path: 'dash', component: DashComponent,
     children: [
        { path: 'overview', component: OverviewComponent },
        { path: 'vehicles', component: CarsComponent },
        { path: 'simplesearch', component: SimplesearchComponent},
	{ path: 'advancedsearch', component: AdvancedSearchComponent },
        { path: 'addcar', component: AddCarComponent },
     ]
  },
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

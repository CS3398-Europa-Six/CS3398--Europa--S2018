import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './/app-routing.module';
import { HomeComponent } from './home/home.component';
import { CarsComponent } from './dash/cars/cars.component';
import { ChangelogComponent } from './changelog/changelog.component';
import { FooterComponent } from './footer/footer.component';
import { DashComponent } from './dash/dash.component';
import { OverviewComponent } from './dash/overview/overview.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    CarsComponent,
    ChangelogComponent,
    FooterComponent,
    DashComponent,
    OverviewComponent
  ],
  imports: [
    BrowserModule,
    NgbModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
